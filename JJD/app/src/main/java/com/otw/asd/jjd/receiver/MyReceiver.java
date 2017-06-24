package com.otw.asd.jjd.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.asd.android.cache.LocalCache;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.activity.MainActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Proclamation;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "TalkReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的自定义消息");
            String data = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Logger.e(data.replaceAll("\\\\", ""));

            MainActivity.handler.sendMessage(MainActivity.handler.obtainMessage(MainActivity.RELOGIN, data));


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的通知");

            try {
                JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Logger.e(jsonObject.getString("alertExtra"));
                Proclamation proclamation = new Gson().fromJson(jsonObject.getString("alertExtra"), Proclamation.class);
                Calendar calendar = Calendar.getInstance();
                Calendar calendar1 = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(proclamation.getNoticeShowEndTime()));
                Logger.e(calendar.getTimeInMillis() + "----" + calendar1.getTimeInMillis() + "\n"
                        + ((calendar1.getTimeInMillis() - calendar.getTimeInMillis()) / 1000));
                LocalCache.get(context).put(LocalCacheKey.PROCLAMATION, proclamation, (int) ((calendar1.getTimeInMillis() - calendar.getTimeInMillis()) / 1000));

                MainActivity.handler.sendEmptyMessage(MainActivity.FLOW_SHOW_TITLE);

            } catch (Exception e) {
                Logger.e("json_error....");
            }

            String data = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Logger.e(data);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d(TAG, "用户点击打开了通知");

           /* //打开自定义的Activity
            Intent i = new Intent(context, OrderActivity.class);
            i.putExtra("order", order);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);*/
        } else {
            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}
