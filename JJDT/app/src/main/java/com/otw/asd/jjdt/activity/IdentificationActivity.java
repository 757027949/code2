package com.otw.asd.jjdt.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.MyRadioGroup;
import com.asd.android.widget.pop.PointerPopupWindow;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.SchoolList;
import com.otw.asd.jjdt.entity.Site;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 教练认证
 * Created by Administrator on 2016/4/20.
 */
public class IdentificationActivity extends BaseActivity {

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.rel_school)
    RelativeLayout rel_school;
    @Bind(R.id.rel_address)
    RelativeLayout rel_address;
    @Bind(R.id.rel_number)
    RelativeLayout rel_number;
    @Bind(R.id.rel_name)
    RelativeLayout rel_name;
    @Bind(R.id.rel_identity)
    RelativeLayout rel_identity;
    @Bind(R.id.rel_sub)
    RelativeLayout rel_sub;
    @Bind(R.id.rel_type)
    RelativeLayout rel_type;

    @Bind(R.id.error_mes)
    TextView error_mes;

    @Bind(R.id.school)
    TextView school;
    @Bind(R.id.address)
    TextView address;

    @Bind(R.id.rbGroup_sex)
    MyRadioGroup rbGroup_sex;

    @Bind(R.id.et_number)
    FormEditText et_number;
    @Bind(R.id.et_name)
    FormEditText et_name;
    @Bind(R.id.et_identity)
    FormEditText et_identity;

    @Bind(R.id.cb_sub_two)
    CheckBox cb_sub_two;
    @Bind(R.id.cb_sub_three)
    CheckBox cb_sub_three;
    @Bind(R.id.cb_c1)
    CheckBox cb_c1;
    @Bind(R.id.cb_c2)
    CheckBox cb_c2;
    @Bind(R.id.cb_hechang)
    CheckBox cb_hechang;

    @Bind(R.id.image_identity_one)
    ImageView image_identity_one;
    @Bind(R.id.image_identity_two)
    ImageView image_identity_two;
    @Bind(R.id.image_teacher)
    ImageView image_teacher;
    @Bind(R.id.image_travel)
    ImageView image_travel;
    @Bind(R.id.image_car)
    ImageView image_car;

    PointerPopupWindow schoolPop = null, addressPop = null;

    final int REQUESTCODE_IMAGE_IDENTITY_ONE = 0;
    final int REQUESTCODE_IMAGE_IDENTITY_TWO = 1;
    final int REQUESTCODE_IMAGE_TEACHER = 2;
    final int REQUESTCODE_IMAGE_TRAVEL = 3;
    final int REQUESTCODE_IMAGE_CAR = 4;

    OnGetDataListListener onGetSchoolListListener, onGetSiteListListener;
    final int SCHOOLLIST = 5;
    final int SET_SCHOOLLIST = 6;
    final int SITEBYSCHOOLID = 7;
    final int SET_SITEBYSCHOOLID = 8;
    final int TEACHERVALIDATE = 9;

    String userSex = "男";
    String image_path_identity_one = "", image_path_identity_two = "", image_path_teacher = "", image_path_travel = "", image_path_car = "";

    UserInfo userInfo = null;
    SchoolList schoolList = null;
    CommonAdapter<SchoolList.ObjBean> schoolAdapter;
    CommonAdapter<Site> siteAdapter;
    List<Site> currentSites = new ArrayList<>();
    //缓存场地
    Map<String, List<Site>> map = new HashMap<>();

    public OnGetDataListListener getOnGetSchoolListListener() {
        return onGetSchoolListListener;
    }

    public void setOnGetSchoolListListener(OnGetDataListListener onGetSchoolListListener) {
        this.onGetSchoolListListener = onGetSchoolListListener;
    }

    public OnGetDataListListener getOnGetSiteListListener() {
        return onGetSiteListListener;
    }

    public void setOnGetSiteListListener(OnGetDataListListener onGetSiteListListener) {
        this.onGetSiteListListener = onGetSiteListListener;
    }

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SCHOOLLIST:
                OkHttpUtils.post().url(UrlUtil.SCHOOLLIST)
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
                                    schoolList = new Gson().fromJson(response, SchoolList.class);
                                    if (schoolList.isSuccess()) {
                                        if (null != onGetSchoolListListener) {
                                            onGetSchoolListListener.onfinished();
                                        }
                                        schoolAdapter = new CommonAdapter<SchoolList.ObjBean>(IdentificationActivity.this, schoolList.getObj(), R.layout.pop_layout_listview_item) {
                                            public void convert(ViewHolder holder, final SchoolList.ObjBean bean) {
                                                holder.setText(R.id.item, bean.getSchoolName());
                                                holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                                                    public void onClick(View v) {
                                                        schoolPop.dismiss();
                                                        school.setText(bean.getSchoolName());
                                                        school.setTag(bean.getSchoolId());
                                                        mHandler.sendEmptyMessage(SITEBYSCHOOLID);
//                                                        mHandler.sendEmptyMessageDelayed(SITEBYSCHOOLID, 5000);
                                                    }
                                                });
                                            }
                                        };
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });

                break;

            case SET_SCHOOLLIST:
                if (null == schoolPop) {
                    schoolPop = new PointerPopupWindow(this, school.getWidth());
                    if (schoolList.getObj().size() > 5) {
                        schoolPop.setHeight((int) (GestureUtils.getScreenPix(this).heightPixels * 0.3));
                    }
                    View timePopView = getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
                    ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(schoolAdapter);
                    schoolPop.setContentView(timePopView);
                    schoolPop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
                    schoolPop.showAsDropDown(school);
                } else {
                    if (schoolPop.isShowing()) {
                        schoolPop.dismiss();
                    } else {
                        schoolPop.showAsDropDown(school);
                    }
                }
                break;

            case SITEBYSCHOOLID:
                OkHttpUtils.post().url(UrlUtil.SITEBYSCHOOLID)
                        .addParams("schoolId", (String) school.getTag())
                        .addParams("isRoad", "N")//是否路线
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(true);//自动
//                                setNotSilence(false);//手动
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        if (null != onGetSiteListListener) {
                                            onGetSiteListListener.onfinished();
                                        }
                                        try {
                                            List<Site> sites = new Gson().fromJson(jsonObject.getString("obj"), new TypeToken<List<Site>>() {
                                            }.getType());
                                            map.put((String) school.getTag(), sites);
                                            currentSites.clear();
                                            currentSites.addAll(sites);
                                            siteAdapter = new CommonAdapter<Site>(IdentificationActivity.this, currentSites, R.layout.pop_layout_listview_item) {
                                                public void convert(ViewHolder holder, final Site site) {
                                                    holder.setText(R.id.item, site.getSiteName());
                                                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                                                        public void onClick(View v) {
                                                            addressPop.dismiss();
                                                            address.setText(site.getSiteName());
                                                            address.setTag(site.getSiteId());
                                                        }
                                                    });
                                                }
                                            };
                                            mHandler.sendEmptyMessage(SET_SITEBYSCHOOLID);//自动
                                        } catch (JsonSyntaxException e) {//转化失败
                                            map.put((String) school.getTag(), null);
                                            Toast.makeText(IdentificationActivity.this, jsonObject.getString("obj"), Toast.LENGTH_SHORT).show();
//                                            MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                        }
                                    } else {
                                        MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case SET_SITEBYSCHOOLID:
                currentSites.clear();
                currentSites.addAll(map.get((String) school.getTag()));
                if (0 == currentSites.size()) {
                    MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog("该驾校无任何场地！！！").show();
                    break;
                } else {
                    if (null == addressPop) {
                        addressPop = new PointerPopupWindow(this, address.getWidth());
                        if (currentSites.size() > 5) {
                            addressPop.setHeight((int) (GestureUtils.getScreenPix(this).heightPixels * 0.37266));
                        }
                        View timePopView = getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);

                        ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(siteAdapter);
                        addressPop.setContentView(timePopView);
                        addressPop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
                        addressPop.showAsDropDown(address);
                    } else {
                        if (currentSites.size() > 5) {
                            addressPop.setHeight((int) (GestureUtils.getScreenPix(this).heightPixels * 0.37266));
                        } else {
                            addressPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                        siteAdapter.notifyDataSetChanged();
                        if (addressPop.isShowing()) {
                            addressPop.dismiss();
                        } else {
                            addressPop.showAsDropDown(address);
                        }
                    }
                }
                break;

            case TEACHERVALIDATE:
                String userIdCardObversePath = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image_identity_one));
                String userIdCardReversePath = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image_identity_two));
                String teacherCardPath = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image_teacher));
                String teacherRunCardPath = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image_travel));
                String teachCarPath = BitmapUtil.getInstance().convertIconToString(BitmapUtil.getInstance().getImageViewBitmap(image_car));
                OkHttpUtils.post().url(UrlUtil.TEACHERVALIDATE)
                        .addParams("schoolId", (String) school.getTag())
                        .addParams("siteId", (String) address.getTag())
                        .addParams("teacherCard", et_number.getText().toString())
                        .addParams("userRealName", et_name.getText().toString())
                        .addParams("userSex", userSex)
                        .addParams("userIdCard", et_identity.getText().toString())
                        .addParams("teachIsSubject2", cb_sub_two.isChecked() ? "Y" : "N")
                        .addParams("teachIsSubject3", cb_sub_three.isChecked() ? "Y" : "N")
                        .addParams("teachTypeC1", cb_c1.isChecked() ? "Y" : "N")
                        .addParams("teachTypeC2", cb_c2.isChecked() ? "Y" : "N")
                        .addParams("isHeChang", cb_hechang.isChecked() ? "Y" : "N")
                        .addParams("userIdCardObversePath", userIdCardObversePath)
                        .addParams("userIdCardReversePath", userIdCardReversePath)
                        .addParams("teacherCardPath", teacherCardPath)
                        .addParams("teacherRunCardPath", teacherRunCardPath)
                        .addParams("teachCarPath", teachCarPath)
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
                                        userInfo.getObj().getTeacher().setValidateState("0");
                                        userInfo.getObj().getUser().setUserRealName(et_name.getText().toString());
