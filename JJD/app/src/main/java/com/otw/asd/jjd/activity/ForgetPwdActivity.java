package com.otw.asd.jjd.activity;

import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.Button;

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

/**
 * 找回密码
 * Created by Administrator on 2016/3/31.
 */
public class ForgetPwdActivity extends BaseActivity {

    @Bind(R.id.phone)
    FormEditText phone;
    @Bind(R.id.code)
    FormEditText code;
    @Bind(R.id.pwd)
    FormEditText pwd;

    @Bind(R.id.get_code)
    Button get_code;

    String getCode;//获取到的验证码

    final int SENDVALIDATECODE = 0;
    final int RESETLOGINPASSWORD = 1;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SENDVALIDATECODE:
                OkHttpUtils.post().url(UrlUtil.SENDVALIDATECODE)
                        .addParams("userRegisterTelephone", phone.getText().toString())
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(true);
                            }

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        timer.start();
                                        get_code.setEnabled(false);
                                        getCode = jsonObject.getString("obj");
                                    } else {
                                        MyDialog.getInstance(ForgetPwdActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(ForgetPwdActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
            case RESETLOGINPASSWORD:
                OkHttpUtils.post().url(UrlUtil.RESETLOGINPASSWORD)
                        .addParams("userRegisterTelephone", phone.getText().toString())
                        .addParams("userLoginPasswordNew", pwd.getText().toString().trim())
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
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(ForgetPwdActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(ForgetPwdActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView(View view) {
        setTopTitle("找回密码");
    }


    @OnClick({R.id.get_code, R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.get_code:
                if (!phone.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(SENDVALIDATECODE);
                break;

            case R.id.submit:
                if (!code.testValidity()) {
                    return;
                }
                if (!pwd.testValidity()) {
                    return;
                }
                if (!code.getText().toString().equals(getCode)) {
                    code.setError("验证码有误");
                    return;
                }
                mHandler.sendEmptyMessage(RESETLOGINPASSWORD);
                break;
        }
    }


    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            get_code.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            get_code.setEnabled(true);
            get_code.setText("获取验证码");
        }
    };


}
