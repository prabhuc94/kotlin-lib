package com.wee3ventures.fontier.utils

import android.annotation.SuppressLint
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateConverter {

    @Throws(ParseException::class, IOException::class)
    fun dateParser(mInput : String, mFormat : String) : Date{
        val format = SimpleDateFormat(mFormat, Locale.ENGLISH)
        return format.parse(mInput)
    }

    @Throws(ParseException::class, IOException::class)
    @SuppressLint("SimpleDateFormat")
    fun dateConverter(mInput : String, mInputFormat : String, mOutputFormat : String) : String{
        val inputFormatter = SimpleDateFormat(mInputFormat)
        val outputFormatter = SimpleDateFormat(mOutputFormat)
        val actualDate = inputFormatter.parse(mInput)
        return outputFormatter.format(actualDate)
    }

}