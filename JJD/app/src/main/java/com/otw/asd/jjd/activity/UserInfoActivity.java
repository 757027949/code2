package com.otw.asd.jjd.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.Constant;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Contact;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.entity.UserExp;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;
import com.otw.asd.jjd.widget.ExpView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 我的资料
 * Created by Administrator on 2016/3/31.
 */
public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.lint_exp)
    LinearLayout lint_exp;
    @Bind(R.id.current_exp)
    TextView current_exp;
    @Bind(R.id.need_exp)
    TextView need_exp;
    @Bind(R.id.expView)
    ExpView expView;
    @Bind(R.id.edit)
    TextView edit;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.name_and_age)
    TextView name_and_age;
    @Bind(R.id.image_sex)
    ImageView image_sex;
    @Bind(R.id.declaration)
    TextView declaration;
    @Bind(R.id.birthday)
    TextView birthday;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.constellation)
    TextView constellation;
    @Bind(R.id.byUser)
    TextView byUser;
    @Bind(R.id.line_bottom)
    LinearLayout line_bottom;

    User user;
    String userId;
    List<String> imagePaths = new ArrayList<String>();

    final int SET_VIEW_VALUE = 0;
    final int GETUSER = 1;
    final int GETEXP = 2;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SET_VIEW_VALUE:
                user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));
                setViewValue();
                break;
            case GETUSER:
                OkHttpUtils.post().url(UrlUtil.GETUSER)
                        .addParams("userId", userId)
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return false;
                            }

                            @Override
                            public Context getContext() {
                                return UserInfoActivity.this;
                            }

                            @Override
                            public SweetAlertDialog getLoadDialog() {
                                return getLodingAlertDialog();
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    user = new Gson().fromJson(response, User.class);
                                    if (user.isSuccess()) {
                                        edit.setVisibility(View.GONE);
                                        line_bottom.setVisibility(View.VISIBLE);
                                        setViewValue();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(UserInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case GETEXP:
                OkHttpUtils.post().url(UrlUtil.GETEXP)
                        .addParams("userId", userId)
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return true;
                            }

                            @Override
                            public Context getContext() {
                                return UserInfoActivity.this;
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

                                        current_exp.setText(userExp.getCurrentExp() + "");
                                        need_exp.setText(userExp.getNextLevelExp() + "");
                                        expView.setExpViewValue((float) userExp.getCurrentExp() / (float) userExp.getNextLevelExp(), "LV." + userExp.getCurrentLevel());
                                        lint_exp.setVisibility(View.VISIBLE);
                                        ShareUtil.getInstance(UserInfoActivity.this).setLocalCookie(LocalCacheKey.USER_EXP, PojoUtils.getInstance().getOjbectBase64String(userExp));
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
//        Logger.e("RecordActivity...");
        return R.layout.activity_user_info;
    }

    @Override
    public void initView(View view) {
        setTopTitle("");

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setDelayTime(5000);
        banner.setScrollerTime(500);

        if ("self".equals(getIntent().getStringExtra("flow"))) {//自己
            user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));
            edit.setVisibility(View.VISIBLE);
            line_bottom.setVisibility(View.GONE);
            setViewValue();
            userId = user.getObj().getUser().getUserId();
        } else if ("other".equals(getIntent().getStringExtra("flow"))) {
            userId = getIntent().getStringExtra("userId");
            mHandler.sendEmptyMessage(GETUSER);
        }

        try {
            String expString = ShareUtil.getInstance(UserInfoActivity.this).getLocalCookie(LocalCacheKey.USER_EXP);
            if (!"".equals(expString)) {
                UserExp userExp = (UserExp) PojoUtils.getInstance().getObject(expString);

                current_exp.setText(userExp.getCurrentExp() + "");
                need_exp.setText(userExp.getNextLevelExp() + "");
                expView.setExpViewValue((float) userExp.getCurrentExp() / (float) userExp.getNextLevelExp(), "LV." + userExp.getCurrentLevel());
                lint_exp.setVisibility(View.VISIBLE);
            } else {
                mHandler.sendEmptyMessage(GETEXP);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 设置页面信息
     */
    private void setViewValue() {
        setTopTitle(user.getObj().getUser().getUserNickName());
        String naaString = "";
        if (!"".equals(user.getObj().getUser().getUserNickName())) {//昵称不为空
            naaString = user.getObj().getUser().getUserNickName();
        }
        if (!"".equals(user.getObj().getUser().getUserAge())) {//年龄不为空
            if (!"".equals(user.getObj().getUser().getUserNickName())) {//昵称不为空
                naaString += "," + user.getObj().getUser().getUserAge();
            } else {
                naaString = user.getObj().getUser().getUserAge() + "";
            }
        }
        name_and_age.setText(naaString);
//            name_and_age.setText(user.getObj().getUser().getUserNickName() + "," + user.getObj().getUser().getUserAge());
        image_sex.setImageResource("男".equals(user.getObj().getUser().getUserSex()) ? R.mipmap.ic_boy : R.mipmap.ic_girl);
        declaration.setText(user.getObj().getUser().getUserSign());
        birthday.setText(user.getObj().getUser().getUserBirthDay());
        if (!"".equals(user.getObj().getAddress().getAddressProvinceName())) {
            address.setText(user.getObj().getAddress().getAddressProvinceName() + "," + user.getObj().getAddress().getAddressCityName() + "," + user.getObj().getAddress().getAddressAreaName());
        }
        if ("1".equals(user.getObj().getUser().getAffectiveStates())) {
            status.setText("单身");
        } else if ("2".equals(user.getObj().getUser().getAffectiveStates())) {
            status.setText("恋爱中");
        } else if ("3".equals(user.getObj().getUser().getAffectiveStates())) {
            status.setText("已婚");
        } else if ("4".equals(user.getObj().getUser().getAffectiveStates())) {
            status.setText("离婚");
        } else if ("5".equals(user.getObj().getUser().getAffectiveStates())) {
            status.setText("丧偶");
        }
        constellation.setText(user.getObj().getUser().getConstellation());
        if (!"".equals(user.getObj().getUser().getByUserId())) {
            byUser.setText(user.getObj().getUser().getByUserName());
        }

        imagePaths.clear();
        for (User.ObjBean.AlbumBean albumBean : user.getObj().getUserAlbums()) {
            if (!"".equals(albumBean.getUserAlbumPath())) {
                imagePaths.add(albumBean.getUserAlbumPath());
            }
        }
        banner.setImages(imagePaths, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                Glide.with(UserInfoActivity.this).load(url).error(R.drawable.ic_error).placeholder(R.mipmap.loading2).into(view);
            }
        });
    }

    @OnClick({R.id.edit, R.id.add, R.id.send})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.edit:
                Intent intent = new Intent(this, UpdateUserInfoActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.add:
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.show();
                Window window = dialog.getWindow();
                window.setContentView(R.layout.dialog_check_add_user);
//                window.findViewById(R.id.line).setAlpha(0.9f);
                window.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                window.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.send:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String userId = getIntent().getStringExtra("userId");

                            Map<String, Contact> contacts;
                            if (!Hawk.contains(Contact.CONTACT_KEY)) {
                                contacts = new HashMap<String, Contact>();
                            } else {
                                contacts = Hawk.get(Contact.CONTACT_KEY);
                            }
//                            Contact contact = new Contact(user.getObj().getUser().getUserId(), 0 != user.getObj().getUserAlbums().size() ? user.getObj().getUserAlbums().get(0).getUserAlbumPath() : "", user.getObj().getUser().getUserNickName(), user.getObj().getUser().getUserSex());
                            Contact contact = new Contact(user.getObj().getUser().getUserId(), user.getObj().getUser().getUserHeadPath(), user.getObj().getUser().getUserNickName(), user.getObj().getUser().getUserSex());
                            contacts.put(userId, contact);
                            Hawk.put(Contact.CONTACT_KEY, contacts);


//                            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
//                            EMMessage message = EMMessage.createTxtSendMessage("hello...", userId);
//                            //发送消息
//                            EMClient.getInstance().chatManager().sendMessage(message);

                            Intent intent = new Intent(UserInfoActivity.this, ChatActivity.class);
                            // it's single chat
                            intent.putExtra(Constant.EXTRA_USER_ID, userId);
                            intent.putExtra("nickName", contact.getName());
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }
                }.start();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1 == requestCode && 1 == resultCode) {
            mHandler.sendEmptyMessage(SET_VIEW_VALUE);
        }
    }
}
