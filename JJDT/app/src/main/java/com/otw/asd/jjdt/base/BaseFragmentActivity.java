package com.otw.asd.jjdt.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
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
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Request;
//import qiu.niorgai.StatusBarCompat;

/**
 * Created by ASD on 2015/12/30.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements IBaseActivity, Handler.Callback {
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;

    protected Handler mHandler;

    TextView topTitle;
    SweetAlertDialog loadDialog;

    protected final int FLOW_SESSION = -505;

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
//        setStatusBarBackgroundColor(Color.parseColor("#F2F3F3"));
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
        if (!LocalCacheKey.FLOW_SESSION.equals(LocalCache.get(this).getAsString(LocalCacheKey.FLOW_SESSION))) {
//            if (null != LocalCache.get(this).getAsObject(LocalCacheKey.KEY_USER)) {
            if (!"".equals(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.KEY_USER))) {
                mHandler.sendEmptyMessageDelayed(FLOW_SESSION, 500);
            }
        }
    }


    /**
     * 设置状态栏背景颜色
     */
    private void setStatusBarBackgroundColor(int color) {
//        StatusBarCompat.setStatusBarColor(this, color);
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
        // this.overridePendingTransition(R.anim.translate_in,
        // R.anim.translate_out);
    }*/

    /**
     * 关闭当前
     */
    public void finishSelf() {
        BaseFragmentActivity.this.finish();
    }

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
                    BaseFragmentActivity.this.finish();
                }
            });
        } catch (Exception e) {
        }
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

/*    *//**
     * 显示Snakerbar
     *
     * @param mes
     *//*
    public void showSnackbar(String mes) {
        Snackbar.make(mContextView, mes, Snackbar.LENGTH_SHORT).show();
    }

    *//**
     * 显示Snakerbar
     *
     * @param mes
     *//*
    public void showSnackbar(int mes) {
        Snackbar.make(mContextView, mes, Snackbar.LENGTH_SHORT).show();
    }*/

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
            if (!NetworkUtil.isNetAvailable(BaseFragmentActivity.this)) {// 当前无网络！！
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
                MyDialog.getInstance(BaseFragmentActivity.this).getWaringAlertDialog(error).show();
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
                        .addParams("userRegisterTelephone", ShareUtil.getInstance(BaseFragmentActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS))
                        .addParams("userLoginPassword", ShareUtil.getInstance(BaseFragmentActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("deviceCode", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjdt")
                        .addParams("clientPlatform", "android")
                        .addParams("userIsTeacher", "Y")
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
                                        LocalCache.get(BaseFragmentActivity.this).put(LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION_TIME);
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


    /**
     * 获取数据
     */
    public interface OnGetDataListListener {
        /**
         * 完成
         */
        void onfinished();
    }
}
