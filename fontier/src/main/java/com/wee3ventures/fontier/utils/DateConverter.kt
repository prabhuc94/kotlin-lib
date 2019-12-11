package com.wee3ventures.fontier.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import com.wee3ventures.fontier.model.TimeDifferenceModel
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

    fun showDatePicker(mContext : Context, listener : DatePickerDialog.OnDateSetListener){
        val mCalendar = Calendar.getInstance()
        val mYear = mCalendar[Calendar.YEAR]
        val mMonth = mCalendar[Calendar.MONTH]
        val mDay = mCalendar[Calendar.DAY_OF_MONTH]
        DatePickerDialog(mContext,listener,mYear,mMonth,mDay).show()
    }

    @SuppressLint("SimpleDateFormat")
    fun showDate(inputDate : Date, format : String) : String{
        return SimpleDateFormat(format).format(inputDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun showDate(inputDate : String, format : String) : String{
        return SimpleDateFormat(format).format(Date(inputDate))
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDate(date : Date, format: String) : String {
        return SimpleDateFormat(format).format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDate(date: String, format: String) : Date{
        return SimpleDateFormat(format).parse(date)
    }

    fun dateComparison(year : Int, month : Int, dayOfMonth : Int) : Boolean{
        val cal = Calendar.getInstance()
        cal.set(year,month,dayOfMonth)
        val currentDate = Calendar.getInstance()
        return cal.time.after(currentDate.time) || cal.time == currentDate.time
    }

    fun getDateFormat(year : Int, month : Int, dayOfMonth : Int) : Date{
        val cal = Calendar.getInstance()
        cal.set(year,month,dayOfMonth)
        return cal.time
    }

    fun getDate(year : Int, month : Int, dayOfMonth : Int,format: String) : Any{
        val cal = Calendar.getInstance()
        cal.set(year,month,dayOfMonth)
        return showDate(cal.time, format)
    }

    fun getCurrentDate(format: String) : Any{
        val cal = Calendar.getInstance()
        return showDate(cal.time, format)
    }

    @SuppressLint("SimpleDateFormat")
    fun checktimings(time: String, destTime : String, inputFormat : String): Boolean {
        val sdf = SimpleDateFormat(inputFormat)
        try {
            val date1 = sdf.parse(destTime)
            val date2 = sdf.parse(time)
            return date1 > date2
        } catch (e: ParseException) {
            Console.log("DateConverter",message = "onCatch ParseException:\t$e",throwable = e)
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun timeDifference(startTime : String, endTime : String) : TimeDifferenceModel {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val startDate = simpleDateFormat.parse(startTime)
        val endDate = simpleDateFormat.parse(endTime)

        var difference = endDate.time - startDate.time
        if (difference < 0) {
            val dateMax = simpleDateFormat.parse("24:00")
            val dateMin = simpleDateFormat.parse("00:00")
            difference = dateMax.time - startDate.time + (endDate.time - dateMin.time)
        }
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        val min =
            (difference - (1000 * 60 * 60 * 24 * days).toLong() - (1000 * 60 * 60 * hours).toLong()).toInt() / (1000 * 60)

        return TimeDifferenceModel(days = days,hours = hours,mins = min)
    }

}