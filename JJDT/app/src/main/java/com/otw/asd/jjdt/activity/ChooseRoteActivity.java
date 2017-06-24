package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.RefreshUtil;
import com.asd.android.widget.MyDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.entity.ChooseRote;
import com.otw.asd.jjdt.util.AssetsUtils;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 选择线路
 * Created by Administrator on 2016/3/31.
 */
public class ChooseRoteActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.no_data_layout)
    ViewStub no_data_layout;
    View no_data_layout_view = null;

    @Bind(R.id.listview)
    ListView listview;

    ArrayList<AddressPicker.Province> data;
    AddressPicker addressPicker;

    //addressProvinceName//省，addressCityName//市，addressAreaName//区,addressDetailName//详细名字
    String addressProvinceName = "", addressCityName = "", addressAreaName = "", addressDetailName = "";


    int pageSize = 5;
    int totalPages = 1;
    int currentPage = 1;
    TextView textView;
    final int SITELISTBYAREA = 0;
    final int SET_SITELISTBYAREA = 1;

    List<ChooseRote.SitesBean> beanList = new ArrayList<>();
    CommonAdapter<ChooseRote.SitesBean> commonAdapter = null;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SITELISTBYAREA:
                final int type = (int) msg.obj;//0:非静默  其他：静默（1：刷新  2：加载更多）
                OkHttpUtils.post().url(UrlUtil.SITELISTBYAREA)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("addressProvinceName", addressProvinceName)
                        .addParams("addressCityName", addressCityName)
                        .addParams("addressAreaName", addressAreaName)
                        .addParams("addressDetailName", addressDetailName)
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
                                            ChooseRote chooseRote = new Gson().fromJson(jsonObject.getString("obj"), ChooseRote.class);
                                            if (null != no_data_layout_view) {
                                                no_data_layout_view.setVisibility(View.GONE);
                                            }

                                            totalPages = RefreshUtil.getTotalPages(chooseRote.getSiteTotalCount(), pageSize);
                                            beanList.addAll(chooseRote.getSites());

                                            mHandler.sendEmptyMessage(SET_SITELISTBYAREA);
                                        } catch (JsonSyntaxException e) {
                                            if (null == no_data_layout_view) {
                                                no_data_layout_view = no_data_layout.inflate();
                                            } else {
                                                no_data_layout_view.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } else {
                                        MyDialog.getInstance(ChooseRoteActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(ChooseRoteActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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

            case SET_SITELISTBYAREA:
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
                if (null == commonAdapter) {
                    commonAdapter = new CommonAdapter<ChooseRote.SitesBean>(this, beanList, R.layout.adapter_layout_choose_rote_item) {
                        @Override
                        public void convert(ViewHolder holder, final ChooseRote.SitesBean sitesBean) {
                            holder.setText(R.id.item, sitesBean.getSiteName());
                            holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra("data", sitesBean);
                                    setResult(0, intent);
                                    finishSelf();
                                }
                            });
                        }
                    };
                    listview.setAdapter(commonAdapter);
                } else {
                    commonAdapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_choose_rote;
    }

    @Override
    public void initView(final View view) {
        setTopTitle("选择区域");

        initRefreshLayout();

        getAddressPicker().show();
    }

    @OnClick({R.id.choose_address})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.choose_address:
                if (getAddressPicker().isShowing()) {
                    getAddressPicker().dismiss();
                } else {
                    getAddressPicker().show();
                }
                break;
        }
    }

    public AddressPicker getAddressPicker() {
        if (null == data) {
            data = new ArrayList<AddressPicker.Province>();
            String json = AssetsUtils.readText(this, "city.json");
            data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
            addressPicker = new AddressPicker(this, data);
            addressPicker.setSelectedItem("重庆", "重庆", "");
            addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(String province, String city, String county) {
                    addressProvinceName = province;
                    addressCityName = city;
                    addressAreaName = county;
                    beanList.clear();
                    mHandler.sendMessage(mHandler.obtainMessage(SITELISTBYAREA, 0));
                }
            });
        }
        return addressPicker;
    }


    private void initRefreshLayout() {
        refresh_layout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(this, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        refresh_layout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        currentPage = 1;
        beanList.clear();
        mHandler.sendMessage(mHandler.obtainMessage(SITELISTBYAREA, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            mHandler.sendMessage(mHandler.obtainMessage(SITELISTBYAREA, 2));
            return true;
        } else {
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }
}
