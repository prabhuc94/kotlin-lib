@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.wee3ventures.fontier.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.wee3ventures.fontier.BuildConfig

@SuppressLint("CommitPrefEdits")
class Preference {
    private var sharedPreferences : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? = null
    constructor(mContext : Context){
        sharedPreferences = mContext.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }
    constructor(mContext : Context, preferenceName : String){
        sharedPreferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    constructor(mContext : Context, preferenceName : String, mode : Int){
        sharedPreferences = mContext.getSharedPreferences(preferenceName, mode)
        editor = sharedPreferences!!.edit()
    }

    fun setPrefs(key : String,value : String){
        editor!!.putString(key,value).commit()
    }

    fun setPrefs(key : String,value : Boolean){
        editor!!.putBoolean(key,value).commit()
    }

    fun setPrefs(key : String,value : Float){
        editor!!.putFloat(key,value).commit()
    }

    fun setPrefs(key : String,value : Int){
        editor!!.putInt(key,value).commit()
    }

    fun setPrefs(key : String,value : Long){
        editor!!.putLong(key,value).commit()
    }

    fun setPrefs(key : String,value : MutableSet<String>){
        editor!!.putStringSet(key,value).commit()
    }

    fun getPrefs(key: String, defValue : String) : String {
        return sharedPreferences!!.getString(key,defValue)!!
    }

    fun getPrefs(key: String, defValue : MutableSet<String>) : MutableSet<String>{
        return sharedPreferences!!.getStringSet(key,defValue)!!
    }

    fun getPrefs(key: String, defValue : Int) : Int{
        return sharedPreferences!!.getInt(key,defValue)
    }

    fun getPrefs(key: String, defValue : Float) : Float{
        return sharedPreferences!!.getFloat(key,defValue)
    }

    fun getPrefs(key: String, defValue : Long) : Long{
        return sharedPreferences!!.getLong(key,defValue)
    }

    fun getPrefs(key: String, defValue : Boolean) : Boolean{
        return sharedPreferences!!.getBoolean(key,defValue)
    }

    fun clear(key: String){
        editor?.remove(key)?.commit()
    }

    fun clear(){
        editor!!.clear().commit()
    }
}