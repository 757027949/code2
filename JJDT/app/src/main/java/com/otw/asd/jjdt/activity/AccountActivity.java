package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.Account;
import com.otw.asd.jjdt.entity.AccountOtherDefault;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的账户
 * Created by Administrator on 2016/4/20.
 */
public class AccountActivity extends BaseActivity {

    @Bind(R.id.person_image)
    ImageView person_image;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.account_number)
    TextView account_number;
    @Bind(R.id.balance)
    TextView balance;

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

    UserInfo userInfo = null;
    Account account = null;

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendMessage(mHandler.obtainMessage(ACCOUNT, 0));
    }

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
                                    balance.setText("￥" + account.getObj().getAccount().getAccountBalance());
                                } catch (Exception e) {
                                    MyDialog.getInstance(AccountActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                if (current_requests <= max_requests) {
                                    current_requests++;
                                    mHandler.sendMessageDelayed(mHandler.obtainMessage(ACCOUNT, 1), 1000);
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
        setTopTitle("我的账户");

//        userInfo = (UserInfo) LocalCache.get(this).getAsObject(LocalCacheKey.KEY_USER);
        userInfo = (UserInfo) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.KEY_USER));
        Glide.with(AccountActivity.this).load(userInfo.getObj().getUser().getUserHeadPath()).error(R.mipmap.ic_person).into(person_image);
        name.setText(userInfo.getObj().getUser().getUserRealName());
        account_number.setText(userInfo.getObj().getUser().getUserRegisterTelephone());

//        LocalCache localCache = LocalCache.get(this);
//        AccountOtherDefault accountOtherDefault = (AccountOtherDefault) localCache.getAsObject(LocalCacheKey.ACCOUNTOTHERDEFAULT);
//        if(null!=accountOtherDefault){
//            if (accountOtherDefault.getObj().size() < 0) {
        mHandler.sendEmptyMessage(GETACCOUNTOTHERDEFAULT);
//            }
//        }else{
//            mHandler.sendEmptyMessage(GETACCOUNTOTHERDEFAULT);
//        }
    }

    @OnClick({R.id.detail, R.id.account_out})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.detail:
                Intent intent = new Intent(this, AccountDetailActivity.class);
                intent.putExtra("data", account);
                startActivity(intent);
                break;
            case R.id.account_out:
                intent = new Intent(this, AccountWithdrawalsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
