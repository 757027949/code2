package com.otw.asd.jjdt.activity;

import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.asd.android.widget.MyRadioGroup;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseFragmentActivity;
import com.otw.asd.jjdt.fragment.RecordFragment;

import butterknife.Bind;

/**
 * 我的记录
 * Created by Administrator on 2016/4/20.
 */
public class RecordActivity extends BaseFragmentActivity {
    @Bind(R.id.rbGroup_status)
    MyRadioGroup rbGroup_status;

    RecordFragment recordCompleteFragment, recordUncompleteFragment;

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_record;
    }

    @Override
    public void initView(View view) {
        setTopTitle("我的记录");


        rbGroup_status.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                selectBar(checkedId);
            }
        });

        selectBar(R.id.rb_complete);
    }


    public void selectBar(int checkedId) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager
                .beginTransaction();
        hideFragment(beginTransaction);
        switch (checkedId) {
            case R.id.rb_complete:
                if (null == recordCompleteFragment) {
                    recordCompleteFragment = new RecordFragment();
                    recordCompleteFragment.setIsFinish("Y");
                    beginTransaction.add(R.id.frame, recordCompleteFragment);
                } else {
                    beginTransaction.show(recordCompleteFragment);
                }
                break;

            case R.id.rb_uncomplete:
                if (null == recordUncompleteFragment) {
                    recordUncompleteFragment = new RecordFragment();
                    recordUncompleteFragment.setIsFinish("N");
                    beginTransaction.add(R.id.frame, recordUncompleteFragment);
                } else {
                    beginTransaction.show(recordUncompleteFragment);
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
        if (null != recordCompleteFragment) {
            beginTransaction.hide(recordCompleteFragment);
        }
        if (null != recordUncompleteFragment) {
            beginTransaction.hide(recordUncompleteFragment);
        }
    }

}
