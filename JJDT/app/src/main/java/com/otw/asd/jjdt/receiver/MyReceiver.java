package com.otw.asd.jjdt.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.asd.android.cache.LocalCache;
import com.asd.util.JsonMapper;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.activity.MainActivity;
import com.otw.asd.jjdt.activity.OrderActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.Order;

import org.json.JSONObject;

import java.util.HashMap;

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

    private NotificationManager nm;

    HashMap<String, Order> orderMap;

    /**
     * 是否取消订单
     */
    boolean isCancelOrder = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        orderMap = (HashMap<String, Order>) LocalCache.get(context).getAsObject(LocalCacheKey.ORDERS);
        if (null == orderMap) {
            orderMap = new HashMap<>();
        }

        Bundle bundle = intent.getExtras();
        Order order = null;
        try {
            JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            Logger.e(jsonObject.getString("alertExtra"));
            order = new Gson().fromJson(jsonObject.getString("alertExtra"), Order.class);
            orderMap.put(order.getCourseOrderId(), order);
            isCancelOrder = false;
        } catch (Exception e) {
            isCancelOrder = true;
        }

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的自定义消息");

            String data = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Logger.e(data.replaceAll("\\\\", ""));

            MainActivity.mHandler.sendMessage(MainActivity.mHandler.obtainMessage(MainActivity.RELOGIN, data));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的通知");
            LocalCache.get(context).put(LocalCacheKey.ORDERS, orderMap);
            try {
                MainActivity.mHandler.sendMessage(MainActivity.mHandler.obtainMessage(MainActivity.JPUSH_SET_ORDER_SIZE, orderMap.size()));
            } catch (Exception e) {
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d(TAG, "用户点击打开了通知");
            if (isCancelOrder) {
                return;
            }

            //打开自定义的Activity
            Intent i = new Intent(context, OrderActivity.class);
            i.putExtra("order", order);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

            if (orderMap.containsKey(order.getCourseOrderId())) {
                orderMap.remove(order.getCourseOrderId());
            }
            LocalCache.get(context).put(LocalCacheKey.ORDERS, orderMap);
            Logger.e(JsonMapper.toNormalJson(orderMap));
            try {
                MainActivity.mHandler.sendMessage(MainActivity.mHandler.obtainMessage(MainActivity.JPUSH_SET_ORDER_SIZE, orderMap.size()));
            } catch (Exception e) {
            }
        } else {
            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


}
