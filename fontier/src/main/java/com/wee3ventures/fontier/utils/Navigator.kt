package com.wee3ventures.fontier.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri

object Navigator {
    const val COMING_FROM = "coming_from"
    inline fun <reified T : Activity> launch(context: Context): Boolean {
        val intent = Intent(context.applicationContext, T::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        return true
    }
    inline fun <reified T : Activity> launch(mContext: Context,comingFrom : String) {
        val intent = Intent(mContext.applicationContext, T::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (!(comingFrom == null && comingFrom.length > 0)) {
            intent.putExtra(COMING_FROM,comingFrom)
        }
        mContext.startActivity(intent)
    }

    fun exit(context: Context){
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun URL(context: Context, url : Any){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("$url")
//        intent.addCategory(Intent.CATEGORY_APP_BROWSER)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}