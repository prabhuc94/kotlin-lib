package com.wee3ventures.fontier.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.wee3ventures.fontier.BuildConfig

object Console {
    enum class LOG{
        Debug,Error,Info,Warn,Verbose
    }
    fun Activity.error(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(TAG, "$message")
            } else {
                Log.e(TAG, "$message", throwable)
            }
        }
    }

    fun Activity.log(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(TAG, "$message")
            } else {
                Log.e(TAG, "$message", throwable)
            }
        }
    }

    fun Activity.info(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.i(TAG, "$message")
            } else {
                Log.i(TAG, "$message", throwable)
            }
        }
    }

    fun Activity.debug(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.d(TAG, "$message")
            } else {
                Log.d(TAG, "$message", throwable)
            }
        }
    }

    fun Activity.warning(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.w(TAG, "$message")
            } else {
                Log.w(TAG, "$message", throwable)
            }
        }
    }

    fun Activity.verbose(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.v(TAG, "$message")
            } else {
                Log.v(TAG, "$message", throwable)
            }
        }
    }


    fun Context.error(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(TAG, "$message")
            } else {
                Log.e(TAG, "$message", throwable)
            }
        }
    }

    fun Context.log(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(TAG, "$message")
            } else {
                Log.e(TAG, "$message", throwable)
            }
        }
    }

    fun Context.info(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.i(TAG, "$message")
            } else {
                Log.i(TAG, "$message", throwable)
            }
        }
    }

    fun Context.debug(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.d(TAG, "$message")
            } else {
                Log.d(TAG, "$message", throwable)
            }
        }
    }

    fun Context.warning(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.w(TAG, "$message")
            } else {
                Log.w(TAG, "$message", throwable)
            }
        }
    }

    fun Context.verbose(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.v(TAG, "$message")
            } else {
                Log.v(TAG, "$message", throwable)
            }
        }
    }


    fun Fragment.error(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(TAG, "$message")
            } else {
                Log.e(TAG, "$message", throwable)
            }
        }
    }

    fun Fragment.log(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.e(TAG, "$message")
            } else {
                Log.e(TAG, "$message", throwable)
            }
        }
    }

    fun Fragment.info(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.i(TAG, "$message")
            } else {
                Log.i(TAG, "$message", throwable)
            }
        }
    }

    fun Fragment.debug(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.d(TAG, "$message")
            } else {
                Log.d(TAG, "$message", throwable)
            }
        }
    }

    fun Fragment.warning(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.w(TAG, "$message")
            } else {
                Log.w(TAG, "$message", throwable)
            }
        }
    }

    fun Fragment.verbose(message : Any, @Nullable throwable: Throwable? = null){
        val TAG = this.javaClass.simpleName
        if (BuildConfig.DEBUG) {
            if (throwable == null) {
                Log.v(TAG, "$message")
            } else {
                Log.v(TAG, "$message", throwable)
            }
        }
    }

    @SuppressLint("LogNotTimber")
    fun log(@Nullable TAG : String = BuildConfig.LIBRARY_PACKAGE_NAME, @Nullable activity: Activity? = null, message : Any, @Nullable throwable: Throwable? = null, type : LOG = LOG.Error){
        @Suppress("NAME_SHADOWING") val TAG : String? = if (activity != null){
            activity::class.java.simpleName
        } else {
            TAG
        }
        if (BuildConfig.DEBUG){
            when (type){
                LOG.Error->{
                    if (throwable == null) {
                        Log.e(TAG, "$message")
                    } else {
                        Log.e(TAG, "$message",throwable)
                    }
                }
                LOG.Debug->{
                    if (throwable == null) {
                        Log.d(TAG,"$message")
                    } else {
                        Log.d(TAG,"$message",throwable)
                    }
                }
                LOG.Info->{
                    if (throwable == null) {
                        Log.i(TAG,"$message")
                    } else {
                        Log.i(TAG,"$message",throwable)
                    }
                }
                LOG.Verbose->{
                    if (throwable == null) {
                        Log.v(TAG,"$message")
                    } else {
                        Log.v(TAG,"$message",throwable)
                    }
                }
                LOG.Warn->{
                    if (throwable == null) {
                        Log.w(TAG,"$message")
                    } else {
                        Log.w(TAG,"$message",throwable)
                    }
                }
            }
        }
    }
}