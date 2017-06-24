package com.asd.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;

import com.asd.android.util.R;

/**
 * Created by Administrator on 2016/7/26.
 */
public class BaseCustomDialog {
    private Dialog dialog;

    public BaseCustomDialog() {
    }

    public BaseCustomDialog(Context context, int layoutId) {
        dialog = new Dialog(context, R.style.BaseCustomDialog);
        dialog.setContentView(layoutId);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
