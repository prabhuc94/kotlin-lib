package com.wee3ventures.fontier.builder

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Parcel
import com.wee3ventures.fontier.builder.listener.OnErrorListener
import com.wee3ventures.fontier.builder.listener.OnMultiSelectedListener
import com.wee3ventures.fontier.builder.listener.OnSelectedListener
import com.wee3ventures.fontier.builder.type.SelectType
import io.reactivex.Single
import io.reactivex.SingleEmitter
import java.lang.ref.WeakReference


class TedRxImagePicker {
    companion object {
        @JvmStatic
        fun with(context: Context) = Builder(WeakReference(context))
    }

    @SuppressLint("ParcelCreator")
    class Builder(private val contextWeakReference: WeakReference<Context>) :
        TedImagePickerBaseBuilder<Builder>() {
        override fun writeToParcel(dest: Parcel?, flags: Int) {
        }

        override fun describeContents(): Int {
            return 0
        }

        fun start(): Single<Uri> =
            Single.create { emitter ->
                this.onSelectedListener = object : OnSelectedListener {
                    override fun onSelected(uri: Uri) {
                        emitter.onSuccess(uri)
                    }
                }
                start(SelectType.SINGLE, emitter)
            }


        fun startMultiImage(): Single<List<Uri>> =
            Single.create { emitter ->
                this.onMultiSelectedListener = object : OnMultiSelectedListener {
                    override fun onSelected(uriList: List<Uri>) {
                        emitter.onSuccess(uriList)
                    }
                }
                start(SelectType.MULTI, emitter)
            }

        private fun start(selectType: SelectType, emitter: SingleEmitter<*>) {
            this.onErrorListener = object : OnErrorListener {
                override fun onError(message: String) {
                    emitter.onError(RuntimeException(message))
                }
            }
            this.selectType = selectType
            contextWeakReference.get()?.let {
                startInternal(it)
            }

        }
    }


}


