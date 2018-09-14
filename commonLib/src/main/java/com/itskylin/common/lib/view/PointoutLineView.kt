package com.itskylin.common.lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.itskylin.common.lib.R


/**
 * @author Sky Lin
 * @version V1.0
 * @Package CommonLib/com.konying.commonlib.view
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/7/5 11:06
 */
class PointoutLineView : View {

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var lineColor: Int = 0
    private var lineWidth: Float = 0f

    private lateinit var linePaint: Paint

    //padding
    private var leftPadding: Int = 0
    private var topPadding: Int = 0
    private var rightPadding: Int = 0
    private var bottomPadding: Int = 0


    //set style
    private var styleInt: Int = 0

    var isShowleft: Boolean = false
    var isShowTop: Boolean = false
    var isShowRight: Boolean = false
    var isShowBottom: Boolean = false

    enum class Style {
        TOP, MIDDLE, BOTTOM
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (attrs != null) {
            @SuppressLint("Recycle") val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointoutLineView)
            lineColor = typedArray.getColor(R.styleable.PointoutLineView_lineColor, DEFAULT_COLOR)
            lineWidth = typedArray.getDimension(R.styleable.PointoutLineView_lineWidth, DEFAULT_LINE_WIDTH)
            styleInt = typedArray.getInt(R.styleable.PointoutLineView_showStyle, 2)
            style
        }
        linePaint = Paint()
        linePaint.color = lineColor
        linePaint.strokeWidth = lineWidth
        linePaint.isAntiAlias = true
        linePaint.strokeCap = Paint.Cap.BUTT
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        this.leftPadding = left
        this.topPadding = top
        this.rightPadding = right
        this.bottomPadding = bottom
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //top
        if (isShowTop) canvas.drawLine((mWidth / 2).toFloat(), top.toFloat(), (mWidth / 2).toFloat(), (mHeight / 2).toFloat(), linePaint)//red
        //        canvas.drawLine(mWidth / 2, getTop(), mWidth / 2, mHeight / 2, linePaintTop);//red
        //bottom
        if (isShowBottom) canvas.drawLine((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mWidth / 2).toFloat(), mHeight.toFloat(), linePaint)//
        //        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight, linePaintBottom);//

        //leftPadding
        if (isShowleft) canvas.drawLine(leftPadding.toFloat(), (mHeight / 2).toFloat(), (mWidth / 2).toFloat(), (mHeight / 2).toFloat(), linePaint)
        //        canvas.drawLine(leftPadding, mHeight / 2, mWidth / 2, mHeight / 2, linePaintLeft);

        //right
        if (isShowRight) canvas.drawLine((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mWidth.toFloat(), (mHeight / 2).toFloat(), linePaint)
        //        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth, mHeight / 2, linePaintRight);
    }

    var style: Style? = null
        get() = field
        set(value) = when (value) {
            PointoutLineView.Style.TOP -> {
                field = value
                isShowleft = false
                isShowTop = false
                isShowRight = true
                isShowBottom = true
            }
            PointoutLineView.Style.BOTTOM -> {
                field = value
                isShowleft = false
                isShowTop = true
                isShowRight = true
                isShowBottom = false
            }
            PointoutLineView.Style.MIDDLE -> {
                field = value
                isShowleft = false
                isShowTop = true
                isShowRight = true
                isShowBottom = true
            }
            else -> {
                field = value
                isShowleft = false
                isShowTop = true
                isShowRight = true
                isShowBottom = true
            }
        }


    companion object {
        private val DEFAULT_SHOW = true
        private val DEFAULT_COLOR = Color.BLACK
        private val DEFAULT_LINE_WIDTH = 2f
    }
}
