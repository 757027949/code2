package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.AccountWithdrawal;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 选择银行卡
 * Created by Administrator on 2016/3/31.
 */
public class ChooseYLActivity extends BaseActivity {

    @Bind(R.id.list)
    ListView list;

    List<AccountWithdrawal> accountWithdrawals = new ArrayList<>();
    CommonAdapter<AccountWithdrawal> adapter;

    final int ACCOUNTOTHERLIST = 0;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (1 == requestCode && 1 == resultCode) {
                AccountWithdrawal accountWithdrawal = (AccountWithdrawal) data.getSerializableExtra("data");
                accountWithdrawals.add(0, accountWithdrawal);
                adapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ACCOUNTOTHERLIST:
                OkHttpUtils.post().url(UrlUtil.ACCOUNTOTHERLIST)
                        .addParams("accountOtherType", "bank")//bank银行卡，zfb支付宝，wx微信
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
                                        accountWithdrawals = new Gson().fromJson(jsonObject.getString("obj"), new TypeToken<List<AccountWithdrawal>>() {
                                        }.getType());
                                        list.setAdapter(adapter = new CommonAdapter<AccountWithdrawal>(ChooseYLActivity.this, accountWithdrawals, R.layout.adapter_layout_choose_yl_item) {
                                            @Override
                                            public void convert(final ViewHolder holder, final AccountWithdrawal accountWithdrawal) {
                                                String numberS = accountWithdrawal.getAccountOtherNumber();
                                                String replaceS = numberS.substring(0, numberS.length() - 6);
                                                holder.setText(R.id.number, accountWithdrawal.getAccountOtherBankName().split("·")[0] + "卡（" + numberS.replace(replaceS, "******") + "）");
                                                Glide.with(ChooseYLActivity.this).load(accountWithdrawal.getAccountOtherPath()).error(R.mipmap.ic_pay_yl).into((ImageView) holder.getView(R.id.image));
                                                holder.setOnClickListener(R.id.line_item, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent intent = new Intent();
                                                        intent.putExtra("data", accountWithdrawal);
                                                        setResult(3, intent);
                                                        finishSelf();
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        MyDialog.getInstance(ChooseYLActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(ChooseYLActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_choose_yl;
    }

    @Override
    public void initView(View view) {
        setTopTitle("选择银行卡");

        mHandler.sendEmptyMessage(ACCOUNTOTHERLIST);
    }

    @OnClick({R.id.add})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddYLActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

}
