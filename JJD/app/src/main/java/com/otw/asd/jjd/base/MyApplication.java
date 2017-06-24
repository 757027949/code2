package com.otw.asd.jjd.base;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.asd.android.crash.AppContext;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2016/3/30.
 */
public class MyApplication extends MultiDexApplication {

    public void onCreate() {
        super.onCreate();
        instance = this;
//        AutoLayoutConifg.getInstance().useDeviceSize();

        AppContext.initialize(this, MyCrashActivity.class);

        Hawk.init(this).setEncryption(new NoEncryption()).build();

        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//初始化
        EMClient.getInstance().init(instance, options);
//在做打包混淆时，关闭debug模式， 避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(instance, options);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wxa9401c7c5404858c", "0dcc6c75c25b71de637ab1836a5e1b52");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

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
