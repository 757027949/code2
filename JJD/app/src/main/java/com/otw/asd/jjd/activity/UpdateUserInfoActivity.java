package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.util.AssetsUtils;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 修改资料
 */
public class UpdateUserInfoActivity extends BaseActivity {

    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.image2)
    ImageView image2;
    @Bind(R.id.image3)
    ImageView image3;
    @Bind(R.id.image4)
    ImageView image4;
    @Bind(R.id.image5)
    ImageView image5;
    @Bind(R.id.image6)
    ImageView image6;
    @Bind(R.id.remove1)
    ImageView remove1;
    @Bind(R.id.remove2)
    ImageView remove2;
    @Bind(R.id.remove3)
    ImageView remove3;
    @Bind(R.id.remove4)
    ImageView remove4;
    @Bind(R.id.remove5)
    ImageView remove5;
    @Bind(R.id.remove6)
    ImageView remove6;
    @Bind(R.id.name)
    FormEditText name;
    @Bind(R.id.declaration)
    FormEditText declaration;
    @Bind(R.id.address_info)
    FormEditText address_info;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.birthday)
    TextView birthday;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.constellation)
    TextView constellation;

    @Bind(R.id.byUser)
    FormEditText byUser;
    @Bind(R.id.rel_byUser)
    RelativeLayout rel_byUser;

    OptionPicker sexOptionPicker, statusOptionPicker, constellationOptionPicker;
    DatePicker birthdayDatePicker;
    ArrayList<AddressPicker.Province> data;
    AddressPicker addressPicker;

    User user;

    String addressProvinceName = "", addressCityName = "", addressAreaName = "", affectiveStates = "", userAlbumSerial = "", picturePath = "", userAlbumId = "";
    final int UPDATEUSERINFO = 0;
    final int ADDUSERALBUM = 1;
    final int UPDATEUSERALBUM = 2;
    final int DELETEUSERALBUM = 3;

