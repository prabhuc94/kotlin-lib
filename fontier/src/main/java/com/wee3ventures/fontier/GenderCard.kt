package com.wee3ventures.fontier

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.wee3ventures.fontier.`interface`.GenderListener
import com.wee3ventures.fontier.utils.GlideApp
import com.wee3ventures.fontier.utils.Utility

class GenderCard : LinearLayout {
    private var femalelabel : FontView ?= null
    private var malelabel : FontView ?= null
    private var maleIcon : ImageView ?= null
    private var femaleIcon : ImageView ?= null
    private var maleBtn : LinearLayout ?= null
    private var femaleBtn : LinearLayout ?= null
    private var hintlabel : FontView ?= null
    private var listener : GenderListener ?= null

    constructor(context: Context?) : super(context) { initView(context,null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { initView(context, attrs) }

    @SuppressLint("Recycle")
    private fun initView(context: Context?, attributeSet: AttributeSet?){
        val a = context?.obtainStyledAttributes(attributeSet,R.styleable.GenderCard)
        val view = (context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.layout_gender_color,this,true)
        maleIcon = view.findViewById(R.id.maleicon)
        femaleIcon = view.findViewById(R.id.femaleicon)
        malelabel = view.findViewById(R.id.malelabel)
        femalelabel = view.findViewById(R.id.femalelabel)
        maleBtn = view.findViewById(R.id.maleBtn)
        femaleBtn = view.findViewById(R.id.femaleBtn)
        hintlabel = view.findViewById(R.id.hintlabel)

        a?.getBoolean(R.styleable.GenderCard_male_selected,false)?.let { maleSelector(it) }
        a?.getBoolean(R.styleable.GenderCard_female_selected,false)?.let { femaleSelector(it) }
        //a?.getInteger(R.styleable.GenderCard_font_type, EditorInfo.TYPE_NULL)?.let { setFont(it) }
        //a?.getResourceId(R.styleable.GenderCard_male_icon,0)?.let { setMaleIcon(resources.getDrawable(it)) }
        //a?.getResourceId(R.styleable.GenderCard_female_icon,0)?.let { setFemaleIcon(resources.getDrawable(it)) }
        a?.getString(R.styleable.GenderCard_male_text)?.let { setMaleLabel(it) }
        a?.getString(R.styleable.GenderCard_female_text)?.let { setFemaleLabel(it) }
        a?.getString(R.styleable.GenderCard_android_hint)?.let { sethint(it) }
        a?.getBoolean(R.styleable.GenderCard_is_hint, false)?.let { isHint(it) }

        onClicker()
        a?.recycle()
    }

    private fun onClicker(){
        if(maleBtn != null && femaleBtn != null){
//            if (listener != null){
//                malelabel?.isPressed = maleBtn?.isPressed!!
//                femalelabel?.isPressed = femaleBtn?.isPressed!!
//            }
            maleBtn?.setOnClickListener {
                if (listener != null){
                    listener?.onGender(it,"${malelabel?.text}")
                    maleSelector(true)
                }
            }

            femaleBtn?.setOnClickListener {
                if (listener != null){
                    listener?.onGender(it,"${femalelabel?.text}")
                    femaleSelector(true)
                }
            }

            maleBtn?.setOnLongClickListener {
                Utility.showToolTip(it,"${malelabel?.text}")
                return@setOnLongClickListener  true
            }

            femaleBtn?.setOnLongClickListener {
                Utility.showToolTip(it,"${femalelabel?.text}")
                return@setOnLongClickListener  true
            }
        }
    }

    fun onGenderListener(listener: GenderListener){
        this.listener = listener
    }

    fun setFont(fontName: Int){
        malelabel?.setFont(fontName)
        femalelabel?.setFont(fontName)
    }

    fun setMaleIcon(drawable : Any){
        if (maleIcon != null){
            GlideApp.with(this)
                .load(drawable)
                .into(maleIcon!!)
        }
    }

    fun setFemaleIcon(drawable: Any){
        if(femaleIcon != null){
            GlideApp.with(this)
                .load(drawable)
                .into(femaleIcon!!)
        }
    }

    fun setMaleLabel(input : String?){
        if (malelabel != null && input != null) {
            malelabel?.text = input
        }
    }

    fun setFemaleLabel(input : String?){
        if (femalelabel != null && input != null) {
            femalelabel?.text = input
        }
    }

    fun sethint(hint : String){
        if (hintlabel != null){
            hintlabel?.hint = hint
        }
    }

    fun isHint(status: Boolean){
        if (hintlabel != null) {
            if (status) {
                hintlabel?.visibility = View.VISIBLE
            } else {
                hintlabel?.visibility = View.GONE
            }
        }
    }

    fun maleSelector(status : Boolean){
        maleBtn?.isSelected = status
        malelabel?.isSelected = status
        femaleBtn?.isSelected = status.not()
        femalelabel?.isSelected = status.not()
    }

    fun femaleSelector(status : Boolean){
        maleBtn?.isSelected = status.not()
        malelabel?.isSelected = status.not()
        femaleBtn?.isSelected = status
        femalelabel?.isSelected = status
    }


}