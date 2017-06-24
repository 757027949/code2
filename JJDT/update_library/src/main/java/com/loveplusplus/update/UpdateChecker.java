package com.loveplusplus.update;

import android.content.Context;
import android.util.Log;

public class UpdateChecker {


    /**
     *
     * @param context
     * @param url 获取数据地址 返回结果（ json {"url":"http://192.168.205.33:8080/Hello/app_v3.0.1_Other_20150116.apk","versionCode":2,"updateMessage":"版本更新信息"} ）
     * @param isShowDialog
     */
    public static void checkForDialog(Context context, String url, boolean isShowDialog) {
        if (context != null) {
            new CheckUpdateTask(context, url, Constants.TYPE_DIALOG, isShowDialog).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Context context, String url) {
        if (context != null) {
            new CheckUpdateTask(context, url, Constants.TYPE_NOTIFICATION, false).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }

    }


}
