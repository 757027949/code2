package com.otw.asd.jjd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.asd.android.http.okhttp.MyCallBack;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.util.DateUtils;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.CommunityActivity;
import com.otw.asd.jjd.activity.CommunityHotInfoActivity;
import com.otw.asd.jjd.base.BaseFragment;
import com.otw.asd.jjd.entity.Community;
import com.otw.asd.jjd.util.DateUtil;
import com.otw.asd.jjd.util.RelativeDateFormat;
import com.otw.asd.jjd.util.UrlUtil;
import com.otw.asd.jjd.widget.SpacesItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 热门（精选）
 * Created by Administrator on 2016/9/27.
 */

public class CommunityHotFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    List<Community> communityList = new ArrayList<>();
    CommonAdapter<Community> adapter;

    CommunityActivity activity;


    int pageSize = 6;
    int totalPages = 1;
    int currentPage = 1;

    final int COMMONS = 0;
    final int SET_COMMONS = 1;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case COMMONS:
                final int type = (int) msg.obj;//0：刷新
                OkHttpUtils.post().url(UrlUtil.COMMONS)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("commonType", "ALL")
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                if (0 == type) {
                                    return true;
                                }
                                return false;
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
                                            List<Community> communities = new Gson().fromJson(jsonObject.getString("obj"), new TypeToken<List<Community>>() {
                                            }.getType());
                                            if (communities.size() == pageSize) {
                                                totalPages++;
                                            }
                                            communityList.addAll(communities);
                                            if (0 == communityList.size()) {
                                                activity.showEmpty();
                                            } else {
                                                activity.hiddeEmpty();
                                            }

                                            mHandler.sendEmptyMessage(SET_COMMONS);
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        MyDialog.getInstance(mContext).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(mContext).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                                refresh_layout.endRefreshing();
                                refresh_layout.endLoadingMore();
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                refresh_layout.endRefreshing();
                                refresh_layout.endLoadingMore();
                            }
                        });
                break;

            case SET_COMMONS:
                if (null == adapter) {
                    adapter = new CommonAdapter<Community>(mContext, R.layout.layout_item_community_hot, communityList) {
                        String[] results = getResources().getStringArray(R.array.community_college);
                        int itemHeight = 0;

                        @Override
                        protected void convert(ViewHolder holder, final Community community, int position) {
                            View view = holder.getView(R.id.mes);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                            View rel = holder.getView(R.id.rel_content);
                            rel.measure(0, 0);
                            if (!"".equals(community.getCommon().getCommonCoverPath())) {//有封面
                                holder.setVisible(R.id.picture, true);
                                if (0 == itemHeight) {
                                    itemHeight = rel.getMeasuredHeight();
                                }
                                Glide.with(mContext).load(community.getCommon().getCommonCoverPath()).error(R.drawable.ic_error).into((ImageView) holder.getView(R.id.picture));
                            } else {
                                holder.setVisible(R.id.picture, false);
                            }
                            if (holder.getView(R.id.picture).getVisibility() == View.VISIBLE) {
                                params.height = itemHeight;
                            } else {
                                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            }
                            view.setLayoutParams(params);
                            Glide.with(mContext).load(community.getUser().getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                            holder.setText(R.id.name, community.getUser().getUserNickName());
//                                                        holder.setText(R.id.time, DateUtil.getDateDiff(community.getCommon().getCommonTime()));
                            try {
                                holder.setText(R.id.time, RelativeDateFormat.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(community.getCommon().getCommonTime())));
                            } catch (ParseException e) {
                            }
                            holder.setText(R.id.mes, community.getCommon().getCommonTitle());
                            switch (community.getCommon().getCommonType()) {
                                case "ALL":
                                    holder.setText(R.id.college, "热门精选");
                                    break;
                                case "A":
                                    holder.setText(R.id.college, results[0]);
                                    break;
                                case "B":
                                    holder.setText(R.id.college, results[1]);
                                    break;
                                case "C":
                                    holder.setText(R.id.college, results[2]);
                                    break;
                                case "D":
                                    holder.setText(R.id.college, results[3]);
                                    break;
                            }
                            holder.setText(R.id.see_number, community.getCommon().getVisitCount() + "次");

                            holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, CommunityHotInfoActivity.class);
                                    intent.putExtra("commonId", community.getCommon().getCommonId());
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    recycler_view.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_community_hot;
    }

    @Override
    public void initView(View view) {
        activity = (CommunityActivity) mContext;

        initRefreshLayout();
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recycler_view.addItemDecoration(decoration);


        mHandler.sendMessage(mHandler.obtainMessage(COMMONS, 1));

       /* List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("");
        }
        recycler_view.setAdapter(new CommonAdapter<String>(mContext, R.layout.layout_item_community_hot, strings) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                if (position % 2 == 0) {
                    holder.setVisible(R.id.picture, false);
                }
                View view = holder.getView(R.id.mes);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                View rel = holder.getView(R.id.rel_content);
                rel.measure(0, 0);
                params.height = rel.getMeasuredHeight();
                view.setLayoutParams(params);
                holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CommunityHotInfoActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });*/
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
        refresh_layout.endRefreshing();
        currentPage = 1;
        communityList.clear();
//        adapter.notifyDataSetChanged();
        mHandler.sendMessage(mHandler.obtainMessage(COMMONS, 0));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            mHandler.sendMessage(mHandler.obtainMessage(COMMONS, 1));
            return true;
        } else {
            Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && null != activity) {
            if (0 == communityList.size()) {
                activity.showEmpty();
            } else {
                activity.hiddeEmpty();
            }
        }
    }
}
