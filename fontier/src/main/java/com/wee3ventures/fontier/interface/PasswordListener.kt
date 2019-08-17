package com.wee3ventures.fontier.`interface`

import android.view.View
import androidx.fragment.app.DialogFragment

interface PasswordListener {

    fun onPassword (password : Any, view: View, dialog : DialogFragment)

    fun onSignIn(signIn : Boolean, dialog : DialogFragment)

}