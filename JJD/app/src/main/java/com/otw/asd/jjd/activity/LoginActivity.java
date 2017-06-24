package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.Map;

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
    final int GETUSER = 1;

    private UMShareAPI mShareAPI = null;
    /**
     * 平台登录：platform，第三方登录：qq,wx
     */
    String userOtherType = "platform", openId = "";
    String userRegisterTelephone = "", userLoginPassword = "";

    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case LOGIN:
                OkHttpUtils.post().url(UrlUtil.LOGIN)
                        .addParams("userRegisterTelephone", userRegisterTelephone)
                        .addParams("userLoginPassword", userLoginPassword)
                        .addParams("deviceCode", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjd")
//                        .addParams("userLoginPassword", EncryptUtil.md5(pwd.getText().toString()))
                        .addParams("clientPlatform", "android")
                        .addParams("userIsTeacher", "N")
                        .addParams("userOtherType", userOtherType)
                        .addParams("openId", openId)
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
                                                 LocalCache.get(LoginActivity.this).put(LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION_TIME);
                                                 try {
                                                     User user = new Gson().fromJson(response, User.class);
                                                     if (user.isSuccess()) {
                                                         ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER, PojoUtils.getInstance().getOjbectBase64String(user));
                                                         ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS, name.getText().toString());
                                                         ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_PWD, pwd.getText().toString());
                                                     }
                                                 } catch (Exception e) {
                                                     MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                                 }

                                                 if (200 == jsonObject.getInt("code")) {
//                                                     mHandler.sendEmptyMessage(GETUSER);

                                                     finishSelf();
                                                 } else if (204 == jsonObject.getInt("code")) {//未绑定（帐号登录）
                                                     Intent intent = new Intent(LoginActivity.this, BindOtherAccountActivity.class);
                                                     intent.putExtra("flow","login");
                                                     startActivityForResult(intent, 3);
                                                 }
                                             } else {
                                                 if (404 == jsonObject.getInt("code")) {//未绑定（第三方登录）
                                                     Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                                     intent.putExtra("userOtherType", userOtherType);
                                                     intent.putExtra("openId", openId);
                                                     startActivityForResult(intent, 2);
                                                 } else {
                                                     MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                                 }
                                             }
                                         } catch (Exception e) {
                                             MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                         }
                                     }
                                 }
                        );
                break;

            case GETUSER:
                LocalCache.get(this).put(LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION_TIME);
                OkHttpUtils.post().url(UrlUtil.GETUSER)
                        .addParams("userRegisterTelephone", userRegisterTelephone)
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
                                             User user = new Gson().fromJson(response, User.class);
                                             if (user.isSuccess()) {
//                                        LocalCache localCache = LocalCache.get(LoginActivity.this);
//                                        localCache.put(LocalCacheKey.LOCAL_USER, user);
//                                        localCache.put(LocalCacheKey.FLOW_USER_ACCOUNTS, name.getText().toString());
//                                        localCache.put(LocalCacheKey.FLOW_USER_PWD, pwd.getText().toString());
                                                 ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER, PojoUtils.getInstance().getOjbectBase64String(user));
                                                 ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS, name.getText().toString());
                                                 ShareUtil.getInstance(LoginActivity.this).setLocalCookie(LocalCacheKey.FLOW_USER_PWD, pwd.getText().toString());
                                       /* if ("teacherCurriculum".equals(getIntent().getStringExtra("flow"))) {//预约课程
                                            finishSelf();
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finishSelf();
                                        }*/
                                                 finishSelf();
                                             }
                                         } catch (Exception e) {
                                             MyDialog.getInstance(LoginActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                         }
                                     }
                                 }

                        );
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
        mShareAPI = UMShareAPI.get(this);

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(name, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.07));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(pwd, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.07));
    }

    @OnClick({R.id.login, R.id.register, R.id.forget, R.id.image_wx, R.id.image_qq})
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
                userRegisterTelephone = name.getText().toString();
                userLoginPassword = pwd.getText().toString();
                userOtherType = "platform";
                mHandler.sendEmptyMessage(LOGIN);
                break;
            case R.id.register:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("userOtherType", "platform");
                startActivityForResult(intent, 1);
//                startActivity(intent);
                break;
            case R.id.forget:
                intent = new Intent(this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.image_wx:
                userOtherType = "wx";
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.image_qq:
                userOtherType = "qq";
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
//                intent = new Intent(this, UserInfoActivity.class);
//                startActivity(intent);
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (null != data) {
                openId = data.get("openid");
                mHandler.sendEmptyMessage(LOGIN);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (1 == requestCode && 1 == resultCode) {
                name.setText(data.getStringExtra("phone"));
            } else if (2 == requestCode && 2 == resultCode) {//第三方登录
                userRegisterTelephone = data.getStringExtra("phone");
                userLoginPassword = data.getStringExtra("pwd");
                userOtherType = "platform";
                mHandler.sendEmptyMessage(LOGIN);
            } else if (3 == requestCode && 3 == resultCode) {
//                mHandler.sendEmptyMessage(GETUSER);

                finishSelf();
            }
        }
    }
}
