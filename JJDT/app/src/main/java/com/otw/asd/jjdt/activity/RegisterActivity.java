package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.widget.MyDialog;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册
 * Created by Administrator on 2016/3/31.
 */
public class RegisterActivity extends BaseActivity {

    @Bind(R.id.phone)
    FormEditText phone;
    @Bind(R.id.code)
    FormEditText code;
    @Bind(R.id.pwd)
    FormEditText pwd;

    @Bind(R.id.get_code)
    Button get_code;

    @Bind(R.id.cb_register)
    CheckBox cb_register;


    final int SENDVALIDATECODE = 0;
    final int REGISTER = 1;

    String getCode = "";//获取到的验证码

    @Override
    public boolean handleMessage(Message msg) {
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
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        timer.start();
                                        get_code.setEnabled(false);
                                        getCode = jsonObject.getString("obj");
                                    } else {
                                        MyDialog.getInstance(RegisterActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(RegisterActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case REGISTER:
                OkHttpUtils.post().url(UrlUtil.REGISTER)
                        .addParams("userRegisterTelephone", phone.getText().toString())
                        .addParams("userLoginPassword", pwd.getText().toString().trim())
                        .addParams("userPayPassword", "000000")
//                        .addParams("userLoginPassword", MD5Util.md5(pwd.getText().toString().trim()).toString())
//                        .addParams("userPayPassword", MD5Util.md5("000000").toString())
                        .addParams("userIsTeacher", "Y")
                        .addParams("userOtherType", "platform")//平台登录：platform，第三方登录：qq,wx
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
                                        Intent intent = new Intent();
                                        intent.putExtra("phone", phone.getText().toString());
                                        setResult(1, intent);
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(RegisterActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(RegisterActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        setTopTitle("注册");
    }


    @OnClick({R.id.get_code, R.id.submit, R.id.register_mes})
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
                if (!cb_register.isChecked()) {
                    Toast.makeText(this, "请选中‘" + getResources().getString(R.string.register_check_mes) + "’", Toast.LENGTH_SHORT).show();
                    return;
                }
                mHandler.sendEmptyMessage(REGISTER);
                break;
            case R.id.register_mes:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", UrlUtil.TERM_AND_HIDE);
                intent.putExtra("flow", "register");
                startActivity(intent);
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
