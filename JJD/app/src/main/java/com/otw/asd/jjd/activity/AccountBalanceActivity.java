package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.asd.android.util.ActivityUtil;
import com.asd.android.util.app.AppManager;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.Account;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 余额
 * Created by Administrator on 2016/3/31.
 */
public class AccountBalanceActivity extends BaseActivity {

    @Bind(R.id.current_money)
    TextView current_money;

    Account account;//账户对象

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_account_balance;
    }

    @Override
    public void initView(View view) {
        setTopTitle("余额");

        account = (Account) getIntent().getSerializableExtra("data");
        if (null != account) {
            current_money.setText("￥" + account.getObj().getAccount().getAccountBalance());
        }
    }


    @OnClick({R.id.detail, R.id.account_out})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.detail:
                Intent intent = new Intent(this, AccountDetailActivity.class);
                intent.putExtra("data", account);
                startActivity(intent);
                break;
            case R.id.account_out:
                intent = new Intent(this, AccountWithdrawalsActivity.class);
                startActivity(intent);
                AppManager.addActivity(this);
                break;
        }
    }

}
