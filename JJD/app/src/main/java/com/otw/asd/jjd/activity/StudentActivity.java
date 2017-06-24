package com.otw.asd.jjd.activity;

import android.os.Message;
import android.view.View;

import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;


/**
 * 代理招生
 * Created by Administrator on 2016/3/31.
 */
public class StudentActivity extends BaseActivity {


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_student;
    }

    @Override
    public void initView(View view) {
        setTopTitle(R.string.left_student);

    }
}
