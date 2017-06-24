package com.otw.asd.jjdt.fragment;

import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.activity.CurriculumActivity;
import com.otw.asd.jjdt.base.BaseFragment;
import com.otw.asd.jjdt.entity.CurriculumList;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2016/6/12.
 */
public class CurriculumFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.am_subject)
    TextView am_subject;
    @Bind(R.id.am_type)
    TextView am_type;
    @Bind(R.id.am_address)
    TextView am_address;
    @Bind(R.id.pm_subject)
    TextView pm_subject;
    @Bind(R.id.pm_type)
    TextView pm_type;
    @Bind(R.id.pm_address)
    TextView pm_address;

    @Bind(R.id.am_listview)
    ListView am_listview;
    @Bind(R.id.pm_listview)
    ListView pm_listview;

    CurriculumActivity activity;

    final int COURSEORDERLISTBYTEACHER = 0;
    final int SET_COURSEORDERLISTBYTEACHER = 1;

    CurriculumList curriculumList;//获取到的课程

    List<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> amCourseOrdersBeen = new ArrayList<>();
    CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> amCommonAdapter;
    List<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> pmCourseOrdersBeen = new ArrayList<>();
    CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> pmCommonAdapter;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case COURSEORDERLISTBYTEACHER:
                OkHttpUtils.post().url(UrlUtil.COURSEORDERLISTBYTEACHERANDDATE)
                        .addParams("courseOrderDate", activity.getRbTime())
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
                                    curriculumList = new Gson().fromJson(response, CurriculumList.class);
                                    if (curriculumList.isSuccess()) {
                                        mHandler.sendEmptyMessage(SET_COURSEORDERLISTBYTEACHER);
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(mContext).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                    refresh_layout.endRefreshing();
                                }
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                refresh_layout.endRefreshing();
                            }
                        });
                break;

            case SET_COURSEORDERLISTBYTEACHER:
                for (int i = 0; i < curriculumList.getObj().getDatas().size(); i++) {
                    CurriculumList.ObjBean.DatasBean datasBean = curriculumList.getObj().getDatas().get(i);
                    if ("am".equals(datasBean.getData().getTimeType()) || 0 == i) {//am 0 == i
                        am_subject.setText(datasBean.getData().getSubject());
                        am_type.setText(datasBean.getData().getType());
                        am_address.setText(datasBean.getData().getSiteName());
                        amCourseOrdersBeen.clear();
                        amCourseOrdersBeen.addAll(datasBean.getData().getCourseOrders());
                        if (null == amCommonAdapter) {
                            amCommonAdapter = new CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean>(mContext, amCourseOrdersBeen, R.layout.adapter_layout_curriculum_item) {
                                @Override
                                public void convert(ViewHolder holder, CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean) {
                                    holder.setText(R.id.time_start, courseOrdersBean.getCourseOrderBeginTime() + ":00");
                                    holder.setText(R.id.time_end, courseOrdersBean.getCourseOrderEndTime() + ":00");
                                    setImageNumber(holder, courseOrdersBean.getPeopleNumber(), courseOrdersBean.getUsers());
                                }
                            };
                            am_listview.setAdapter(amCommonAdapter);
                        } else {
                            amCommonAdapter.notifyDataSetChanged();
                        }
                    } else {
                        pm_subject.setText(datasBean.getData().getSubject());
                        pm_type.setText(datasBean.getData().getType());
                        pm_address.setText(datasBean.getData().getSiteName());
                        pmCourseOrdersBeen.clear();
                        pmCourseOrdersBeen.addAll(datasBean.getData().getCourseOrders());
                        if (null == pmCommonAdapter) {
                            pmCommonAdapter = new CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean>(mContext, pmCourseOrdersBeen, R.layout.adapter_layout_curriculum_item) {
                                @Override
                                public void convert(ViewHolder holder, CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean) {
                                    holder.setText(R.id.time_start, courseOrdersBean.getCourseOrderBeginTime() + ":00");
                                    holder.setText(R.id.time_end, courseOrdersBean.getCourseOrderEndTime() + ":00");
                                    setImageNumber(holder, courseOrdersBean.getPeopleNumber(), courseOrdersBean.getUsers());
                                }
                            };
                            pm_listview.setAdapter(pmCommonAdapter);
                        } else {
                            pmCommonAdapter.notifyDataSetChanged();
                        }
                    }
                }
                refresh_layout.endRefreshing();
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_curriculum;
    }

    @Override
    public void initView(View view) {
        activity = (CurriculumActivity) mContext;

        mHandler.sendEmptyMessage(COURSEORDERLISTBYTEACHER);

        initRefreshLayout();
    }

    /**
     * 设置item头像
     *
     * @param holder
     * @param userInfos
     */
    public void setImageNumber(ViewHolder holder, int peopleNumber, List<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean.User> userInfos) {
        holder.getView(R.id.item_one).setVisibility(View.INVISIBLE);
        holder.getView(R.id.item_two).setVisibility(View.INVISIBLE);
        holder.getView(R.id.item_three).setVisibility(View.INVISIBLE);
        holder.getView(R.id.item_four).setVisibility(View.INVISIBLE);

        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into((ImageView) holder.getView(R.id.item_one));
        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into((ImageView) holder.getView(R.id.item_two));
        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into((ImageView) holder.getView(R.id.item_three));
        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into((ImageView) holder.getView(R.id.item_four));

        for (int j = 1; j <= peopleNumber; j++) {
            if (1 == j) {
                holder.setVisible(R.id.item_one, true);
            } else if (2 == j) {
                holder.setVisible(R.id.item_two, true);
            } else if (3 == j) {
                holder.setVisible(R.id.item_three, true);
            } else if (4 == j) {
                holder.setVisible(R.id.item_four, true);
            }
        }

       /* if (1 == peopleNumber) {
            holder.setVisible(R.id.item_one, true);
        } else if (2 == peopleNumber) {
            holder.setVisible(R.id.item_one, true);
            holder.setVisible(R.id.item_two, true);
        } else if (3 == peopleNumber) {
            holder.setVisible(R.id.item_one, true);
            holder.setVisible(R.id.item_two, true);
            holder.setVisible(R.id.item_three, true);
        } else if (4 == peopleNumber) {
            holder.setVisible(R.id.item_one, true);
            holder.setVisible(R.id.item_two, true);
            holder.setVisible(R.id.item_three, true);
            holder.setVisible(R.id.item_four, true);
        }*/

        if (null != userInfos) {
            for (int i = 0; i < userInfos.size(); i++) {
                if (0 == i) {
                    Glide.with(this).load(userInfos.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.item_one));
                }
                if (1 == i) {
                    Glide.with(this).load(userInfos.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.item_two));
                }
                if (2 == i) {
                    Glide.with(this).load(userInfos.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.item_three));
                }
                if (3 == i) {
                    Glide.with(this).load(userInfos.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.item_four));
                }
            }
        }
    }

    private void initRefreshLayout() {
        refresh_layout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(mContext, false);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        refresh_layout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        List<UserInfo> userInfos = new ArrayList<>();
//        userInfos.add(new UserInfo());
//        userInfos.add(new UserInfo());
//        curriculumList.getObj().getDatas().get(0).getData().getCourseOrders().get(0).setUsers(userInfos);
//        userInfos.add(new UserInfo());
//        curriculumList.getObj().getDatas().get(1).getData().getCourseOrders().get(1).setUsers(userInfos);
        mHandler.sendEmptyMessage(COURSEORDERLISTBYTEACHER);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