//                                        LocalCache.get(IdentificationActivity.this).put(LocalCacheKey.KEY_USER, userInfo);
                                        ShareUtil.getInstance(IdentificationActivity.this).setLocalCookie(LocalCacheKey.KEY_USER, PojoUtils.getInstance().getOjbectBase64String(userInfo));
                                        Intent intent = new Intent(IdentificationActivity.this, MainActivity.class);
                                        intent.putExtra("flow", "identification");
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_identification;
    }

    @Override
    public void initView(View view) {
        if ("laucher".equals(getIntent().getStringExtra("flow"))) {
//            if (null == getOnSessionStatusListener()) {//未获取过session
            mHandler.sendEmptyMessage(FLOW_SESSION);
            setOnSessionStatusListener(new OnSessionStatusListener() {
                @Override
                public void getSessionStaus(boolean status) {
                    if (status) {
                        mHandler.sendEmptyMessage(SCHOOLLIST);
//                        mHandler.sendEmptyMessageDelayed(SCHOOLLIST, 5000);
                    }
                }
            });
//            }
        } else if ("login".equals(getIntent().getStringExtra("flow"))) {
            mHandler.sendEmptyMessage(SCHOOLLIST);
        }


        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(school, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.035));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(address, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.035));

//        userInfo = (UserInfo) LocalCache.get(this).getAsObject(LocalCacheKey.KEY_USER);
        userInfo = (UserInfo) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.KEY_USER));
        if (null != userInfo) {
            if ("-1".equals(userInfo.getObj().getTeacher().getValidateState()) && !"".equals(userInfo.getObj().getTeacher().getValidateExplain())) {//未认证 -1:未认证或认证失败 0：认证中 1：认证通过) {//未认证
                error_mes.setVisibility(View.VISIBLE);
                error_mes.setText(userInfo.getObj().getTeacher().getValidateExplain());
            }
        }

        rbGroup_sex.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_man:
                        userSex = "男";
                        break;

                    case R.id.rb_woman:
                        userSex = "女";
                        break;
                }
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
            }
            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
            }

            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            }
        }
    }

    public static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 1;

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SOME_FEATURES_PERMISSIONS: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                        this.finishSelf();
                        AppManager.AppExit(this);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
        if (resultCode == RESULT_OK) {
            // 获取返回的图片列表
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            // 处理你自己的逻辑 ....
//                mHandler.sendMessage(mHandler.obtainMessage(UPDATEIMG, path.get(0)));
//                Glide.with(MyInfoActivity.this).load(path.get(0)).error(R.drawable.ic_error).into(person_image);
            String pathString = path.get(0);
            if (REQUESTCODE_IMAGE_IDENTITY_ONE == requestCode) {
                image_path_identity_one = pathString;
                Glide.with(IdentificationActivity.this).load(pathString).into(image_identity_one);
            } else if (REQUESTCODE_IMAGE_IDENTITY_TWO == requestCode) {
                image_path_identity_two = pathString;
                Glide.with(IdentificationActivity.this).load(pathString).into(image_identity_two);
            } else if (REQUESTCODE_IMAGE_TEACHER == requestCode) {
                image_path_teacher = pathString;
                Glide.with(IdentificationActivity.this).load(pathString).into(image_teacher);
            } else if (REQUESTCODE_IMAGE_TRAVEL == requestCode) {
                image_path_travel = pathString;
                Glide.with(IdentificationActivity.this).load(pathString).into(image_travel);
            } else if (REQUESTCODE_IMAGE_CAR == requestCode) {
                image_path_car = pathString;
                Glide.with(IdentificationActivity.this).load(pathString).into(image_car);
            }
        }
