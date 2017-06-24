package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.andreabaccega.widget.FormEditText;
import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.StringUtil;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.pop.PointerPopupWindow;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.Apply;
import com.otw.asd.jjd.entity.SignUp;
import com.otw.asd.jjd.util.AssetsUtils;
import com.otw.asd.jjd.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 报名学车
 * Created by Administrator on 2016/3/31.
 */
public class SignUpActivity extends BaseActivity {

    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.type)
    TextView type;

    @Bind(R.id.name)
    FormEditText name;
    @Bind(R.id.address_info)
    FormEditText address_info;
    @Bind(R.id.phone)
    FormEditText phone;

    PointerPopupWindow typePop = null;//学车类型

    final int APPLYREQUEST = 0;

    ArrayList<AddressPicker.Province> data;
    AddressPicker addressPicker;

    Apply apply = new Apply();//报名参数对象

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case APPLYREQUEST:
                OkHttpUtils.post().url(UrlUtil.APPLYREQUEST)
//                        .addParams("userRealName", name.getText().toString())
//                        .addParams("userHomeAddress", address.getText().toString() + address_info.getText().toString())
//                        .addParams("userContactTelephone", phone.getText().toString())
                        .addParams("applyStudyType", type.getText().toString().contains("1") ? "C1" : "C2")
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
                                    SignUp signUp = new Gson().fromJson(response, SignUp.class);
                                    if (signUp.isSuccess()) {
                                        apply.setUserRealName(name.getText().toString());
                                        apply.setUserHomeAddress(address_info.getText().toString());
                                        apply.setUserContactTelephone(phone.getText().toString());
                                        apply.setApplyStudyType(type.getText().toString().contains("1") ? "C1" : "C2");
                                        apply.setMoney(signUp.getObj().getMoney());

                                        Intent intent = new Intent(SignUpActivity.this, SignUpInfoActivity.class);
                                        intent.putExtra("data", signUp);
                                        intent.putExtra("apply", apply);
                                        startActivity(intent);

                                    } else {
                                        MyDialog.getInstance(SignUpActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(SignUpActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
        return R.layout.activity_sign_up;
    }

    @Override
    public void initView(View view) {
        AppManager.addActivity(this);
        setTopTitle("报名");

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(address, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.04));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(type, 2, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.04));
    }

    @OnClick({R.id.address, R.id.type, R.id.next})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.address:
                if (null == data) {
                    data = new ArrayList<AddressPicker.Province>();
                    String json = AssetsUtils.readText(this, "city.json");
                    data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
                    addressPicker = new AddressPicker(this, data);
                    addressPicker.setSelectedItem("重庆", "重庆", "");
                }
                addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(String province, String city, String county) {
                        address.setText(province + "-" + city + "-" + county);
                        apply.setProvinceName(province);
                        apply.setCityName(city);
                        apply.setAreaName(county);
                    }
                });
                addressPicker.show();
                break;

            case R.id.type:
                if (null == typePop) {
                    createStylePop(view);
                }
                if (typePop.isShowing()) {
                    typePop.dismiss();
                } else {
                    typePop.showAsPointer(view);
                }
                break;
            case R.id.next:
                if (!name.testValidity()) {
                    return;
                }
                if (StringUtil.getInstance().isEmpty(address.getText().toString())) {
                    Toast.makeText(this, "请选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!address_info.testValidity()) {
                    return;
                }
                if (!phone.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(APPLYREQUEST);
                break;
        }
    }


    /**
     * 显示学车类型Pop
     *
     * @param view
     * @return
     */
    private PointerPopupWindow createStylePop(View view) {
        if (null == typePop) {
            List<String> styles = new ArrayList<>();
            styles.add("C1（手动档）");
            styles.add("C2（自动档）");
            typePop = new PointerPopupWindow(this, view.getWidth());
            View timePopView = getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(new CommonAdapter<String>(SignUpActivity.this, styles, R.layout.pop_layout_listview_item) {
                public void convert(ViewHolder holder, final String s) {
                    holder.setText(R.id.item, s);
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        public void onClick(View v) {
                            typePop.dismiss();
                            type.setText(s);
                        }
                    });
                }
            });
            typePop.setContentView(timePopView);
            typePop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
        }
        return typePop;
    }

}
