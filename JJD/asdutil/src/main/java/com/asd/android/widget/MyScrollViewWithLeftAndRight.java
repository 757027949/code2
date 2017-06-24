package com.asd.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 左右滑动事件冲突解决
 * Created by ASD on 2015/12/31.
 */
public class MyScrollViewWithLeftAndRight extends ScrollView{


        public MyScrollViewWithLeftAndRight(Context context) {
            super(context);
            mGestureDetector = new GestureDetector(new YScrollDetector());
            setFadingEdgeLength(0);
        }

        public MyScrollViewWithLeftAndRight(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            mGestureDetector = new GestureDetector(new YScrollDetector());
            setFadingEdgeLength(0);
        }

        public MyScrollViewWithLeftAndRight(Context context, AttributeSet attrs) {
            super(context, attrs);
            mGestureDetector = new GestureDetector(new YScrollDetector());
            setFadingEdgeLength(0);
        }

        private GestureDetector mGestureDetector;
        View.OnTouchListener mGestureListener;

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return super.onInterceptTouchEvent(ev)
                    && mGestureDetector.onTouchEvent(ev);
        }

        class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                if (distanceY != 0 && distanceX != 0) {

                }
                if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                    return true;
                }
                return false;
            }
        }

}