//        }
    }

    @OnTextChanged(R.id.school)
    public void schoolChoose() {
        address.setText("");
    }

    @OnClick({R.id.school, R.id.address, R.id.image_identity_one, R.id.image_identity_two, R.id.image_teacher, R.id.image_travel, R.id.image_car, R.id.commit})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.school:
                if (null == schoolAdapter) {//未获取到数据（获取中...）
                    getLodingAlertDialog().show();
                    setOnGetSchoolListListener(new OnGetDataListListener() {
                        @Override
                        public void onfinished() {
                            getLodingAlertDialog().dismiss();
                            mHandler.sendEmptyMessage(SET_SCHOOLLIST);
                        }
                    });
                } else {
                    mHandler.sendEmptyMessage(SET_SCHOOLLIST);
                }
                break;

            case R.id.address:
                if ("".equals(school.getText().toString())) {
                    Toast.makeText(this, "请选择驾校", Toast.LENGTH_SHORT).show();
                    return;
                }
                //手动
               /* if (!map.containsKey((String) school.getTag())) {//本地不存在
                    getLoadingAlertDialog().show();
                    setOnGetSiteListListener(new OnGetDataListListener() {
                        @Override
                        public void onfinished() {
                            getLoadingAlertDialog().dismiss();
                            mHandler.sendEmptyMessage(SET_SITEBYSCHOOLID);
                        }
                    });
                } else {
                mHandler.sendEmptyMessage(SET_SITEBYSCHOOLID);//自动
                }*/

                if (!map.containsKey((String) school.getTag())) {//本地不存在(获取失败）
                    mHandler.sendEmptyMessage(SITEBYSCHOOLID);
                } else {
                    mHandler.sendEmptyMessage(SET_SITEBYSCHOOLID);//自动
                }
                break;

            case R.id.image_identity_one:
                getImages(REQUESTCODE_IMAGE_IDENTITY_ONE);
                break;

            case R.id.image_identity_two:
                getImages(REQUESTCODE_IMAGE_IDENTITY_TWO);
                break;

            case R.id.image_teacher:
                getImages(REQUESTCODE_IMAGE_TEACHER);
                break;

            case R.id.image_travel:
                getImages(REQUESTCODE_IMAGE_TRAVEL);
                break;

            case R.id.image_car:
                getImages(REQUESTCODE_IMAGE_CAR);
                break;

            case R.id.commit:
                if ("".equals(school.getText().toString())) {
                    scrollToLocation(rel_school);
                    Toast.makeText(this, "请选择驾校", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(address.getText().toString())) {
                    scrollToLocation(rel_address);
                    Toast.makeText(this, "请选择场地", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!et_number.testValidity()) {
                    scrollToLocation(rel_number);
                    return;
                }
                if (!et_name.testValidity()) {
                    scrollToLocation(rel_name);
                    return;
                }
                if (!et_identity.testValidity()) {
                    scrollToLocation(rel_identity);
                    return;
                }
                if (!cb_sub_two.isChecked() && !cb_sub_three.isChecked()) {
                    scrollToLocation(rel_sub);
                    Toast.makeText(this, "请勾选科目", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!cb_c1.isChecked() && !cb_c2.isChecked()) {
                    scrollToLocation(rel_type);
                    Toast.makeText(this, "请勾选类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(image_path_identity_one) || "".equals(image_path_identity_two)) {
                    Toast.makeText(this, "请上传身份证正反两面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(image_path_teacher)) {
                    Toast.makeText(this, "请上传教练证照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(image_path_travel)) {
                    Toast.makeText(this, "请上传行驶证照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(image_path_car)) {
                    Toast.makeText(this, "请上传教练车照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                mHandler.sendEmptyMessage(TEACHERVALIDATE);
                break;

        }

    }

    /**
     * 滚动到某个控件位置
     *
     * @param view
     */
    private void scrollToLocation(View view) {
//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
//        Logger.e(location[1] + "-----------" + view.getTop());
//        scrollView.smoothScrollTo(0, location[1]);

        scrollView.smoothScrollTo(0, view.getTop());
    }

    /**
     * 获取照片
     *
     * @param requestCode 请求CODE
     */
    private void getImages(int requestCode) {
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
     * 获取数据
     */
    public interface OnGetDataListListener {
        /**
         * 完成
         */
        void onfinished();
    }
}
