package com.wee3ventures.fontier.phoneauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wee3ventures.fontier.R
import kotlinx.android.synthetic.main.fragment_mobile_registration.*

class Registration : DialogFragment(), View.OnClickListener {

    interface onCompletelistener{
        fun onComplete(countryCode : Any, contactNumber : Any)
    }

    private val countryAdapter : CountryAdapter by lazy { CountryAdapter() }
    private var listener : onCompletelistener ?= null
    private var countryData : MutableList<CountryCode> = mutableListOf()

    fun setonCompleteListener (listener : onCompletelistener){
        this.listener = listener
    }

    fun setCountryCodeData(data : List<CountryCode>){
        this.countryData = data as MutableList<CountryCode>
        countryAdapter.addData(countryData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.GDOTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mobile_registration,container,false)
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null){
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        }
        countryCodeSpinner.adapter = countryAdapter

        if (listener != null) {
            closeBtn.setOnClickListener(this)
            performBtn.setOnClickListener(this)
        } else {
            showException("Listener not yet initialized")
        }
    }

    data class CountryCode(
        val countryName : String ?= null,
        val countryCode : String ?= null,
        val countryFlag : Any ?= null
    )

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.closeBtn -> dismiss()
            R.id.performBtn -> if (countryData.isNullOrEmpty()) showException("Country Code is Not Yet Initialised") else { if (mobileinput.text.isNullOrEmpty()) showException("Mobile Data is missing") else listener?.onComplete(seletedItem(), mobileinput.text) }
        }
    }

    private fun seletedItem() : Any = if (countryCodeSpinner.adapter.isEmpty) showException("Adapter is empty") else "${( countryCodeSpinner.selectedItem as CountryCode ).countryCode}"

    private fun showException(message : String){
        RuntimeException(message)
    }
}