package com.wee3ventures.fontier.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.utils.GlideApp

object Ext {
    fun ViewGroup.inflate(layoutRes : Int) : View{
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    fun View.visible(){
        visibility = View.VISIBLE
    }

    fun View.invisible(){
        visibility = View.INVISIBLE
    }

    fun View.gone(){
        visibility = View.GONE
    }

    fun ImageView.load(imageVal : Any){
        GlideApp.with(this.context).asBitmap().load(imageVal).error(R.drawable.ic_gurudevo_logo).placeholder(R.drawable.ic_gurudevo_logo).into(this)
    }
}