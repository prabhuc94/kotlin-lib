package com.wee3ventures.fontier.utils

import android.content.res.ColorStateList
import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.button.MaterialButton
import com.wee3ventures.fontier.FontView

object ChangeColor {

    fun fontView(view : FontView, color : Int){ view.setTextColor(color) }
    fun fontView(views : List<FontView>, color : Int){ views.forEach { it.setTextColor(color) } }

    fun buttonView(view: MaterialButton, color: Int) { view.setTextColor(color) }

    fun imageView(views : List<ImageView>, tintColor : Int){
        views.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.imageTintList = ColorStateList.valueOf(tintColor)
            } else {
                DrawableCompat.setTint(it.drawable, tintColor)
            }
        }
    }
    fun imageView(view : ImageView, tintColor : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.imageTintList = ColorStateList.valueOf(tintColor)
        } else {
            DrawableCompat.setTint(view.drawable, tintColor)
        }
    }

    fun ImageView.setTint(@ColorRes colorRes: Int){
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)) )
    }
}