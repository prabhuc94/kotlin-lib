package com.wee3ventures.fontier.`interface`

import android.view.View

interface PasswordListener {

    fun onPassword (password : Any, view: View)

    fun onSignIn(signIn : Boolean)

}