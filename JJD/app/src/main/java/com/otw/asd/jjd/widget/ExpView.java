package com.otw.asd.jjd.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/10/11.
 */

public class ExpView extends View {
    /**
     * 控件宽度
     */
    private int width;
    /**
     * 控件高度
     */
    private int height;

    private float strokeWidth = 10;

    /**
     * 当前经验值比例
     */
    private float currentScale = 0;
    private String currentExpLevel = "LV.1";

    Paint paint;

    public ExpView(Context context) {
        super(context);
        initView(context);
    }

    public ExpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ExpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth(); //获取宽度
        height = getHeight();//获取高度

        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        paintExpView(canvas);
    }

    private void paintExpView(Canvas canvas) {
        paint.setColor(Color.parseColor("#E2E2E2"));
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);//设置为圆角
        paint.setAntiAlias(true);
        RectF outerArea = new RectF(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height);
        canvas.drawArc(outerArea,
                (float) (180),
                (float) (180), false, paint);

        paint.setColor(Color.parseColor("#95C468"));
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);//设置为圆角
        paint.setAntiAlias(true);
        outerArea = new RectF(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height);
        canvas.drawArc(outerArea,
                (float) (180),
                (float) (180 * currentScale), false, paint);

        paint.setColor(Color.parseColor("#DE7DA6"));
        paint.setStyle(Paint.Style.FILL);
        outerArea = new RectF(strokeWidth, strokeWidth, width - strokeWidth, height);
        Shader mShader = new LinearGradient(width / 2, 0, width / 2, height,
                new int[]{Color.parseColor("#B9DB94"), Color.parseColor("#E2F5C7"), Color.parseColor("#F0FCE6"), Color.WHITE}, null, Shader.TileMode.REPEAT);
        paint.setShader(mShader);
        canvas.drawArc(outerArea,
                (float) (180),
                (float) (180), false, paint);

        paint.setColor(Color.parseColor("#95C468"));
        paint.setStrokeWidth(2);
        paint.setTextSize(width / 5);
        paint.setShader(null);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(currentExpLevel, width / 2, height / 2, paint);
    }

    /**
     * 设置当前经验值样式
     *
     * @param currentScale
     * @param currentExpLevel
     */
    public synchronized void setExpViewValue(float currentScale, String currentExpLevel) {
        this.currentScale = currentScale;
        this.currentExpLevel = currentExpLevel;
        postInvalidate();
    }
}
