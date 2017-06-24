package com.otw.asd.jjd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.RefreshUtil;
import com.asd.android.widget.MyDialog;
import com.asd.util.JsonMapper;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.ChooseVoucherActivity;
import com.otw.asd.jjd.base.BaseFragment;
import com.otw.asd.jjd.entity.Voucher;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/7/14.
 */
public class VoucherFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.no_data_layout)
    ViewStub no_data_layout;
    View no_data_layout_view = null;

    @Bind(R.id.listview)
    ListView listview;

    String isUsable = "Y";

    int pageSize = 5;
    int totalPages = 1;
    int currentPage = 1;
    TextView textView;

    final int ACCOUNTCOUPONLIST = 0;
    final int SET_ACCOUNTCOUPONLIST = 1;

    List<Voucher.DatasBean> beanList = new ArrayList<>();
    CommonAdapter<Voucher.DatasBean> adapter;

    ChooseVoucherActivity activity;

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ACCOUNTCOUPONLIST:
                final int type = (int) msg.obj;//0:非静默  其他：静默（1：刷新  2：加载更多）
                OkHttpUtils.post().url(UrlUtil.ACCOUNTCOUPONLIST)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("accountCouponUseState", isUsable)
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                if (1 == type) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }

                            @Override
                            public Context getContext() {
                                return mContext;
                            }

                            @Override
                            public SweetAlertDialog getLoadDialog() {
                                return getLodingAlertDialog();
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        try {
                                            Voucher voucher = new Gson().fromJson(jsonObject.getString("obj"), Voucher.class);
                                            if (null != no_data_layout_view) {
                                                no_data_layout_view.setVisibility(View.GONE);
                                            }

                                            totalPages = RefreshUtil.getTotalPages(voucher.getTotalCount(), pageSize);

                                            beanList.addAll(voucher.getDatas());
                                            mHandler.sendEmptyMessage(SET_ACCOUNTCOUPONLIST);
                                        } catch (JsonSyntaxException e) {
//                                            MyDialog.getInstance(mContext).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                            if (null == no_data_layout_view) {
                                                no_data_layout_view = no_data_layout.inflate();
                                            } else {
                                                no_data_layout_view.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } else {
                                        MyDialog.getInstance(mContext).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(mContext).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
                                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case SET_ACCOUNTCOUPONLIST:
                if ("order".equals(activity.getFlow()) && "N".equals(getIsUsable()) && activity.isCheckVoucher()) {//预约课程 未使用 约束
                    List<Voucher.DatasBean> list = new ArrayList<>();
                    for (Voucher.DatasBean bean : beanList) {
                        if (!"rebate".equals(bean.getAccountCouponType())) {//折扣券：rebate，2，代金券：replace
                            if ((int) bean.getAccountCouponReplace() > 5) {//不能使用
                                list.add(bean);
                            }
                        }
                    }
                    beanList.removeAll(list);
                    if (0 == beanList.size()) {
                        if (null == no_data_layout_view) {
                            no_data_layout_view = no_data_layout.inflate();
                        } else {
                            no_data_layout_view.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if (null == textView) {
                    textView = new TextView(mContext);
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
                if (null == adapter) {
                    listview.setAdapter(adapter = new CommonAdapter<Voucher.DatasBean>(mContext, beanList, R.layout.adapter_layout_voucher_item) {
                        @Override
                        public void convert(ViewHolder holder, final Voucher.DatasBean datasBean) {
                            if ("N".equals(datasBean.getAccountCouponUseState()) && "Y".equals(datasBean.getAccountCouponIsValid())) {
                                holder.getView(R.id.btn_bak).setBackgroundResource(R.drawable.voucher_item_enabled_bak);
                            } else {
                                holder.getView(R.id.btn_bak).setBackgroundResource(R.drawable.voucher_item_unenabled_bak);
                            }
                            holder.setText(R.id.mes, datasBean.getAccountCouponExplain());
                            if ("rebate".equals(datasBean.getAccountCouponType())) {//折扣券：rebate，2，代金券：replace
                                holder.setText(R.id.content, "折扣券|" + datasBean.getAccountCouponRebate() + "折");
                            } else {
                                holder.setText(R.id.content, "代金券|" + (int) datasBean.getAccountCouponReplace() + "元");
                            }
                            holder.setText(R.id.time, "有效期：" + datasBean.getAccountCouponBeginTime() + " - " + datasBean.getAccountCouponEndTime());
                            holder.setTag(R.id.btn_bak, datasBean);
                            holder.setOnClickListener(R.id.btn_bak, null);
                            if ("order".equals(activity.getFlow()) && "N".equals(getIsUsable()) && "N".equals(datasBean.getAccountCouponUseState()) && "Y".equals(datasBean.getAccountCouponIsValid())) {//预约课程是可用优惠券
                                holder.setOnClickListener(R.id.btn_bak, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Logger.e(JsonMapper.toNormalJson(v.getTag()));
                                        Intent intent = new Intent();
                                        intent.putExtra("data", (Voucher.DatasBean) v.getTag());
                                        activity.setResult(0, intent);
                                        activity.finishSelf();
                                    }
                                });
                            }
                            try {
                                Glide.with(mContext).load(R.mipmap.ic_voucher).into((ImageView) holder.getView(R.id.image));
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_voucher;
    }

    @Override
    public void initView(View view) {
        activity = (ChooseVoucherActivity) mContext;
        initRefreshLayout();

        mHandler.sendMessage(mHandler.obtainMessage(ACCOUNTCOUPONLIST, 0));

        Logger.e(activity.getFlow() + "------" + getIsUsable());
    }


    private void initRefreshLayout() {
        refresh_layout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(mContext, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        refresh_layout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        currentPage = 1;
        beanList.clear();
        mHandler.sendMessage(mHandler.obtainMessage(ACCOUNTCOUPONLIST, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            mHandler.sendMessage(mHandler.obtainMessage(ACCOUNTCOUPONLIST, 2));
            return true;
        } else {
            Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }
}
