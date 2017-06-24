package com.otw.asd.jjd.activity;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.PojoUtils;
import com.asd.android.widget.MyDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.SignUpOrder;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;

import butterknife.Bind;

/**
 * 报名学车信息详情
 * Created by Administrator on 2016/3/31.
 */
public class SignUpOrderActivity extends BaseActivity {

    @Bind(R.id.order_id)
    TextView order_id;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.pay_time)
    TextView pay_time;
    @Bind(R.id.pay_money)
    TextView pay_money;

    /**
     * 是否获取到数据
     */
    boolean get_data = false;
    final int APPLYINFO = 0;


    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case APPLYINFO:
                OkHttpUtils.post().url(UrlUtil.APPLYINFO)
//                        .addParams("accountOrderId", ((User) LocalCache.get(this).getAsObject(LocalCacheKey.LOCAL_USER)).getObj().getUser().getAccountOrderId())
                        .addParams("accountOrderId", ((User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER))).getObj().getUser().getAccountOrderId())
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
                                    SignUpOrder order = new Gson().fromJson(response, SignUpOrder.class);
                                    if (order.isSuccess()) {
                                        get_data = true;
                                        setViewValue(order);
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(SignUpOrderActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sign_up_order;
    }

    @Override
    public void initView(View view) {
        setTopTitle("报名凭证");
        String flow = getIntent().getStringExtra("flow");

        if ("sign".equals(flow)) {//报名
            SignUpOrder signUpOrder = getIntent().getParcelableExtra("data");

            setViewValue(signUpOrder);
        } else if ("select".equals(flow)) {//已报名(select)  查看
            mHandler.sendEmptyMessage(APPLYINFO);
        }
    }

    /**
     * 设置界面信息
     *
     * @param signUpOrder
     */
    private void setViewValue(SignUpOrder signUpOrder) {
        String orderId = signUpOrder.getObj().getAccountOrder().getAccountOrderId();
        String s = orderId.substring(7, 23);
        order_id.setText(orderId.replace(s, "******"));
        type.setText(signUpOrder.getObj().getApply().getApplyStudyType());
        pay_time.setText(signUpOrder.getObj().getAccountOrder().getAccountOrderTime());
        pay_money.setText("￥" + signUpOrder.getObj().getAccountOrder().getAccountOrderAmount());
    }

    /**
     * 重新获取数据
     *
     * @param view
     */
    public void rootViewClick(View view) {
        if (!get_data) {
            mHandler.sendEmptyMessage(APPLYINFO);
        }
    }

    /**
     * 关闭当前
     *
     * @param view
     */
    public void closeSelf(View view) {
        finish();
    }


}
