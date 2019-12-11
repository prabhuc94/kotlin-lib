package com.wee3ventures.fontier.ocr_reader.Ocr

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.google.android.gms.vision.text.TextBlock
import com.wee3ventures.fontier.ocr_reader.ui.GraphicOverlay
import com.wee3ventures.fontier.ocr_reader.ui.GraphicOverlay.Graphic

class OcrGraphic : Graphic {

    var mId = 0

    val TEXT_COLOR = Color.WHITE

    var sRectPaint: Paint? = null
    var sTextPaint: Paint? = null
    var mText: TextBlock? = null

    constructor(mOverlay: GraphicOverlay<*>, text: TextBlock?) : super(mOverlay){
        mText = text
        if (sRectPaint == null) {
            sRectPaint = Paint()
            sRectPaint!!.color = TEXT_COLOR
            sRectPaint!!.style = Paint.Style.STROKE
            sRectPaint!!.strokeWidth = 4.0f
        }
        if (sTextPaint == null) {
            sTextPaint = Paint()
            sTextPaint!!.color = TEXT_COLOR
            sTextPaint!!.textSize = 54.0f
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()
    }

    fun getId(): Int {
        return mId
    }

    fun setId(id: Int) {
        this.mId = id
    }

    fun getTextBlock(): TextBlock? {
        return mText
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    override fun contains(x: Float, y: Float): Boolean {
        val text = mText ?: return false
        val rect = RectF(text.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        return rect.left < x && rect.right > x && rect.top < y && rect.bottom > y
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas?) {
        val text = mText ?: return
        // Draws the bounding box around the TextBlock.
        val rect = RectF(text.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        canvas?.drawRect(rect, sRectPaint)
        // Break the text into multiple lines and draw each one according to its own bounding box.
        val textComponents =
            text.components
        for (currentText in textComponents) {
            val left: Float = translateX(currentText.boundingBox.left.toFloat())
            val bottom: Float = translateY(currentText.boundingBox.bottom.toFloat())
            canvas?.drawText(currentText.value, left, bottom, sTextPaint)
        }
    }
}
