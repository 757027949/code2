package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.asd.android.util.ActivityUtil;
import com.asd.android.util.BitmapUtil;
import com.asd.android.widget.MyRadioGroup;
import com.asd.util.JsonMapper;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseFragmentActivity;
import com.otw.asd.jjd.entity.ChatRedordParam;
import com.otw.asd.jjd.fragment.RecordFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的记录
 * Created by Administrator on 2016/3/31.
 */
public class RecordActivity extends BaseFragmentActivity {

    @Bind(R.id.top_right)
    ImageView top_right;

    @Bind(R.id.rbGroup_status)
    MyRadioGroup rbGroup_status;

    int checkedId;

    RecordFragment recordCompleteFragment, recordUncompleteFragment;

    public ImageView getTop_right() {
        return top_right;
    }

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

        if ("chat".equals(getIntent().getStringExtra("flow"))) {//聊天页面
            top_right.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.mipmap.ic_share).into(top_right);
        }

        rbGroup_status.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                selectBar(checkedId);
            }
        });

        selectBar(R.id.rb_complete);
    }

    @OnClick({R.id.top_right})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.top_right:
                Intent intent = new Intent();
                List<ChatRedordParam> list = new ArrayList<>();
                List<View> images = new ArrayList<>();
                List<String> imagesStrings = new ArrayList<>();
                if (R.id.rb_complete == checkedId) {
                    list.addAll(recordCompleteFragment.getRedordParamMap().values());
                    images.addAll(recordCompleteFragment.getViewMap().values());
                } else {
                    list.addAll(recordUncompleteFragment.getRedordParamMap().values());
                    images.addAll(recordUncompleteFragment.getViewMap().values());
                }
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) list);
                for (View view1 : images) {
                    imagesStrings.add(BitmapUtil.getInstance().convertIconToString(BitmapUtil.convertViewToBitmap(view1)));
                }
                intent.putStringArrayListExtra("images", (ArrayList<String>) imagesStrings);
                setResult(RESULT_OK, intent);
                finishSelf();
                break;
        }
    }

    public void selectBar(int checkedId) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager
                .beginTransaction();
        hideFragment(beginTransaction);
        this.checkedId = checkedId;
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
