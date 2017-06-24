package com.otw.asd.jjdt.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.http.okhttp.callback.StringCallback;
import com.asd.android.util.NetworkUtil;
import com.asd.android.widget.MyDialog;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Request;

/**
 * Created by ASD on 2015/12/30.
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment, Handler.Callback {
    /**
     * 当前Fragment渲染的视图View
     **/
    protected View mContextView = null;
    /**
     * 依附的Activity
     **/
    protected Activity mContext = null;

    protected Handler mHandler;

    SweetAlertDialog loadDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //缓存当前依附的activity
        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHandler = new Handler(this);
        // 渲染视图View
        if (null == mContextView) {
            mContextView = inflater.inflate(bindLayout(), container, false);
            mContextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputmanger = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            });
            contextViewGetFocused();
            ButterKnife.bind(this, mContextView);
            // 控件初始化
            initView(mContextView);
        }

        return mContextView;
    }

    /**
     * 当前视图获取焦点
     *
     */
    public void contextViewGetFocused() {
        if (null != mContextView) {
            mContextView.setFocusable(true);
            mContextView.setFocusableInTouchMode(true);
            mContextView.requestFocus();
        }
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
     * 显示Loading对话框
     *
     * @return
     */
    public SweetAlertDialog getLodingAlertDialog() {
        if (null == loadDialog) {
            loadDialog = MyDialog.getInstance(mContext).getLoadingAlertDialog();
        }
        return loadDialog;
    }

    @Override
    public void onDestroy() {
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
            if (!NetworkUtil.isNetAvailable(mContext)) {// 当前无网络！！
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
                MyDialog.getInstance(mContext).getWaringAlertDialog(error).show();
            }
            getError(error);
        }
    }
}
