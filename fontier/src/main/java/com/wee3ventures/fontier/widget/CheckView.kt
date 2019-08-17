package com.wee3ventures.fontier.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wee3ventures.fontier.FontView
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.`interface`.CheckListener
import com.wee3ventures.fontier.utils.GlideApp

@SuppressLint("ViewConstructor")
class CheckView : ConstraintLayout {
    private var title : FontView ?= null
    private var subtitle : FontView ?= null
    private var checker : ConstraintLayout ?= null
    private var icon : ImageView ?= null
    private var tick : ImageView ?= null
    private var listener : CheckListener ?= null

    constructor(context: Context?, attrs: AttributeSet?) :super(context, attrs) {
        initView(context = context,attrs = attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context,attrs)
    }

    constructor(context: Context?) : super(context) {
        initView(context,null)
    }

    @SuppressLint("Recycle")
    private fun initView(context: Context?, attrs: AttributeSet?) {
        val a = context?.obtainStyledAttributes(attrs, R.styleable.CheckView, 0, 0)
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_check_view,this,true)
        val tickMark = a?.getResourceId(R.styleable.CheckView_tickView,0)
        val iconSrc = a?.getResourceId(R.styleable.CheckView_srcView, 0)
        val iconBackground = a?.getResourceId(R.styleable.CheckView_backgroundView, 0)
        val titleSrc = a?.getString(R.styleable.CheckView_textView)
        val subtitleSrc = a?.getString(R.styleable.CheckView_subtitle)
        val isTick = a?.getBoolean(R.styleable.CheckView_is_tick,false)
         title = view.findViewById<FontView>(R.id.textlabel)
         icon = view.findViewById<ImageView>(R.id.iconView)
         tick = view.findViewById<ImageView>(R.id.checkMark)
         subtitle = view.findViewById<FontView>(R.id.underDescription)
         checker = view.findViewById<ConstraintLayout>(R.id.checker)
            checker?.setOnClickListener {
                if (listener != null){
                    listener?.onCheck(this)
                }
            }
        val tickSrc = resources.getDrawable(tickMark!!)
        val iconDrawable = resources.getDrawable(iconSrc!!)
        val backSrc = resources.getDrawable(iconBackground!!)
        setIconBackground(backSrc)
        setIcon(iconDrawable)
        isTick(isTick)
        setTitle("$titleSrc")
        setSubtitle("$subtitleSrc")
        setTick(tickSrc)
        a.recycle()
    }

    fun setIconBackground(drawable : Drawable?){
        if (icon != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                icon?.background = drawable
            } else {
                icon?.setBackgroundDrawable(drawable)
            }
        }
    }

    fun setIconBackground(drawable: Int){
        if (icon != null){
            icon?.setBackgroundResource(drawable)
        }
    }

    fun setIcon(inputIcon : Any){
        if (icon != null) {
            GlideApp.with(this.context)
                .load(inputIcon)
                .into(icon!!)
        }
    }

    fun setIcon(inputIcon : Drawable){
        if (icon != null) {
            GlideApp.with(this.context)
                .load(inputIcon)
                .into(icon!!)
        }
    }

    fun setTick(tickIcon : Any){
        if (tick != null) {
            GlideApp.with(this.context)
                .load(tickIcon)
                .into(tick!!)
        }
    }

    fun setTick(tickIcon : Drawable){
        if (tick != null) {
            GlideApp.with(this.context)
                .load(tickIcon)
                .into(tick!!)
        }
    }

    fun setTitle(titleValue : Any){
        if (title != null) {
            title?.text = "$titleValue"
        }
    }

    fun setSubtitle(subtitleValue : Any){
        if (subtitle != null) {
            subtitle?.text = "$subtitleValue"
        }
    }

    fun disableSubtitle(){
        if (subtitle != null) {
            subtitle?.visibility = View.GONE
        }
    }

    fun disableTick(){
        if (tick != null) {
            tick?.visibility = View.GONE
        }
    }

    fun enableTick(){
        if (tick != null) {
            tick?.visibility = View.VISIBLE
        }
    }

    fun enableSubTitle(){
        if (subtitle != null) {
            subtitle?.visibility = View.VISIBLE
        }
    }

    fun onCheckListener(listener : CheckListener){
        this.listener = listener
    }

    fun isTick(tickValue : Boolean?){
        if (tickValue!!){
            enableTick()
            disableSubtitle()
        } else {
            disableTick()
            enableSubTitle()
        }
    }


}