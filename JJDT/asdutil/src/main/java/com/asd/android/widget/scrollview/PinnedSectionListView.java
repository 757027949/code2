/*
 * Copyright (C) 2013 Sergej Shafarenka, halfbit.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file kt in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.asd.android.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;


/**
 * 分类listview
 * ListView, which is capable to pin section views at its top while the rest is still scrolled.
 */
public class PinnedSectionListView extends com.asd.android.widget.PinnedSectionListView {

    public PinnedSectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinnedSectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


 /*   float x, y;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                x = ev.getX();
//                y = ev.getY();
                setParentScrollAble(false);//当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview 停住不能滚动
            case MotionEvent.ACTION_MOVE:
                setParentScrollAble(false);
                break;
            case MotionEvent.ACTION_UP:
//                if (Math.abs(x - ev.getX()) > Math.abs(y - ev.getY())) {
//                    setParentScrollAble(false);
//                } else {
//                    setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限
//                }
                setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限
                break;
            case MotionEvent.ACTION_CANCEL:
                setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    *//**
     * 是否把滚动事件交给父scrollview
     *
     * @param flag
     *//*
    public void setParentScrollAble(boolean flag) {
        //requestDisallowInterceptTouchEvent  true:父组件，不要拦截我的事件
        try {
            getParent().requestDisallowInterceptTouchEvent(!flag);//这里的parentScrollView就是listview外面的那个scrollview
        } catch (Exception e) {
            getParent().requestDisallowInterceptTouchEvent(!flag);//这里的parentScrollView就是listview外面的那个scrollview
        }
    }*/
}
