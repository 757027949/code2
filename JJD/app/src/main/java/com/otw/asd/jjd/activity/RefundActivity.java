package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.widget.MyDialog;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.RecordInfo;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 申请退款
 * Created by Administrator on 2016/3/31.
 */
public class RefundActivity extends BaseActivity {

    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.order_id)
    TextView order_id;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.persons)
    TextView persons;
    @Bind(R.id.address)
    TextView address;

    @Bind(R.id.mes)
    FormEditText mes;

    RecordInfo recordInfo;

    final int ADDREFUNDAPPLY = 0;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ADDREFUNDAPPLY:
                OkHttpUtils.post().url(UrlUtil.ADDREFUNDAPPLY)
                        .addParams("courseOrderId", recordInfo.getCourseOrder().getCourseOrderId())
                        .addParams("refundApplyExplain", mes.getText().toString())
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
                                        Toast.makeText(RefundActivity.this, jsonObject.getString("obj"), Toast.LENGTH_SHORT).show();
                                        setResult(0, new Intent());
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(RefundActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(RefundActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
        return R.layout.activity_refund;
    }

    @Override
    public void initView(View view) {
        setTopTitle("申请退款");

        setViewValue();
    }

    private void setViewValue() {
        try {
            recordInfo = (RecordInfo) getIntent().getSerializableExtra("data");
            money.setText("￥" + recordInfo.getCourseOrderUnitPrice());
            String orderId = recordInfo.getCourseOrder().getCourseOrderId();
            String s = orderId.substring(7, 23);
            order_id.setText(orderId.replace(s, "******"));
            status.setText(recordInfo.getCourseOrder().getCourseOrderState());
            price.setText(recordInfo.getCourseOrderUnitPrice() + "元");
            persons.setText(recordInfo.getCourseOrder().getPeopleNumber() + "人");
            address.setText(recordInfo.getSite().getAddress().getAddressProvinceName() + recordInfo.getSite().getAddress().getAddressCityName() + recordInfo.getSite().getAddress().getAddressAreaName() + recordInfo.getSite().getAddress().getAddressDetailName());
        } catch (Exception e) {
        }
    }

    @OnClick({R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.submit:
                if (!mes.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(ADDREFUNDAPPLY);
                break;
        }
    }

}
