package echo.com.surveys.view

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.util.AttributeSet
import android.view.MotionEvent
import fr.castorflex.android.verticalviewpager.VerticalViewPager

class CustomViewPager : VerticalViewPager {

    internal var mStartDragY: Float = 0.toFloat()
    internal var mOnSwipeOutListener: OnSwipeOutListener? = null
    var isPagingEnabled = true

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setOnSwipeOutListener(listener: OnSwipeOutListener) {
        mOnSwipeOutListener = listener
    }

    private fun onSwipeOutAtStart() {
        if (mOnSwipeOutListener != null) {
            mOnSwipeOutListener!!.onSwipeOutAtStart()
        }
    }

    private fun onSwipeOutAtEnd() {
        if (mOnSwipeOutListener != null) {
            mOnSwipeOutListener!!.onSwipeOutAtEnd()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action and MotionEventCompat.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> if (isPagingEnabled) {
                mStartDragY = ev.y
            }
        }
        return this.isPagingEnabled && super.onInterceptTouchEvent(ev)

    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {

        if (currentItem == 0 || currentItem == adapter.count - 1) {
            val action = ev.action
            val y = ev.y
            when (action and MotionEventCompat.ACTION_MASK) {
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                    if (currentItem == 0 && y > mStartDragY) {
                        if (y > mStartDragY + 150) {
                            if (isPagingEnabled) {
                                onSwipeOutAtStart()
                            }
                        }
                    }
                    if (currentItem == adapter.count - 1 && y < mStartDragY) {
                        if (y + 150 < mStartDragY) {
                            if (isPagingEnabled) {
                                onSwipeOutAtEnd()
                            }
                        }
                    }
                }
            }
        } else {
            mStartDragY = 0f
        }
        return super.onTouchEvent(ev)

    }

//    fun setPagingEnabled(isPagingEnabled: Boolean) {
//        this.isPagingEnabled = isPagingEnabled
//    }

    interface OnSwipeOutListener {
        fun onSwipeOutAtStart()
        fun onSwipeOutAtEnd()
    }

}
