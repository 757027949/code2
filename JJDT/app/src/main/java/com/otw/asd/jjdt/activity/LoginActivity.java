package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.Message;
import android.provider.Settings;
import android.view.View;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录
 * Created by Administrator on 2016/3/31.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.name)
    FormEditText name;
    @Bind(R.id.pwd)
    FormEditText pwd;

    final int LOGIN = 0;
    final int USERINFO = 1;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOGIN:
                OkHttpUtils.post().url(UrlUtil.LOGIN)
                        .addParams("userRegisterTelephone", name.getText().toString())
                        .addParams("userLoginPassword", pwd.getText().toString().trim())
                        .addParams("deviceCode", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjdt")
//                        .addParams("userLoginPassword", MD5Util.md5(pwd.getText().toString().trim()).toString())
                        .addParams("clientPlatform", "android")
                        .addParams("userIsTeacher", "Y")
                        .addParams("userOtherType", "platform")
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
//                                        mHandler.sendEmptyMessage(USERINFO);

                                        try {
                                            UserInfo userInfo = new Gson().fromJson(response, UserInfo.class);

                                            if (userInfo.isSuccess()) {
//                                                LocalCache localCache = LocalCache.get(LoginActivity.this);
//                                                localCache.put(LocalCacheKey.KEY_USER, userInfo);//缓存至本地

                                                ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.KEY_USER, PojoUtils.getInstance().getOjbectBase64String(userInfo));
                                                ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS, name.getText().toString());
                                                ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_PWD, pwd.getText().toString());

                                                if ("-1".equals(userInfo.getObj().getTeacher().getValidateState())) {//未认证 -1:未认证或认证失败 0：认证中 1：认证通过
                                                    Intent intent = new Intent(LoginActivity.this, IdentificationActivity.class);
                                                    intent.putExtra("flow", "login");
                                                    startActivity(intent);
                                                } else {
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                }
                                                finishSelf();
                                            }
                                        } catch (Exception e) {
                                            MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                        }
                                    } else {
                                        MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
            case USERINFO:
                LocalCache.get(this).put(LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION_TIME);
                OkHttpUtils.post().url(UrlUtil.USERINFO)
                        .addParams("userRegisterTelephone", name.getText().toString())
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
                                    UserInfo userInfo = new Gson().fromJson(response, UserInfo.class);

                                    if (userInfo.isSuccess()) {
//                                        LocalCache localCache = LocalCache.get(LoginActivity.this);
//                                        localCache.put(LocalCacheKey.KEY_USER, userInfo);//缓存至本地
                                        ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.KEY_USER, PojoUtils.getInstance().getOjbectBase64String(userInfo));

//                                        localCache.put(LocalCacheKey.FLOW_USER_ACCOUNTS, name.getText().toString());
//                                        localCache.put(LocalCacheKey.FLOW_USER_PWD, pwd.getText().toString());
                                        ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS, name.getText().toString());
                                        ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_PWD, pwd.getText().toString());

                                        if ("-1".equals(userInfo.getObj().getTeacher().getValidateState())) {//未认证 -1:未认证或认证失败 0：认证中 1：认证通过
                                            Intent intent = new Intent(LoginActivity.this, IdentificationActivity.class);
                                            intent.putExtra("flow", "login");
                                            startActivity(intent);
                                        } else {
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        }
                                        finishSelf();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(name, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.07));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(pwd, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.07));
    }

    @OnClick({R.id.login, R.id.register, R.id.forget})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.login:
                if (!name.testValidity()) {
                    return;
                }
                if (!pwd.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(LOGIN);
                break;
            case R.id.register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, 1);
//                startActivity(intent);
                break;
            case R.id.forget:
                intent = new Intent(this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (1 == requestCode && 1 == resultCode) {
                name.setText(data.getStringExtra("phone"));
            }
        }
    }

}
