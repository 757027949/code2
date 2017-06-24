package com.otw.asd.jjd.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.EncryptUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.MyRadioGroup;
import com.asd.util.JsonMapper;
import com.google.gson.Gson;
import com.jungly.gridpasswordview.GridPasswordView;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Apply;
import com.otw.asd.jjd.entity.Charge;
import com.otw.asd.jjd.entity.CurriculumOrder;
import com.otw.asd.jjd.entity.SignUpOrder;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;
import com.pingplusplus.android.Pingpp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 支付方式
 * Created by Administrator on 2016/3/31.
 */
public class PayModeActivity extends BaseActivity {

    @Bind(R.id.rbGroup_mode)
    MyRadioGroup rbGroup_mode;

    @Bind(R.id.rb_zfb)
    RadioButton rb_zfb;
    @Bind(R.id.rb_wx)
    RadioButton rb_wx;
    @Bind(R.id.rb_balance)
    RadioButton rb_balance;

    @Bind(R.id.money)
    TextView money;

    /**
     * 0:支付宝 1:微信 2：余额
     */
    int pay_mode = 0;

    /**
     * 是否使用平台支付
     */
    String isPlatformPay = "N";

    String flow;

    Apply apply;
    CurriculumOrder curriculumOrder;//课程报名订单

    final int APPLY = 0;
    //    final int ADDACCOUNTORDER = 1;
//    final int PAY_MODE = 2;
    final int COURSEORDERAPPLY = 3;
    final int GETCHARGE = 4;

    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 余额支付
     */
    private static final String CHANNEL_ACCOUNT = "account";
    String accountOrderMethod = CHANNEL_ALIPAY;
    String amount = "";
    String accountOrderExplain = "";
    String userPayPassword = "";
    String chargeId = "";
    String course = "";
    String accountCouponId = "";

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if ("signup".equals(flow)) {//报名
            AppManager.finishAllActivity();
        } else if ("markOrder".equals(flow)) {//预约教练下单
            AppManager.finishAllActivity();
        } else if ("recordInfo".equals(flow)) {//记录详情

        }*/
    }

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case APPLY:
                OkHttpUtils.post().url(UrlUtil.APPLY)
                        .addParams("addressProvinceName", apply.getProvinceName())
                        .addParams("addressCityName", apply.getCityName())
                        .addParams("addressAreaName", apply.getAreaName())
                        .addParams("addressDetailName", apply.getUserHomeAddress())
                        .addParams("userRealName", apply.getUserRealName())
                        .addParams("userContactTelephone", apply.getUserContactTelephone())
                        .addParams("applyStudyType", apply.getApplyStudyType())
//                        .addParams("isAccountPay", isPlatformPay)
//                        .addParams("accountOrderMethod", 0 == pay_mode ? "zfb" : "wx")
                        .addParams("accountOrderAmount", apply.getMoney() + "")//支付金额
//                        .addParams("accountOrderExplain", "报名学车")//支付说明
                        .addParams("userPayPassword", EncryptUtil.MD5(userPayPassword))
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
                                    SignUpOrder order = new Gson().fromJson(response, SignUpOrder.class);
                                    if (order.isSuccess()) {
//                                        User user = (User) LocalCache.get(PayModeActivity.this).getAsObject(LocalCacheKey.LOCAL_USER);
                                        User user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(PayModeActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER));
                                        user.getObj().getUser().setIsApply("Y");
                                        user.getObj().getUser().setAccountOrderId(order.getObj().getAccountOrder().getAccountOrderId());
//                                        LocalCache.get(PayModeActivity.this).put(LocalCacheKey.LOCAL_USER, user);
                                        ShareUtil.getInstance(PayModeActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER,PojoUtils.getInstance().getOjbectBase64String(user));

                                        Intent intent = new Intent(PayModeActivity.this, SignUpOrderActivity.class);
                                        intent.putExtra("flow", "sign");
                                        intent.putExtra("data", order);
                                        startActivity(intent);
                                        AppManager.finishAllActivity();
                                    }
                                } catch (Exception e) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (!jsonObject.getBoolean("success")) {
                                            MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                        }
                                    } catch (JSONException e1) {
                                        MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                    }

                                }
                            }
                        });
                break;

            case COURSEORDERAPPLY:
