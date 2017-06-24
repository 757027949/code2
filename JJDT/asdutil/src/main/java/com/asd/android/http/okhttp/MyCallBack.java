package com.asd.android.http.okhttp;

import android.content.Context;

import com.asd.android.http.okhttp.callback.StringCallback;
import com.asd.android.util.NetworkUtil;
import com.asd.android.widget.MyDialog;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/6/20.
 */
public abstract class MyCallBack extends StringCallback {

    /**
     * 设置是否静默请求
     */
    public abstract boolean setIsSilence();

    /**
     * 获取Context
     *
     * @return
     */
    public abstract Context getContext();

    /**
     * 获取等待对话框
     *
     * @return
     */
    public abstract SweetAlertDialog getLoadDialog();


    /**
     * 错误时回调方法(静默时重写此方法)
     */
    public void getError(String error) {
    }


    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
        if (!setIsSilence()) {
            getLoadDialog().show();
        }
    }

    @Override
    public void onAfter() {
        super.onAfter();
        getLoadDialog().dismiss();
    }

    @Override
    public void onError(Request request, Exception e) {
        getLoadDialog().dismiss();
        String error = "";
        if (!NetworkUtil.isNetAvailable(getContext())) {// 当前无网络！！
            error = "当前无网络！！";
        } else if (e instanceof ConnectTimeoutException) {// 服务器链接超时
            error = "链接超时";
        } else if (e instanceof SocketTimeoutException) {// 服务器未响应
            error = "服务器未响应";
        } else {//服务器异常
            error = "服务器异常";
        }
        if (!setIsSilence()) {
            try {
                MyDialog.getInstance(getContext()).getWaringAlertDialog(error).show();
            } catch (Exception e1) {
            }
        }
        getError(error);
    }

}
