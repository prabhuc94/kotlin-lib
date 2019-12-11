package com.wee3ventures.fontier.ocr_reader

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.snackbar.Snackbar
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.ocr_reader.Ocr.OcrDetectorProcessor
import com.wee3ventures.fontier.ocr_reader.Ocr.OcrGraphic
import com.wee3ventures.fontier.ocr_reader.ui.CameraSource
import com.wee3ventures.fontier.ocr_reader.ui.CameraSourcePreview
import com.wee3ventures.fontier.ocr_reader.ui.GraphicOverlay
import java.io.IOException

open class OcrCaptureActivity : AppCompatActivity() {
    val TAG = "OcrCaptureActivity"

    // Intent request code to handle updating play services if needed.
    val RC_HANDLE_GMS = 9001

    // Permission request codes need to be < 256
    val RC_HANDLE_CAMERA_PERM = 2

    // Constants used to pass extra data in the intent
    companion object{
        val AutoFocus = "AutoFocus"
        val UseFlash = "UseFlash"
    }
    val TextBlockObject = "String"

    var mCameraSource: CameraSource? = null
    var mPreview: CameraSourcePreview? = null
    var mGraphicOverlay: GraphicOverlay<OcrGraphic>? = null

    // Helper objects for detecting taps and pinches.
    var scaleGestureDetector: ScaleGestureDetector? = null
    var gestureDetector: GestureDetector? = null

    /**
     * Initializes the UI and creates the detector pipeline.
     */
    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.ocr_capture)
        mPreview = findViewById(R.id.preview) as CameraSourcePreview?
        mGraphicOverlay = findViewById(R.id.graphicOverlay) as GraphicOverlay<OcrGraphic>?
        // read parameters from the intent used to launch the activity.
        val autoFocus: Boolean = getIntent().getBooleanExtra(AutoFocus, false)
        val useFlash: Boolean = getIntent().getBooleanExtra(UseFlash, false)
        // Check for the camera permission before accessing the camera.  If the
// permission is not granted yet, request permission.
        val rc: Int = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash)
        } else {
            requestCameraPermission()
        }
        gestureDetector = GestureDetector(this, CaptureGestureListener())
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        Snackbar.make(mGraphicOverlay?.rootView!!, "Tap to capture. Pinch/Stretch to zoom", Snackbar.LENGTH_LONG).show()
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    open fun requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission")
        val permissions =
            arrayOf(Manifest.permission.CAMERA)
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
            return
        }
        val thisActivity: Activity = this
        val listener =
            View.OnClickListener {
                ActivityCompat.requestPermissions(
                    thisActivity, permissions,
                    RC_HANDLE_CAMERA_PERM
                )
            }
        Snackbar.make(
            mGraphicOverlay?.rootView!!, R.string.permission_camera_rationale,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.ok, listener)
            .show()
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        val b = scaleGestureDetector!!.onTouchEvent(e)
        val c = gestureDetector!!.onTouchEvent(e)
        return b || c || super.onTouchEvent(e)
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the ocr detector to detect small text samples
     * at long distances.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    open fun createCameraSource(
        autoFocus: Boolean,
        useFlash: Boolean
    ) {
        val context: Context = getApplicationContext()
        // A text recognizer is created to find text.  An associated processor instance
// is set to receive the text recognition results and display graphics for each text block
// on screen.
        val textRecognizer = TextRecognizer.Builder(context).build()
        textRecognizer.setProcessor(OcrDetectorProcessor(mGraphicOverlay))
        if (!textRecognizer.isOperational) { // Note: The first time that an app using a Vision API is installed on a
// device, GMS will download a native libraries to the device in order to do detection.
// Usually this completes before the app is run for the first time.  But if that
// download has not yet completed, then the above call will not detect any text,
// barcodes, or faces.
//
// isOperational() can be used to check if the required native libraries are currently
// available.  The detectors will automatically become operational once the library
// downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.")
            // Check for low storage.  If there is low storage, the native library will not be
// downloaded, so detection will not become operational.
            val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null
            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show()
                Log.w(TAG, getString(R.string.low_storage_error))
            }
        }
        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
// to other detection examples to enable the text recognizer to detect small pieces of text.
        mCameraSource = CameraSource.Builder(applicationContext, textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1280, 1024)
            .setRequestedFps(2.0f)
            .setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_TORCH else null)
            .setFocusMode(if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null)
            .build()
    }

    /**
     * Restarts the camera.
     */
    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    /**
     * Stops the camera.
     */
    override fun onPause() {
        super.onPause()
        if (mPreview != null) {
            mPreview?.stop()
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (mPreview != null) {
            mPreview?.release()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: $requestCode")
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.size != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source")
            // We have permission, so create the camerasource
            val autoFocus: Boolean = getIntent().getBooleanExtra(AutoFocus, false)
            val useFlash: Boolean = getIntent().getBooleanExtra(UseFlash, false)
            createCameraSource(autoFocus, useFlash)
            return
        }
        Log.e(
            TAG, "Permission not granted: results len = " + grantResults.size +
                    " Result code = " + if (grantResults.size > 0) grantResults[0] else "(empty)"
        )
        val listener =
            DialogInterface.OnClickListener { dialog, id -> finish() }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Multitracker sample")
            .setMessage(R.string.no_camera_permission)
            .setPositiveButton(R.string.ok, listener)
            .show()
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    @Throws(SecurityException::class)
    open fun startCameraSource() { // Check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
            getApplicationContext()
        )
        if (code != ConnectionResult.SUCCESS) {
            val dlg =
                GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }
        if (mCameraSource != null) {
            try {
                mPreview?.start(mCameraSource, mGraphicOverlay!!)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                mCameraSource?.release()
                mCameraSource = null
            }
        }
    }

    /**
     * onTap is called to capture the first TextBlock under the tap location and return it to
     * the Initializing Activity.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the activity is ending.
     */
    fun onTap(rawX: Float, rawY: Float): Boolean {
        val graphic: OcrGraphic = mGraphicOverlay?.getGraphicAtLocation(rawX, rawY)!!
        var text: TextBlock? = null
        if (graphic != null) {
            text = graphic.getTextBlock()
            if (text != null && text.value != null) {
                val data = Intent()
                data.putExtra(TextBlockObject, text.value)
                setResult(CommonStatusCodes.SUCCESS, data)
                finish()
            } else {
                Log.d(TAG, "text data is null")
            }
        } else {
            Log.d(TAG, "no text detected")
        }
        return text != null
    }

   inner class CaptureGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return onTap(e.rawX, e.rawY) || super.onSingleTapConfirmed(e)
        }
    }

    inner class ScaleListener : OnScaleGestureListener {
        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         * retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            return false
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         * retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         *
         *
         * Once a scale has ended, [ScaleGestureDetector.getFocusX]
         * and [ScaleGestureDetector.getFocusY] will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         * retrieve extended info about event state.
         */
        override fun onScaleEnd(detector: ScaleGestureDetector) {
            mCameraSource?.doZoom(detector.scaleFactor)
        }
    }
}
