package com.otw.asd.jjd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.EditTextUtil;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.MyRadioGroup;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.AccountDetailInfo;
import com.otw.asd.jjd.entity.Charge;
import com.otw.asd.jjd.util.UrlUtil;
import com.pingplusplus.android.Pingpp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * 充值
 * Created by Administrator on 2016/3/31.
 */
public class AccountRechargeActivity extends BaseActivity {

    @Bind(R.id.money)
    FormEditText money;

    @Bind(R.id.rbGroup_mode)
    MyRadioGroup rbGroup_mode;

    @Bind(R.id.rb_wx)
    RadioButton rb_wx;
    @Bind(R.id.rb_zfb)
    RadioButton rb_zfb;

    @Bind(R.id.submit)
    Button submit;

    final int ACCOUNTRECHARGE = 0;
    final int GETCHARGE = 4;

    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    String accountOrderMethod = CHANNEL_ALIPAY;
    String accountOrderExplain = "支付宝充值";
    String chargeId = "";

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ACCOUNTRECHARGE:
                OkHttpUtils.post().url(UrlUtil.ACCOUNTRECHARGE)
                        .addParams("chargeId", chargeId)
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
                                        AccountDetailInfo accountDetailInfo = new Gson().fromJson(jsonObject.getString("obj"), AccountDetailInfo.class);
                                        Intent intent = new Intent(AccountRechargeActivity.this, AccountDetailInfoActivity.class);
                                        intent.putExtra("flow", "recharge");
                                        intent.putExtra("data", accountDetailInfo);
                                        startActivity(intent);
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(AccountRechargeActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(AccountRechargeActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case GETCHARGE:
                OkHttpUtils.post().url(UrlUtil.GETCHARGE)
                        .addParams("channel", accountOrderMethod)
                        .addParams("accountOrderAmount", money.getText().toString())
                        .addParams("chargeSubject", "recharge")
                        .addParams("courseOrderIds", "")
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(true);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                Pingpp.createPayment(AccountRechargeActivity.this, response);
                                Charge charge = new Gson().fromJson(response, Charge.class);
                                chargeId = charge.getId();
                            }
                        });
                break;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");

                // 处理返回值
                // "success" - 支付成功
                // "fail"    - 支付失败
                // "cancel"  - 取消支付
                // "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）

                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                Logger.e(result + "\n" + errorMsg + "\n" + extraMsg);
                if ("success".equals(result)) {
                    mHandler.sendEmptyMessage(ACCOUNTRECHARGE);
                } else {
                    if ("fail".equals(result)) {
                        MyDialog.getInstance(AccountRechargeActivity.this).getWaringAlertDialog("支付失败").show();
                    } else if ("cancel".equals(result)) {
                        MyDialog.getInstance(AccountRechargeActivity.this).getWaringAlertDialog("取消支付").show();
                    } else if ("invalid".equals(result)) {
                        MyDialog.getInstance(AccountRechargeActivity.this).getWaringAlertDialog("支付插件未安装").show();
                    }
                }
            }
        }
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_account_recharge;
    }

    @Override
    public void initView(View view) {
        setTopTitle("充值");

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_wx, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_wx, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_zfb, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_zfb, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));

        rbGroup_mode.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_zfb:
                        accountOrderMethod = CHANNEL_ALIPAY;
                        accountOrderExplain = "支付宝充值";
                        break;

                    case R.id.rb_wx:
                        accountOrderMethod = CHANNEL_WECHAT;
                        accountOrderExplain = "微信充值";
                        break;
                }
            }
        });
    }

    @OnTextChanged(R.id.money)
    public void checkMoney() {
        if (EditTextUtil.getInstance().initTextViewNumberDecimalSizeAndCheck(money, 2)) {
            if (Float.parseFloat("0.01") > Float.parseFloat(money.getText().toString())) {
                submit.setEnabled(false);
            } else {
                submit.setEnabled(true);
            }
        } else {
            submit.setEnabled(false);
        }
    }

    @OnClick({R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.submit:
                if (!money.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(GETCHARGE);
                break;
        }
    }

}
