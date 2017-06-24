package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.TimeUtil;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.MyRadioGroup;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.BaseFragment;
import com.otw.asd.jjdt.base.BaseFragmentActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.CurriculumList;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.fragment.CurriculumFragment;
import com.otw.asd.jjdt.fragment.SetCurriculumFragment;
import com.otw.asd.jjdt.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的课程
 * Created by Administrator on 2016/4/20.
 */
public class CurriculumActivity extends BaseFragmentActivity {


    @Bind(R.id.rbGroup_time)
    MyRadioGroup rbGroup_time;
    @Bind(R.id.rb_today)
    RadioButton rb_today;
    @Bind(R.id.rb_nextday)
    RadioButton rb_nextday;

    /**
     * 当前选中日期
     */
    String rbTime;

    CurriculumFragment todayCurriculumFragment, nextdayCurriculumFragment;

    public String getRbTime() {
        return rbTime;
    }

    public void setRbTime(String rbTime) {
        this.rbTime = rbTime;
    }


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_curriculum;
    }

    @Override
    public void initView(View view) {
        setTopTitle("我的课程");

        initRbTime();

        rbGroup_time.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                selectBar(checkedId);
            }
        });

        selectBar(R.id.rb_today);
    }


    /**
     * 初始化课程安排显示时间
     */
    private void initRbTime() {
        rb_today.setText(TimeUtil.getInstall().getCurrentTimeFormat("yyyy-MM-dd"));
        rb_nextday.setText(TimeUtil.getInstall().getTime("yyyy-MM-dd", 1));
    }


    public void selectBar(int checkedId) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager
                .beginTransaction();
        hideFragment(beginTransaction);
        switch (checkedId) {
            case R.id.rb_today:
                setRbTime(rb_today.getText().toString());
                if (null == todayCurriculumFragment) {
                    todayCurriculumFragment = new CurriculumFragment();
                    beginTransaction.add(R.id.frame, todayCurriculumFragment);
                } else {
                    beginTransaction.show(todayCurriculumFragment);
                }
                break;

            case R.id.rb_nextday:
                setRbTime(rb_nextday.getText().toString());
                if (null == nextdayCurriculumFragment) {
                    nextdayCurriculumFragment = new CurriculumFragment();
                    beginTransaction.add(R.id.frame, nextdayCurriculumFragment);
                } else {
                    beginTransaction.show(nextdayCurriculumFragment);
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
        if (null != todayCurriculumFragment) {
            beginTransaction.hide(todayCurriculumFragment);
        }
        if (null != nextdayCurriculumFragment) {
            beginTransaction.hide(nextdayCurriculumFragment);
        }
    }

}
