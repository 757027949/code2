package com.otw.asd.jjd.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.http.okhttp.callback.StringCallback;
import com.asd.android.util.NetworkUtil;
import com.asd.android.widget.MyDialog;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.LoginActivity;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Request;

/**
 * Created by ASD on 2015/12/30.
 */
public abstract class BaseActivity extends Activity implements IBaseActivity, Handler.Callback {
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

    protected Handler mHandler;

    final int FLOW_SESSION = -505;

    TextView topTitle;
    SweetAlertDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        if (null == mContextView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
            mContextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
        }
        setContentView(mContextView);
        contextViewGetFocused();
        ButterKnife.bind(this);
        initBackMethed();
        mHandler = new Handler(this);
        initView(mContextView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //是否该获取session
        if (!(this instanceof LoginActivity)) {
            if (!LocalCacheKey.FLOW_SESSION.equals(LocalCache.get(this).getAsString(LocalCacheKey.FLOW_SESSION))) {
//                if (null != LocalCache.get(this).getAsObject(LocalCacheKey.LOCAL_USER)) {
                if (!"".equals(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
                    mHandler.sendEmptyMessageDelayed(FLOW_SESSION, 500);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
    }

    @Override
    public void onDestroy() {
        MyApplication.getInstance().finishActivity(this);
        super.onDestroy();
        if (null != loadDialog && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
        loadDialog = null;
        try {
            OkHttpUtils.getInstance().cancelTag(this);
        } catch (Exception e) {
        }
    }

    /**
     * 当前视图获取焦点
     */
    public void contextViewGetFocused() {
        if (null != mContextView) {
            mContextView.setFocusable(true);
            mContextView.setFocusableInTouchMode(true);
            mContextView.requestFocus();
        }
    }

    /**
     * 关闭当前activity
     */
  /*  public void finishSelf(View view) {
        this.finish();
        // this.overridePendingTransition(R.anim.activity_translate_in,
        // R.anim.activity_translate_out);
    }*/

    /**
     * 获取RES下的String
     *
     * @param stringId
     * @return
     */
    public String getResString(int stringId) {
        return getResources().getString(stringId);
    }

    /**
     * 初始化返回事件
     */
    public void initBackMethed() {
        try {
            mContextView.findViewById(R.id.top_left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishSelf();
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 关闭当前
     */
    public void finishSelf() {
        BaseActivity.this.finish();
    }

    /**
     * 设置头部标题
     */
    public void setTopTitle(String title) {
        try {
            topTitle = (TextView) mContextView.findViewById(R.id.top_title);
            topTitle.setText(title);
        } catch (Exception e) {
        }
    }

    ;

    /**
     * 设置头部标题
     */
    public void setTopTitle(int title) {
        try {
            topTitle = (TextView) mContextView.findViewById(R.id.top_title);
            topTitle.setText(title);
        } catch (Exception e) {
        }
    }

    /**
     * 显示Snakerbar
     *
     * @param mes
     */
    public void showSnackbar(String mes) {
        Snackbar.make(mContextView, mes, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示Snakerbar
     *
     * @param mes
     */
    public void showSnackbar(int mes) {
        Snackbar.make(mContextView, mes, Snackbar.LENGTH_SHORT).show();
    }

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
            try {
                getLodingAlertDialog().dismiss();
            } catch (Exception e) {
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            getLodingAlertDialog().dismiss();
            String error = "";
            if (!NetworkUtil.isNetAvailable(BaseActivity.this)) {// 当前无网络！！
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
                MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog(error).show();
            }
            getError(error);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case FLOW_SESSION:
                LocalCache localCache = LocalCache.get(this);
                OkHttpUtils.post().url(UrlUtil.LOGIN)
//                        .addParams("userRegisterTelephone", localCache.getAsString(LocalCacheKey.FLOW_USER_ACCOUNTS))
//                        .addParams("userLoginPassword", localCache.getAsString(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("userRegisterTelephone", ShareUtil.getInstance(BaseActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS))
                        .addParams("userLoginPassword", ShareUtil.getInstance(BaseActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("deviceCode", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjd")
                        .addParams("clientPlatform", "android")
                        .addParams("userIsTeacher", "N")
                        .addParams("userOtherType", "platform")
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(false);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        LocalCache.get(BaseActivity.this).put(LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION_TIME);
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
}
