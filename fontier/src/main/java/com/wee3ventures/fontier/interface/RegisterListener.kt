package com.wee3ventures.fontier.`interface`

import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface RegisterListener {

    fun onCheck(username : Any, usernamelayout : TextInputLayout, usernamefield : TextInputEditText, dialog : DialogFragment)

    fun onSignIn(signIn : Boolean, dialog : DialogFragment)

}