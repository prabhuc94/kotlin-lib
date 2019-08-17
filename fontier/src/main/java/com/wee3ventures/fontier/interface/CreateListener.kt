package com.wee3ventures.fontier.`interface`

import androidx.fragment.app.DialogFragment

interface CreateListener {
    fun onSignUp(username : Any, userMail : Any, userPass : Any, dialog : DialogFragment)
    fun onSignIn(signIn : Boolean, dialog : DialogFragment)
    fun onBackpressed(backPress : Boolean, dialog : DialogFragment)
}