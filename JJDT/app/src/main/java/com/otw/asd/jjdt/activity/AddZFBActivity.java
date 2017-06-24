package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.RegexUtils;
import com.asd.android.widget.MyDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.entity.AccountWithdrawal;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 添加支付宝
 * Created by Administrator on 2016/3/31.
 */
public class AddZFBActivity extends BaseActivity {

    @Bind(R.id.accounts)
    FormEditText accounts;

    final int ADDACCOUNTOTHER = 0;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ADDACCOUNTOTHER:
                OkHttpUtils.post().url(UrlUtil.ADDACCOUNTOTHER)
                        .addParams("accountOtherType", "zfb")//bank银行卡，zfb支付宝，wx微信
                        .addParams("accountOtherOwner", "")//账户拥有者
                        .addParams("accountOtherNumber", accounts.getText().toString())//帐号
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
                                        AccountWithdrawal accountWithdrawal = new Gson().fromJson(jsonObject.getString("obj"), AccountWithdrawal.class);
                                        Intent intent = new Intent();
                                        intent.putExtra("data", accountWithdrawal);
                                        setResult(0, intent);
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(AddZFBActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(AddZFBActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_add_zfb;
    }

    @Override
    public void initView(View view) {
        setTopTitle("添加支付宝");
    }

    @OnClick({R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.submit:
                if (!accounts.testValidity()) {
                    return;
                }
                if (!RegexUtils.checkZFB(accounts.getText().toString())) {
                    accounts.setError("支付宝帐号输入有误...");
                    return;
                }
                mHandler.sendEmptyMessage(ADDACCOUNTOTHER);
                break;
        }
    }

}
