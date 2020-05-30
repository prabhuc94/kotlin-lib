package com.wee3ventures.fontier.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wee3ventures.fontier.FontView
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.`interface`.CheckListener
import com.wee3ventures.fontier.utils.GlideApp
import org.jetbrains.anko.backgroundDrawable

@SuppressLint("ViewConstructor")
class CheckView : LinearLayout {
    private var title : FontView ?= null
    private var subtitle : FontView ?= null
    private var checker : ConstraintLayout ?= null
    private var icon : ImageView ?= null
    private var tick : ImageView ?= null
    private var srcHolder : CardView?= null
    private var listener : CheckListener ?= null
    constructor(context: Context?) : super(context) { initView(context,null) }
    constructor(context: Context?, attrs: AttributeSet?) :super(context, attrs) { initView(context = context,attrs = attrs) }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {initView(context,attrs) }

    @SuppressLint("Recycle")
    private fun initView(context: Context?, attrs: AttributeSet?) {
        val a = context?.obtainStyledAttributes(attrs, R.styleable.CheckView, 0, 0)
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_check_view,this,true)
        title = view.findViewById(R.id.textlabel)
        icon = view.findViewById(R.id.iconView)
        tick = view.findViewById(R.id.checkMark)
        subtitle = view.findViewById(R.id.underDescription)
        checker = view.findViewById(R.id.checker)
        srcHolder = view.findViewById(R.id.iconHolder)

        checker?.setOnClickListener { if (listener != null){ listener?.onCheck(this) } }
        a?.getDrawable(R.styleable.CheckView_tickView)?.let { setTick(it) }
        a?.getDrawable(R.styleable.CheckView_srcView)?.let { setIcon(it) }
        a?.getBoolean(R.styleable.CheckView_is_tick,false)?.let { isTick(it) }
        a?.getString(R.styleable.CheckView_textView)?.let { setTitle(it) }
        a?.getString(R.styleable.CheckView_subtitle)?.let { setSubtitle(it) }
        a?.getDimension(R.styleable.CheckView_srcCorner,30f)?.let { setCornerforSrc(it) }

        a?.recycle()
    }

    fun setCornerforSrc(value : Float){
        srcHolder?.let { it.radius = value }
    }

    fun setIconBackground(drawable : Drawable?){
        icon?.let { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) background = drawable else backgroundDrawable = drawable }
    }

    fun setIconBackground(drawable: Int){
        icon?.let { setBackgroundResource(drawable) }
    }

    fun setIcon(inputIcon : Any){
        icon?.let { GlideApp.with(this).load(inputIcon).into(it) }
    }

    fun setIcon(inputIcon : Drawable){
        icon?.let { GlideApp.with(this).load(inputIcon).into(it) }
    }

    fun setTick(tickIcon : Drawable){
        tick?.let { GlideApp.with(this).load(tickIcon).into(it) }
    }

    fun setTick(tickIcon : Any){
        tick?.let { GlideApp.with(this).load(tickIcon).into(it) }
    }

    fun setTitle(titleValue : Any){
        title?.let { it.text = "$titleValue" }
    }

    fun setSubtitle(subtitleValue : Any){
        subtitle?.let { it.text = "$subtitleValue" }
    }

    fun disableSubtitle(){
        subtitle?.let { it.visibility = View.GONE }
    }

    fun disableTick(){
        tick?.let { it.visibility = View.GONE }
    }

    fun enableTick(){
        tick?.let { it.visibility = View.VISIBLE }
    }

    fun enableSubTitle(){
        subtitle?.let { it.visibility = View.VISIBLE }
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

    fun title() : String = "${title?.text}"

    fun subtitle() : String = "${subtitle?.text}"


}