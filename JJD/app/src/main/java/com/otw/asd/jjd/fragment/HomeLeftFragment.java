package com.otw.asd.jjd.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.MyCallBack;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.AccountActivity;
import com.otw.asd.jjd.activity.BindOtherAccountActivity;
import com.otw.asd.jjd.activity.IdeaActivity;
import com.otw.asd.jjd.activity.MainActivity;
import com.otw.asd.jjd.activity.RecordActivity;
import com.otw.asd.jjd.activity.SignUpActivity;
import com.otw.asd.jjd.activity.SignUpOrderActivity;
import com.otw.asd.jjd.activity.StudentActivity;
import com.otw.asd.jjd.activity.UserInfoActivity;
import com.otw.asd.jjd.base.BaseFragment;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.AccountOtherDefault;
import com.otw.asd.jjd.entity.Contact;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.entity.UserExp;
import com.otw.asd.jjd.util.PhoneUtil;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/3/30.
 */
public class HomeLeftFragment extends BaseFragment {

    @Bind(R.id.item_record)
    TextView item_record;
    @Bind(R.id.item_account)
    TextView item_account;
    @Bind(R.id.item_sign_up)
    TextView item_sign_up;
    @Bind(R.id.item_student)
    TextView item_student;
    @Bind(R.id.item_bind)
    TextView item_bind;
    @Bind(R.id.item_idea)
    TextView item_idea;
    @Bind(R.id.item_exit)
    TextView item_exit;
    @Bind(R.id.nickName)
    TextView nickName;
    @Bind(R.id.exp)
    TextView exp;
    @Bind(R.id.declaration)
    TextView declaration;

    @Bind(R.id.person_image)
    ImageView person_image;

    RelativeLayout rel_head;
    @Bind(R.id.line_record)
    LinearLayout line_record;
    @Bind(R.id.line_account)
    LinearLayout line_account;
    @Bind(R.id.line_sign_up)
    LinearLayout line_sign_up;
    @Bind(R.id.line_student)
    LinearLayout line_student;

    MainActivity mainActivity = null;

    SweetAlertDialog alertDialog = null;

    final int FLOW_CLOOSE_MENU = 0;//关闭菜单
    public static final int FLOW_INIT_VIEW = -1;//初始化界面

    final int LOGOUT = 1;
    final int GETEXP = 2;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.sendEmptyMessageDelayed(FLOW_CLOOSE_MENU, 500);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case FLOW_INIT_VIEW:
                try {
                    nickName.setText(mainActivity.getUser().getObj().getUser().getUserNickName());
                    declaration.setText(mainActivity.getUser().getObj().getUser().getUserSign());
                    Logger.e(mainActivity.getUser().getObj().getUser().getUserHeadPath());
                    Glide.with(mContext).load(mainActivity.getUser().getObj().getUser().getUserHeadPath()).error(R.mipmap.ic_person).into(person_image);
//                    Glide.with(mContext).load(mainActivity.getUser().getObj().getUserAlbums().size() > 0 ? mainActivity.getUser().getObj().getUserAlbums().get(0).getUserAlbumPath() : R.mipmap.ic_person).error(R.mipmap.ic_person).into(person_image);
                } catch (Exception e) {
                }
                try {
                    String expString = ShareUtil.getInstance(mContext).getLocalCookie(LocalCacheKey.USER_EXP);
                    if (!"".equals(expString)) {
                        UserExp userExp = (UserExp) PojoUtils.getInstance().getObject(expString);
                        exp.setText("LV" + userExp.getCurrentLevel());
                    }
                    mHandler.sendEmptyMessage(GETEXP);
                } catch (Exception e) {
                }
                break;
            case FLOW_CLOOSE_MENU:
//                Logger.e("FLOW_CLOOSE_MENU...");
                if (null != mainActivity) {
                    mainActivity.closeSlidingMenu();
                }
                break;
            case LOGOUT:
                try {
                    OkHttpUtils.post().url(UrlUtil.LOGOUT)
                            .build()
                            .execute();
                } catch (Exception e) {
                }
                break;

