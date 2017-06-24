package com.otw.asd.jjd.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.CommunityActivity;
import com.otw.asd.jjd.activity.CommunityCollegeInfoActivity;
import com.otw.asd.jjd.base.BaseFragment;
import com.otw.asd.jjd.entity.Community;
import com.otw.asd.jjd.util.RelativeDateFormat;
import com.otw.asd.jjd.util.UrlUtil;
import com.otw.asd.jjd.util.glide.transform.GlideRoundTransform;
import com.otw.asd.jjd.widget.SpacesItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 其他社区
 * Created by Administrator on 2016/9/27.
 */

public class CommunityCollegeFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    CommunityActivity activity;

//    @Bind(R.id.grid)
//    StaggeredGridView grid;


    int pageSize = 6;
    int totalPages = 1;
    int currentPage = 1;

    /**
     * 列个数
     */
    int itemCount = 2;
    /**
     * item填充边距
     */
    int itemMarginWidth = 16;

    List<Community> communityList = new ArrayList<>();
    CommonAdapter<Community> adapter;

    boolean isRefreshing = false;

    final int COMMONS = 0;
    final int SET_COMMONS = 1;

    /**
     * 是否显示过
     */
    boolean isShowed = false;

    /**
     * ALL:全部
     * A:学车星秀
     * B:天天趣看
     * C:吐槽树洞
     * D:勾搭我吧
     */
    private String commonType = "ALL";

    public String getCommonType() {
        return commonType;
    }

    public CommunityCollegeFragment setCommonType(String commonType) {
        this.commonType = commonType;
        return this;
    }


    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case COMMONS:
                final int type = (int) msg.obj;//0：刷新
                OkHttpUtils.post().url(UrlUtil.COMMONS)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("commonType", commonType)
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                if (0 == type) {
                                    isRefreshing = true;
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
                                            if (isShowed) {
                                                if (0 == communityList.size()) {
                                                    activity.showEmpty();
                                                } else {
                                                    activity.hiddeEmpty();
                                                }
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
                                isRefreshing = false;
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                refresh_layout.endRefreshing();
                                refresh_layout.endLoadingMore();
                                isRefreshing = false;
                            }
                        });


                /*List<String> strings = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    strings.add("" + i);
                }
                grid.setAdapter(new CommonAdapter<String>(mContext, strings, R.layout.layout_item_community_college) {
                    @Override
                    public void convert(ViewHolder holder, String s) {
                        if (holder.getPosition() % 2 == 0) {
                            holder.setVisible(R.id.rel_image, false);
                        }
                        Glide.with(mContext).load(holder.getPosition() % 2 == 0 ? R.mipmap.ic_address : R.mipmap.ic_person).error(R.drawable.ic_error).into((ImageView) holder.getView(R.id.image));
                        holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, CommunityCollegeInfoActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });*/
//                grid.addHeaderView(new TextView(mContext));
//                grid.addFooterView(new TextView(mContext));
//                grid.setAdapter(adapter = new CommunityCollegeAdapter(mContext, strings));
                break;

            case SET_COMMONS:
                if (null == adapter) {
                    adapter = new CommonAdapter<Community>(mContext, R.layout.layout_item_community_college, communityList) {
                        float itemWidth = 0;

                        @Override
                        protected void convert(final ViewHolder holder, final Community community, final int position) {
                            holder.setVisible(R.id.rel_image, false);

                            if (!"".equals(community.getCommon().getCommonCoverPath())) {//有封面
                                holder.setVisible(R.id.rel_image, true);


                                if (0 == itemWidth) {
                                    itemWidth = (GestureUtils.getScreenPix(mContext).widthPixels - itemMarginWidth * 2 * itemCount) / 2;
                                }
                                ImageView image = holder.getView(R.id.image_cover);
//                                if (null == holder.getView(R.id.rel_image).getTag()) {
                                if (0 != community.getCommon().getCommonCoverWidth()) {
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
//                                    itemHeight = ((float) (community.getCommon().getCommonCoverHeight()) * (itemWidth / (float) community.getCommon().getCommonCoverWidth()));
//                                    params.height = (int) itemHeight;
//                                    params.height = (int) ((float) (community.getCommon().getCommonCoverHeight()) * (itemWidth / (float) community.getCommon().getCommonCoverWidth()));
                                    params.height = (int) (itemWidth * ((float) (community.getCommon().getCommonCoverHeight()) / (float) (community.getCommon().getCommonCoverWidth())));
                                    image.setLayoutParams(params);
//                                        holder.getView(R.id.rel_image).setTag(true);
                                    Logger.e(itemWidth + "~~~~~~~~~~~" + params.height + "```" + itemWidth / params.height + "\n"
                                            + community.getCommon().getCommonCoverWidth() + "---" + community.getCommon().getCommonCoverHeight() + "====" + community.getCommon().getCommonCoverWidth() / community.getCommon().getCommonCoverHeight() + "\n"
                                            + ((float) itemWidth) / (float) community.getCommon().getCommonCoverWidth() + "---" + ((float) params.height) / (float) community.getCommon().getCommonCoverHeight());
                                }
//                                }

//                                Glide.with(mContext).load(community.getCommon().getCommonCoverPath()).override((int) itemWidth, (int) itemHeight).centerCrop().error(R.drawable.ic_error).placeholder(R.mipmap.loading2).transform(new GlideRoundTransform(mContext, 5)).into(image);
//                                Glide.with(mContext).load(community.getCommon().getCommonCoverPath()).error(R.drawable.ic_error).placeholder(R.mipmap.loading2).transform(new GlideRoundTransform(mContext, 5)).into(image);
                                Glide.with(mContext).load(community.getCommon().getCommonCoverPath()).transform(new GlideRoundTransform(mContext, 5)).into(image);
                            }
                            holder.setText(R.id.mes, community.getCommon().getCommonTitle());
                            holder.setText(R.id.see_number, community.getCommon().getVisitCount() + "次");
                            holder.setText(R.id.evaluate_number, community.getCommon().getReplyCount() + "");
                            Glide.with(mContext).load(community.getUser().getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                            holder.setText(R.id.name, community.getUser().getUserNickName());
//                          holder.setText(R.id.time, DateUtil.getDateDiff(community.getCommon().getCommonTime()));
                            try {
                                holder.setText(R.id.time, RelativeDateFormat.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(community.getCommon().getCommonTime())));
                            } catch (ParseException e) {
                            }
                            holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, CommunityCollegeInfoActivity.class);
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
        return R.layout.fragment_community_college;
    }

    @Override
    public void initView(View view) {
        activity = (CommunityActivity) mContext;

        initRefreshLayout();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(itemCount, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setAutoMeasureEnabled(true);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(itemMarginWidth);
        recycler_view.addItemDecoration(decoration);

        mHandler.sendMessage(mHandler.obtainMessage(COMMONS, 1));
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
        if (!isRefreshing) {
            mHandler.sendMessage(mHandler.obtainMessage(COMMONS, 0));
        }
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
            isShowed = true;
            if (0 == communityList.size()) {
                activity.showEmpty();
            } else {
                activity.hiddeEmpty();
            }
        }
    }
}
