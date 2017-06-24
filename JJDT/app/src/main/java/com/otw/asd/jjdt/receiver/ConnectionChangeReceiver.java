package com.otw.asd.jjdt.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.WindowManager;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.MyCallBack;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.NetworkUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.widget.MyDialog;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.activity.LaucherActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 网络变化监听广播
 *
 * @author 123
 */
public class ConnectionChangeReceiver extends BroadcastReceiver implements Handler.Callback {

    Context context;

    final int FLOW_SESSION = -505;
    Handler handler;
    LocalCache localCache;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        handler = new Handler(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (null == mobNetInfo) {
            if (!wifiNetInfo.isConnected()) {
                NetworkUtil.setIsConnected(false);
            } else {
                NetworkUtil.setIsConnected(true);

                connectedNetwork();
            }
        } else {
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {// 网络不可以用
                NetworkUtil.setIsConnected(false);
                // Toast.makeText(context, "网络不可以用...",
                // Toast.LENGTH_SHORT).show();
                // 改变背景或者 处理网络的全局变量
            } else {
                NetworkUtil.setIsConnected(true);
                // Toast.makeText(context, "网络可用...",
                // Toast.LENGTH_SHORT).show();
                // 改变背景或者 处理网络的全局变量


                connectedNetwork();
            }
        }
    }

    public void connectedNetwork() {
        localCache = LocalCache.get(context);
//        UserInfo user = (UserInfo) localCache.getAsObject(LocalCacheKey.KEY_USER);
        UserInfo user = (UserInfo) PojoUtils.getInstance().getObject(ShareUtil.getInstance(context).getLocalCookie(LocalCacheKey.KEY_USER));;

        if (null != user) {
            handler.sendEmptyMessage(FLOW_SESSION);
        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case FLOW_SESSION:
                OkHttpUtils.post().url(UrlUtil.LOGIN)
//                        .addParams("userRegisterTelephone", localCache.getAsString(LocalCacheKey.FLOW_USER_ACCOUNTS))
//                        .addParams("userLoginPassword", localCache.getAsString(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("userRegisterTelephone", ShareUtil.getInstance(context).getLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS))
                        .addParams("userLoginPassword", ShareUtil.getInstance(context).getLocalCookie(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("deviceCode", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjdt")
                        .addParams("clientPlatform", "android")
                        .addParams("userIsTeacher", "Y")
                        .addParams("userOtherType", "platform")
                        .build()
                        .execute(new MyCallBack() {

                            @Override
                            public boolean setIsSilence() {
                                if (ActivityUtil.isBackgroundRunning(context, context.getPackageName())) {
                                    return true;
                                }
                                return false;
                            }

                            @Override
                            public Context getContext() {
                                return context;
                            }

                            @Override
                            public SweetAlertDialog getLoadDialog() {
                                return getLodingAlertDialog();
                            }


                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        LocalCache.get(context).put(LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION_TIME);
                                    } else {
//                                        MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
//                                    MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    SweetAlertDialog loadDialog;

    /**
     * 显示Loading对话框
     *
     * @return
     */
    public SweetAlertDialog getLodingAlertDialog() {
        if (null == loadDialog) {
            loadDialog = MyDialog.getInstance(context).getLoadingAlertDialog();
            loadDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        return loadDialog;
    }

}
