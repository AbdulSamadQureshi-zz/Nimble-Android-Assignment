package echo.com.surveys.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class CustomViewPager extends VerticalViewPager {

    float mStartDragY;
    OnSwipeOutListener mOnSwipeOutListener;
    protected boolean isPagingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mOnSwipeOutListener = listener;
    }

    private void onSwipeOutAtStart() {
        if (mOnSwipeOutListener!=null) {
            mOnSwipeOutListener.onSwipeOutAtStart();
        }
    }

    private void onSwipeOutAtEnd() {
        if (mOnSwipeOutListener!=null) {
            mOnSwipeOutListener.onSwipeOutAtEnd();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch(ev.getAction() & MotionEventCompat.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                if(isPagingEnabled) {
                    mStartDragY = ev.getY();
                }
                break;
        }
        return this.isPagingEnabled && super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){

        if(getCurrentItem()==0 || getCurrentItem()==getAdapter().getCount()-1){
            final int action = ev.getAction();
            float y = ev.getY();
            switch(action & MotionEventCompat.ACTION_MASK){
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    if (getCurrentItem()==0 && y>mStartDragY) {
                        if(y > mStartDragY + 150) {
                            if(isPagingEnabled) {
                                onSwipeOutAtStart();
                            }
                        }
                    }
                    if (getCurrentItem()==getAdapter().getCount()-1 && y<mStartDragY){
                        if(y +150< mStartDragY) {
                            if(isPagingEnabled) {
                                onSwipeOutAtEnd();
                            }
                        }
                    }
                    break;
            }
        }else{
            mStartDragY=0;
        }
        return super.onTouchEvent(ev);

    }

    public void setPagingEnabled(boolean isPagingEnabled) {
        this.isPagingEnabled = isPagingEnabled;
    }

    public interface OnSwipeOutListener {
        void onSwipeOutAtStart();
        void onSwipeOutAtEnd();
    }

}
