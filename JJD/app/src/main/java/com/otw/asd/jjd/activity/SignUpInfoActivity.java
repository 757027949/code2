package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.app.AppManager;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.Apply;
import com.otw.asd.jjd.entity.SignUp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 报名学车信息条件
 * Created by Administrator on 2016/3/31.
 */
public class SignUpInfoActivity extends BaseActivity {

    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.service)
    TextView service;
    @Bind(R.id.condition)
    TextView condition;

    @Bind(R.id.listView)
    ListView listView;

    SignUp signUp;

    final int FLOW_INIT_VIEW = 0;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case FLOW_INIT_VIEW:
                Intent intent = getIntent();
                type.setText(((Apply) intent.getParcelableExtra("apply")).getApplyStudyType());
                signUp = (SignUp) intent.getSerializableExtra("data");
                service.setText(signUp.getObj().getApplyConfigService());
                condition.setText(signUp.getObj().getApplyConfigCriteria());
                money.setText("￥" + signUp.getObj().getMoney());
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_sign_up_info;
    }

    @Override
    public void initView(View view) {
        AppManager.addActivity(this);
        setTopTitle(R.string.left_sign_up);

        mHandler.sendEmptyMessage(FLOW_INIT_VIEW);

        String[] items = getResources().getStringArray(R.array.sign_up_info_items);
        List<String> strings = new ArrayList<>();
        for (String s : items) {
            strings.add(s);
        }
        listView.setAdapter(new CommonAdapter<String>(SignUpInfoActivity.this, strings, R.layout.adapter_layout_sign_up_money_item) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.hint_money, s);
                if (0 == holder.getPosition()) {
                    holder.setText(R.id.money, signUp.getObj().getApplyConfigItem0());
                } else if (1 == holder.getPosition()) {
                    holder.setText(R.id.money, signUp.getObj().getApplyConfigItem1());
                } else if (2 == holder.getPosition()) {
                    holder.setText(R.id.money, signUp.getObj().getApplyConfigItem2());
                } else if (3 == holder.getPosition()) {
                    holder.setText(R.id.money, signUp.getObj().getApplyConfigItem3());
                } else if (4 == holder.getPosition()) {
                    holder.setText(R.id.money, signUp.getObj().getApplyConfigItem4());
                }
            }
        });
    }

    @OnClick({R.id.pay})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.pay:
//                Intent intent = new Intent(this, SignUpOrderActivity.class);
                Intent intent = new Intent(this, PayModeActivity.class);
                intent.putExtra("flow", "signup");
                intent.putExtra("apply", getIntent().getParcelableExtra("apply"));
                startActivity(intent);
                break;
        }
    }

}
