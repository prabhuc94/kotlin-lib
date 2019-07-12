package com.wee3ventures.fontier.ui.message

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import com.wee3ventures.fontier.R


class Message(
    context: Context,
    title: String,
    subtitle: String,
    bold: Boolean,
    typeFace: Typeface,
    cancelable: Boolean
) {
    private var dialog: Dialog? = null
    private var dialogButtonOk: TextView? = null
    private var dialogButtonNo: TextView? = null
    private var title_lbl: TextView? = null
    private var subtitle_lbl: TextView? = null
    private var separator: View? = null
    private var positiveListener: MessageListener? = null
    private var negativeListener: MessageListener? = null
    private var negativeExist: Boolean = false
    private var LOG_ERROR = "Message_ERROR"

    init {
        negativeExist = false
        dialog = Dialog(context)
        dialog?.setContentView(R.layout.pop_up_dialog)
        if (dialog!!.window != null)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initViews()
        dialog?.setCancelable(cancelable)
        setTitle(title)
        setSubtitle(subtitle)
        setBoldPositiveLabel(bold)
        setTypefaces(typeFace)
        initEvents()
    }

    fun setPositive(okLabel: String, listener: MessageListener) {
        this.positiveListener = listener
        this.dismiss()
        setPositiveLabel(okLabel)
    }

    fun setNegative(koLabel: String, listener: MessageListener?) {
        if (listener != null) {
            this.negativeListener = listener
            this.dismiss()
            negativeExist = true
            setNegativeLabel(koLabel)
        }
    }

    fun show() {
        if (!negativeExist) {
            dialogButtonNo?.visibility = View.GONE
            separator?.visibility = View.GONE
        }
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    fun setTitle(title: String) {
        title_lbl?.text = title
    }

    fun setSubtitle(subtitle: String) {
        subtitle_lbl?.text = subtitle
    }

    private fun setPositiveLabel(positive: String) {
        dialogButtonOk?.text = positive
    }

    private fun setNegativeLabel(negative: String) {
        dialogButtonNo?.text = negative
    }

    private fun setBoldPositiveLabel(bold: Boolean) {
        if (bold)
            dialogButtonOk?.setTypeface(null, Typeface.BOLD)
        else
            dialogButtonOk?.setTypeface(null, Typeface.NORMAL)
    }

    private fun setTypefaces(appleFont: Typeface?) {
        if (appleFont != null) {
            title_lbl?.typeface = appleFont
            subtitle_lbl?.typeface = appleFont
            dialogButtonOk?.typeface = appleFont
            dialogButtonNo?.typeface = appleFont
        }
    }


    private fun initViews() {
        title_lbl = dialog?.findViewById(R.id.title)
        subtitle_lbl = dialog?.findViewById(R.id.subtitle)
        dialogButtonOk = dialog?.findViewById(R.id.dialogButtonOK)
        dialogButtonNo = dialog?.findViewById(R.id.dialogButtonNO)
        separator = dialog?.findViewById(R.id.separator)
    }

    private fun initEvents() {
        dialogButtonOk?.setOnClickListener {
            if (positiveListener != null) {
                positiveListener?.onClick(this@Message)
            }
        }
        dialogButtonNo?.setOnClickListener {
            if (negativeListener != null) {
                negativeListener?.onClick(this@Message)
            }
        }
    }
}