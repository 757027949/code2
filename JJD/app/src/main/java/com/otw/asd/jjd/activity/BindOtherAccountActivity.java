package com.otw.asd.jjd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.widget.MyDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.base.MyApplication;
import com.otw.asd.jjd.entity.AccountOtherDefault;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.entity.UserConfig;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 提示绑定第三方帐号
 */
public class BindOtherAccountActivity extends BaseActivity {

    private UMShareAPI mShareAPI = null;

    private final int ADDUSEROTHER = 0;
    private final int GETUSERCONFIG = 1;

    User user;
    UserConfig userConfig;

    @Bind(R.id.btn_wx)
    Button btn_wx;
    @Bind(R.id.btn_qq)
    Button btn_qq;


    /**
     * 平台登录：platform，第三方登录：qq,wx
     */
    String userOtherType = "platform", openId = "";

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ADDUSEROTHER:
                OkHttpUtils.post().url(UrlUtil.ADDUSEROTHER)
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
                                    Toast.makeText(BindOtherAccountActivity.this, jsonObject.getString("obj"), Toast.LENGTH_SHORT).show();
                                    if (jsonObject.getBoolean("success")) {
                                        if (null == userConfig) {
                                            userConfig = new UserConfig();
                                        }
                                        if ("wx".equals(userOtherType)) {
                                            userConfig.setIsBindWechat("Y");
                                        } else if ("qq".equals(userOtherType)) {
                                            userConfig.setIsBindQQ("Y");
                                        }
                                        ShareUtil.getInstance(BindOtherAccountActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER_CONFIG, PojoUtils.getInstance().getOjbectBase64String(userConfig));

                                        Intent intent = new Intent();
                                        intent.putExtra("result", "bind");
                                        setResult(3, intent);
                                        finishSelf();
                                        if ("left".equals(getIntent().getStringExtra("flow"))) {
                                            Toast.makeText(BindOtherAccountActivity.this, "绑定成功...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(BindOtherAccountActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case GETUSERCONFIG:
                OkHttpUtils.post().url(UrlUtil.GETUSERCONFIG)
                        .addParams("userId", user.getObj().getUser().getUserId())
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
                                        UserConfig config = new Gson().fromJson(jsonObject.getString("obj"), UserConfig.class);
                                        btn_wx.setEnabled(!"Y".equals(config.getIsBindWechat()));
                                        btn_qq.setEnabled(!"Y".equals(config.getIsBindQQ()));

                                        ShareUtil.getInstance(BindOtherAccountActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER_CONFIG, PojoUtils.getInstance().getOjbectBase64String(config));
                                    } else {
                                        MyDialog.getInstance(BindOtherAccountActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(BindOtherAccountActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bind_other_account;
    }

    @Override
    public void initView(View view) {
        ViewCompoundDrawableUtil drawableUtil = new ViewCompoundDrawableUtil(this);
        drawableUtil.initTextViewCompoundDrawable(btn_qq, 1, 0.15f, 0, 0);
        drawableUtil.initTextViewCompoundDrawable(btn_wx, 1, 0.15f, 0, 0);

        try {
            user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));
            String configStr = ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER_CONFIG);
            if (!"".equals(configStr)) {
                userConfig = (UserConfig) PojoUtils.getInstance().getObject(configStr);
            }
        } catch (Exception e) {
        }

        mShareAPI = UMShareAPI.get(this);
        if (null == userConfig) {//未获取过配置信息
            if (null != user) {
                mHandler.sendEmptyMessage(GETUSERCONFIG);
            }
        } else {
            btn_wx.setEnabled(!"Y".equals(userConfig.getIsBindWechat()));
            btn_qq.setEnabled(!"Y".equals(userConfig.getIsBindQQ()));
        }
    }

    @OnClick({R.id.btn_wx, R.id.btn_qq, R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_wx:
                userOtherType = "wx";
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;

            case R.id.btn_qq:
                userOtherType = "qq";
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.submit:
                Intent intent = new Intent();
                intent.putExtra("result", "unbind");
                setResult(3, intent);
                finishSelf();
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (null != data) {
                openId = data.get("openid");
                mHandler.sendEmptyMessage(ADDUSEROTHER);
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
    }
}