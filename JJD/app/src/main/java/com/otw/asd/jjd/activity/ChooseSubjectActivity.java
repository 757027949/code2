package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.util.UrlUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 题库选择科目
 * Created by Administrator on 2016/3/31.
 */
public class ChooseSubjectActivity extends BaseActivity {

    @Bind(R.id.subject_one)
    Button subject_one;
    @Bind(R.id.subject_two)
    Button subject_two;

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_choose_subject;
    }

    @Override
    public void initView(View view) {
        setTopTitle("科目选择");

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(subject_one, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.12));
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(subject_two, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.12));
    }

    @OnClick({R.id.subject_one, R.id.subject_two})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.subject_one:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", UrlUtil.SUBJECT_ONE);
                intent.putExtra("flow", "subject");
                startActivity(intent);
                break;

            case R.id.subject_two:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", UrlUtil.SUBJECT_FOUR);
                intent.putExtra("flow", "subject");
                startActivity(intent);
                break;
        }
    }

}
