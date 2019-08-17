package com.wee3ventures.fontier.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.request.RequestOptions
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.`interface`.LoginListener
import com.wee3ventures.fontier.model.LoginModel
import com.wee3ventures.fontier.utils.Fonts
import com.wee3ventures.fontier.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_black_login.*

class BlackLoginPop (val response : LoginModel, val listener: LoginListener) : DialogFragment(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.GDOTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_black_login,container,false)
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
                        .apply {
                            RequestOptions
                                .placeholderOf(R.drawable.ic_locked_icon_gdo)
                                .error(R.drawable.ic_locked_icon_gdo)
                                .circleCrop()
                                .fitCenter()
                        }
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

        loginBtn.setOnClickListener(this)
        forgotBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)
    }
    private fun setFont(typeFace : Typeface){
        userLayout.typeface = typeFace
        userName.typeface = typeFace
        passLayout.typeface = typeFace
        userPass.typeface = typeFace
        rememberBox.typeface = typeFace
        loginBtn.typeface = typeFace
        fontView.typeface = typeFace
        signUpBtn.typeface = typeFace
        forgotBtn.typeface = typeFace
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginBtn -> {
                listener.onLogin(username = "${userName.text}",password = "${userPass.text}",remember_me = rememberBox.isChecked, dialog = this)
            }

            R.id.signUpBtn -> {
                listener.onSignUp(signUp = true, dialog = this)
            }

            R.id.forgotBtn -> {
                listener.onForgot(forgotUsername = true, dialog = this)
            }
        }
    }
}