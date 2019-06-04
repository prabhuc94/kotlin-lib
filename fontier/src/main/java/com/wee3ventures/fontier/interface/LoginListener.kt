package com.wee3ventures.fontier.`interface`

interface LoginListener {
    fun onLogin(username : Any,password : Any,remember_me : Boolean)
    fun onForgot(forgotUsername : Any)
    fun onSignUp(signUp : Boolean)
}