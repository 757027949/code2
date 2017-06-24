package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.EditTextUtil;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.MyRadioGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.AccountDetailInfo;
import com.otw.asd.jjdt.entity.AccountOtherDefault;
import com.otw.asd.jjdt.entity.AccountWithdrawal;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * 提现
 * Created by Administrator on 2016/3/31.
 */
public class AccountWithdrawalsActivity extends BaseActivity {

    @Bind(R.id.money)
    FormEditText money;

    @Bind(R.id.rbGroup_mode)
    MyRadioGroup rbGroup_mode;

    @Bind(R.id.rb_add_zfb)
    RadioButton rb_add_zfb;
    @Bind(R.id.rb_add_yl)
    RadioButton rb_add_yl;
    @Bind(R.id.rb_zfb)
    RadioButton rb_zfb;
    @Bind(R.id.rb_yl)
    RadioButton rb_yl;

    @Bind(R.id.submit)
    Button submit;

    ViewCompoundDrawableUtil viewCompoundDrawableUtil;

    boolean isChanged = false;//当前选择的RB是否改变

    String accountOtherId = "";

    final int ACCOUNTEXIT = 0;
    final int GETACCOUNTOTHERDEFAULT = 1;

    AccountOtherDefault accountOtherDefault;
    AccountWithdrawal resultAccountWithdrawal;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ACCOUNTEXIT:
                OkHttpUtils.post().url(UrlUtil.ACCOUNTEXIT)
                        .addParams("accountOrderAmount", money.getText().toString())
                        .addParams("accountOtherId", accountOtherId)
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
                                        if (null != resultAccountWithdrawal) {
                                            LocalCache localCache = LocalCache.get(AccountWithdrawalsActivity.this);
                                            AccountOtherDefault accountOtherDefault = (AccountOtherDefault) localCache.getAsObject(LocalCacheKey.ACCOUNTOTHERDEFAULT);
                                            if (accountOtherDefault.getObj().size() > 0) {//有默认支付方式
                                                for (AccountWithdrawal objBean : accountOtherDefault.getObj()) {
                                                    if (objBean.getAccountOtherType().equals(resultAccountWithdrawal.getAccountOtherType())) {
                                                        accountOtherDefault.getObj().remove(objBean);
                                                        accountOtherDefault.getObj().add(resultAccountWithdrawal);
                                                        localCache.put(LocalCacheKey.ACCOUNTOTHERDEFAULT, accountOtherDefault);
                                                        break;
                                                    }
                                                }
                                            } else {
                                                accountOtherDefault.getObj().add(resultAccountWithdrawal);
                                                localCache.put(LocalCacheKey.ACCOUNTOTHERDEFAULT, accountOtherDefault);
                                            }
                                        }
                                        AccountDetailInfo accountDetailInfo = new Gson().fromJson(jsonObject.getString("obj"), AccountDetailInfo.class);
                                        Intent intent = new Intent(AccountWithdrawalsActivity.this, AccountDetailInfoActivity.class);
                                        intent.putExtra("flow", "withdrawal");
                                        intent.putExtra("data", accountDetailInfo);
                                        startActivity(intent);
                                    } else {
                                        MyDialog.getInstance(AccountWithdrawalsActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(AccountWithdrawalsActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case GETACCOUNTOTHERDEFAULT:
                OkHttpUtils.post().url(UrlUtil.GETACCOUNTOTHERDEFAULT)
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
                                    accountOtherDefault = new Gson().fromJson(response, AccountOtherDefault.class);
                                    if (accountOtherDefault.isSuccess()) {
                                        LocalCache localCache = LocalCache.get(AccountWithdrawalsActivity.this);
                                        localCache.put(LocalCacheKey.ACCOUNTOTHERDEFAULT, accountOtherDefault);
                                        setRgView();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(AccountWithdrawalsActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
        return R.layout.activity_account_withdrawals;
    }

    @Override
    public void initView(View view) {
        AppManager.addActivity(this);
        setTopTitle("提现");

        viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_zfb, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_zfb, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_yl, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(rb_yl, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));

        rbGroup_mode.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                isChanged = true;
                switch (checkedId) {
                    /*case R.id.rb_add_zfb:
                        Intent intent = new Intent(AccountWithdrawalsActivity.this, AddZFBActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rb_add_yl:
                        intent = new Intent(AccountWithdrawalsActivity.this, AddYLActivity.class);
                        startActivity(intent);
                        break;*/
                    case R.id.rb_zfb:
                        accountOtherId = (String) rb_zfb.getTag();
                        break;
                    case R.id.rb_yl:
                        accountOtherId = (String) rb_yl.getTag();
                        break;
                }
            }
        });

        LocalCache localCache = LocalCache.get(this);
        accountOtherDefault = (AccountOtherDefault) localCache.getAsObject(LocalCacheKey.ACCOUNTOTHERDEFAULT);
        if (null == accountOtherDefault) {
            mHandler.sendEmptyMessage(GETACCOUNTOTHERDEFAULT);
        } else {
            setRgView();
        }


    }

    /**
     * 设置提现方式视图
     */
    private void setRgView() {
        for (AccountWithdrawal objBean : accountOtherDefault.getObj()) {
            if ("zfb".equals(objBean.getAccountOtherType())) {
                showAddZFB(false);
                rb_zfb.setText("支付宝（" + objBean.getAccountOtherNumber() + "）");
                rb_zfb.setTag(objBean.getAccountOtherId());
            } else if ("bank".equals(objBean.getAccountOtherType())) {
                showAddYL(false);
                String numberS = objBean.getAccountOtherNumber();
                String replaceS = numberS.substring(0, numberS.length() - 6);
                rb_yl.setText(objBean.getAccountOtherBankName().split("·")[0] + "卡（" + numberS.replace(replaceS, "******") + "）");
                rb_yl.setTag(objBean.getAccountOtherId());

                getYLIco(objBean);
            }
        }
    }

    /**
     * 获取银行图片
     *
     * @param objBean
     */
    private void getYLIco(AccountWithdrawal objBean) {
        Glide.with(this).load(objBean.getAccountOtherPath())
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget(250, 250) {
                    @Override
                    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
                        setYLIco((Bitmap) resource);
                    }

                });
    }

    /**
     * 设置银行图片
     *
     * @param leftRes
     */
    private void setYLIco(Bitmap leftRes) {
        Drawable drawableLeft = BitmapUtil.getInstance().bitmap2Drawable(leftRes);
        /// 这一步必须要做,否则不会显示.
        drawableLeft.setBounds(0, 0, (int) (GestureUtils.getScreenPix(AccountWithdrawalsActivity.this).widthPixels * 0.1), (int) (GestureUtils.getScreenPix(AccountWithdrawalsActivity.this).widthPixels * 0.1));
        Drawable drawablRight = getResources().getDrawable(R.drawable.rb_select);
        /// 这一步必须要做,否则不会显示.
        drawablRight.setBounds(0, 0, (int) (GestureUtils.getScreenPix(AccountWithdrawalsActivity.this).widthPixels * 0.05), (int) (GestureUtils.getScreenPix(AccountWithdrawalsActivity.this).widthPixels * 0.05));
        rb_yl.setCompoundDrawables(drawableLeft, null, drawablRight, null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            resultAccountWithdrawal = (AccountWithdrawal) data.getSerializableExtra("data");
            if (0 == requestCode && 0 == resultCode) {
                showAddZFB(false);
                setZFBView();
            } else if (1 == requestCode && 1 == resultCode) {
                showAddYL(false);
                setYLView();
            } else if (2 == requestCode && 2 == resultCode) {
                setZFBView();
            } else if (3 == requestCode && 3 == resultCode) {
                setYLView();
            }
            accountOtherId = resultAccountWithdrawal.getAccountOtherId();
        }
    }

    /**
     * 设置支付宝
     */
    private void setZFBView() {
        rb_zfb.setText("支付宝（" + resultAccountWithdrawal.getAccountOtherNumber() + "）");
        rb_zfb.setTag(resultAccountWithdrawal.getAccountOtherId());
    }

    /**
     * 设置银行卡
     */
    private void setYLView() {
        String numberS = resultAccountWithdrawal.getAccountOtherNumber();
        String replaceS = numberS.substring(0, numberS.length() - 6);
        rb_yl.setText(resultAccountWithdrawal.getAccountOtherBankName().split("·")[0] + "卡（" + numberS.replace(replaceS, "******") + "）");
        rb_yl.setTag(resultAccountWithdrawal.getAccountOtherId());

        setYLIco(BitmapUtil.getInstance().drawable2Bitmap(getResources().getDrawable(R.mipmap.ic_pay_yl)));

        getYLIco(resultAccountWithdrawal);
    }

    @OnClick({R.id.rb_add_zfb, R.id.rb_add_yl, R.id.rb_zfb, R.id.rb_yl, R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rb_add_zfb:
                Intent intent = new Intent(AccountWithdrawalsActivity.this, AddZFBActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.rb_add_yl:
                intent = new Intent(AccountWithdrawalsActivity.this, AddYLActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rb_zfb:
                if (!isChanged) {
                    intent = new Intent(AccountWithdrawalsActivity.this, ChooseZFBActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    isChanged = false;
                }
                break;
            case R.id.rb_yl:
                if (!isChanged) {
                    intent = new Intent(AccountWithdrawalsActivity.this, ChooseYLActivity.class);
                    startActivityForResult(intent, 3);
                } else {
                    isChanged = false;
                }
                break;
            case R.id.submit:
                if (!money.testValidity()) {
                    return;
                }
                if ("".equals(accountOtherId)) {
                    Toast.makeText(this, "请选择提现账户", Toast.LENGTH_SHORT).show();
                    return;
                }
                mHandler.sendEmptyMessage(ACCOUNTEXIT);
                break;
        }
    }

    /**
     * 展示添加支付宝
     *
     * @param show true:展示 反之
     */
    private void showAddZFB(boolean show) {
        if (show) {
            rb_add_zfb.setVisibility(View.VISIBLE);
            rb_zfb.setVisibility(View.GONE);
        } else {
            rb_add_zfb.setVisibility(View.GONE);
            rb_zfb.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 展示添加银联
     *
     * @param show true:展示 反之
     */
    private void showAddYL(boolean show) {
        if (show) {
            rb_add_yl.setVisibility(View.VISIBLE);
            rb_yl.setVisibility(View.GONE);
        } else {
            rb_add_yl.setVisibility(View.GONE);
            rb_yl.setVisibility(View.VISIBLE);
        }
    }

}