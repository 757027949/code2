package com.otw.asd.jjdt.base;

import android.app.Activity;
import android.app.Application;

import com.asd.android.crash.AppContext;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2016/3/30.
 */
public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
        instance = this;
//        AutoLayoutConifg.getInstance().useDeviceSize();

        AppContext.initialize(this, MyCrashActivity.class);

        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }


    /**
     * 打开的activity
     **/
    private List<Activity> activities = new ArrayList<Activity>();
    /**
     * 应用实例
     **/
    private static MyApplication instance;

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束所有的activity
     */
    public void finishAllActivity() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
