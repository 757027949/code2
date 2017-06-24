package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.asd.android.util.ActivityUtil;
import com.asd.android.widget.MyRadioGroup;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseFragmentActivity;
import com.otw.asd.jjd.fragment.VoucherFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 选择优惠券
 * Created by Administrator on 2016/7/14.
 */
public class ChooseVoucherActivity extends BaseFragmentActivity {
    @Bind(R.id.rbGroup_status)
    MyRadioGroup rbGroup_status;

    VoucherFragment usedVoucherFragment, unusedVoucherFragment;

    String flow;
    boolean isCheckVoucher;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public boolean isCheckVoucher() {
        return isCheckVoucher;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_choose_voucher;
    }

    @Override
    public void initView(View view) {
        setTopTitle("选择优惠券");

        flow = getIntent().getStringExtra("flow");
        isCheckVoucher = getIntent().getBooleanExtra("isCheckVoucher", false);

        rbGroup_status.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                selectBar(checkedId);
            }
        });
        selectBar(R.id.rb_use);
    }

    @OnClick({R.id.add_voucher})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.add_voucher:
                Intent intent = new Intent(this, AddVoucherActivity.class);
                startActivity(intent);
                break;
        }

    }


    public void selectBar(int checkedId) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager
                .beginTransaction();
        hideFragment(beginTransaction);
        switch (checkedId) {
            case R.id.rb_use:
                if (null == usedVoucherFragment) {
                    usedVoucherFragment = new VoucherFragment();
                    usedVoucherFragment.setIsUsable("N");
                    beginTransaction.add(R.id.frame, usedVoucherFragment);
                } else {
                    beginTransaction.show(usedVoucherFragment);
                }
                break;

            case R.id.rb_unuse:
                if (null == unusedVoucherFragment) {
                    unusedVoucherFragment = new VoucherFragment();
                    unusedVoucherFragment.setIsUsable("Y");
                    beginTransaction.add(R.id.frame, unusedVoucherFragment);
                } else {
                    beginTransaction.show(unusedVoucherFragment);
                }
                break;
        }
        beginTransaction.commit();
    }


    /**
     * 隐藏Fragment
     *
     * @param beginTransaction
     */
    private void hideFragment(FragmentTransaction beginTransaction) {
        if (null != usedVoucherFragment) {
            beginTransaction.hide(usedVoucherFragment);
        }
        if (null != unusedVoucherFragment) {
            beginTransaction.hide(unusedVoucherFragment);
        }
    }
}
