package com.asd.android.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.asd.android.util.gesture.GestureUtils;

/**
 * Created by ASD on 2015/12/31.
 */
public class RadioGroupUtil {
    float drableSize = 0.08f;
    float textSize = 0.03f;
    Context context;

    public RadioGroupUtil(Context context) {
        this.context = context;
    }

    public RadioGroupUtil(float drableSize, float textSize, Context context) {
        this.drableSize = drableSize;
        this.textSize = textSize;
        this.context = context;
    }

    /**
     * 设置RadioGroup属性
     *
     * @param group      radiogroup
     * @param direction    图片位置  0：left 1:top 2：right 3:bottom
     * @param mdrableSize 图片大小  屏幕宽度百分比  例：0.08
     * @param mtextSize   字体大小  屏幕宽度百分比 例：0.03
     */
    public void initRadioGroupAttr(RadioGroup group,int direction, float mdrableSize, float mtextSize) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if (view instanceof RadioButton) {
                RadioButton radioButton = ((RadioButton) view);
                try {
                    Drawable[] drawables = radioButton.getCompoundDrawables();
                    int j = (int) (GestureUtils.getScreenPix(context).widthPixels * mdrableSize);
                    drawables[direction].setBounds(0, 0, j, j);
                    radioButton.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                } catch (Exception e) {
                }
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (GestureUtils.getScreenPix(context).widthPixels * mtextSize));
            }
        }
    }

    /**
     * 设置RadioGroup属性
     *
     * @param group radiogroup
     * @param direction    图片位置  0：left 1:top 2：right 3:bottom
     *              图片大小  屏幕宽度百分比  默认：0.08
     *              字体大小  屏幕宽度百分比 默认：0.03
     */
    public void initRadioGroupAttr(RadioGroup group,int direction) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if (view instanceof RadioButton) {
                RadioButton radioButton = ((RadioButton) view);
                try {
                    Drawable[] drawables = radioButton.getCompoundDrawables();
                    int j = (int) (GestureUtils.getScreenPix(context).widthPixels * drableSize);
                    drawables[direction].setBounds(0, 0, j, j);
                    radioButton.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                } catch (Exception e) {
                }
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (GestureUtils.getScreenPix(context).widthPixels * textSize));
            }
        }
    }
}
