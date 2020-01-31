package com.wee3ventures.fontier.utils

import android.app.Activity
import android.app.Dialog
import android.text.Editable
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.TooltipCompat

object Utility {
    fun showToolTip(view: View, message : Any){
        TooltipCompat.setTooltipText(view,"$message")
    }

    fun addPadding(t: String, s: String?, num: Int): String? {
        require(!(null == s || 0 >= num)) { "Null value cannot accepted" }
        if ("$s".length <= num) { //String to small, do nothing
            return s
        }
        val retVal = s?.let { StringBuilder(it) }
        if (retVal != null) {
            var i = retVal.length
            while (i > 0) {
                retVal.insert(i, t)
                i -= num
            }
            return retVal.toString()
        }
        return null
    }

    fun removeLastChar(str: String): String? {
        return str.substring(0, str.length - 1)
    }

    fun otpMsgWithNo(mobileNumber : Any) : Any{
        return "Please type the verification code sent to $mobileNumber"
    }

    fun editable (input : Any) : Editable = Editable.Factory.getInstance().newEditable("$input")

    fun htmlValue (input : Any, input1 : Any) : String = Html.fromHtml("<small>$input</small>  <small>(</small> <b>$input1</b> <small>)</small>").toString()

    fun setupDialog(dialog: Dialog, style: Int ?= null, activity: Activity, layoutId : Int, cancelable : Boolean ?= false, backgroundColor : Int ?= android.R.color.transparent,width : Int ?= WindowManager.LayoutParams.MATCH_PARENT, height : Int ?= WindowManager.LayoutParams.MATCH_PARENT){
        val inflatedView = LayoutInflater.from(activity).inflate(layoutId, null, false)
        dialog.setCancelable(cancelable!!)
        dialog.setCanceledOnTouchOutside(cancelable!!) // Disable the cancel if user click outside of the popup/dialog
        dialog.setContentView(inflatedView) // add the desired view
        dialog.window?.setLayout(width!!,height!!) // It'll help to occupy the required screen size
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // This will Make non accessible previous screen for other than Popup view
        dialog.window?.setGravity(Gravity.CENTER) // This will make it appear the Popup in TOP
        dialog.window?.setBackgroundDrawableResource(backgroundColor!!) // It'll make the Dialog background as Transparent
        dialog.show()
    }

    fun setupDialog(dialog: Dialog, inflatedView: View, cancelable : Boolean ?= false, backgroundColor : Int ?= android.R.color.transparent,width : Int ?= WindowManager.LayoutParams.MATCH_PARENT, height : Int ?= WindowManager.LayoutParams.MATCH_PARENT){
        dialog.setCancelable(cancelable!!)
        dialog.setCanceledOnTouchOutside(cancelable!!) // Disable the cancel if user click outside of the popup/dialog
        dialog.setContentView(inflatedView) // add the desired view
        dialog.window?.setLayout(width!!,height!!) // It'll help to occupy the required screen size
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND) // This will Make non accessible previous screen for other than Popup view
        dialog.window?.setGravity(Gravity.CENTER) // This will make it appear the Popup in TOP
        dialog.window?.setBackgroundDrawableResource(backgroundColor!!) // It'll make the Dialog background as Transparent
        dialog.show()
    }
}