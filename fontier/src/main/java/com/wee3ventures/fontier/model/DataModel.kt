package com.wee3ventures.fontier.model

import com.wee3ventures.fontier.Enumaration.Fonts

data class LoginModel(
    val logo : Any,
    val background : Any,
    val passwordError : Any,
    val usernameError : Any,
    val fontPath : String,
    val fontName : Fonts
)