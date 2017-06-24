package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.util.gesture.GestureUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hedgehog.ratingbar.RatingBar;
import com.loveplusplus.update.UpdateChecker;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.AccountOtherDefault;
import com.otw.asd.jjdt.entity.Order;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.service.NetworkStateService;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity {

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.hechang)
    TextView hechang;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.years)
    TextView years;
    @Bind(R.id.order)
    TextView order;

    @Bind(R.id.ratingbar)
    RatingBar ratingbar;

    @Bind(R.id.curriculum)
    TextView curriculum;
    @Bind(R.id.set_curriculum)
    TextView set_curriculum;
    @Bind(R.id.record)
    TextView record;
    @Bind(R.id.account)
    TextView account;

    @Bind(R.id.image_verification_status)
    ImageView image_verification_status;

    View home_menu_top;
    TagAliasCallback tagAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    mHandler.sendEmptyMessageDelayed(JPUSH_SET_VALUE, 1000 * 60);
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
            }
            Logger.e(logs);
        }

    };


    public static Handler mHandler;

    final int USERINFO = 0;
    final int SET_VIEW_STATUS = 1;
    final int JPUSH_SET_VALUE = 2;

    /**
     * 设置单来了数量
     */
    public static final int JPUSH_SET_ORDER_SIZE = 3;
    /**
     * 重新登录
     */
    public static final int RELOGIN = 4;
    final int LOGOUT = 5;

    SweetAlertDialog alertDialog = null;


    Intent serviceIntent;
    UserInfo userInfo;
    LocalCache localCache;

    @Override
    protected void onStart() {
        super.onStart();
        serviceIntent = new Intent(this, NetworkStateService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        try {
            mHandler.sendMessage(mHandler.obtainMessage(JPUSH_SET_ORDER_SIZE, ((HashMap<String, Order>) LocalCache.get(this).getAsObject(LocalCacheKey.ORDERS)).size()));
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }


    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case USERINFO:
                OkHttpUtils.post().url(UrlUtil.USERINFO)
                        .addParams("userRegisterTelephone", userInfo.getObj().getUser().getUserRegisterTelephone())
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(false);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    UserInfo user = new Gson().fromJson(response, UserInfo.class);

                                    if (user.isSuccess()) {
//                                        LocalCache localCache = LocalCache.get(MainActivity.this);
//                                        localCache.put(LocalCacheKey.KEY_USER, user);//缓存至本地

                                        ShareUtil.getInstance(MainActivity.this).setLocalCookie(LocalCacheKey.KEY_USER, PojoUtils.getInstance().getOjbectBase64String(user));
                                        userInfo = user;
                                        mHandler.sendEmptyMessage(SET_VIEW_STATUS);
                                    }
                                } catch (Exception e) {
//                                    MyDialog.getInstance(MainActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case SET_VIEW_STATUS:
                if ("0".equals(userInfo.getObj().getTeacher().getValidateState())) {
                    showHomeMenuTopView();
                } else {
                    closeHomeMenuTopView();
                }
                setViewValue();
                break;

            case JPUSH_SET_VALUE:
                JPushInterface.init(getApplicationContext());
                String tag = userInfo.getObj().getUser().getUserId();
                String alias = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjdt";
                Logger.e(tag + "====" + alias);
                Set<String> set = new LinkedHashSet<String>();
                set.add(tag);
//                set.add(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)+"_jjdt");
                JPushInterface.setAliasAndTags(getApplicationContext(), alias, set, tagAliasCallback);
                break;

            case JPUSH_SET_ORDER_SIZE:
                order.setText("单来了（" + msg.obj + "）");
                break;

            case RELOGIN:
                Intent intent = new Intent(this, ReloginActivity.class);
                intent.putExtra("mes", msg.obj.toString());
                startActivity(intent);
                break;

            case LOGOUT:
                try {
                    OkHttpUtils.post().url(UrlUtil.LOGOUT)
                            .build()
                            .execute();
                } catch (Exception e) {
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        //初始化
        showHomeMenuTopView();

        localCache = LocalCache.get(this);
//        userInfo = (UserInfo) localCache.getAsObject(LocalCacheKey.KEY_USER);
        userInfo = (UserInfo) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.KEY_USER));

        mHandler = new Handler(this);
        mHandler.sendEmptyMessage(JPUSH_SET_VALUE);
        if ("laucher".equals(getIntent().getStringExtra("flow"))) {//需要登录操作
//            mHandler.sendEmptyMessage(USERINFO);
            mHandler.sendEmptyMessage(SET_VIEW_STATUS);
        } else {//登录  认证  ------  取本地数据
            mHandler.sendEmptyMessage(SET_VIEW_STATUS);
        }

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(curriculum, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.15));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(set_curriculum, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.15));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(record, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.15));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(account, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.15));

        UpdateChecker.checkForDialog(this, UrlUtil.GETCLIENT, false);
