package com.wee3ventures.fontier

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class HintValueView : LinearLayout {

    private var hintlabel : FontView ?= null
    private var textlabel : FontView ?= null
    private var hintValueCard : LinearLayout ?= null

    constructor(context: Context?) : super(context) { initView(context,null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { initView(context, attrs) }

    @SuppressLint("Recycle", "ResourceAsColor")
    private fun initView(context: Context?, attrs: AttributeSet?){
        val a = context?.obtainStyledAttributes(attrs,R.styleable.HintValueView)
        val view = (context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.layout_label_value_text_view,this,true)
        hintlabel = view.findViewById(R.id.hintlabel)
        textlabel = view.findViewById(R.id.valuelabel)
        hintValueCard = view.findViewById(R.id.hintvalueCard)
        a?.getString(R.styleable.HintValueView_android_hint)?.let { sethint(it) }
        a?.getString(R.styleable.HintValueView_android_text)?.let { setText(it) }
        a?.getResourceId(R.styleable.HintValueView_android_background,0)?.let { setDrawableBakground(it) }
        a?.recycle()
    }

    fun setDrawableBakground(it: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            hintValueCard?.background = ContextCompat.getDrawable(context,it)
        } else {
            hintValueCard?.setBackgroundResource(it)
        }
    }

    fun setText(it: String) {
        textlabel?.text = "$it"
    }

    fun sethint(it: String) {
        hintlabel?.text = "$it"
    }
}