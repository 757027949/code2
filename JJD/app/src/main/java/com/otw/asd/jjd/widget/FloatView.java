package com.otw.asd.jjd.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.asd.android.cache.LocalCache;
import com.asd.android.util.gesture.GestureUtils;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.MainActivity;
import com.otw.asd.jjd.base.LocalCacheKey;

import butterknife.Bind;

/**
 * 悬浮窗
 * Created by Administrator on 2016/4/18.
 */
public class FloatView implements View.OnClickListener {
    private Context context;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;

    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private float mStartX;
    private float mStartY;

    private View view;
    ImageView image;

    LocalCache localCache;
    OnFlostViewClickListener onFlostViewClickListener;

    public FloatView(Context context) {
        this.context = context;
        this.localCache = LocalCache.get(context);
        view = LayoutInflater.from(context).inflate(R.layout.layout_floating, null);
        image = (ImageView) view.findViewById(R.id.image);
        createView();
    }

    public OnFlostViewClickListener getOnFlostViewClickListener() {
        return onFlostViewClickListener;
    }

    public void setOnFlostViewClickListener(OnFlostViewClickListener onFlostViewClickListener) {
        this.onFlostViewClickListener = onFlostViewClickListener;
    }

    private void createView() {

        // 1、获取WindowManager对象  仅在当前应用中显示悬浮窗
//          windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 1、获取WindowManager对象  全局悬浮按钮，会显示在其他应用和桌面上
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        // 2、设置LayoutParams(全局变量）相关参数
        windowManagerParams = new WindowManager.LayoutParams();
//        windowManagerParams = ((FloatApplication) getApplication()).getWindowParams();
        //3、设置相关的窗口布局参数 （悬浮窗口效果）
//        windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE; // 设置window type
        // 设置window type
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            windowManagerParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        //4、设置Window flag == 不影响后面的事件  和  不可聚焦
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 注意，flag的值可以为：
         * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
         * LayoutParams.FLAG_NOT_FOCUSABLE  不可聚焦
         * LayoutParams.FLAG_NOT_TOUCHABLE 不可触摸
         */
        //5、 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x 0、y 150初始值
//        windowManagerParams.x = "0".equals(localCache.getAsString(LocalCacheKey.FLOAT_VIEW_X)) ? GestureUtils.getScreenPix(context).widthPixels : Integer.parseInt((localCache.getAsString(LocalCacheKey.FLOAT_VIEW_X)));
//        windowManagerParams.y = "0".equals(localCache.getAsString(LocalCacheKey.FLOAT_VIEW_Y)) ? (int) (GestureUtils.getScreenPix(context).heightPixels * 0.35) : Integer.parseInt((localCache.getAsString(LocalCacheKey.FLOAT_VIEW_Y)));
        windowManagerParams.x = null == localCache.getAsObject(LocalCacheKey.FLOAT_VIEW_X) ? GestureUtils.getScreenPix(context).widthPixels : (int) (localCache.getAsObject(LocalCacheKey.FLOAT_VIEW_X));
        windowManagerParams.y = null == localCache.getAsObject(LocalCacheKey.FLOAT_VIEW_Y) ? (int) (GestureUtils.getScreenPix(context).heightPixels * 0.35) : (int) (localCache.getAsObject(LocalCacheKey.FLOAT_VIEW_Y));
        //6、设置悬浮窗口长宽数据
        windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //获得屏幕的宽高
        Display display = windowManager.getDefaultDisplay();
        final int screenWith = display.getWidth();
        int screenHeight = display.getHeight();
        System.out.println("screenWith=" + screenWith + ",screenHeight=" + screenHeight);

        image.setOnClickListener(this);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //1、获取到状态栏的高度
                Rect frame = new Rect();
                image.getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;
                System.out.println("状态栏高度:" + statusBarHeight);
                //2、获取相对屏幕的坐标，即以屏幕左上角为原点 。y轴坐标= y（获取到屏幕原点的距离）-状态栏的高度
                x = event.getRawX();
                y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度

                System.out.println("x=" + x + ",y=" + y);
                //3、处理触摸移动
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                        // 获取相对View的坐标，即以此View左上角为原点
                        mTouchX = event.getX();
                        mTouchY = event.getY();
                        mStartX = x;
                        mStartY = y;

                        System.out.println(",mTouchX=" + mTouchX + ",mTouchY="
                                + mTouchY);
                        break;

                    case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                        updateViewPosition();
                        break;

                    case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                        float left = x - mTouchX;
                        if (left <= screenWith / 2) {//图标icon吸附在左边
                            x = mTouchX;
                        } else {//图标icon吸附在右边
                            x = mTouchX + screenWith;
                        }

                        updateViewPosition();
                        //移动终点的坐标，重置为0
                        mTouchX = mTouchY = 0;
                        //移动距离少于5 ,则视为点击，触发点击的回调
//                        if ((x - mStartX) < 5 && (y - mStartY) < 5) {
                        if (Math.abs(y - mStartY) < 5) {
                            onClick(v);
                        }
                        break;
                }
                return true;
            }
        });

    }


    /**
     * 用于更新 悬浮窗位置参数
     */
    private void updateViewPosition() {
        int currentX = (int) (x - mTouchX);
        int currentY = (int) (y - mTouchY);
        localCache.put(LocalCacheKey.FLOAT_VIEW_X, currentX);
        localCache.put(LocalCacheKey.FLOAT_VIEW_Y, currentY);
        windowManagerParams.x = currentX;
        windowManagerParams.y = currentY;
        System.out.println("wp.x=" + windowManagerParams.x + ",wp.y=" + windowManagerParams.y);
        // 刷新屏幕显示
        windowManager.updateViewLayout(view, windowManagerParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
//                context.startActivity(new Intent(context, MainActivity.class));
                if (null != onFlostViewClickListener) {
                    onFlostViewClickListener.click(v);
                }
                break;
            default:
                break;
        }

    }

    /**
     * 显示悬浮窗
     */
    public void showFloat() {
        windowManager.addView(view, windowManagerParams); // 显示myFloatView图像
    }

    /**
     * 销毁悬浮窗口
     */
    public void destroyFloat() {
        // 在程序退出(Activity销毁）时销毁悬浮窗口
        windowManager.removeView(view);
    }

    /**
     * 悬浮窗点击监听
     */
    public interface OnFlostViewClickListener {
        /**
         * 点击
         */
        void click(View view);
    }
}
