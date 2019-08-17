package com.wee3ventures.fontier.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.`interface`.PasswordListener
import com.wee3ventures.fontier.`interface`.RegisterListener
import com.wee3ventures.fontier.model.UnErrorModel
import com.wee3ventures.fontier.utils.Fonts
import com.wee3ventures.fontier.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_password_setting_form_gdvo.*
import kotlinx.android.synthetic.main.fragment_register_form_common_gdvo.*
import kotlinx.android.synthetic.main.fragment_register_form_common_gdvo.fontView
import kotlinx.android.synthetic.main.fragment_register_form_common_gdvo.logBackView
import kotlinx.android.synthetic.main.fragment_register_form_common_gdvo.logoView
import kotlinx.android.synthetic.main.fragment_register_form_common_gdvo.signinBtn

class RegisterPop (val response : UnErrorModel, val listener: RegisterListener) : DialogFragment(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.GDOTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_register_form_common_gdvo,container,false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
            toolbar.setNavigationOnClickListener { dismiss() }
            toolbar.title = " "
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null){
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        setUtility()
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun setUtility(){
        if (response != null){
            when{
                response.background != null -> {
                    GlideApp.with(activity!!)
                        .load(response.background)
                        .into(logBackView)
                }

                response.logo != null -> {
                    GlideApp.with(activity!!)
                        .load(response.logo)
                        .placeholder(R.drawable.ic_gurudevo_logo)
                        .error(R.drawable.ic_gurudevo_logo)
                        .into(logoView)
                }

                response.fontName != null -> {
                    setFont(Fonts.getFontFace(mContext = activity?.applicationContext!!,fontName = response.fontName))
                }

                response.fontPath != null -> {
                    setFont(Fonts.getFontFace(mContext = activity?.applicationContext!!,fontPath = response.fontPath))
                }
                else -> throw RuntimeException("Some fields are missing")
            }
        }
        checkBtn.setOnClickListener(this)
        signinBtn.setOnClickListener(this)
    }
    private fun setFont(typeFace : Typeface){
        userLayout.typeface = typeFace
        userName.typeface = typeFace
        checkBtn.typeface = typeFace
        fontView.typeface = typeFace
        signinBtn.typeface = typeFace
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.checkBtn -> listener.onCheck("${userName.text}", userLayout, userName , dialog = this)
            R.id.signinBtn -> listener.onSignIn(true, dialog = this)
        }
    }
}

class PasswordPop (val response : UnErrorModel, val listener: PasswordListener) : DialogFragment(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.GDOTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_password_setting_form_gdvo,container,false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
            toolbar.setNavigationOnClickListener { dismiss() }
            toolbar.title = " "
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null){
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        setUtility()
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun setUtility(){
        if (response != null){
            when{
                response.background != null -> {
                    GlideApp.with(activity!!)
                        .load(response.background)
                        .into(logBackView)
                }

                response.logo != null -> {
                    GlideApp.with(activity!!)
                        .load(response.logo)
                        .placeholder(R.drawable.ic_gurudevo_logo)
                        .error(R.drawable.ic_gurudevo_logo)
                        .into(logoView)
                }

                response.fontName != null -> {
                    setFont(Fonts.getFontFace(mContext = activity?.applicationContext!!,fontName = response.fontName))
                }

                response.fontPath != null -> {
                    setFont(Fonts.getFontFace(mContext = activity?.applicationContext!!,fontPath = response.fontPath))
                }
                else -> throw RuntimeException("Some fields are missing")
            }
        }
        setButtonFont(Fonts.getFontFace(this.activity?.applicationContext!!,com.wee3ventures.fontier.Enumaration.Fonts.POPPINS_MEDIUM))
        submitBtn.setOnClickListener(this)
        signinBtn.setOnClickListener(this)
    }
    private fun setFont(typeFace : Typeface){
        confirmlayout.typeface = typeFace
        confirmUserPass.typeface = typeFace
        userPass.typeface = typeFace
        passLayout.typeface = typeFace
    }

    private fun setButtonFont(typeFace: Typeface){
        submitBtn.typeface = typeFace
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.checkBtn -> if (checkPassword()) listener.onPassword("${userPass.text}", confirmlayout, dialog = this)
            R.id.signinBtn -> listener.onSignIn(true, dialog = this)
        }
    }

    private fun checkPassword() : Boolean{
        return if (userPass.text?.equals(confirmUserPass.text)!!){
            true
        } else {
            confirmlayout.error = "Password Mismatch"
            false
        }
    }
}