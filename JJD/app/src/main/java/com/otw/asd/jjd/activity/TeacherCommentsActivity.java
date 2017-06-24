package com.otw.asd.jjd.activity;

import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hedgehog.ratingbar.RatingBar;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.Comment;
import com.otw.asd.jjd.util.RelativeDateFormat;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2016/6/28.
 */
public class TeacherCommentsActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;
    @Bind(R.id.listView)
    ListView listView;

    List<Comment.CommentAndUsersBean> commentAndUsersBeanList = new ArrayList<>();
    CommonAdapter<Comment.CommentAndUsersBean> adapter;

    Comment comment;
    int pageSize = 5;
    int totalPages = 1;
    int currentPage = 1;

    final int COMMENTLISTBYUSER = 2;
    final int SET_COMMENTLISTBYUSER = 3;

    TextView textView;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case COMMENTLISTBYUSER:
                final int type = (int) msg.obj;//0:非静默  其他：静默（1：刷新  2：加载更多）
                OkHttpUtils.post().url(UrlUtil.COMMENTLISTBYUSER)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("userId", getIntent().getStringExtra("userId"))
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
                                            comment = new Gson().fromJson(jsonObject.getString("obj"), Comment.class);

                                            totalPages = RefreshUtil.getTotalPages(comment.getCommentTotalCount(), pageSize);
                                            mHandler.sendEmptyMessage(SET_COMMENTLISTBYUSER);
                                        } catch (JsonSyntaxException e) {
//                                            MyDialog.getInstance(mContext).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                        }
                                    } else {
                                        MyDialog.getInstance(TeacherCommentsActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(TeacherCommentsActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
                                Toast.makeText(TeacherCommentsActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case SET_COMMENTLISTBYUSER:
                commentAndUsersBeanList.addAll(comment.getCommentAndUsers());
                if (null == textView) {
                    textView = new TextView(this);
                    textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setText(R.string.list_footer_txt);
                }
                if (currentPage == totalPages) {
                    listView.removeFooterView(textView);
                } else {
                    if (listView.getFooterViewsCount() < 1) {
                        listView.addFooterView(textView);
                    }
                }
                if (null == adapter) {
                    listView.setAdapter(adapter = new CommonAdapter<Comment.CommentAndUsersBean>(this, commentAndUsersBeanList, R.layout.layout_item_teacher_mes) {
                        Drawable halfDrawable = getResources().getDrawable(R.mipmap.ic_star_half);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        @Override
                        public void convert(ViewHolder holder, Comment.CommentAndUsersBean bean) {
                            try {
                                holder.setText(R.id.name, bean.getUserNickName());
                                RatingBar bar = holder.getView(R.id.star_num);
                                bar.setStarHalfDrawable(halfDrawable);
                                bar.setStar(bean.getCommentEvaScore());
                                holder.setText(R.id.mes, bean.getCommentContent());
                                holder.setText(R.id.pass_time, RelativeDateFormat.format(format.parse(bean.getCommentTime())));
                                Glide.with(mContext).load(bean.getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_teacher_comments;
    }

    @Override
    public void initView(View view) {
        setTopTitle("评价");
        initRefreshLayout();

        mHandler.sendMessage(mHandler.obtainMessage(COMMENTLISTBYUSER, 0));
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
        commentAndUsersBeanList.clear();
        mHandler.sendMessage(mHandler.obtainMessage(COMMENTLISTBYUSER, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            mHandler.sendMessage(mHandler.obtainMessage(COMMENTLISTBYUSER, 2));
            return true;
        } else {
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }
}
