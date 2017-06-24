package com.otw.asd.jjdt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.ImageView;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.http.okhttp.callback.StringCallback;
import com.asd.android.util.NetworkUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.app.AppManager;
import com.asd.android.widget.MyDialog;
import com.asd.util.JsonMapper;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Request;


/**
 * 加载页面
 *
 * @author 123
 */
public class LaucherActivity extends Activity implements Handler.Callback {

    ImageView imageView1;

    Handler handler;

    final int LOGIN = 0;
    final int GET_WARING_DIALOG = 1;

    UserInfo userInfo;
    SweetAlertDialog waringDialog = null;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOGIN:
                LocalCache localCache = LocalCache.get(this);
                OkHttpUtils.post().url(UrlUtil.LOGIN)
//                        .addParams("userRegisterTelephone", localCache.getAsString(LocalCacheKey.FLOW_USER_ACCOUNTS))
//                        .addParams("userLoginPassword", localCache.getAsString(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("userRegisterTelephone", ShareUtil.getInstance(LaucherActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS))
                        .addParams("userLoginPassword", ShareUtil.getInstance(LaucherActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("deviceCode", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjdt")
                        .addParams("clientPlatform", "android")
                        .addParams("userIsTeacher", "Y")
                        .addParams("userOtherType", "platform")
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(true);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
//                                        tologinpage();

                                        try {
                                            UserInfo userInfo = new Gson().fromJson(response, UserInfo.class);

                                            if (userInfo.isSuccess()) {
//                                                LocalCache localCache = LocalCache.get(LaucherActivity.this);
//                                                localCache.put(LocalCacheKey.KEY_USER, userInfo);//缓存至本地

                                                ShareUtil.getInstance(LaucherActivity.this).setLocalCookie(LocalCacheKey.KEY_USER, PojoUtils.getInstance().getOjbectBase64String(userInfo));

                                                tologinpage();
                                            }
                                        } catch (Exception e) {
                                            MyDialog.getInstance(LaucherActivity.this).getWaringAlertDialog(getResources().getString(R.string.json_error)).show();
                                        }
                                    } else {
//                                        handler.sendEmptyMessage(GET_WARING_DIALOG);
                                        handler.sendMessage(handler.obtainMessage(GET_WARING_DIALOG, jsonObject.getString("obj")));
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(LaucherActivity.this).getWaringAlertDialog(getResources().getString(R.string.json_error)).show();
                                }
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
//                                handler.sendEmptyMessage(GET_WARING_DIALOG);
                                handler.sendMessage(handler.obtainMessage(GET_WARING_DIALOG, error));
                            }
                        });
                break;

            case GET_WARING_DIALOG:
                if (null == waringDialog) {
                    waringDialog = new SweetAlertDialog(LaucherActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示!")
//                            .setContentText("获取数据失败！！！")
                            .setCancelText("退出应用")
                            .setConfirmText("重新获取")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    handler.sendEmptyMessage(LOGIN);
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    AppManager.AppExit(LaucherActivity.this);
                                }
                            });
                }
                waringDialog.setContentText((String) msg.obj);
                waringDialog.show();
                break;
        }
        return false;
    }

  /*  class Myhandler extends Handler {
        public Myhandler() {
        }

        public Myhandler(Looper lp) {

            super(lp);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x11) {
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                tologinpage();
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_laucher);

        imageView1 = (ImageView) findViewById(R.id.imageView1);

        //启动网络状态变化监听
//		if (!ActivityUtil.isServiceRunning(this,
//				"com.example.xutiltext.service.NetworkStateService")) {
//			Intent i = new Intent(this, NetworkStateService.class);
//			startService(i);
//		}

   /*     HandlerThread ht = new HandlerThread("" + System.currentTimeMillis());
        ht.start();
        Myhandler mu = new Myhandler(ht.getLooper());
        Message ms = mu.obtainMessage();
        ms.what = 0x11;
        ms.sendToTarget();*/


        handler = new Handler(this);

//        userInfo = (UserInfo) LocalCache.get(this).getAsObject(LocalCacheKey.KEY_USER);
        userInfo = (UserInfo) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.KEY_USER));
        if (null == userInfo) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            handler.sendEmptyMessage(LOGIN);
        }
    }

    // 跳转到登陆页面
    public void tologinpage() {
//        userInfo = null;
        Logger.e(JsonMapper.toNormalJson(userInfo));
      /*  if (null == userInfo) {//未登录
            startActivity(new Intent(this, LoginActivity.class));
        } else */
        if ("-1".equals(userInfo.getObj().getTeacher().getValidateState())) {//未认证 -1:未认证或认证失败 0：认证中 1：认证通过) {//未认证
            Intent intent = new Intent(this, IdentificationActivity.class);
            intent.putExtra("flow", "laucher");
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("flow", "laucher");
            startActivity(intent);
        }

        finish();
        // overridePendingTransition(R.anim.enteralpha,R.anim.exitalpha);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        BitmapUtil.releaseImageViewResouce(imageView1);
    }


    SweetAlertDialog loadDialog;

    /**
     * 显示Loading对话框
     *
     * @return
     */
    public SweetAlertDialog getLodingAlertDialog() {
        if (null == loadDialog) {
            loadDialog = MyDialog.getInstance(this).getLoadingAlertDialog();
        }
        return loadDialog;
    }

    public abstract class MyCallBack extends StringCallback {
        /**
         * 是否静默请求
         */
        boolean isSilence = false;

        /**
         * 设置是否静默请求
         */
        public abstract void setIsSilence();

        public boolean isSilence() {
            return isSilence;
        }

        /**
         * 是否静默请求
         *
         * @param isSilence
         */
        public void setNotSilence(boolean isSilence) {
            this.isSilence = isSilence;
        }

        /**
         * 错误时回调方法(静默时重写此方法)
         */
        public void getError(String error) {
        }

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            setIsSilence();
            if (isSilence) {
                getLodingAlertDialog().show();
            }
        }

        @Override
        public void onAfter() {
            super.onAfter();
            getLodingAlertDialog().dismiss();
        }

        @Override
        public void onError(Request request, Exception e) {
            getLodingAlertDialog().dismiss();
            String error = "";
            if (!NetworkUtil.isNetAvailable(LaucherActivity.this)) {// 当前无网络！！
                error = "当前无网络！！";
//                MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog("当前无网络！！").show();
            } else if (e instanceof ConnectTimeoutException) {// 服务器链接超时
                error = "链接超时";
//                MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog("链接超时").show();
            } else if (e instanceof SocketTimeoutException) {// 服务器未响应
                error = "服务器未响应";
//                MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog("服务器未响应").show();
            } else {//服务器异常
                error = "服务器异常";
//                MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog("服务器异常").show();
            }

            if (isSilence) {
//                MyDialog.getInstance(LaucherActivity.this).getWaringAlertDialog(error).show();
            }

            getError(error);
        }
    }
}