//        UpdateChecker.checkForNotification(this,UrlUtil.GETCLIENT);
    }

    private void setViewValue() {
        Glide.with(this).load(userInfo.getObj().getUser().getUserHeadPath()).error(R.mipmap.ic_person).into(image);
        hechang.setVisibility("Y".equals(userInfo.getObj().getTeacher().getIsHeChang()) ? View.VISIBLE : View.GONE);
        name.setText(userInfo.getObj().getUser().getUserRealName());
        years.setText(userInfo.getObj().getTeacher().getTeacherTeachYear());
        Drawable halfDrawable = getResources().getDrawable(R.mipmap.ic_star_half);
        ratingbar.setStarHalfDrawable(halfDrawable);
        ratingbar.setStar(userInfo.getObj().getTeacher().getTeacherLevel());
    }

    @OnClick({R.id.curriculum, R.id.record, R.id.set_curriculum, R.id.account, R.id.order, R.id.exit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.curriculum:
                Intent intent = new Intent(this, CurriculumActivity.class);
                startActivity(intent);
                break;
            case R.id.record:
                intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.set_curriculum:
                intent = new Intent(this, SetCurriculumActivity.class);
                startActivity(intent);
                break;
            case R.id.account:
                intent = new Intent(this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.order:
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("flow", "main");
                startActivity(intent);
                break;
            case R.id.exit:
                if (null == alertDialog) {
                    alertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示！！")
                            .setContentText("是否退出当前用户？")
                            .setCancelText("取消")
                            .setConfirmText("确定")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    mHandler.sendEmptyMessage(LOGOUT);
                                    ShareUtil.getInstance(MainActivity.this).setLocalCookie(LocalCacheKey.KEY_USER, "");
                                    LocalCache.get(MainActivity.this).put(LocalCacheKey.ACCOUNTOTHERDEFAULT, (AccountOtherDefault) null);
                                    MainActivity.this.finishSelf();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                }
                            });
                }
                alertDialog.show();
                break;
        }
    }

    /**
     * 显示验证中View
     */
    private void showHomeMenuTopView() {
        image_verification_status.setVisibility(View.GONE);
        if (null != home_menu_top) {
            home_menu_top.setVisibility(View.VISIBLE);
            return;
        }
        ViewStub viewStub = (ViewStub) findViewById(R.id.home_menu_top);
        home_menu_top = viewStub.inflate();
    }

    /**
     * 关闭验证中View
     */
    private void closeHomeMenuTopView() {
        image_verification_status.setVisibility(View.VISIBLE);
        if (null != home_menu_top) {
            home_menu_top.setVisibility(View.GONE);
        }
    }


    private int mBackKeyPressedTimes = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBackKeyPressedTimes == 0) {
                Snackbar.make(getWindow().getDecorView(), "再按一次退出程序 ", Snackbar.LENGTH_SHORT).show();
                mBackKeyPressedTimes = 1;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            mBackKeyPressedTimes = 0;
                        }
                    }
                }.start();
                return false;
            } else {
                AppManager.AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
