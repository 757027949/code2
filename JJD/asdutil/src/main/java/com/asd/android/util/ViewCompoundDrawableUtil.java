package com.asd.android.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.asd.android.util.gesture.GestureUtils;

/**
 * 控件图片处理
 * Created by ASD on 2016/1/7.
 */
public class ViewCompoundDrawableUtil {
    float drableSize = 0.08f;
    float textSize = 0.03f;
    Context context;

    public ViewCompoundDrawableUtil(Context context) {
        this.context = context;
    }

    public ViewCompoundDrawableUtil(float drableSize, float textSize, Context context) {
        this.drableSize = drableSize;
        this.textSize = textSize;
        this.context = context;
    }

    /**
     * 设置TextViewCompoundDrawable属性
     *
     * @param view        TextView
     * @param direction   图片位置  0：left 1:top 2：right 3:bottom
     * @param mdrableSize 图片大小  屏幕宽度百分比  例：0.08
     * @param mtextSize   字体大小  屏幕宽度百分比 0:不改变 例：0.03
     */
    public void initTextViewCompoundDrawable(TextView view, int direction, float mdrableSize, float mtextSize) {
        try {
            Drawable[] drawables = view.getCompoundDrawables();
            int j = (int) (GestureUtils.getScreenPix(context).widthPixels * mdrableSize);
            drawables[direction].setBounds(0, 0, j, j);
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        } catch (Exception e) {
        }
        if (0f != mtextSize) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (GestureUtils.getScreenPix(context).widthPixels * mtextSize));
        }
    }

    /**
     * 设置TextViewCompoundDrawable属性
     *
     * @param view        TextView
     * @param direction   图片位置  0：left 1:top 2：right 3:bottom
     * @param mdrableSize 图片大小    例：0.08
     * @param mtextSize   字体大小   0:不改变 例：0.03
     * @param type        0:  宽度  1：高度  （百分比）
     */
    public void initTextViewCompoundDrawable(TextView view, int direction, float mdrableSize, float mtextSize, int type) {
        try {
            Drawable[] drawables = view.getCompoundDrawables();
            int j = (int) (0 == type ? GestureUtils.getScreenPix(context).widthPixels * mdrableSize : GestureUtils.getScreenPix(context).heightPixels * mdrableSize);
            drawables[direction].setBounds(0, 0, j, j);
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        } catch (Exception e) {
        }
        if (0f != mtextSize) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (0 == type ? GestureUtils.getScreenPix(context).widthPixels * mtextSize : GestureUtils.getScreenPix(context).widthPixels * mtextSize));
        }
    }

    /**
     * 设置TextViewCompoundDrawable属性
     *
     * @param view      TextView
     * @param direction 图片位置  0：left 1:top 2：right 3:bottom
     *                  drableSize 图片大小  屏幕宽度百分比  例：0.08
     *                  textSize   字体大小  屏幕宽度百分比 例：0.03
     */
    public void initTextViewCompoundDrawable(TextView view, int direction) {
        try {
            Drawable[] drawables = view.getCompoundDrawables();
            int j = (int) (GestureUtils.getScreenPix(context).widthPixels * drableSize);
            drawables[direction].setBounds(0, 0, j, j);
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        } catch (Exception e) {
        }
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (GestureUtils.getScreenPix(context).widthPixels * textSize));
    }

    /**
     * 设置TextViewCompoundDrawable属性
     *
     * @param view      TextView
     * @param direction 图片位置  0：left 1:top 2：right 3:bottom
     * @param dSize     图片的大小（PX）
     */
    public void initTextViewCompoundDrawable(TextView view, int direction, int dSize) {
        try {
            Drawable[] drawables = view.getCompoundDrawables();
//            int j = (int) (GestureUtils.getScreenPix(context).widthPixels * drableSize);
//            drawables[direction].setBounds(0, 0, j, j);
            drawables[direction].setBounds(0, 0, dSize, dSize);
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        } catch (Exception e) {
        }
    }

}
