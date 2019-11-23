package com.wee3ventures.fontier.utils

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
}