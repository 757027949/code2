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

    public static MyDialog getInstance(Context context) {
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
     * 成功Dialog对话框
     *
     * @param string                提示语
     * @param onDialogClickListener 确定点击回调事件
     */
    public void getConfirmDialog(String string,
                                 final OnDialogClickListener onDialogClickListener) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("提示!").setContentText(string)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (null != onDialogClickListener) {
                            onDialogClickListener.okClick();
                        }
                    }
                }).show();
    }

    /**
     * 对话框点击回调事件
     *
     * @author asd
     */
    public interface OnDialogClickListener {
        public void okClick();

        public void cancalClick();
    }

}
