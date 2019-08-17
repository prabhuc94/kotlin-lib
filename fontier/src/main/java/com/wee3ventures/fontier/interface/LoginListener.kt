package com.wee3ventures.fontier.`interface`

import androidx.fragment.app.DialogFragment

interface LoginListener {
    fun onLogin(username : Any,password : Any,remember_me : Boolean, dialog : DialogFragment)
    fun onForgot(forgotUsername : Any, dialog : DialogFragment)
    fun onSignUp(signUp : Boolean, dialog : DialogFragment)
}