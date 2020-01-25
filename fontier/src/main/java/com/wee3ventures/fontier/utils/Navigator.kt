package com.wee3ventures.fontier.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import java.io.File

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
        context.startActivity(intent)
    }

    fun email(context: Context, mailto : Array<String>, subject : String){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, mailto)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            setPackage("com.google.android.gm")
        }
        context.startActivity(Intent.createChooser(intent, "Send mail..."))
    }

    fun share(context: Context, message : String){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        context.startActivity(Intent.createChooser(intent, "Choose via"))
    }

    fun whatsapp(context: Context,message: String){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
            setPackage("com.whatsapp")
        }
        context.startActivity(Intent.createChooser(intent, "Choose via"))
    }

    fun share(context: Context,message: String, intentType : String ?= "text/plain", file : File ?= null, appPackage : String ?= null){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = intentType
            putExtra(Intent.EXTRA_TEXT, message)
        }
        if (file != null){
            intent.putExtra(Intent.EXTRA_STREAM, file.toUri())
        }

        if (appPackage != null){
            intent.setPackage(appPackage)
        }

        context.startActivity(Intent.createChooser(intent, "Choose via"))
    }
}