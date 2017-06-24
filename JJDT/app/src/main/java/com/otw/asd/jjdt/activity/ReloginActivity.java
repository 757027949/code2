package com.otw.asd.jjdt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.base.MyApplication;
import com.otw.asd.jjdt.entity.AccountOtherDefault;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 提示异地登录
 */
public class ReloginActivity extends BaseActivity {
    final int LOGOUT = 0;
    SweetAlertDialog alertDialog;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOGOUT:
                try {
                    OkHttpUtils.post().url(UrlUtil.LOGOUT)
                            .build()
                            .execute();
                } catch (Exception e) {
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_relogin;
    }

    @Override
    public void initView(View view) {
        mHandler.sendEmptyMessage(LOGOUT);

//        LocalCache.get(ReloginActivity.this).put(LocalCacheKey.KEY_USER, (UserInfo) null);
        ShareUtil.getInstance(ReloginActivity.this).setLocalCookie(LocalCacheKey.KEY_USER, "");
        LocalCache.get(ReloginActivity.this).put(LocalCacheKey.ACCOUNTOTHERDEFAULT, (AccountOtherDefault) null);
        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示！！")
                .setContentText(getIntent().getStringExtra("mes"))
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                MyApplication.getInstance().finishAllActivity();
                startActivity(new Intent(ReloginActivity.this, LoginActivity.class));
            }
        });
    }

}
