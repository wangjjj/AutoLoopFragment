package com.cocoon.jay.autoloopfragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class LoopViewPager extends ViewPager {



    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoopViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            if(mListener != null) mListener.next();
        } else if (action == MotionEvent.ACTION_DOWN) {
            if(mListener != null) mListener.stop();
        }
        return super.dispatchTouchEvent(ev);
    }


    private OnTouchViewListener mListener;
    public void setOnTouchViewListener(OnTouchViewListener mListener){
        this.mListener = mListener;
    }
    /**
     *响应事件
     */
    public interface OnTouchViewListener{
        void next();
        void stop();
    }


}
