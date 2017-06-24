package com.otw.asd.jjd.activity;

import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.RefreshUtil;
import com.asd.android.widget.MyDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.adapter.AccountDetailAdapter;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.Account;
import com.otw.asd.jjd.entity.AccountDetail;
import com.otw.asd.jjd.entity.AccountDetailInfo;
import com.otw.asd.jjd.entity.PinnedSectionItem;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 明细
 * Created by Administrator on 2016/3/31.
 */
public class AccountDetailActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.no_data_layout)
    ViewStub no_data_layout;
    View no_data_layout_view = null;

    @Bind(R.id.listview)
    ListView listview;

    Account account;//账户对象

    int pageSize = 5;
    int totalPages = 1;
    int currentPage = 1;

    TextView textView;

    final int ACCOUNTORDERLIST = 0;
    final int SET_ACCOUNTORDERLIST = 2;
    final int ACCOUNT = 1;

    /**
     * 当前最后一条数据日期
     */
    String currentLastDataDate = "";
    List<PinnedSectionItem> pinnedSectionItems = new ArrayList<>();
    AccountDetailAdapter detailAdapter = null;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ACCOUNTORDERLIST:
                final int type = (int) msg.obj;//0:非静默  其他：静默（1：刷新  2：加载更多）
                if (null == account) {
                    mHandler.sendEmptyMessage(ACCOUNT);
                    return false;
                }
                OkHttpUtils.post().url(UrlUtil.ACCOUNTORDERLIST)
                        .addParams("accountId", account.getObj().getAccount().getAccountId())
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                if (1 == type) {
                                    setNotSilence(false);
                                } else {
                                    setNotSilence(true);
                                }
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        try {
                                            AccountDetail accountDetail = new Gson().fromJson(jsonObject.getString("obj"), AccountDetail.class);
                                            if (null != no_data_layout_view) {
                                                no_data_layout_view.setVisibility(View.GONE);
                                            }

                                            totalPages = RefreshUtil.getTotalPages(accountDetail.getTotalCount(), pageSize);

                                            PinnedSectionItem pinnedSectionItem;
                                            for (AccountDetail.AccountOrderListBean accountOrderListBean : accountDetail.getAccountOrderList()) {
                                                if (!currentLastDataDate.equals(accountOrderListBean.getDate())) {
                                                    pinnedSectionItem = new PinnedSectionItem();
                                                    pinnedSectionItem.setItemType(PinnedSectionItem.SECTION);
                                                    pinnedSectionItem.setSectionString(accountOrderListBean.getDate());
                                                    pinnedSectionItems.add(pinnedSectionItem);
                                                }
                                                for (AccountDetailInfo accountOrdersBean : accountOrderListBean.getAccountOrders()) {
                                                    pinnedSectionItems.add(accountOrdersBean);
                                                    currentLastDataDate = accountOrderListBean.getDate();
                                                }
                                            }
                                            mHandler.sendEmptyMessage(SET_ACCOUNTORDERLIST);
                                        } catch (JsonSyntaxException e) {
//                                            MyDialog.getInstance(mContext).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                            if (null == no_data_layout_view) {
                                                no_data_layout_view = no_data_layout.inflate();
                                            } else {
                                                no_data_layout_view.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } else {
                                        MyDialog.getInstance(AccountDetailActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(AccountDetailActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }

                                if (1 == type) {
                                    refresh_layout.endRefreshing();
                                } else if (2 == type) {
                                    refresh_layout.endLoadingMore();
                                }
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                if (1 == type) {
                                    refresh_layout.endRefreshing();
                                } else if (2 == type) {
                                    refresh_layout.endLoadingMore();
                                }
                            }
                        });
                break;

            case SET_ACCOUNTORDERLIST:
                if (null == textView) {
                    textView = new TextView(this);
                    textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setText(R.string.list_footer_txt);
                }
                if (currentPage == totalPages) {
                    listview.removeFooterView(textView);
                } else {
                    if (listview.getFooterViewsCount() < 1) {
                        listview.addFooterView(textView);
                    }
                }
                if (null == detailAdapter) {
                    detailAdapter = new AccountDetailAdapter(this, pinnedSectionItems);
                    listview.setAdapter(detailAdapter);
                } else {
                    detailAdapter.notifyDataSetChanged();
                }
                break;

            case ACCOUNT:
                OkHttpUtils.post().url(UrlUtil.ACCOUNT)
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
                                    account = new Gson().fromJson(response, Account.class);
                                    mHandler.sendMessage(mHandler.obtainMessage(ACCOUNTORDERLIST, 1));
                                } catch (Exception e) {
                                    MyDialog.getInstance(AccountDetailActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
        return R.layout.activity_account_detail;
    }

    @Override
    public void initView(View view) {
        setTopTitle("明细");

        account = (Account) getIntent().getSerializableExtra("data");
        initRefreshLayout();

        mHandler.sendMessage(mHandler.obtainMessage(ACCOUNTORDERLIST, 0));
    }

    private void initRefreshLayout() {
        refresh_layout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(AccountDetailActivity.this, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        refresh_layout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        currentPage = 1;
        currentLastDataDate = "";
        pinnedSectionItems.clear();
        mHandler.sendMessage(mHandler.obtainMessage(ACCOUNTORDERLIST, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            mHandler.sendMessage(mHandler.obtainMessage(ACCOUNTORDERLIST, 2));
            return true;
        } else {
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }
}
