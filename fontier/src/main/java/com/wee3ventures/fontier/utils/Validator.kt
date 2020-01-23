package com.wee3ventures.fontier.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


object Validator {
    const val EMAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\\\w-]+\\\\.)+[a-zA-Z]{2,4})\$"
    const val INVALID_EMAIL_MESSAGE = "Email address is invalid"
    const val INVALID_PHONE_MESSAGE = "Phone number is invalid"
    const val EMPTY_FIELD_MESSAGE = "Field is should not empty"
    const val PASSWORD_MISMATCH = "Password is mismatch"

    fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches()
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun isValidMobileWithCount(phone: String): Boolean {
        return if (!Pattern.matches("[a-zA-Z]+", phone)) phone.length in 7..13 else false
    }

    fun isValid(input : String, pattern: String) : Boolean{
        return Pattern.compile(pattern).matcher(input).matches()
    }

    val mailWatcher : TextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val data = s.toString()

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    fun textWatcher(textInputLayout : TextInputLayout, textInputEditText: TextInputEditText, pattern: String ?= null , errorMessage : String ? = EMPTY_FIELD_MESSAGE){
        textInputLayout.editText?.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val data = s.toString()
                if (data.isEmpty().not()) {
                    if ("${textInputEditText.tag}".isEmpty()) {
                        if (pattern != null) {
                            if (isValid(data, pattern).not()) {
                                textInputLayout.isErrorEnabled = true; textInputLayout.error = errorMessage
                            } else textInputLayout.isErrorEnabled = false
                        } else textInputLayout.isErrorEnabled = false
                    } else {
                        when (textInputEditText.tag) {
                            "Username", "name", "Name" -> {
                                if (data.isNullOrEmpty()) {
                                    textInputLayout.isErrorEnabled = true; textInputLayout.error = EMPTY_FIELD_MESSAGE
                                } else textInputLayout.isErrorEnabled = false
                            }
                            "Email", "Mail", "email", "mail" -> {
                                if (isValidEmail(data)) textInputLayout.isErrorEnabled =
                                    false else { textInputLayout.isErrorEnabled = true; textInputLayout.error = INVALID_EMAIL_MESSAGE }
                            }
                            "Phone", "Contact", "phone", "contact" -> {
                                if (isValidMobile(data)) textInputLayout.isErrorEnabled =
                                    false else { textInputLayout.isErrorEnabled = true; textInputLayout.error = INVALID_PHONE_MESSAGE }
                            }
                        }
                    }
                } else {
                    textInputLayout.isErrorEnabled = true ; textInputLayout.error = EMPTY_FIELD_MESSAGE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    fun textWatcher(textInputLayout : TextInputLayout, pattern: String ?= null , errorMessage : String ? = EMPTY_FIELD_MESSAGE){
        textInputLayout.editText?.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val data = s.toString()
                if (data.isEmpty().not()) {
                    if ("${textInputLayout.editText?.tag}".isEmpty()) {
                        if (pattern != null) {
                            if (isValid(data, pattern).not()) {
                                textInputLayout.isErrorEnabled = true; textInputLayout.error = errorMessage
                            } else textInputLayout.isErrorEnabled = false
                        } else textInputLayout.isErrorEnabled = false
                    } else {
                        when (textInputLayout.editText?.tag) {
                            "Username", "name", "Name" -> {
                                if (data.isNullOrEmpty()) {
                                    textInputLayout.isErrorEnabled = true; textInputLayout.error = EMPTY_FIELD_MESSAGE
                                } else textInputLayout.isErrorEnabled = false
                            }
                            "Email", "Mail", "email", "mail" -> {
                                if (isValidEmail(data)) textInputLayout.isErrorEnabled =
                                    false else { textInputLayout.isErrorEnabled = true; textInputLayout.error = INVALID_EMAIL_MESSAGE }
                            }
                            "Phone", "Contact", "phone", "contact" -> {
                                if (isValidMobile(data)) textInputLayout.isErrorEnabled =
                                    false else { textInputLayout.isErrorEnabled = true; textInputLayout.error = INVALID_PHONE_MESSAGE }
                            }
                        }
                    }
                } else {
                    textInputLayout.isErrorEnabled = true ; textInputLayout.error = EMPTY_FIELD_MESSAGE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    fun textWatcher(views : List<TextInputLayout>, pattern: String ?= null , errorMessage : String ? = EMPTY_FIELD_MESSAGE){
        views.forEach { textInputLayout -> textInputLayout.editText?.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val data = s.toString()
                if (data.isEmpty().not()) {
                    if ("${textInputLayout.tag}".isEmpty()) {
                        if (pattern != null) {
                            if (isValid(data, pattern).not()) {
                                textInputLayout.isErrorEnabled = true; textInputLayout.error = errorMessage
                            } else textInputLayout.isErrorEnabled = false
                        } else textInputLayout.isErrorEnabled = false
                    } else {
                        when (textInputLayout.tag) {
                            "Username", "name", "Name" -> {
                                if (data.isEmpty()) {
                                    textInputLayout.isErrorEnabled = true; textInputLayout.error = EMPTY_FIELD_MESSAGE
                                } else textInputLayout.isErrorEnabled = false
                            }
                            "Email", "Mail", "email", "mail" -> {
                                if (isValidEmail(data)) textInputLayout.isErrorEnabled =
                                    false else { textInputLayout.isErrorEnabled = true; textInputLayout.error = INVALID_EMAIL_MESSAGE }
                            }
                            "Phone", "Contact", "phone", "contact" -> {
                                if (isValidMobile(data)) textInputLayout.isErrorEnabled =
                                    false else { textInputLayout.isErrorEnabled = true; textInputLayout.error = INVALID_PHONE_MESSAGE }
                            }
                        }
                    }
                } else {
                    textInputLayout.isErrorEnabled = true ; textInputLayout.error = EMPTY_FIELD_MESSAGE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }) }
    }
}