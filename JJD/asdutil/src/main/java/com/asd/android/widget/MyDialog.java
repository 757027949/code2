package com.asd.android.widget;


import android.content.Context;
import android.graphics.Color;

import com.asd.android.util.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author Administrator
 */
public class MyDialog {
    private static Context context;
    private static MyDialog myDialog;

    public synchronized static MyDialog getInstance(Context context) {
        MyDialog.context = context;
        if (null == myDialog) {
            myDialog = new MyDialog();
        }
        return myDialog;
    }

    /**
     * 显示Loading对话框
     *
     * @return
     */
    public SweetAlertDialog getLoadingAlertDialog() {
        SweetAlertDialog loadDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        loadDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadDialog.setTitleText("Loading");
        loadDialog.setCancelable(false);
        return loadDialog;
    }

    /**
     * 显示警告对话框
     *
     * @param mes
     * @return
     */
    public SweetAlertDialog getWaringAlertDialog(String mes) {
        SweetAlertDialog waringDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示!")
                .setContentText(mes)
                .setConfirmText("确定");
        return waringDialog;
    }

    /**
     * 显示警告对话框
     *
     * @param mes
     * @param onSweetConfirmClickListener
     * @return
     */
    public SweetAlertDialog getWaringAlertDialog(String mes, SweetAlertDialog.OnSweetClickListener onSweetConfirmClickListener) {
        SweetAlertDialog waringDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示!")
                .setContentText(mes)
                .setConfirmClickListener(onSweetConfirmClickListener)
                .setConfirmText("确定");
        return waringDialog;
    }

    /**
     * 显示警告对话框
     * @param mes
     * @param confirmString
     * @param confirmClickListener
     * @param cancelString
     * @param cancelClickListener
     * @return
     */
    public SweetAlertDialog getWaringAlertDialog(String mes, String confirmString, SweetAlertDialog.OnSweetClickListener confirmClickListener, String cancelString, SweetAlertDialog.OnSweetClickListener cancelClickListener) {
        SweetAlertDialog waringDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示!")
                .setContentText(mes)
                .setCancelText(confirmString)
                .setConfirmText(cancelString)
                .setConfirmClickListener(confirmClickListener)
                .setCancelClickListener(cancelClickListener);
        return waringDialog;
    }

    /**
     * 显示成功对话框
     *
     * @param mes
     * @param confirmClickListener
     * @return
     */
    public SweetAlertDialog getSuccessAlertDialog(String mes, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("提示！")
                .setContentText(mes)
                .setConfirmClickListener(confirmClickListener);
        return alertDialog;
    }

    /**
     * 显示成功对话框
     *
     * @param mes
     * @param confirmClickListener
     * @param cancelClickListener
     * @return
     */
    public SweetAlertDialog getSuccessAlertDialog(String mes, SweetAlertDialog.OnSweetClickListener confirmClickListener, SweetAlertDialog.OnSweetClickListener cancelClickListener) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("提示！")
                .setContentText(mes)
                .setConfirmClickListener(confirmClickListener)
                .setCancelClickListener(cancelClickListener);
        return alertDialog;
    }

}
