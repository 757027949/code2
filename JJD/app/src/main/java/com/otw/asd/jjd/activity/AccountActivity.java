package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.pop.PointerPopupWindow;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Account;
import com.otw.asd.jjd.entity.AccountOtherDefault;
import com.otw.asd.jjd.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的账户
 * Created by Administrator on 2016/3/31.
 */
public class AccountActivity extends BaseActivity {

    @Bind(R.id.current_money)
    TextView current_money;
    @Bind(R.id.integrate)
    TextView integrate;
    @Bind(R.id.voucher)
    TextView voucher;

    @Bind(R.id.top_right)
    ImageView top_right;

    /**
     * 最大请求次数
     */
    final int max_requests = 3;
    /**
     * 当前请求次数
     */
    int current_requests = 1;
    final int ACCOUNT = 0;
    final int GETACCOUNTOTHERDEFAULT = 1;


    Account account = null;
    PointerPopupWindow setPwdPopupWindow;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ACCOUNT:
                final int type = (int) msg.obj;
                OkHttpUtils.post().url(UrlUtil.ACCOUNT)
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                if (0 == type) {
                                    setNotSilence(true);
                                } else {
                                    setNotSilence(false);
                                }
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    account = new Gson().fromJson(response, Account.class);
                                    current_money.setText("￥" + account.getObj().getAccount().getAccountBalance());
                                    integrate.setText(account.getObj().getAccount().getAccountIntegral() + "");
                                    voucher.setText(account.getObj().getAccountCouponCount() + "");
                                } catch (Exception e) {
                                    MyDialog.getInstance(AccountActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                if (current_requests <= max_requests) {
                                    current_requests++;
                                    mHandler.sendMessage(mHandler.obtainMessage(ACCOUNT, 1));
                                }
                            }
                        });
                break;

            case GETACCOUNTOTHERDEFAULT:
                OkHttpUtils.post().url(UrlUtil.GETACCOUNTOTHERDEFAULT)
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(false);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    AccountOtherDefault accountOtherDefault = new Gson().fromJson(response, AccountOtherDefault.class);
                                    if (accountOtherDefault.isSuccess()) {
                                        LocalCache localCache = LocalCache.get(AccountActivity.this);
                                        localCache.put(LocalCacheKey.ACCOUNTOTHERDEFAULT, accountOtherDefault);
                                    }
                                } catch (Exception e) {
//                                    MyDialog.getInstance(AccountActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_account;
    }

    @Override
    public void initView(View view) {
        setTopTitle(R.string.left_account);

        top_right.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.ic_right_menu).into(top_right);

//        LocalCache localCache = LocalCache.get(this);
//        AccountOtherDefault  accountOtherDefault = (AccountOtherDefault) localCache.getAsObject(LocalCacheKey.ACCOUNTOTHERDEFAULT);
//        if(null!=accountOtherDefault){
//            if (accountOtherDefault.getObj().size() < 0) {
//        mHandler.sendEmptyMessage(GETACCOUNTOTHERDEFAULT);
//            }
//        }else{
//            mHandler.sendEmptyMessage(GETACCOUNTOTHERDEFAULT);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendMessage(mHandler.obtainMessage(ACCOUNT, 0));
        mHandler.sendEmptyMessage(GETACCOUNTOTHERDEFAULT);
    }

    @OnClick({R.id.line_money, R.id.account_in, R.id.top_right, R.id.line_integrate, R.id.line_voucher})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.line_money:
                Intent intent = new Intent(this, AccountBalanceActivity.class);
                intent.putExtra("data", account);
                startActivity(intent);
                break;

            case R.id.account_in:
                intent = new Intent(this, AccountRechargeActivity.class);
                startActivity(intent);
                break;

            case R.id.top_right:
                createStylePop().showAsPointer(view);
                break;

            case R.id.line_integrate://积分

                break;

            case R.id.line_voucher://代金券
                intent = new Intent(this, ChooseVoucherActivity.class);
                intent.putExtra("flow", "account");
                startActivity(intent);
                break;
        }
    }

    private PointerPopupWindow createStylePop() {
        if (null == setPwdPopupWindow) {
            List<String> styles = new ArrayList<>();
            styles.add("忘记支付密码");
            setPwdPopupWindow = new PointerPopupWindow(this, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.45), ViewGroup.LayoutParams.WRAP_CONTENT);
            View timePopView = getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(new CommonAdapter<String>(AccountActivity.this, styles, R.layout.pop_layout_listview_menu_item) {
                public void convert(final ViewHolder holder, final String s) {
                    holder.setText(R.id.item, s);
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        public void onClick(View v) {
                            setPwdPopupWindow.dismiss();
                            if (0 == holder.getPosition()) {
                                Intent intent = new Intent(mContext, ForgetPayPwdActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            });
            setPwdPopupWindow.setContentView(timePopView);
            setPwdPopupWindow.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
        }
        return setPwdPopupWindow;
    }

}
