package com.wee3ventures.fontier

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.wee3ventures.fontier.Enumaration.Fonts
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
        a?.getBoolean(R.styleable.FontCard_is_aadhar,false)?.let { isAadhar(it) }
        a?.getBoolean(R.styleable.FontCard_isHint,false)?.let { isHint(it) }
        a?.getBoolean(R.styleable.FontCard_isAadhar,false)?.let { isAadhar(it) }
        a?.getString(R.styleable.FontCard_android_hint)?.let { sethint(it) }
        a?.getString(R.styleable.FontCard_android_text)?.let { setText(it) }
        a?.getColor(R.styleable.FontCard_textColour,R.color.grey_800)?.let { setTextColor(it) }
        a?.getColor(R.styleable.FontCard_hintColour,R.color.grey_500)?.let { setHintColor(it) }
        a?.getInteger(R.styleable.FontCard_fontype,EditorInfo.TYPE_NULL)?.let { setFont(it) }
        a?.getDimensionPixelSize(R.styleable.FontCard_android_textSize,16)?.let { setTextSize(convertPixelsToDp(it.toFloat(),context)) }
        a?.getDimensionPixelSize(R.styleable.FontCard_hintSize,13)?.let { setHintSize(convertPixelsToDp(it.toFloat(),context)) }
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
            hintlabel?.text = hint
        }
    }

    fun setText(input : String){
        if (textlabel != null){
            textlabel?.text = input
        }
    }

    fun setHintColor(colorId : Int){
        if (hintlabel != null){
            hintlabel?.setTextColor(resources.getColor(colorId))
        }
    }

    fun setTextColor(colorId : Int){
        if (textlabel != null){
            textlabel?.setTextColor(resources.getColor(colorId))
        }
    }

    fun setTextSize(size : Float){
        if (textlabel != null)
            textlabel?.textSize = size
    }

    fun setHintSize(size: Float){
        if (hintlabel != null)
            hintlabel?.setTextSize(TypedValue.COMPLEX_UNIT_SP,size.toFloat())
    }

    fun setFont(fontName: Int){
        when (fontName){
            1 -> setFont(Fonts.POPPINS_REGULAR)
            2 -> setFont(Fonts.POPPINS_LIGHT)
            3 -> setFont(Fonts.POPPINS_EXTRALIGHT)
            4 -> setFont(Fonts.POPPINS_BOLD)
            5 -> setFont(Fonts.POPPINS_MEDIUM)
            6 -> setFont(Fonts.MERRIEWEATHER_REGULAR)
            7 -> setFont(Fonts.MERRIEWEATHER_BOLD)
            8 -> setFont(Fonts.MERRIEWEATHER_LIGHT)
            9 -> setFont(Fonts.DAYROM_REGULAR)
            10 -> setFont(Fonts.DAYROM_X)
            else -> { RuntimeException("Invalid Font / Path") }
        }
    }

    private fun setFont(fontName: Fonts){
        com.wee3ventures.fontier.utils.Fonts.setFont(views = listOf(hintlabel!!,textlabel!!),fontName = fontName)
    }

    fun setFont(fontName: Fonts ?= null, fontPath : String ?= null){
        com.wee3ventures.fontier.utils.Fonts.setFont(views = listOf(hintlabel!!,textlabel!!),fontName = fontName,fontPath = fontPath)
    }

    fun setHintFont(fontName: Fonts){
        hintlabel?.setFont(fontName)
    }

    fun setHintFont(fontName: Int){
        hintlabel?.setFont(fontName)
    }

    fun setHintFont(fontName: Typeface){
        hintlabel?.setFont(fontName)
    }

    fun setHintFont(fontName: String){
        textlabel?.setFont(fontName)
    }


    fun setInputFont(fontName: Fonts){
        textlabel?.setFont(fontName)
    }

    fun setInputFont(fontName: Int){
        textlabel?.setFont(fontName)
    }

    fun setInputFont(fontName: Typeface){
        textlabel?.setFont(fontName)
    }

    fun setInputFont(fontName: String){
        textlabel?.setFont(fontName)
    }

    val hint : Any? =  if (hintlabel != null) hintlabel?.text else null

    val text : Any ?= if (textlabel != null) textlabel?.text else null

    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }


}