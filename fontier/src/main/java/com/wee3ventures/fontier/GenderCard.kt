package com.wee3ventures.fontier

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.wee3ventures.fontier.`interface`.GenderListener
import com.wee3ventures.fontier.utils.GlideApp
import com.wee3ventures.fontier.utils.Utility

class GenderCard : ConstraintLayout {
    private var femalelabel : FontView ?= null
    private var malelabel : FontView ?= null
    private var maleIcon : ImageView ?= null
    private var femaleIcon : ImageView ?= null
    private var maleBtn : LinearLayout ?= null
    private var femaleBtn : LinearLayout ?= null
    private var hintlabel : FontView ?= null
    private var listener : GenderListener ?= null
    private lateinit var tag : String

    constructor(context: Context?) : super(context!!) { initView(context = context,attributeSet = null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) { initView(context = context,attributeSet = attrs) }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) { initView(context = context,attributeSet = attrs) }


    @SuppressLint("Recycle", "ResourceAsColor")
    private fun initView(context: Context?, attributeSet: AttributeSet?){
        val a = context?.obtainStyledAttributes(attributeSet,R.styleable.GenderCard)
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_gender_color,this,true)
        maleIcon = view.findViewById(R.id.maleicon)
        femaleIcon = view.findViewById(R.id.femaleicon)
        malelabel = view.findViewById(R.id.malelabel)
        femalelabel = view.findViewById(R.id.femalelabel)
        maleBtn = view.findViewById(R.id.maleBtn)
        femaleBtn = view.findViewById(R.id.femaleBtn)
        hintlabel = view.findViewById(R.id.hintlabel)

        maleBtn?.setOnClickListener {
            tag = "${it.tag}"
            if (listener != null){
                listener?.onGender(it,"${malelabel?.text}")
                maleSelector(true)
            }
        }

        femaleBtn?.setOnClickListener {
            tag = "${it.tag}"
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

        a?.getBoolean(R.styleable.GenderCard_male_selected,false)?.let { maleSelector(it) }
        a?.getBoolean(R.styleable.GenderCard_female_selected,false)?.let { femaleSelector(it) }
        a?.getDimensionPixelSize(R.styleable.GenderCard_android_textSize,16)?.let { setTextSize(convertPixelsToDp(it.toFloat(),context)) }
        a?.getDimensionPixelSize(R.styleable.GenderCard_genderHintSize,13)?.let { setHintSize(convertPixelsToDp(it.toFloat(),context)) }
        a?.getString(R.styleable.GenderCard_male_text)?.let { setMaleLabel(it) }
        a?.getString(R.styleable.GenderCard_female_text)?.let { setFemaleLabel(it) }
        a?.getString(R.styleable.GenderCard_android_hint)?.let { sethint(it) }
        a?.getBoolean(R.styleable.GenderCard_is_hint, false)?.let { isHint(it) }
        a?.getInteger(R.styleable.GenderCard_genderfont,EditorInfo.TYPE_NULL)?.let { setFont(it) }
        a?.getColor(R.styleable.GenderCard_text_Colour,R.color.grey_800)?.let { setTextColor(it) }
        a?.getColor(R.styleable.GenderCard_hint_Colour,R.color.grey_500)?.let { setHintColor(it) }

        a?.recycle()
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun setTextSize(size : Float){
        if (malelabel != null && femalelabel != null) {
            malelabel?.textSize = size ; femalelabel?.textSize = size
        }
    }

    fun setHintSize(size: Float){
        if (hintlabel != null)
            hintlabel?.textSize = size
    }

    fun setHintColor(colorId : Int){
        if (hintlabel != null){
            hintlabel?.setTextColor(resources.getColor(colorId))
        }
    }

    fun setTextColor(colorId : Int){
        if (isView()){
            malelabel?.setTextColor(resources.getColor(colorId))
            femalelabel?.setTextColor(resources.getColor(colorId))
        }
    }

    fun setTextColor(colorId : String){
        if (isView()){
            malelabel?.setTextColor(Color.parseColor(colorId))
            femalelabel?.setTextColor(Color.parseColor(colorId))
        }
    }

    fun isSeleted() : String = this.tag

    fun onGenderListener(listener: GenderListener){
        this.listener = listener
    }

    fun isEditMode(status: Boolean){
        maleBtn?.apply {
            this.isClickable = status
            this.isEnabled = status
        }
        femaleBtn?.apply {
            this.isClickable = status
            this.isEnabled = status
        }
    }

    fun isEditMode() : Boolean = maleBtn?.isClickable!! || femaleBtn?.isClickable!!

    fun setFont(fontName: Int){
        malelabel?.setFont(fontName)
        femalelabel?.setFont(fontName)
    }

    fun setMaleIcon(drawable : Any){
        maleIcon?.let {
            GlideApp.with(this)
                .load(drawable)
                .into(it)
        }
    }

    fun setFemaleIcon(drawable: Any){
        femaleIcon?.let {
            GlideApp.with(this)
                .load(drawable)
                .into(it)
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
            hintlabel?.text = hint
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
        if (isView()) {
            activeMale()
            deactiveFemale()
        }
    }

    fun femaleSelector(status : Boolean){
        if (isView()) {
            deactiveMale()
            activeFemale()
        }
    }

    fun isMale(status : Boolean){
        if (isView()) {
            activeMale()
            deactiveFemale()
        }
    }

    fun isFemale(status : Boolean){
        if (isView()) {
            deactiveMale()
            activeFemale()
        }
    }

    private fun activeMale() {
        maleBtn?.isSelected = true
        malelabel?.apply {
            this.isActivated = true
            this.isSelected = true
            this.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

    private fun deactiveMale() {
        maleBtn?.isSelected = false
        malelabel?.apply {
            this.isActivated = false
            this.isSelected = false
            this.setTextColor(Color.parseColor("#424242"))
        }
    }

    private  fun activeFemale() {
        femaleBtn?.isSelected = true
        femalelabel?.apply {
            this.isActivated = true
            this.isSelected = true
            this.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

    private  fun deactiveFemale() {
        femaleBtn?.isSelected = false
        femalelabel?.apply {
            this.isActivated = false
            this.isSelected = false
            this.setTextColor(Color.parseColor("#424242"))
        }
    }

    fun isMale() : Boolean{
        return maleBtn?.isSelected!! || malelabel?.isSelected!!
    }

    fun isFemale() : Boolean{
        return femaleBtn?.isSelected!! || femalelabel?.isSelected!!
    }

    private fun isView() : Boolean = maleBtn != null && malelabel != null && femaleBtn != null && femalelabel != null
}