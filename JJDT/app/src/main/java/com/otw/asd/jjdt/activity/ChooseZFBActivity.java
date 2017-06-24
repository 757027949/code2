package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.entity.AccountWithdrawal;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 选择支付宝
 * Created by Administrator on 2016/3/31.
 */
public class ChooseZFBActivity extends BaseActivity {

    @Bind(R.id.line_items)
    LinearLayout line_items;


    float proportion = 1;//比例
//    CheckBox[] checkBoxes = null;//

    List<AccountWithdrawal> accountWithdrawals;

    final int ACCOUNTOTHERLIST = 0;
    final int SET_ACCOUNTOTHERLIST = 1;

    @Override
    protected void onResume() {
        super.onResume();
//        checkBoxes = null;//
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (0 == requestCode && 0 == resultCode) {
                AccountWithdrawal accountWithdrawal = (AccountWithdrawal) data.getSerializableExtra("data");
                accountWithdrawals.add(0, accountWithdrawal);
                line_items.removeAllViews();
                mHandler.sendEmptyMessage(SET_ACCOUNTOTHERLIST);
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ACCOUNTOTHERLIST:
                OkHttpUtils.post().url(UrlUtil.ACCOUNTOTHERLIST)
                        .addParams("accountOtherType", "zfb")//bank银行卡，zfb支付宝，wx微信
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
                                        mHandler.sendEmptyMessage(SET_ACCOUNTOTHERLIST);
                                        Logger.e(accountWithdrawals.size() + "==========");
                                    } else {
                                        MyDialog.getInstance(ChooseZFBActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(ChooseZFBActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case SET_ACCOUNTOTHERLIST:
                if (null == accountWithdrawals) {
                    return false;
                }
                for (int i = 0; i < accountWithdrawals.size(); i++) {
                    AccountWithdrawal accountWithdrawal = accountWithdrawals.get(i);

                    Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_pay_zfb);
                    leftDrawable.setBounds(0, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1), (int) (GestureUtils.getScreenPix(this).widthPixels * 0.1));
                    Drawable rightDrawable = getResources().getDrawable(R.drawable.rb_select);
                    rightDrawable.setBounds(0, 0, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05), (int) (GestureUtils.getScreenPix(this).widthPixels * 0.05));

                    CheckBox checkBox = new CheckBox(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, (int) (1 * proportion), 0, 0);
                    checkBox.setLayoutParams(params);
                    checkBox.setBackgroundColor(Color.WHITE);
                    checkBox.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                    checkBox.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
                    checkBox.setCompoundDrawablePadding((int) (30 * proportion));
                    checkBox.setPadding((int) (50 * proportion), (int) (20 * proportion), (int) (50 * proportion), (int) (20 * proportion));
                    checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * proportion);
                    checkBox.setGravity(Gravity.CENTER_VERTICAL);
                    checkBox.setText(accountWithdrawal.getAccountOtherNumber());
                    checkBox.setTag(i);
                    if (2 == i) {
                        checkBox.setChecked(true);
                    }
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    resetCheckBoxesAttrs();
//                    CheckBox checkBox = (CheckBox) buttonView;
//                    checkBox.setChecked(true);
                            Intent intent = new Intent();
                            intent.putExtra("data", accountWithdrawals.get((Integer) buttonView.getTag()));
                            setResult(2, intent);
                            finishSelf();
                        }
                    });
                    line_items.addView(checkBox, line_items.getChildCount());
//            checkBoxes[i] = checkBox;
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_choose_zfb;
    }

    @Override
    public void initView(View view) {
        setTopTitle("选择支付宝");
        proportion = GestureUtils.getScreenPix(this).widthPixels / 768f;

        mHandler.sendEmptyMessage(ACCOUNTOTHERLIST);

//        checkBoxes = new CheckBox[5];

    }


    /**
     * 重置选项项
     */
 /*   private void resetCheckBoxesAttrs() {
        if (null == checkBoxes && line_items.getChildCount() > 0) {
            checkBoxes = new CheckBox[line_items.getChildCount()];
            for (int i = 0; i < line_items.getChildCount(); i++) {
                checkBoxes[i] = (CheckBox) line_items.getChildAt(i);
            }
        }
        if (null != checkBoxes) {
            for (int i = 0; i < checkBoxes.length; i++) {
//                checkBoxes[i].setChecked(false);
                ((CheckBox) line_items.getChildAt(i)).setChecked(false);
            }
        }
    }*/
    @OnClick({R.id.add})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddZFBActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

}