            case GETEXP:
                OkHttpUtils.post().url(UrlUtil.GETEXP)
                        .addParams("userId", mainActivity.getUser().getObj().getUser().getUserId())
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return true;
                            }

                            @Override
                            public Context getContext() {
                                return mContext;
                            }

                            @Override
                            public SweetAlertDialog getLoadDialog() {
                                return getLodingAlertDialog();
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        UserExp userExp = new Gson().fromJson(jsonObject.getString("obj"), UserExp.class);
                                        exp.setText("LV" + userExp.getCurrentLevel());
                                        ShareUtil.getInstance(mContext).setLocalCookie(LocalCacheKey.USER_EXP, PojoUtils.getInstance().getOjbectBase64String(userExp));
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                break;
        }
        return false;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_left;
    }

    @Override
    public void initView(View view) {
        initMenuAttr();
    }

    public void initMenuAttr() {
        ViewCompoundDrawableUtil drawableUtil = new ViewCompoundDrawableUtil(getContext());
        drawableUtil.initTextViewCompoundDrawable(item_record, 0, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.035));
        drawableUtil.initTextViewCompoundDrawable(item_record, 2, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.015));
        drawableUtil.initTextViewCompoundDrawable(item_account, 0, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.035));
        drawableUtil.initTextViewCompoundDrawable(item_account, 2, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.015));
        drawableUtil.initTextViewCompoundDrawable(item_sign_up, 0, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.035));
        drawableUtil.initTextViewCompoundDrawable(item_sign_up, 2, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.015));
        drawableUtil.initTextViewCompoundDrawable(item_student, 0, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.035));
        drawableUtil.initTextViewCompoundDrawable(item_student, 2, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.015));
        drawableUtil.initTextViewCompoundDrawable(item_idea, 0, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.035));
        drawableUtil.initTextViewCompoundDrawable(item_idea, 2, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.015));
        drawableUtil.initTextViewCompoundDrawable(item_exit, 0, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.035));
        drawableUtil.initTextViewCompoundDrawable(item_exit, 2, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.015));
        drawableUtil.initTextViewCompoundDrawable(item_bind, 0, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.035));
        drawableUtil.initTextViewCompoundDrawable(item_bind, 2, (int) (GestureUtils.getScreenPix(getContext()).heightPixels * 0.015));


      /*  BadgeView badgeView = new BadgeView(getContext(), line_account);
        badgeView.setText("10");
        badgeView.setBadgeMargin(100);
        badgeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
        badgeView.setBadgePosition(BadgeView.POSITION_CENTER_VERTICAL_RIGHY);
        badgeView.show();*/
    }

    @OnClick({R.id.rel_head, R.id.item_record, R.id.item_bind, R.id.item_account, R.id.item_sign_up, R.id.item_student, R.id.item_idea, R.id.item_exit, R.id.hot_phone})
    public void itemClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rel_head:
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                intent.putExtra("flow", "self");
                startActivity(intent);
                break;
            case R.id.item_record:
                intent = new Intent(getContext(), RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.item_bind:
                intent = new Intent(getContext(), BindOtherAccountActivity.class);
                intent.putExtra("flow", "left");
                startActivity(intent);
                break;
            case R.id.item_account:
                intent = new Intent(getContext(), AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.item_sign_up:
                if ("N".equals(mainActivity.getUser().getObj().getUser().getIsApply())) {
                    intent = new Intent(getContext(), SignUpActivity.class);
                } else {
                    intent = new Intent(getContext(), SignUpOrderActivity.class);
                    intent.putExtra("flow", "select");
                }
                startActivity(intent);
                break;
            case R.id.item_student:
                intent = new Intent(getContext(), StudentActivity.class);
                startActivity(intent);
                break;
            case R.id.item_idea:
                intent = new Intent(getContext(), IdeaActivity.class);
                startActivity(intent);
                break;
            case R.id.item_exit:
                if (null == alertDialog) {
                  /*  alertDialog = MyDialog.getInstance(mContext).getWaringAlertDialog("是否退出当前用户!!!", "确定", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            LocalCache.get(mContext).put(LocalCacheKey.LOCAL_USER, (User) null);
                            sweetAlertDialog.dismiss();
                            try {
                                mainActivity.closeSlidingMenu();
                                mainActivity.getFloatView().destroyFloat();
                                mHandler.sendEmptyMessage(LOGOUT);
                            } catch (Exception e) {
                            }
                        }
                    }, "取消", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });*/

                    alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
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
//                                    LocalCache.get(mContext).put(LocalCacheKey.LOCAL_USER, (User) null);
                                    ShareUtil.getInstance(mContext).setLocalCookie(LocalCacheKey.LOCAL_USER, "");
                                    ShareUtil.getInstance(mContext).setLocalCookie(LocalCacheKey.LOCAL_USER_CONFIG, "");
                                    ShareUtil.getInstance(mContext).setLocalCookie(LocalCacheKey.USER_EXP, "");
                                    LocalCache.get(mContext).put(LocalCacheKey.ACCOUNTOTHERDEFAULT, (AccountOtherDefault) null);
                                    sweetAlertDialog.dismiss();
                                    try {
                                        mHandler.sendEmptyMessage(LOGOUT);
                                        mainActivity.setInitEMSuccess(false);
                                        mainActivity.closeSlidingMenu();
                                        mainActivity.getFloatView().destroyFloat();
                                    } catch (Exception e) {
                                    }
                                    Hawk.put(Contact.CONTACT_KEY, new HashMap<String, Contact>());

                                    EMClient.getInstance().logout(false, new EMCallBack() {

                                        @Override
                                        public void onSuccess() {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onProgress(int progress, String status) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onError(int code, String message) {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                                }
                            });
                }
                alertDialog.show();


//                AppManager.AppExit(mContext);
//                intent = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent);
                break;

            case R.id.hot_phone:
                try {
                    PhoneUtil.getInstance(mContext).call(((TextView) view).getText().toString().replace("-", ""));
                } catch (Exception e) {
                }
                break;
        }
    }
}
