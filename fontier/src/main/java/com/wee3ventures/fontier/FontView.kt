package com.wee3ventures.fontier

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.wee3ventures.fontier.Enumaration.Fonts

class FontView(context: Context, attributeSet: AttributeSet) : androidx.appcompat.widget.AppCompatTextView(context, attributeSet) {

    init {
        initView(context = context, attributeSet = attributeSet)
    }

    private fun initView(context: Context,attributeSet: AttributeSet){
        val a = context.obtainStyledAttributes(attributeSet,R.styleable.FontView)
        setFont(a.getInteger(R.styleable.FontView_fontName,EditorInfo.TYPE_NULL))
        a.recycle()
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

    fun setFont(fontName : String){
        this.typeface = Typeface.createFromAsset(this.resources.assets,fontName)
    }

    fun setFont(fontName: Typeface){
        this.typeface = fontName
    }

    fun setFont(fontName: Fonts){
        this.typeface = when (fontName){
            Fonts.DAYROM_REGULAR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.dayrom)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/dayrom.ttf")
                }
            }
            Fonts.DAYROM_X -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.dayromx)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/dayromx.ttf")
                }
            }
            Fonts.POPPINS_REGULAR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.poppins_regular)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/poppins_regular.ttf")
                }
            }
            Fonts.POPPINS_BOLD -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.poppins_bold)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/poppins_bold.ttf")
                }
            }
            Fonts.POPPINS_MEDIUM -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.poppins_medium)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/poppins_medium.ttf")
                }
            }
            Fonts.POPPINS_LIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.poppins_light)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/poppins_light.ttf")
                }
            }
            Fonts.POPPINS_EXTRALIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.poppins_extralight)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/poppins_extralight.ttf")
                }
            }
            Fonts.MERRIEWEATHER_REGULAR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.merriweather_regular)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/merriweather_regular.ttf")
                }
            }
            Fonts.MERRIEWEATHER_BOLD -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.merriweather_bold)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/merriweather_bold.ttf")
                }
            }
            Fonts.MERRIEWEATHER_LIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.merriweather_light)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/merriweather_light.ttf")
                }
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.resources.getFont(R.font.poppins_regular)
                } else {
                    Typeface.createFromAsset(this.resources.assets,"fonts/poppins_regular.ttf")
                }
            }
        }
    }
}