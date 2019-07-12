package com.wee3ventures.fontier.ui.toaster

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.wee3ventures.fontier.Enumaration.Fonts

class Toaster(val message : String) {
    fun message(mContext: Context, mSize : Float? = 16f, mFonts : Fonts? = Fonts.POPPINS_REGULAR) : Toast{
        return Toast.makeText(mContext.applicationContext, message, Toast.LENGTH_LONG).also {
            val toastLayout = it.view as LinearLayout
            val toastTV = toastLayout.getChildAt(0) as TextView
            toastTV.textSize = mSize!!
            toastTV.typeface = com.wee3ventures.fontier.utils.Fonts.getFontFace(mContext.applicationContext, mFonts!!)
        }
    }
}