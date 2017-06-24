package com.otw.asd.jjd.activity;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.asd.android.util.app.AppManager;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.AccountDetailInfo;

import butterknife.Bind;


/**
 * 明细详情
 * Created by Administrator on 2016/3/31.
 */
public class AccountDetailInfoActivity extends BaseActivity {

    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.order_id)
    TextView order_id;
    @Bind(R.id.current_money)
    TextView current_money;

    final int SET_ACCOUNTORDERDETAIL = 0;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SET_ACCOUNTORDERDETAIL:
                AccountDetailInfo accountDetailInfo = (AccountDetailInfo) getIntent().getSerializableExtra("data");
                money.setText("￥" + accountDetailInfo.getAccountOrderAmount());
                status.setText("Y".equals(accountDetailInfo.getAccountOrderState()) ? "已完成" : "未完成");
                type.setText(accountDetailInfo.getAccountOrderExplain());
                time.setText(accountDetailInfo.getAccountOrderTime());
                String orderId = accountDetailInfo.getAccountOrderId();
                String s = orderId.substring(7, 23);
                order_id.setText(orderId.replace(s, "******"));
                current_money.setText(accountDetailInfo.getAccountOrderAfterAmount() + "");
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_account_detail_info;
    }

    @Override
    public void initView(View view) {
        setTopTitle("明细详情");

        String flow = getIntent().getStringExtra("flow");
        if ("withdrawal".equals(flow) || "recharge".equals(flow) || "accountDetail".equals(flow)) {//withdrawal：提现  recharge：充值  accountDetail:明细
            mHandler.sendEmptyMessage(SET_ACCOUNTORDERDETAIL);
        }
    }

    @Override
    public void finishSelf() {
        super.finishSelf();
        AppManager.finishAllActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishSelf();
    }
}
