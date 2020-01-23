package com.wee3ventures.fontier.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.`interface`.CreateListener
import com.wee3ventures.fontier.utils.Validator
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_create_account.userPass

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
        userLayout.tag = "Username"
        phoneLayout.tag = "Phone"
        mailLayout.tag = "Email"

        userName.tag = "Username"
        phoneTxt.tag = "Phone"
        mailTxt.tag = "Email"

        Validator.textWatcher(passLayout,userPass,errorMessage = Validator.EMPTY_FIELD_MESSAGE)
        Validator.textWatcher(confPassLayout,confPass,errorMessage = Validator.EMPTY_FIELD_MESSAGE)
        Validator.textWatcher(userLayout,userName,errorMessage = Validator.EMPTY_FIELD_MESSAGE)
        Validator.textWatcher(phoneLayout,phoneTxt,errorMessage = Validator.EMPTY_FIELD_MESSAGE)
        Validator.textWatcher(mailLayout,mailTxt,pattern = Validator.EMAIL_PATTERN, errorMessage = Validator.EMPTY_FIELD_MESSAGE)
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
                passCheck()
                if (checkViews()) listener.onSignUp(username = "${userName.text}",userMail = "${mailTxt.text}", userPhone = "${phoneTxt.text}",userPass = "${userPass.text}", dialog = this)
            }

            R.id.signinBtn -> {
                listener.onSignIn(signIn = true, dialog = this)
            }

            R.id.backBtn -> {
                listener.onBackpressed(backPress = true, dialog = this)
            }
        }
    }

    private fun checkViews() : Boolean = userName.text.isNullOrEmpty().not() && mailTxt.text.isNullOrEmpty().not() &&
            Validator.isValidEmail("${mailTxt.text}") && phoneTxt.text.isNullOrEmpty().not() &&
            Validator.isValidMobile("${phoneTxt.text}") &&
            userPass.text.isNullOrEmpty().not() && confPass.text.isNullOrEmpty().not()
            && "${userPass.text}".equals("${confPass.text}")

    private fun passCheck(){
        if ("${userPass.text}".equals("${confPass.text}")){
            confPassLayout.isErrorEnabled = false
        } else {
            confPassLayout.isErrorEnabled = true
            confPassLayout.error = Validator.PASSWORD_MISMATCH
        }
    }
}