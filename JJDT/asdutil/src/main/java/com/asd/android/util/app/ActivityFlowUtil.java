package com.asd.android.util.app;

import android.app.Activity;

import java.util.ArrayList;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * Created by Administrator on 2016/4/21.
 */
public class ActivityFlowUtil {
    /**
     * 当前打开的activity列表
     */
    public ArrayList<Activity> activityList;
    private static ActivityFlowUtil activityFlowUtil;

    public static ActivityFlowUtil getInstance() {
        if (null == activityFlowUtil) {
            activityFlowUtil = new ActivityFlowUtil();
        }
        return activityFlowUtil;
    }

    /**
     * 添加当前Activity 到列表中
     */
    public void addActivity(Activity acitivity) {
        if (activityList == null) {
            activityList = new ArrayList<Activity>();
        }
        activityList.add(acitivity);
    }


    /**
     * 遍历退出所有Activity
     */
    public void exit() {
        if (null != activityList) {
            for (Activity activity : activityList) {
                activity.finish();
            }
            activityList.clear();// 千万记得清空取消引用。
        }

    }
}