//                Logger.e(userPayPassword + "\n" + JsonMapper.toNormalJson(getIntent().getStringArrayListExtra("courseOrderIds")));
                OkHttpUtils.post().url(UrlUtil.COURSEORDERAPPLY)
                        .addParams("courseOrderIds", JsonMapper.toNormalJson(getIntent().getStringArrayListExtra("courseOrderIds")))
                        .addParams("userPayPassword", EncryptUtil.MD5(userPayPassword))
                        .addParams("chargeId", chargeId)
                        .addParams("accountCouponId", accountCouponId)
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
                                        try {
                                            AppManager.finishAllActivity();
                                            MainActivity.handler.sendEmptyMessage(MainActivity.FLOW_GO_RECORD);
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case GETCHARGE:
                OkHttpUtils.post().url(UrlUtil.GETCHARGE)
                        .addParams("channel", accountOrderMethod)
                        .addParams("accountOrderAmount", amount)
                        .addParams("chargeSubject", course)
                        .addParams("accountCouponId", accountCouponId)
                        .addParams("courseOrderIds", "apply".equals(course) ? "" : JsonMapper.toNormalJson(getIntent().getStringArrayListExtra("courseOrderIds")))//课程订单集合
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
                                    Charge charge = new Gson().fromJson(response, Charge.class);
                                    chargeId = charge.getId();
                                    Pingpp.createPayment(PayModeActivity.this, response);
                                } catch (Exception e) {
                                    MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
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
                    if ("signup".equals(flow)) {//报名
                        mHandler.sendEmptyMessage(APPLY);
                    } else if ("markOrder".equals(flow)) {//预约教练下单
                        mHandler.sendEmptyMessage(COURSEORDERAPPLY);
                    }
                } else {
                    if ("fail".equals(result)) {
                        MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog("支付失败").show();
                    } else if ("cancel".equals(result)) {
                        MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog("取消支付").show();
                    } else if ("invalid".equals(result)) {
                        MyDialog.getInstance(PayModeActivity.this).getWaringAlertDialog("支付插件未安装").show();
                    }
                }
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_mode;
    }

    @Override
    public void initView(View view) {
        AppManager.addActivity(this);
        setTopTitle("支付方式");

        flow = getIntent().getStringExtra("flow");

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_zfb, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_zfb, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_wx, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_wx, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_balance, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_balance, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));

        rbGroup_mode.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_zfb:
                        pay_mode = 0;
                        isPlatformPay = "N";
                        accountOrderMethod = CHANNEL_ALIPAY;
                        break;
                    case R.id.rb_wx:
                        pay_mode = 1;
                        isPlatformPay = "N";
                        accountOrderMethod = CHANNEL_WECHAT;
                        break;
                    case R.id.rb_balance:
                        pay_mode = 2;
                        isPlatformPay = "Y";
                        accountOrderMethod = CHANNEL_ACCOUNT;
                        break;
                }
            }
        });


        if ("signup".equals(flow)) {//报名
            apply = getIntent().getParcelableExtra("apply");
            amount = apply.getMoney() + "";
            accountOrderExplain = "报名学车";
            money.setText("￥" + (apply.getMoney()));
            course = "apply";
        } else if ("markOrder".equals(flow)) {//预约教练下单
            money.setText("￥" + getIntent().getStringExtra("money"));
            amount = getIntent().getStringExtra("money");
            accountOrderExplain = "课程报名";
            course = "course";
            accountCouponId = null == getIntent().getStringExtra("accountCouponId") ? "" : getIntent().getStringExtra("accountCouponId");
        }

    }

    @OnClick({R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }

        if (accountOrderMethod.equals(CHANNEL_ACCOUNT)) {
            chargeId = "";
            getPwdDialog().show();
        } else {
            mHandler.sendEmptyMessage(GETCHARGE);
        }
    }

    /**
     * 获取输入密码对话框
     *
     * @return
     */
    private Dialog getPwdDialog() {
        final Dialog dialog = new Dialog(this, R.style.PwdDialog);
        dialog.setContentView(R.layout.dialog_account_pay);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView mes = (TextView) dialog.findViewById(R.id.mes);
        mes.setText(accountOrderExplain);
        TextView money = (TextView) dialog.findViewById(R.id.money);
        money.setText("￥" + amount);
        GridPasswordView pwd = (GridPasswordView) dialog.findViewById(R.id.pwd);
        pwd.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                userPayPassword = psw;
                if ("signup".equals(flow)) {//报名
                    mHandler.sendEmptyMessage(APPLY);
                } else if ("markOrder".equals(flow)) {//预约教练下单
                    mHandler.sendEmptyMessage(COURSEORDERAPPLY);
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
