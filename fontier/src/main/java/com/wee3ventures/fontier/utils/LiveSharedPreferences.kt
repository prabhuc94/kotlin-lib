package com.wee3ventures.fontier.utils

import android.content.Context
import android.content.SharedPreferences
import com.wee3ventures.fontier.BuildConfig
import io.reactivex.subjects.PublishSubject

class LiveSharedPreferences {

    private val publisher = PublishSubject.create<String>()
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key -> publisher.onNext(key) }

    private var preferences : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? = null

    private val updates = publisher.doOnSubscribe { preferences?.registerOnSharedPreferenceChangeListener(listener) }.doOnDispose {
        if (!publisher.hasObservers()) preferences?.unregisterOnSharedPreferenceChangeListener(listener) }

    constructor(mContext : Context){
        preferences = mContext.getSharedPreferences(BuildConfig.LIBRARY_PACKAGE_NAME, Context.MODE_PRIVATE)
        editor = preferences!!.edit()
    }
    constructor(mContext : Context, preferenceName : String){
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        editor = preferences!!.edit()
    }

    constructor(mContext : Context, preferenceName : String, mode : Int){
        preferences = mContext.getSharedPreferences(preferenceName, mode)
        editor = preferences!!.edit()
    }

    fun getPreferences(): SharedPreferences {
        return preferences!!
    }

    fun getString(key: String, defaultValue: String): LivePreference<String> {
        return LivePreference(updates, preferences!!, key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): LivePreference<Int> {
        return LivePreference(updates, preferences!!, key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): LivePreference<Boolean> {
        return LivePreference(updates, preferences!!, key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): LivePreference<Float> {
        return LivePreference(updates, preferences!!, key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): LivePreference<Long> {
        return LivePreference(updates, preferences!!, key, defaultValue)
    }

    fun getStringSet(key: String, defaultValue: Set<String>): LivePreference<Set<String>> {
        return LivePreference(updates, preferences!!, key, defaultValue)
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

    fun getPrefs(key: String, defValue : String) : LivePreference<String> {
        return LivePreference(updates, preferences!!, key, defValue)
    }

    fun getPrefs(key: String, defValue : MutableSet<String>) : LivePreference<MutableSet<String>>{
        return LivePreference(updates, preferences!!, key, defValue)
    }

    fun getPrefs(key: String, defValue : Int) : LivePreference<Int> {
        return LivePreference(updates, preferences!!, key, defValue)
    }

    fun getPrefs(key: String, defValue : Float) : LivePreference<Float>{
        return LivePreference(updates, preferences!!, key, defValue)
    }

    fun getPrefs(key: String, defValue : Long) : LivePreference<Long> {
        return LivePreference(updates, preferences!!, key, defValue)
    }

    fun getPrefs(key: String, defValue : Boolean) : LivePreference<Boolean>{
        return LivePreference(updates, preferences!!, key, defValue)
    }



    fun <T> listenMultiple(keys: List<String>, defaultValue: T): MultiPreference<T> {
        return MultiPreference(updates, preferences!!, keys, defaultValue)
    }
}