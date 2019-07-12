package com.wee3ventures.fontier.ui.message

import android.content.Context
import android.graphics.Typeface



class MessageBuilder(mContext: Context) {
    private var tf : Typeface? = null
    private var bold: Boolean = false
    private var cancelable: Boolean = false
    private var title: String? = null
    private var subtitle: String? = null
    private var okLabel: String? = null
    private var koLabel: String? = null
    private var context: Context? = mContext
    private var positiveListener: MessageListener? = null
    private var negativeListener: MessageListener? = null

    fun setTitle(title: String): MessageBuilder {
        this.title = title
        return this
    }

    fun setSubtitle(subtitle: String): MessageBuilder {
        this.subtitle = subtitle
        return this
    }

    fun setBoldPositiveLabel(bold: Boolean): MessageBuilder {
        this.bold = bold
        return this
    }

    fun setFont(font: Typeface): MessageBuilder {
        this.tf = font
        return this
    }

    fun setCancelable(cancelable: Boolean): MessageBuilder {
        this.cancelable = cancelable
        return this
    }

    fun setNegativeListener(koLabel: String, listener: MessageListener): MessageBuilder {
        this.negativeListener = listener
        this.koLabel = koLabel
        return this
    }

    fun setPositiveListener(okLabel: String, listener: MessageListener): MessageBuilder {
        this.positiveListener = listener
        this.okLabel = okLabel
        return this
    }

    fun build(): Message {
        val dialog = Message(context!!, title!!, subtitle!!, bold, tf!!, cancelable)
        if (negativeListener != null) {
            dialog.setNegative("$koLabel", negativeListener)
        }
        if (positiveListener != null) {
            dialog.setPositive("$okLabel", positiveListener!!)
        }
        return dialog
    }
    
}