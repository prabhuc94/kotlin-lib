package com.wee3ventures.fontier.widget.keyboard

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import com.google.android.material.button.MaterialButton
import com.wee3ventures.fontier.FontView
import com.wee3ventures.fontier.R
import com.wee3ventures.fontier.utils.ChangeColor
import com.wee3ventures.fontier.utils.Utility

class Keyboard : TableLayout, View.OnClickListener {

    interface onClickListener {
        fun onClick(view: View, pressedValue : Any, totalValue : Any?)
        fun onBackspave(view: View, lastValue : Any?)
        fun onClear(view: View, isClear : Boolean)
    }

    private lateinit var oneView : FontView
    private lateinit var twoView : FontView
    private lateinit var threeView : FontView
    private lateinit var fourView : FontView
    private lateinit var fiveView : FontView
    private lateinit var sixView : FontView
    private lateinit var sevenView : FontView
    private lateinit var eightView : FontView
    private lateinit var nineView : FontView
    private lateinit var zeroView : FontView

    private lateinit var clearBtn : MaterialButton

    private lateinit var backspace : ImageView

    private lateinit var gridLayout: TableLayout
    private lateinit var row1View : TableRow
    private lateinit var row2View : TableRow
    private lateinit var row3View : TableRow
    private lateinit var row4View : TableRow

    private var lastValue : Any ?= null

    private var listener : Keyboard.onClickListener ?= null

    fun setOnClickListener(listener: onClickListener){
        this.listener = listener
    }

    constructor(context: Context?) : super(context) {  this.initView(context = context,attrs = null) }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { this.initView(context, attrs) }

    private fun initView(context: Context?, attrs: AttributeSet?){
        val attributeSet = context?.obtainStyledAttributes(attrs, R.styleable.Keyboard,0,0)
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_keyboard,this,true)
        this.oneView = view.findViewById(R.id.key_1)
        this.twoView = view.findViewById(R.id.key_2)
        this.threeView = view.findViewById(R.id.key_3)
        this.fourView = view.findViewById(R.id.key_4)
        this.fiveView = view.findViewById(R.id.key_5)
        this.sixView = view.findViewById(R.id.key_6)
        this.sevenView = view.findViewById(R.id.key_7)
        this.eightView = view.findViewById(R.id.key_8)
        this.nineView = view.findViewById(R.id.key_9)
        this.zeroView = view.findViewById(R.id.key_0)

        this.clearBtn = view.findViewById(R.id.key_clear)
        this.backspace = view.findViewById(R.id.key_backspace)

        this.gridLayout = view.findViewById(R.id.key_grid)

        this.row1View = view.findViewById(R.id.key_row_1)
        this.row2View = view.findViewById(R.id.key_row_2)
        this.row3View = view.findViewById(R.id.key_row_3)
        this.row4View = view.findViewById(R.id.key_row_4)


        //Click Listener Initialising
        this.oneView.setOnClickListener(this)
        this.twoView.setOnClickListener(this)
        this.threeView.setOnClickListener(this)
        this.fourView.setOnClickListener(this)
        this.fiveView.setOnClickListener(this)
        this.sixView.setOnClickListener(this)
        this.sevenView.setOnClickListener(this)
        this.eightView.setOnClickListener(this)
        this.nineView.setOnClickListener(this)
        this.zeroView.setOnClickListener(this)

        this.clearBtn.setOnClickListener(this)
        this.backspace.setOnClickListener(this)

        attributeSet?.getColor(R.styleable.Keyboard_tintColor, resources.getColor(R.color.grey_700))?.let { this.setTintColor(color = it) }
        attributeSet?.getColor(R.styleable.Keyboard_dividerColor, resources.getColor(android.R.color.transparent))?.let { this.dividerColor(color = it) }
        attributeSet?.getColor(R.styleable.Keyboard_backgroundColor, resources.getColor(android.R.color.transparent))?.let { this.backgroundColor(it) }

        attributeSet?.recycle()
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.key_backspace -> if (this@Keyboard.listener != null) this@Keyboard.listener?.onBackspave(v, this.deappend()) else showException()
            R.id.key_clear -> if ( this@Keyboard.listener != null ) this@Keyboard.listener?.onClear(v,true) else showException()
            R.id.key_1 -> if ( this@Keyboard.listener != null ) { this.append(1) ; this.listerVal(v,1) } else showException()
            R.id.key_2 -> if ( this@Keyboard.listener != null ) { this.append(2) ; this.listerVal(v,2) } else showException()
            R.id.key_3 -> if ( this@Keyboard.listener != null ) { this.append(3) ; this.listerVal(v,3) } else showException()
            R.id.key_4 -> if ( this@Keyboard.listener != null ) { this.append(4) ; this.listerVal(v,4) } else showException()
            R.id.key_5 -> if ( this@Keyboard.listener != null ) { this.append(5) ; this.listerVal(v,5) } else showException()
            R.id.key_6 -> if ( this@Keyboard.listener != null ) { this.append(6) ; this.listerVal(v,6) } else showException()
            R.id.key_7 -> if ( this@Keyboard.listener != null ) { this.append(7) ; this.listerVal(v,7) } else showException()
            R.id.key_8 -> if ( this@Keyboard.listener != null ) { this.append(8) ; this.listerVal(v,8) } else showException()
            R.id.key_9 -> if ( this@Keyboard.listener != null ) { this.append(9) ; this.listerVal(v,9) } else showException()
            R.id.key_0 -> if ( this@Keyboard.listener != null ) { this.append(0) ; this.listerVal(v,0) } else showException()
        }
    }

    private fun listerVal(view: View,input : Any){
        this@Keyboard.listener?.onClick(view, input, lastValue)
    }

    private fun showException(message : Any ?= "KeyboardListener not yet initialized"){
        RuntimeException("$message")
    }

    fun setTintColor(color : Int){
        ChangeColor.apply {
            this.fontView(listOf( oneView,twoView,threeView,fourView, fiveView, sixView, sevenView, eightView, nineView, zeroView ),color)
            this.imageView(this@Keyboard.backspace,color)
            this.buttonView(this@Keyboard.clearBtn,color)
        }
    }

    fun backgroundColor(color: Int){
        this.gridLayout.setBackgroundColor(color)
    }

    fun dividerColor(color: Int){
        this.gridLayout.dividerDrawable = ColorDrawable(color)
        this.row1View.dividerDrawable = ColorDrawable(color)
        this.row2View.dividerDrawable = ColorDrawable(color)
        this.row3View.dividerDrawable = ColorDrawable(color)
        this.row4View.dividerDrawable = ColorDrawable(color)
    }

    //Appending the Value to the LastValue
    private fun append(value : Any) {
        if (lastValue == null) this.lastValue = value else this.lastValue = "$lastValue$value"
    }

    //Deappending the Value to the LastValue
    private fun deappend() : Any?{
        if (lastValue != null) {
            this.lastValue = Utility.removeLastChar("$lastValue")
        }
        return this.lastValue
    }

}