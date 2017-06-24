package com.otw.asd.jjd.activity;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.widget.MyDialog;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/7/14.
 */
public class AddVoucherActivity extends BaseActivity {
    @Bind(R.id.code)
    FormEditText code;

    final int GETACCOUNTCOUPON = 0;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case GETACCOUNTCOUPON:
                OkHttpUtils.post().url(UrlUtil.GETACCOUNTCOUPON)
                        .addParams("accountCouponConvertCode", code.getText().toString())
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return false;
                            }

                            @Override
                            public Context getContext() {
                                return AddVoucherActivity.this;
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
                                        Toast.makeText(AddVoucherActivity.this, jsonObject.getString("obj"), Toast.LENGTH_SHORT).show();
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(AddVoucherActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(AddVoucherActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_voucher;
    }

    @Override
    public void initView(View view) {
        setTopTitle("激活优惠券");
    }

    @OnClick({R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.submit:
                if (!code.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(GETACCOUNTCOUPON);
                break;
        }
    }
}