//    ImageFactory imageFactory = new ImageFactory();

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case UPDATEUSERINFO:
                OkHttpUtils.post().url(UrlUtil.UPDATEUSERINFO)
                        .addParams("userNickName", name.getText().toString())
                        .addParams("userSex", sex.getText().toString())
                        .addParams("userBirthDay", "".equals(birthday.getText().toString().trim()) ? "" : birthday.getText().toString() + " 00:00:00")
                        .addParams("userSign", declaration.getText().toString())
                        .addParams("affectiveStates", affectiveStates)
                        .addParams("constellation", constellation.getText().toString())
                        .addParams("addressProvinceName", addressProvinceName)
                        .addParams("addressCityName", addressCityName)
                        .addParams("addressAreaName", addressAreaName)
                        .addParams("addressDetailName", address_info.getText().toString())
                        .addParams("byUserRegisterTelephone", byUser.getText().toString())
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
                                    Toast.makeText(UpdateUserInfoActivity.this, jsonObject.getString("obj"), Toast.LENGTH_SHORT).show();
                                    if (jsonObject.getBoolean("success")) {
                                        user.getObj().getUser().setUserNickName(name.getText().toString());
                                        user.getObj().getUser().setUserSex(sex.getText().toString());
                                        user.getObj().getUser().setUserBirthDay(birthday.getText().toString());
                                        user.getObj().getUser().setUserSign(declaration.getText().toString());
                                        user.getObj().getUser().setAffectiveStates(affectiveStates);
                                        user.getObj().getUser().setConstellation(constellation.getText().toString());
                                        user.getObj().getAddress().setAddressProvinceName(addressProvinceName);
                                        user.getObj().getAddress().setAddressCityName(addressCityName);
                                        user.getObj().getAddress().setAddressAreaName(addressAreaName);
                                        user.getObj().getAddress().setAddressDetailName(address_info.getText().toString());
                                        if ("".equals(user.getObj().getUser().getByUserId()) && !"".equals(byUser.getText().toString())) {//没有推荐人 当前设置了推荐人
                                            user.getObj().getUser().setByUserId(byUser.getText().toString());
                                            user.getObj().getUser().setByUserName(byUser.getText().toString());
                                        }
                                        ShareUtil.getInstance(UpdateUserInfoActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER, PojoUtils.getInstance().getOjbectBase64String(user));

                                        setResult(1);
                                        finishSelf();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(UpdateUserInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });

                break;
            case ADDUSERALBUM:
                String imageString = "";
                if ("0".equals(userAlbumSerial)) {
//                    imageString = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(BitmapUtil.getInstance().getImageViewBitmap(image1),400,400));
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image1));
                } else if ("1".equals(userAlbumSerial)) {
//                    imageString = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(BitmapUtil.getInstance().getImageViewBitmap(image2),400,400));
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image2));
                } else if ("2".equals(userAlbumSerial)) {
//                    imageString = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(BitmapUtil.getInstance().getImageViewBitmap(image3),400,400));
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image3));
                } else if ("3".equals(userAlbumSerial)) {
//                    imageString = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(BitmapUtil.getInstance().getImageViewBitmap(image4),400,400));
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image4));
                } else if ("4".equals(userAlbumSerial)) {
//                    imageString = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(BitmapUtil.getInstance().getImageViewBitmap(image5),400,400));
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image5));
                } else if ("5".equals(userAlbumSerial)) {
//                    imageString = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(BitmapUtil.getInstance().getImageViewBitmap(image6),400,400));
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image6));
                }
                OkHttpUtils.post().url(UrlUtil.ADDUSERALBUM)
                        .addParams("userAlbumSerial", userAlbumSerial)
                        .addParams("userAlbumPath", imageString)
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(false);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(userAlbumSerial + "=====" + response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        User.ObjBean.AlbumBean bean = new Gson().fromJson(jsonObject.getString("obj"), User.ObjBean.AlbumBean.class);
                                        if (0 == bean.getUserAlbumSerial()) {
                                            image1.setTag(R.id.update_user_image_id, bean.getUserAlbumId());
                                            remove1.setTag(bean);
                                            remove1.setVisibility(View.VISIBLE);
                                            user.getObj().getUser().setUserHeadPath(bean.getUserAlbumPath());
                                        } else if (1 == bean.getUserAlbumSerial()) {
                                            image2.setTag(R.id.update_user_image_id, bean.getUserAlbumId());
                                            remove2.setTag(bean);
                                            remove2.setVisibility(View.VISIBLE);
                                        } else if (2 == bean.getUserAlbumSerial()) {
                                            image3.setTag(R.id.update_user_image_id, bean.getUserAlbumId());
                                            remove3.setTag(bean);
                                            remove3.setVisibility(View.VISIBLE);
                                        } else if (3 == bean.getUserAlbumSerial()) {
                                            image4.setTag(R.id.update_user_image_id, bean.getUserAlbumId());
                                            remove4.setTag(bean);
                                            remove4.setVisibility(View.VISIBLE);
                                        } else if (4 == bean.getUserAlbumSerial()) {
                                            image5.setTag(R.id.update_user_image_id, bean.getUserAlbumId());
                                            remove5.setTag(bean);
                                            remove5.setVisibility(View.VISIBLE);
                                        } else if (5 == bean.getUserAlbumSerial()) {
                                            image6.setTag(R.id.update_user_image_id, bean.getUserAlbumId());
                                            remove6.setTag(bean);
                                            remove6.setVisibility(View.VISIBLE);
                                        }

                                        user.getObj().getUserAlbums().add(bean);

                                        ShareUtil.getInstance(UpdateUserInfoActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER, PojoUtils.getInstance().getOjbectBase64String(user));
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(UpdateUserInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
            case UPDATEUSERALBUM:
                imageString = "";
                if ("0".equals(userAlbumSerial)) {
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image1));
                } else if ("1".equals(userAlbumSerial)) {
//                    imageString = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(BitmapUtil.getInstance().getImageViewBitmap(image2),400,400));
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image2));
                } else if ("2".equals(userAlbumSerial)) {
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image3));
                } else if ("3".equals(userAlbumSerial)) {
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image4));
                } else if ("4".equals(userAlbumSerial)) {
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image5));
                } else if ("5".equals(userAlbumSerial)) {
                    imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image6));
                }
                OkHttpUtils.post().url(UrlUtil.UPDATEUSERALBUM)
                        .addParams("userAlbumId", userAlbumId)
                        .addParams("userAlbumPath", imageString)
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(false);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(userAlbumId + "=====" + response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        User.ObjBean.AlbumBean bean = new Gson().fromJson(jsonObject.getString("obj"), User.ObjBean.AlbumBean.class);
                                        for (User.ObjBean.AlbumBean bean1 : user.getObj().getUserAlbums()) {
                                            if (bean1.getUserAlbumId().equals(bean.getUserAlbumId())) {
                                                user.getObj().getUserAlbums().remove(bean1);
                                                user.getObj().getUserAlbums().add(bean);
                                                break;
                                            }
                                        }
                                        if ("0".equals(userAlbumSerial)) {//相当于修改头像
                                            user.getObj().getUser().setUserHeadPath(bean.getUserAlbumPath());
                                        }

                                        ShareUtil.getInstance(UpdateUserInfoActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER, PojoUtils.getInstance().getOjbectBase64String(user));
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(UpdateUserInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
            case DELETEUSERALBUM:
                final User.ObjBean.AlbumBean albumBean = (User.ObjBean.AlbumBean) msg.obj;
                OkHttpUtils.post().url(UrlUtil.DELETEUSERALBUM)
                        .addParams("userAlbumId", albumBean.getUserAlbumId())
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
                                        if (user.getObj().getUserAlbums().contains(albumBean)) {
                                            user.getObj().getUserAlbums().remove(albumBean);
                                            ShareUtil.getInstance(UpdateUserInfoActivity.this).setLocalCookie(LocalCacheKey.LOCAL_USER, PojoUtils.getInstance().getOjbectBase64String(user));
                                        }
                                        if ("0".equals(userAlbumSerial)) {
                                            Glide.with(UpdateUserInfoActivity.this).load(R.drawable.ic_image_add).into(image1);
                                            image1.setTag(R.id.update_user_image_id, "");
                                            remove1.setVisibility(View.GONE);
                                        } else if ("1".equals(userAlbumSerial)) {
                                            Glide.with(UpdateUserInfoActivity.this).load(R.drawable.ic_image_add).into(image2);
                                            image2.setTag(R.id.update_user_image_id, "");
                                            remove2.setVisibility(View.GONE);
                                        } else if ("2".equals(userAlbumSerial)) {
                                            Glide.with(UpdateUserInfoActivity.this).load(R.drawable.ic_image_add).into(image3);
                                            image3.setTag(R.id.update_user_image_id, "");
                                            remove3.setVisibility(View.GONE);
                                        } else if ("3".equals(userAlbumSerial)) {
                                            Glide.with(UpdateUserInfoActivity.this).load(R.drawable.ic_image_add).into(image4);
                                            image4.setTag(R.id.update_user_image_id, "");
                                            remove4.setVisibility(View.GONE);
                                        } else if ("4".equals(userAlbumSerial)) {
                                            Glide.with(UpdateUserInfoActivity.this).load(R.drawable.ic_image_add).into(image5);
                                            image5.setTag(R.id.update_user_image_id, "");
                                            remove5.setVisibility(View.GONE);
                                        } else if ("5".equals(userAlbumSerial)) {
                                            Glide.with(UpdateUserInfoActivity.this).load(R.drawable.ic_image_add).into(image6);
                                            image6.setTag(R.id.update_user_image_id, "");
                                            remove6.setVisibility(View.GONE);
                                        }
                                    } else {
                                        MyDialog.getInstance(UpdateUserInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(UpdateUserInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_update_user_info;
    }

    @Override
    public void initView(View view) {
        setTopTitle("");

        user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));

        name.setText(user.getObj().getUser().getUserNickName());
        sex.setText(user.getObj().getUser().getUserSex());
        declaration.setText(user.getObj().getUser().getUserSign());
        birthday.setText(user.getObj().getUser().getUserBirthDay());
        if (!"".equals(user.getObj().getAddress().getAddressProvinceName())) {
            address.setText(user.getObj().getAddress().getAddressProvinceName() + "," + user.getObj().getAddress().getAddressCityName() + "," + user.getObj().getAddress().getAddressAreaName());
        }
        address_info.setText(user.getObj().getAddress().getAddressDetailName());
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
        setUserAlbums();

        rel_byUser.setVisibility("".equals(user.getObj().getUser().getByUserId()) ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置相册
     */
    private void setUserAlbums() {
        if (null != user.getObj().getUserAlbums()) {
            for (int i = 0; i < user.getObj().getUserAlbums().size(); i++) {
                String imagePath = user.getObj().getUserAlbums().get(i).getUserAlbumPath();
                User.ObjBean.AlbumBean albumBean = user.getObj().getUserAlbums().get(i);
                switch (albumBean.getUserAlbumSerial()) {
                    case 0:
                        image1.setTag(R.id.update_user_image_id, user.getObj().getUserAlbums().get(i).getUserAlbumId());
                        remove1.setTag(albumBean);
                        remove1.setVisibility(View.VISIBLE);
                        Glide.with(this).load(imagePath).error(R.drawable.ic_error).into(image1);
                        break;
                    case 1:
                        image2.setTag(R.id.update_user_image_id, user.getObj().getUserAlbums().get(i).getUserAlbumId());
                        remove2.setTag(albumBean);
                        remove2.setVisibility(View.VISIBLE);
                        Glide.with(this).load(imagePath).error(R.drawable.ic_error).into(image2);
                        break;
                    case 2:
                        image3.setTag(R.id.update_user_image_id, user.getObj().getUserAlbums().get(i).getUserAlbumId());
                        remove3.setTag(albumBean);
                        remove3.setVisibility(View.VISIBLE);
                        Glide.with(this).load(imagePath).error(R.drawable.ic_error).into(image3);
                        break;
                    case 3:
                        image4.setTag(R.id.update_user_image_id, user.getObj().getUserAlbums().get(i).getUserAlbumId());
                        remove4.setTag(albumBean);
                        remove4.setVisibility(View.VISIBLE);
                        Glide.with(this).load(imagePath).error(R.drawable.ic_error).into(image4);
                        break;
                    case 4:
                        image5.setTag(R.id.update_user_image_id, user.getObj().getUserAlbums().get(i).getUserAlbumId());
                        remove5.setTag(albumBean);
                        remove5.setVisibility(View.VISIBLE);
                        Glide.with(this).load(imagePath).error(R.drawable.ic_error).into(image5);
                        break;
                    case 5:
                        image6.setTag(R.id.update_user_image_id, user.getObj().getUserAlbums().get(i).getUserAlbumId());
                        remove6.setTag(albumBean);
                        remove6.setVisibility(View.VISIBLE);
                        Glide.with(this).load(imagePath).error(R.drawable.ic_error).into(image6);
                        break;
                }
            }
        }
    }

    @Override
    public void finishSelf() {
        setResult(1);
        super.finishSelf();
    }

    @OnClick({R.id.save, R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6,
            R.id.remove1, R.id.remove2, R.id.remove3, R.id.remove4, R.id.remove5, R.id.remove6,
            R.id.sex, R.id.birthday, R.id.address, R.id.status, R.id.constellation})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.save:
                mHandler.sendEmptyMessage(UPDATEUSERINFO);
                break;
            case R.id.image1:
                getPicture(1);
                break;
            case R.id.image2:
                getPicture(2);
                break;
            case R.id.image3:
                getPicture(3);
                break;
            case R.id.image4:
                getPicture(4);
                break;
            case R.id.image5:
                getPicture(5);
                break;
            case R.id.image6:
                getPicture(6);
                break;
            case R.id.remove1:
                userAlbumSerial = "0";
                removedImage((User.ObjBean.AlbumBean) view.getTag());
                break;
            case R.id.remove2:
                userAlbumSerial = "1";
                removedImage((User.ObjBean.AlbumBean) view.getTag());
                break;
            case R.id.remove3:
                userAlbumSerial = "2";
                removedImage((User.ObjBean.AlbumBean) view.getTag());
                break;
            case R.id.remove4:
                userAlbumSerial = "3";
                removedImage((User.ObjBean.AlbumBean) view.getTag());
                break;
            case R.id.remove5:
                userAlbumSerial = "4";
                removedImage((User.ObjBean.AlbumBean) view.getTag());
                break;
            case R.id.remove6:
                userAlbumSerial = "5";
                removedImage((User.ObjBean.AlbumBean) view.getTag());
                break;
            case R.id.sex:
                if (null == sexOptionPicker) {
                    sexOptionPicker = new OptionPicker(this, getResources().getStringArray(R.array.update_user_info_sex));
                    sexOptionPicker.setCancelTextColor(0xFF33B5E5);
                    sexOptionPicker.setSubmitTextColor(0xFF33B5E5);
                    sexOptionPicker.setSelectedIndex("男".equals(user.getObj().getUser().getUserSex()) ? 0 : 1);
                    sexOptionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(String option) {
                            sex.setText(option);
                        }
                    });
                }
                sexOptionPicker.show();
                break;
            case R.id.birthday:
                if (null == birthdayDatePicker) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());

                    birthdayDatePicker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
                    birthdayDatePicker.setCancelTextColor(0xFF33B5E5);
                    birthdayDatePicker.setSubmitTextColor(0xFF33B5E5);
                    birthdayDatePicker.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));//年份范围
                    try {
                        birthdayDatePicker.setSelectedItem(Integer.parseInt(user.getObj().getUser().getUserBirthDay().split("-")[0]), Integer.parseInt(user.getObj().getUser().getUserBirthDay().split("-")[1]));
                    } catch (Exception e) {
                        birthdayDatePicker.setSelectedItem(calendar.get(Calendar.YEAR) - 20, 1);
                    }
                    birthdayDatePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                        @Override
                        public void onDatePicked(String year, String month, String day) {
                            birthday.setText(year + "-" + month + "-" + day);
                        }
                    });
                }
                birthdayDatePicker.show();
                break;
            case R.id.address:
                if (null == data) {
                    data = new ArrayList<AddressPicker.Province>();
                    String json = AssetsUtils.readText(this, "city.json");
                    data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
                    addressPicker = new AddressPicker(this, data);
                    addressPicker.setCancelTextColor(0xFF33B5E5);
                    addressPicker.setSubmitTextColor(0xFF33B5E5);
                    addressPicker.setSelectedItem(user.getObj().getAddress().getAddressProvinceName(), user.getObj().getAddress().getAddressCityName(), user.getObj().getAddress().getAddressAreaName());
                    addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                        @Override
                        public void onAddressPicked(String province, String city, String county) {
                            address.setText(province + "," + city + "," + county);
                            addressProvinceName = province;
                            addressCityName = city;
                            addressAreaName = county;
                        }
                    });
                }
                addressPicker.show();
                break;
            case R.id.status:
                if (null == statusOptionPicker) {
                    final String[] results = getResources().getStringArray(R.array.update_user_info_status);
                    statusOptionPicker = new OptionPicker(this, results);
                    statusOptionPicker.setCancelTextColor(0xFF33B5E5);
                    statusOptionPicker.setSubmitTextColor(0xFF33B5E5);
                    statusOptionPicker.setSelectedItem(status.getText().toString());
                    statusOptionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(String option) {
                            status.setText(option);
                            for (int j = 0; j < results.length; j++) {
                                if (option.equals(results[j])) {
                                    affectiveStates = (j + 1) + "";
                                    break;
                                }
                            }
                        }
                    });
                }
                statusOptionPicker.show();
                break;
            case R.id.constellation:
                if (null == constellationOptionPicker) {
                    constellationOptionPicker = new OptionPicker(this, getResources().getStringArray(R.array.update_user_info_constellation));
                    constellationOptionPicker.setCancelTextColor(0xFF33B5E5);
                    constellationOptionPicker.setSubmitTextColor(0xFF33B5E5);
                    constellationOptionPicker.setSelectedItem(user.getObj().getUser().getConstellation());
                    constellationOptionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(String option) {
                            constellation.setText(option);
                        }
                    });
                }
                constellationOptionPicker.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data && resultCode == RESULT_OK) {
            // 获取返回的图片列表
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            picturePath = path.get(0);
            userAlbumId = "";
            if (requestCode == 1) {
                // 处理你自己的逻辑 ....
                userAlbumSerial = "0";
                try {
                    userAlbumId = image1.getTag(R.id.update_user_image_id).toString();
                } catch (Exception e) {
//                    image1.setTag(R.id.update_user_image_id, "-100");//防止再次修改无效
                }
//                Glide.with(UpdateUserInfoActivity.this).load(picturePath).error(R.drawable.ic_error).into(image1);
                updateAndAddUserAlbums(image1);
            } else if (requestCode == 2) {
                userAlbumSerial = "1";
                try {
                    userAlbumId = image2.getTag(R.id.update_user_image_id).toString();
                } catch (Exception e) {
//                    image2.setTag(R.id.update_user_image_id, "-100");//防止再次修改无效
                }
//                Glide.with(UpdateUserInfoActivity.this).load(picturePath).error(R.drawable.ic_error).into(image2);
                updateAndAddUserAlbums(image2);
            } else if (requestCode == 3) {
                userAlbumSerial = "2";
                try {
                    userAlbumId = image3.getTag(R.id.update_user_image_id).toString();
                } catch (Exception e) {
//                    image3.setTag(R.id.update_user_image_id, "-100");//防止再次修改无效
                }
//                Glide.with(UpdateUserInfoActivity.this).load(picturePath).error(R.drawable.ic_error).into(image3);
                updateAndAddUserAlbums(image3);
            } else if (requestCode == 4) {
                userAlbumSerial = "3";
                try {
                    userAlbumId = image4.getTag(R.id.update_user_image_id).toString();
                } catch (Exception e) {
//                    image4.setTag(R.id.update_user_image_id, "-100");//防止再次修改无效
                }
//                Glide.with(UpdateUserInfoActivity.this).load(picturePath).error(R.drawable.ic_error).into(image4);
                updateAndAddUserAlbums(image4);
            } else if (requestCode == 5) {
                userAlbumSerial = "4";
                try {
                    userAlbumId = image5.getTag(R.id.update_user_image_id).toString();
                } catch (Exception e) {
//                    image5.setTag(R.id.update_user_image_id, "-100");//防止再次修改无效
                }
//                Glide.with(UpdateUserInfoActivity.this).load(picturePath).error(R.drawable.ic_error).into(image5);
                updateAndAddUserAlbums(image5);
            } else if (requestCode == 6) {
                userAlbumSerial = "5";
                try {
                    userAlbumId = image6.getTag(R.id.update_user_image_id).toString();
                } catch (Exception e) {
//                    image6.setTag(R.id.update_user_image_id, "-100");//防止再次修改无效
                }
//                Glide.with(UpdateUserInfoActivity.this).load(picturePath).error(R.drawable.ic_error).into(image6);
                updateAndAddUserAlbums(image6);
            }
        }
    }

    private void updateAndAddUserAlbums(ImageView imageView) {
        Glide.with(UpdateUserInfoActivity.this).load(picturePath).error(R.drawable.ic_error).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if ("".equals(userAlbumId)) {
                            mHandler.sendEmptyMessage(ADDUSERALBUM);
                        } else {
                            mHandler.sendEmptyMessage(UPDATEUSERALBUM);
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 获取图片
     *
     * @param requestCode
     */
    private void getPicture(int requestCode) {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, requestCode);
    }


    /**
     * 删除图片对话框
     *
     * @param albumBean 相册对象
     */
    public void removedImage(final User.ObjBean.AlbumBean albumBean) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示！")
                .setContentText("确认删除当前图片吗？")
                .setCancelText("取消")
                .setConfirmText("确定")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mHandler.sendMessage(mHandler.obtainMessage(DELETEUSERALBUM, albumBean));
                        sweetAlertDialog.cancel();
                    }
                })
                .show();
    }
}
