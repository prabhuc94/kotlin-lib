package com.wee3ventures.fontier.utils

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import androidx.annotation.Nullable
import com.wee3ventures.fontier.Enumaration.Fonts
import com.wee3ventures.fontier.FontView
import com.wee3ventures.fontier.R

object Fonts {
    fun getFontFace(mContext : Context,fontName : Fonts) : Typeface{
        return when (fontName){
            Fonts.DAYROM_REGULAR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.dayrom)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/dayrom.ttf")
                }
            }
            Fonts.DAYROM_X -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.dayromx)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/dayromx.ttf")
                }
            }
            Fonts.POPPINS_REGULAR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.poppins_regular)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/poppins_regular.ttf")
                }
            }
            Fonts.POPPINS_BOLD -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.poppins_bold)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/poppins_bold.ttf")
                }
            }
            Fonts.POPPINS_MEDIUM -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.poppins_medium)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/poppins_medium.ttf")
                }
            }
            Fonts.POPPINS_LIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.poppins_light)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/poppins_light.ttf")
                }
            }
            Fonts.POPPINS_EXTRALIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.poppins_extralight)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/poppins_extralight.ttf")
                }
            }
            Fonts.MERRIEWEATHER_REGULAR -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.merriweather_regular)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/merriweather_regular.ttf")
                }
            }
            Fonts.MERRIEWEATHER_BOLD -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.merriweather_bold)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/merriweather_bold.ttf")
                }
            }
            Fonts.MERRIEWEATHER_LIGHT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.resources.getFont(R.font.merriweather_light)
                } else {
                    Typeface.createFromAsset(mContext.resources.assets,"fonts/merriweather_light.ttf")
                }
            }
            else -> throw RuntimeException("Given FontName is Invalid")
        }
    }

    fun getFontFace(mContext : Context,fontPath : String) : Typeface{
        return Typeface.createFromAsset(mContext.resources.assets,fontPath)
    }

    fun setFont(@Nullable view : FontView ?= null, @Nullable views : List<FontView> ?= null, @Nullable fontPath: String?= null, @Nullable fontName : Fonts?= Fonts.POPPINS_MEDIUM){
        when {
            view != null -> {
                when {
                    fontPath != null && fontPath.isNullOrBlank().not() -> view.setFont(fontName = fontPath)
                    fontName != null -> view.setFont(fontName = fontName)
                }
            }

            views != null && views.isNullOrEmpty().not() -> {
                when {
                    fontPath != null && fontPath.isNullOrBlank().not() -> {
                        views.forEach { it.setFont(fontName = fontPath) }
                    }

                    fontName != null -> views.forEach { it.setFont(fontName = fontName) }
                }
            }
        }
    }
}