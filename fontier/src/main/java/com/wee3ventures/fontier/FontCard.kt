package com.wee3ventures.fontier

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.wee3ventures.fontier.utils.Utility

class FontCard : LinearLayout {
    private var hintlabel : FontView ?= null
    private var textlabel : FontView ?= null

    constructor(context: Context?) : super(context) { initView(context,null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { initView(context, attrs) }

    @SuppressLint("Recycle", "ResourceAsColor")
    private fun initView(context: Context?, attrs: AttributeSet?){
        val a = context?.obtainStyledAttributes(attrs,R.styleable.FontCard)
        val view = (context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.layout_card_font,this,true)
        hintlabel = view.findViewById(R.id.hintlabel)
        textlabel = view.findViewById(R.id.inputlabel)
        a?.getBoolean(R.styleable.FontCard_hint_needed,false)?.let { isHint(it) }
        a?.getString(R.styleable.FontCard_android_hint)?.let { sethint(it) }
        a?.getString(R.styleable.FontCard_android_text)?.let { setText(it) }
        a?.getColor(R.styleable.FontCard_text_color,R.color.grey_800)?.let { setTextColor(it) }
        a?.getColor(R.styleable.FontCard_hint_color,R.color.grey_800)?.let { setHintColor(it) }
        a?.getBoolean(R.styleable.FontCard_is_aadhar,false)?.let { isAadhar(it) }
        a?.recycle()
    }

    fun isAadhar(status: Boolean){
        if (status){
            val input = Utility.addPadding(" ","${textlabel?.text}",4)
            textlabel?.text = "$input"
        }
    }

    fun isHint(status: Boolean){
        if (hintlabel != null){
            when(status){
                true -> hintlabel?.visibility = View.VISIBLE
                else -> hintlabel?.visibility = View.GONE
            }
        }
    }

    fun sethint(hint : String){
        if (hintlabel != null){
            hintlabel?.hint = hint
        }
    }

    fun setText(input : String){
        if (textlabel != null){
            textlabel?.text = input
        }
    }

    fun setHintColor(colorId : Int){
        if (hintlabel != null){
            hintlabel?.setHintTextColor(resources.getColor(colorId))
        }
    }

    fun setTextColor(colorId : Int){
        if (textlabel != null){
            textlabel?.setTextColor(resources.getColor(colorId))
        }
    }


}