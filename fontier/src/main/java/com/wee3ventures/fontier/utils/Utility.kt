package com.wee3ventures.fontier.utils

import android.text.Editable
import android.text.Html
import android.view.View
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
        val retVal: StringBuilder = StringBuilder(s)
        var i = retVal.length
        while (i > 0) {
            retVal.insert(i, t)
            i -= num
        }
        return retVal.toString()
    }

    fun removeLastChar(str: String): String? {
        return str.substring(0, str.length - 1)
    }

    fun otpMsgWithNo(mobileNumber : Any) : Any{
        return "Please type the verification code sent to $mobileNumber"
    }

    fun editable (input : Any) : Editable = Editable.Factory.getInstance().newEditable("$input")

    fun htmlValue (input : Any, input1 : Any) : String = Html.fromHtml("<small>$input</small>  <small>(</small> <b>$input1</b> <small>)</small>").toString()
}