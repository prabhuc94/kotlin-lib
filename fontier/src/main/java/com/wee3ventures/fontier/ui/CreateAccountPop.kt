package com.wee3ventures.fontier.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.`interface`.CreateListener
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountPop (val listener : CreateListener) : DialogFragment(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.GDOTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.activity_create_account,container,false)
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
        signUpBtn.setOnClickListener(this)
        signinBtn.setOnClickListener(this)
        backBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.signUpBtn -> {
                checkPass()
            }

            R.id.signinBtn -> {
                listener.onSignIn(signIn = true)
            }

            R.id.backBtn -> {
                listener.onBackpressed(backPress = true)
            }
        }
    }

    private fun checkPass(){
        val password = "${confPass.text}"
        val confirmPass = "${confPass.text}"
        if (password == confirmPass){
            confPassLayout.isErrorEnabled = false
            listener.onSignUp(username = "${userName.text}",userMail = "${mailTxt.text}",userPass = "${userPass.text}")
        } else {
            confPassLayout.isErrorEnabled = true
            confPassLayout.error = "Password Mismatch"
        }
    }

}