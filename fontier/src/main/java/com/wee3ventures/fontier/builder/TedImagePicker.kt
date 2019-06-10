package com.wee3ventures.fontier.builder

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Parcel
import com.wee3ventures.fontier.builder.listener.OnErrorListener
import com.wee3ventures.fontier.builder.listener.OnMultiSelectedListener
import com.wee3ventures.fontier.builder.listener.OnSelectedListener
import com.wee3ventures.fontier.builder.type.SelectType
import java.lang.ref.WeakReference


class TedImagePicker {
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


        fun errorListener(onErrorListener: OnErrorListener): Builder {
            this.onErrorListener = onErrorListener
            return this
        }

        fun errorListener(action: (String) -> Unit): Builder {
            this.onErrorListener = object : OnErrorListener {
                override fun onError(message: String) {
                    action(message)
                }
            }
            return this
        }


        fun start(onSelectedListener: OnSelectedListener) {
            this.onSelectedListener = onSelectedListener
            selectType = SelectType.SINGLE
            contextWeakReference.get()?.let {
                startInternal(it)
            }

        }

        fun start(action: (Uri) -> Unit) {
            start(object : OnSelectedListener {
                override fun onSelected(uri: Uri) {
                    action(uri)
                }
            })
        }

        fun startMultiImage(action: (List<Uri>) -> Unit) {
            startMultiImage(object : OnMultiSelectedListener {
                override fun onSelected(uriList: List<Uri>) {
                    action(uriList)
                }
            })
        }

        fun startMultiImage(onMultiSelectedListener: OnMultiSelectedListener) {
            this.onMultiSelectedListener = onMultiSelectedListener
            selectType = SelectType.MULTI
            contextWeakReference.get()?.let {
                startInternal(it)
            }
        }

    }


}


