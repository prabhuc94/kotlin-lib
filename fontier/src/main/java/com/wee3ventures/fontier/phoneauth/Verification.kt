package com.wee3ventures.fontier.phoneauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.utils.Utility
import com.wee3ventures.fontier.widget.keyboard.Keyboard
import kotlinx.android.synthetic.main.fragment_mobile_otp_verification.*

class Verification (val count : Int ?= 6, val mobileNumber : Any ?= null) : DialogFragment(), View.OnClickListener {

    interface onClickListener{
        fun onClick(verificationCode : Any)
        fun onResend(view: View)
    }
    private var listener : onClickListener ?= null

    fun setCount(){
        if (count != null) {
            otpView.itemCount = count
        }
    }

    fun setOtp(otpCode : Any) {
        otpView.text = Utility.editable(otpCode)
    }

    fun setMobileNumber(){
        fontView8.text = "${Utility.otpMsgWithNo("$mobileNumber")}"
    }

    fun setonClicklistener(listener: onClickListener){
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.GDOTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mobile_otp_verification,container,false)
        return view
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null){
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        }
        this.setCount()
        this.setMobileNumber()

        if (listener != null) {
            keyboard.setOnClickListener(object : Keyboard.onClickListener {
                override fun onClick(view: View, pressedValue: Any, totalValue: Any?) {
                    otpView.text = totalValue?.let { Utility.editable(it) }
                }

                override fun onBackspave(view: View, lastValue: Any?) {
                    otpView.text = lastValue?.let { Utility.editable(it) }
                }

                override fun onClear(view: View, isClear: Boolean) {
                    otpView.text?.clear()
                }
            })
            closeBtn.setOnClickListener(this)
            confirmBtn.setOnClickListener(this)
            resendBtn.setOnClickListener(this)
        } else {
            RuntimeException("Listener not yet initialised")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.closeBtn -> dismiss()
            R.id.resendBtn -> listener?.onResend(v)
            R.id.confirmBtn -> listener?.onClick(otpView.text ?: RuntimeException("Otp value is empty"))
        }
    }
}