package com.wee3ventures.fontier.`interface`

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface RegisterListener {

    fun onCheck(username : Any, usernamelayout : TextInputLayout, usernamefield : TextInputEditText)

    fun onSignIn(signIn : Boolean)

}