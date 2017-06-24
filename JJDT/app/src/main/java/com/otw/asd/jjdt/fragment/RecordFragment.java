package com.otw.asd.jjdt.fragment;

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
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.adapter.RecordAdapter;
import com.otw.asd.jjdt.base.BaseFragment;
import com.otw.asd.jjdt.entity.PinnedSectionItem;
import com.otw.asd.jjdt.entity.Record;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2016/6/15.
 */
public class RecordFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.no_data_layout)
    ViewStub no_data_layout;
    View no_data_layout_view = null;

    @Bind(R.id.listview)
    ListView listview;


    int pageSize = 5;
    int totalPages = 1;
    int currentPage = 1;
    TextView textView;
    String isFinish = "Y";
    final int COURSEORDERLISTBYTEACHER = 0;
    final int SET_COURSEORDERLISTBYTEACHER = 1;


    /**
     * 当前最后一条数据日期
     */
    String currentLastDataDate = "";
    List<PinnedSectionItem> pinnedSectionItems = new ArrayList<>();
    RecordAdapter recordAdapter = null;

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case COURSEORDERLISTBYTEACHER:
                final int type = (int) msg.obj;//0:非静默  其他：静默（1：刷新  2：加载更多）
                OkHttpUtils.post().url(UrlUtil.COURSEORDERLISTBYTEACHER)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("isFinish", isFinish)
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
                                            Record record = new Gson().fromJson(jsonObject.getString("obj"), Record.class);
                                            if (null != no_data_layout_view) {
                                                no_data_layout_view.setVisibility(View.GONE);
                                            }

                                            totalPages = RefreshUtil.getTotalPages(record.getTotalCount(), pageSize);

                                            PinnedSectionItem pinnedSectionItem;
                                            for (Record.DatasBean datasBean : record.getDatas()) {
                                                if (!currentLastDataDate.equals(datasBean.getDate())) {
                                                    pinnedSectionItem = new PinnedSectionItem();
                                                    pinnedSectionItem.setItemType(PinnedSectionItem.SECTION);
                                                    pinnedSectionItem.setSectionString(datasBean.getDate());
                                                    pinnedSectionItems.add(pinnedSectionItem);
                                                }
                                                for (Record.DatasBean.CourseOrdersBean courseOrdersBean : datasBean.getCourseOrders()) {
                                                    pinnedSectionItems.add(courseOrdersBean);
                                                    currentLastDataDate = datasBean.getDate();
                                                }
                                            }
                                            mHandler.sendEmptyMessage(SET_COURSEORDERLISTBYTEACHER);
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

            case SET_COURSEORDERLISTBYTEACHER:
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
                if (null == recordAdapter) {
                    recordAdapter = new RecordAdapter(mContext, pinnedSectionItems);
                    listview.setAdapter(recordAdapter);
                } else {
                    recordAdapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_record;
    }

    @Override
    public void initView(View view) {
        initRefreshLayout();

        mHandler.sendMessage(mHandler.obtainMessage(COURSEORDERLISTBYTEACHER, 0));
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
        currentLastDataDate = "";
        pinnedSectionItems.clear();
        mHandler.sendMessage(mHandler.obtainMessage(COURSEORDERLISTBYTEACHER, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            mHandler.sendMessage(mHandler.obtainMessage(COURSEORDERLISTBYTEACHER, 2));
            return true;
        } else {
            Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }
}
