package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asd.android.util.ActivityUtil;
import com.asd.android.util.app.AppManager;
import com.asd.util.JsonMapper;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.CurriculumOrderParams;
import com.otw.asd.jjd.entity.Voucher;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 确认下单
 * Created by Administrator on 2016/3/31.
 */
public class MakeOrderActivity extends BaseActivity {

    @Bind(R.id.day)
    TextView day;
    @Bind(R.id.line_am)
    LinearLayout line_am;
    @Bind(R.id.am_subject)
    TextView am_subject;
    @Bind(R.id.am_type)
    TextView am_type;
    @Bind(R.id.am_address)
    TextView am_address;
    @Bind(R.id.am_price)
    TextView am_price;
    @Bind(R.id.line_pm)
    LinearLayout line_pm;
    @Bind(R.id.pm_subject)
    TextView pm_subject;
    @Bind(R.id.pm_type)
    TextView pm_type;
    @Bind(R.id.pm_address)
    TextView pm_address;
    @Bind(R.id.pm_price)
    TextView pm_price;
    @Bind(R.id.total_price)
    TextView total_price;

    @Bind(R.id.am_flowlayout)
    TagFlowLayout am_flowlayout;
    @Bind(R.id.pm_flowlayout)
    TagFlowLayout pm_flowlayout;

    @Bind(R.id.voucher)
    TextView voucher;
    @Bind(R.id.money)
    TextView money;

    List<String> courseOrderIds;
    CurriculumOrderParams curriculumOrderParams;

    double totalPrice = 0;
    Voucher.DatasBean datasBean;//优惠券对象

    final int RESET_VALUE = 0;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case RESET_VALUE:
                if ("rebate".equals(datasBean.getAccountCouponType())) {//优惠券类型1，折扣券：rebate，2，代金券：replace
                    voucher.setText("享用折扣" + datasBean.getAccountCouponRebate());
                    money.setText("￥" + (totalPrice * datasBean.getAccountCouponRebate()));
                } else {
                    voucher.setText("￥" + datasBean.getAccountCouponReplace());
                    money.setText("￥" + (totalPrice - datasBean.getAccountCouponReplace()));
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_make_order;
    }

    @Override
    public void initView(View view) {
        AppManager.addActivity(this);
        setTopTitle("确认下单");

        setViewValue();
    }

    private void setViewValue() {
        Logger.e(JsonMapper.toNormalJson(getIntent().getStringArrayListExtra("courseOrderIds")));
        Logger.e(JsonMapper.toNormalJson(getIntent().getSerializableExtra("data")));

        day.setText(getIntent().getStringExtra("day"));
        courseOrderIds = getIntent().getStringArrayListExtra("courseOrderIds");
        curriculumOrderParams = (CurriculumOrderParams) getIntent().getSerializableExtra("data");

        double amPrice = 0, pmPrice = 0;
        final LayoutInflater mInflater = LayoutInflater.from(this);
        if (null != curriculumOrderParams.getAmOrders() && curriculumOrderParams.getAmOrders().size() > 0) {
            line_am.setVisibility(View.VISIBLE);
            am_subject.setText(curriculumOrderParams.getAmSubject());
            am_type.setText(curriculumOrderParams.getAmType());
            am_address.setText(curriculumOrderParams.getAmSiteName());

            if (curriculumOrderParams.getAmSubject().contains("二")) {//科目二
                am_price.setText("￥" + curriculumOrderParams.getCourseOrderS2UnitPrice());
                amPrice = curriculumOrderParams.getCourseOrderS2UnitPrice() * curriculumOrderParams.getAmOrders().size();
            } else {//科目三
                am_price.setText("￥" + curriculumOrderParams.getCourseOrderS3UnitPrice());
                amPrice = curriculumOrderParams.getCourseOrderS3UnitPrice() * curriculumOrderParams.getAmOrders().size();
            }

            am_flowlayout.setAdapter(new TagAdapter<CurriculumOrderParams.OrderBean>(curriculumOrderParams.getAmOrders()) {
                @Override
                public View getView(FlowLayout parent, int position, CurriculumOrderParams.OrderBean bean) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.layout_tag_tv, am_flowlayout, false);
                    tv.setText(bean.getTimeString());
                    return tv;
                }
            });
        }
        if (null != curriculumOrderParams.getPmOrders() && curriculumOrderParams.getPmOrders().size() > 0) {
            line_pm.setVisibility(View.VISIBLE);
            pm_subject.setText(curriculumOrderParams.getPmSubject());
            pm_type.setText(curriculumOrderParams.getPmType());
            pm_address.setText(curriculumOrderParams.getPmSiteName());

            if (curriculumOrderParams.getPmSubject().contains("二")) {//科目二
                pm_price.setText("￥" + curriculumOrderParams.getCourseOrderS2UnitPrice());
                pmPrice = curriculumOrderParams.getCourseOrderS2UnitPrice() * curriculumOrderParams.getPmOrders().size();
            } else {//科目三
                pm_price.setText("￥" + curriculumOrderParams.getCourseOrderS3UnitPrice());
                pmPrice = curriculumOrderParams.getCourseOrderS3UnitPrice() * curriculumOrderParams.getPmOrders().size();
            }

            pm_flowlayout.setAdapter(new TagAdapter<CurriculumOrderParams.OrderBean>(curriculumOrderParams.getPmOrders()) {
                @Override
                public View getView(FlowLayout parent, int position, CurriculumOrderParams.OrderBean bean) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.layout_tag_tv, pm_flowlayout, false);
                    tv.setText(bean.getTimeString());
                    return tv;
                }
            });
        }

        totalPrice = amPrice + pmPrice;
        total_price.setText("￥" + totalPrice);
        money.setText("￥" + totalPrice);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (0 == requestCode && 0 == resultCode) {
                datasBean = (Voucher.DatasBean) data.getSerializableExtra("data");
                mHandler.sendEmptyMessage(RESET_VALUE);
            }
        }
    }

    @OnClick({R.id.lint_voucher, R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.lint_voucher:
                Intent intent = new Intent(this, ChooseVoucherActivity.class);
                intent.putExtra("flow", "order");
                intent.putExtra("isCheckVoucher", getIntent().getBooleanExtra("isCheckVoucher", false));
                startActivityForResult(intent, 0);
                break;
            case R.id.submit:
                intent = new Intent(this, PayModeActivity.class);
                intent.putExtra("flow", "markOrder");
                if (null != datasBean) {
                    intent.putExtra("accountCouponId", datasBean.getAccountCouponId());
                    if ("rebate".equals(datasBean.getAccountCouponType())) {//优惠券类型1，折扣券：rebate，2，代金券：replace
                        intent.putExtra("money", (totalPrice * datasBean.getAccountCouponRebate()) + "");
                    } else {
                        intent.putExtra("money", (totalPrice - datasBean.getAccountCouponReplace()) + "");
                    }
                } else {
                    intent.putExtra("money", totalPrice + "");
                }
                intent.putStringArrayListExtra("courseOrderIds", (ArrayList<String>) courseOrderIds);
                startActivity(intent);
                break;
        }
    }

}