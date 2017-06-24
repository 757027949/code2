package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hedgehog.ratingbar.RatingBar;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.RecordInfo;
import com.otw.asd.jjd.util.PhoneUtil;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 记录详情
 * Created by Administrator on 2016/3/31.
 */
public class RecordInfoActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.age)
    TextView age;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.star_num)
    RatingBar star_num;
    @Bind(R.id.total)
    TextView total;
    @Bind(R.id.order_status)
    TextView order_status;
    @Bind(R.id.order_money)
    TextView order_money;
    @Bind(R.id.order_persons)
    TextView order_persons;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.subject)
    TextView subject;
    @Bind(R.id.study_time)
    TextView study_time;
    @Bind(R.id.create_time)
    TextView create_time;
    @Bind(R.id.price)
    TextView price;

    @Bind(R.id.evaluate)
    TextView evaluate;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.refund)
    TextView refund;


    final int GETCOURSEORDERDETAIL = 0;
    final int SET_GETCOURSEORDERDETAIL = 1;
    final int GETREFUNDAPPLYSTATE = 2;
    final int UPDATECOURSEORDERBYCANCEL = 3;

    RecordInfo recordInfo;

    @Override
    public boolean handleMessage(final Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case GETCOURSEORDERDETAIL:
                OkHttpUtils.post().url(UrlUtil.GETCOURSEORDERDETAIL)
                        .addParams("courseOrderId", getIntent().getStringExtra("courseOrderId"))
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                if (0 == msg.what) {
                                    setNotSilence(true);
                                } else {
                                    setNotSilence(false);
                                }
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        recordInfo = new Gson().fromJson(jsonObject.getString("obj"), RecordInfo.class);
                                        mHandler.sendEmptyMessage(GETREFUNDAPPLYSTATE);
                                        mHandler.sendEmptyMessage(SET_GETCOURSEORDERDETAIL);
                                    } else {
                                        MyDialog.getInstance(RecordInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(RecordInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }

                                refresh_layout.endRefreshing();
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                refresh_layout.endRefreshing();
                            }
                        });
                break;

            case SET_GETCOURSEORDERDETAIL:
                try {
                    name.setText(recordInfo.getUserTeacher().getUserRealName());
                    age.setText(recordInfo.getUserTeacher().getUserAge() + "");
                    time.setText(recordInfo.getTeacher().getTeacherTeachYear() + "年");
                    star_num.setStar(recordInfo.getTeacher().getTeacherLevel());
                    total.setText(recordInfo.getCourseOrderTotalCount() + "");
                    order_status.setText(recordInfo.getCourseOrder().getCourseOrderState());
                    order_money.setText(recordInfo.getCourseOrderUnitPrice() + "元");
                    order_persons.setText(recordInfo.getCourseOrder().getPeopleNumber() + "人");
                    type.setText(recordInfo.getCourseOrder().getCourse().getCourseType());
                    subject.setText(recordInfo.getCourseOrder().getCourse().getCourseSubject());
                    study_time.setText(recordInfo.getCourseOrder().getCourseOrderBeginTime().substring(0, 16) + "-" + recordInfo.getCourseOrder().getCourseOrderEndTime().substring(11, 16));
                    create_time.setText(recordInfo.getCourseOrder().getCourseOrderTime());
                    price.setText(recordInfo.getCourseOrder().getCourseOrderPrice() + "元");


                    Glide.with(RecordInfoActivity.this).load(recordInfo.getUserTeacher().getUserHeadPath()).error(R.mipmap.ic_person).into(image);
                    address.setText(recordInfo.getSite().getAddress().getAddressProvinceName() + recordInfo.getSite().getAddress().getAddressCityName() + recordInfo.getSite().getAddress().getAddressAreaName() + recordInfo.getSite().getAddress().getAddressDetailName());

                    cancel.setVisibility(recordInfo.isIsCancel() ? View.VISIBLE : View.GONE);
                } catch (Exception e) {
                }
                if ("Y".equals(recordInfo.getIsComment())) {//是否可以评价
                    evaluate.setVisibility(View.VISIBLE);
//                    pay.setVisibility(View.GONE);
//                    refund.setVisibility(View.GONE);
                }


                break;

            case GETREFUNDAPPLYSTATE:
                OkHttpUtils.post().url(UrlUtil.GETREFUNDAPPLYSTATE)
                        .addParams("courseOrderId", getIntent().getStringExtra("courseOrderId"))
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
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {//"obj":{"refundApplyState":"-1","isRefund":"Y"}
                                        jsonObject = jsonObject.getJSONObject("obj");
                                        if ("Y".equals(jsonObject.getString("isRefund"))) {// isRefund 可以退款
                                            if (!"1".equals(jsonObject.getString("refundApplyState"))) {// refundApplyState  -1:未申请退款  0：申请中  1：已处理
                                                refund.setVisibility(View.VISIBLE);
                                                if ("0".equals(jsonObject.getString("refundApplyState"))) {
                                                    refund.setEnabled(false);
                                                    refund.setText("退款审核中");
                                                }
                                            }
                                        } else {
                                            refund.setVisibility(View.GONE);
                                        }
                                    } else {
                                        MyDialog.getInstance(RecordInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(RecordInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case UPDATECOURSEORDERBYCANCEL:
                OkHttpUtils.post().url(UrlUtil.UPDATECOURSEORDERBYCANCEL)
                        .addParams("courseOrderId", getIntent().getStringExtra("courseOrderId"))
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
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(RecordInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(RecordInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }

        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_record_info;
    }

    @Override
    public void initView(final View view) {
        setTopTitle("记录详情");
        AppManager.addActivity(this);

        initRefreshLayout();
        mHandler.sendMessage(mHandler.obtainMessage(GETCOURSEORDERDETAIL, 0));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (0 == requestCode && 0 == resultCode) {
                refund.setEnabled(false);
                refund.setText("退款审核中");
            }
        }
    }

    @OnClick({R.id.image, R.id.call, R.id.evaluate, R.id.refund, R.id.cancel})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.image:
                if (null == recordInfo) {
                    return;
                }
                Intent intent = new Intent(this, TeacherInfoActivity.class);
                intent.putExtra("teacherId", recordInfo.getTeacher().getTeacherId());
                intent.putExtra("userId", recordInfo.getTeacher().getUserId());
                startActivity(intent);
                break;
            case R.id.call:
                if (null != recordInfo) {
                    PhoneUtil.getInstance(this).call(recordInfo.getUserTeacher().getUserRegisterTelephone());
                }
                break;

            case R.id.evaluate://评价
                intent = new Intent(this, EvaluateActivity.class);
                intent.putExtra("data", recordInfo);
                startActivity(intent);
                break;
            case R.id.refund://退款
                intent = new Intent(this, RefundActivity.class);
                intent.putExtra("data", recordInfo);
                startActivityForResult(intent, 0);
                break;
            case R.id.cancel://取消订单
                mHandler.sendEmptyMessage(UPDATECOURSEORDERBYCANCEL);
//                intent = new Intent(this, PayModeActivity.class);
//                intent.putExtra("flow", "recordInfo");
//                startActivity(intent);
                break;
        }
    }


    private void initRefreshLayout() {
        refresh_layout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(this, false);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        refresh_layout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mHandler.sendMessage(mHandler.obtainMessage(GETCOURSEORDERDETAIL, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

}
