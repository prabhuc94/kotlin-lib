package com.wee3ventures.fontier.ocr_reader.Ocr

import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.text.TextBlock
import com.wee3ventures.fontier.ocr_reader.ui.GraphicOverlay

class OcrDetectorProcessor internal constructor(ocrGraphicOverlay: GraphicOverlay<OcrGraphic>?) :
    Detector.Processor<TextBlock?> {
    private val mGraphicOverlay: GraphicOverlay<OcrGraphic>? = ocrGraphicOverlay
    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    override fun receiveDetections(detections: Detections<TextBlock?>) {
        mGraphicOverlay?.clear()
        val items = detections.detectedItems
        for (i in 0 until items.size()) {
            val item = items.valueAt(i)
            val graphic = OcrGraphic(mGraphicOverlay!!, item)
            mGraphicOverlay.add(graphic)
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    override fun release() {
        mGraphicOverlay?.clear()
    }

}
