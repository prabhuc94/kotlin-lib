@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.wee3ventures.fontier.ui.toaster


import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.wee3ventures.fontier.Enumaration.Fonts

class Toaster(val message: String) {
    fun message(mContext: Context, mSize : Float? = 16f, mFonts : Fonts? = Fonts.POPPINS_REGULAR) : Toast{
        return Toast.makeText(mContext.applicationContext, message, Toast.LENGTH_LONG).also {
            val toastLayout = it.view as LinearLayout
            val toastTV = toastLayout.getChildAt(0) as TextView
            toastTV.textSize = mSize!!
            toastTV.typeface = com.wee3ventures.fontier.utils.Fonts.getFontFace(mContext.applicationContext, mFonts!!)
        }
    }

    fun message(mContext: Context, mSize : Float? = 16f, mFonts : Fonts? = Fonts.POPPINS_REGULAR, duration : Int ?= Toast.LENGTH_SHORT){
        val toast = Toast.makeText(mContext.applicationContext, message, Toast.LENGTH_LONG).also {
            val toastLayout = it.view as LinearLayout
            val toastTV = toastLayout.getChildAt(0) as TextView
            toastTV.textSize = mSize!!
            toastTV.typeface = com.wee3ventures.fontier.utils.Fonts.getFontFace(mContext.applicationContext, mFonts!!)
        }
        toast.duration = duration!!
        toast.show()
    }

    fun snackbar(mContext: Context, textSize : Float ?= 16f, maxLines : Int ?= 3,mFonts : Fonts? = Fonts.POPPINS_REGULAR, duration: Int? = Snackbar.LENGTH_LONG){
        val snackbar = duration?.let { Snackbar.make(View(mContext.applicationContext),message,it) }

        ( snackbar?.view?.findViewById(com.google.android.material.R.id.snackbar_text) as TextView ).apply {
            if (textSize != null) {
                this.textSize = textSize
            }

            if (maxLines != null) {
                this.maxLines = maxLines
            }
        }
        snackbar.changeFont(mFonts)
        snackbar.show()
    }

    fun Snackbar.changeFont(mFonts : Fonts? = Fonts.POPPINS_REGULAR)
    {
        val tv = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        val font = mFonts?.let { com.wee3ventures.fontier.utils.Fonts.getFontFace(context, it) }
        tv.typeface = font
    }
}

object Message{

    fun toast(mContext: Context, message: String, mSize : Float? = 16f,mFonts : Fonts? = Fonts.POPPINS_REGULAR, duration: Int? = Toast.LENGTH_LONG){
        val toast = Toast.makeText(mContext.applicationContext, message, Toast.LENGTH_LONG).also {
            val toastLayout = it.view as LinearLayout
            val toastTV = toastLayout.getChildAt(0) as TextView
            toastTV.textSize = mSize!!
            toastTV.typeface = com.wee3ventures.fontier.utils.Fonts.getFontFace(mContext.applicationContext, mFonts!!)
        }
        toast.duration = duration!!
        toast.show()
    }

    fun snackbar(mContext: Context, message: String,textSize : Float ?= 16f, maxLines : Int ?= 3,mFonts : Fonts? = Fonts.POPPINS_REGULAR, duration: Int? = Snackbar.LENGTH_LONG){
        val snackbar = duration?.let { Snackbar.make( (mContext as Activity).findViewById(android.R.id.content) , message, it) }
        ( snackbar?.view?.findViewById(com.google.android.material.R.id.snackbar_text) as TextView ).apply {
            this.textSize = textSize!!
            this.maxLines = maxLines!!
        }
        snackbar.changeFont(mFonts)
        snackbar.show()
    }

    private fun Snackbar.changeFont(mFonts : Fonts? = Fonts.POPPINS_REGULAR)
    {
        val tv = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        val font = mFonts?.let { com.wee3ventures.fontier.utils.Fonts.getFontFace(context, it) }
        tv.typeface = font
    }
}