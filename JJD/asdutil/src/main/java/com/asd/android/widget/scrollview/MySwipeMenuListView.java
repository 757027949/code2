/*
package com.asd.android.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.asd.android.util.gesture.BuileGestureExt;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.orhanobut.logger.Logger;

*/
/**
 * Created by ASD on 2016/1/16.
 *//*

public class MySwipeMenuListView extends SwipeMenuListView {
    ScrollView scrollView;
    Context context;

    private GestureDetector gestureDetector;

    public MySwipeMenuListView(Context context) {
        super(context);
        this.context = context;
    }

    public MySwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public MySwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

   */
/* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }*//*


    public ScrollView getScrollView() {
        return scrollView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }


    float x, y;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = ev.getX();
                y = ev.getY();
                setParentScrollAble(false);//当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview 停住不能滚动
            case MotionEvent.ACTION_MOVE:
                setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限
                break;
            case MotionEvent.ACTION_UP:
                Logger.e("x====="+x+"=="+ev.getX()+"=="+Math.abs(x - ev.getX()));
                Logger.e("y====="+y+"=="+ev.getY()+"=="+Math.abs(y - ev.getY()));
                if (Math.abs(x - ev.getX()) > Math.abs(y - ev.getY())) {
                    setParentScrollAble(false);
                    Logger.e("false");
                } else {
                    setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限
                    Logger.e("true");
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    */
/**
     * 是否把滚动事件交给父scrollview
     *
     * @param flag
     *//*

    public void setParentScrollAble(boolean flag) {
        //requestDisallowInterceptTouchEvent  true:父组件，不要拦截我的事件
        try {
            scrollView.requestDisallowInterceptTouchEvent(!flag);//这里的parentScrollView就是listview外面的那个scrollview
        } catch (Exception e) {
            getParent().requestDisallowInterceptTouchEvent(!flag);//这里的parentScrollView就是listview外面的那个scrollview
        }
    }
}
*/
