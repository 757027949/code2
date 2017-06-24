package com.otw.asd.jjd.activity;

import android.os.Message;
import android.view.View;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.widget.MyDialog;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 意见
 * Created by Administrator on 2016/3/31.
 */
public class IdeaActivity extends BaseActivity {

    @Bind(R.id.theme)
    FormEditText theme;
    @Bind(R.id.mes)
    FormEditText mes;

    final int SUGGESTION = 0;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SUGGESTION:
                OkHttpUtils.post().url(UrlUtil.SUGGESTION)
                        .addParams("suggestionType", "")//意见类型，可为空
                        .addParams("suggestionTitle", theme.getText().toString())//意见标题,可为空
                        .addParams("suggestionContent", mes.getText().toString())//意见文本，可为空
                        .addParams("suggestionOther", "")//其他信息，可为空
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
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        MyDialog.getInstance(IdeaActivity.this).getSuccessAlertDialog("感谢您提出的宝贵意见或建议！！！", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                finishSelf();
                                            }
                                        }).show();
                                    } else {
                                        MyDialog.getInstance(IdeaActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(IdeaActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_idea;
    }

    @Override
    public void initView(View view) {
        setTopTitle(R.string.left_idea);
    }

    @OnClick(R.id.submit)
    public void commitIdea(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        if (theme.testValidity() && mes.testValidity()) {
            mHandler.sendEmptyMessage(SUGGESTION);
        }
    }

}
