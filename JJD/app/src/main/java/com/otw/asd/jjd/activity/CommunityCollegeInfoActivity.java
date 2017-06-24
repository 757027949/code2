package com.otw.asd.jjd.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.andreabaccega.widget.FormEditText;
import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.util.DateUtils;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.entity.CommunityInfo;
import com.otw.asd.jjd.entity.Reply;
import com.otw.asd.jjd.util.RelativeDateFormat;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/9/28.
 */

public class CommunityCollegeInfoActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.root)
    LinearLayout root;

    @Bind(R.id.top_right)
    ImageView top_right;
    @Bind(R.id.person_image)
    ImageView person_image;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.see_number)
    TextView see_number;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.college)
    TextView college;
    @Bind(R.id.hint_num)
    TextView hint_num;
    @Bind(R.id.mes)
    FormEditText mes;

    @Bind(R.id.list)
    ListView list;

    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;
    @Bind(R.id.listview_evaluate)
    ListView listview_evaluate;

    int pageSize = 10;
    int totalPages = 1;
    int currentPage = 1;

    List<Reply> replyList = new ArrayList<>();
    CommonAdapter<Reply> adapter;

    CommunityInfo communityInfo;

    final int GETCOMMON = 0;
    final int SET_GETCOMMON = 1;
    final int REPLYS = 2;
    final int SET_REPLYS = 3;
    final int ADDREPLY = 4;

    @Override
    public boolean handleMessage(final Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case GETCOMMON:
                OkHttpUtils.post().url(UrlUtil.GETCOMMON)
                        .addParams("commonId", getIntent().getStringExtra("commonId"))
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return false;
                            }

                            @Override
                            public Context getContext() {
                                return CommunityCollegeInfoActivity.this;
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
                                            communityInfo = new Gson().fromJson(jsonObject.getString("obj"), CommunityInfo.class);
//                                            mHandler.sendMessage(mHandler.obtainMessage(SET_GETCOMMON, communityInfo));
                                            mHandler.sendEmptyMessage(SET_GETCOMMON);
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        MyDialog.getInstance(CommunityCollegeInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(CommunityCollegeInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case SET_GETCOMMON:
//                CommunityInfo communityInfo = (CommunityInfo) msg.obj;
                Glide.with(this).load(communityInfo.getUser().getUserHeadPath()).error(R.mipmap.ic_person).into(person_image);
                name.setText(communityInfo.getUser().getUserNickName());
                try {
                    time.setText(RelativeDateFormat.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(communityInfo.getCommon().getCommonTime())));
                } catch (ParseException e) {
                }
                see_number.setText(communityInfo.getCommon().getVisitCount() + "次");
                title.setText(communityInfo.getCommon().getCommonTitle());
                content.setText(communityInfo.getCommon().getCommonText());

                List<String> imagePaths = new ArrayList<>();
                try {
                    if (!"".equals(communityInfo.getCommon().getCommonImagePath())) {
                        List<String> imageStrings = (List<String>) JSON.parse(communityInfo.getCommon().getCommonImagePath());
                        imagePaths.addAll(imageStrings);
                    }
                    if (!"".equals(communityInfo.getCommon().getCommonImagePath1())) {
                        List<String> imageStrings = (List<String>) JSON.parse(communityInfo.getCommon().getCommonImagePath1());
                        imagePaths.addAll(imageStrings);
                    }
                    if (!"".equals(communityInfo.getCommon().getCommonImagePath2())) {
                        List<String> imageStrings = (List<String>) JSON.parse(communityInfo.getCommon().getCommonImagePath2());
                        imagePaths.addAll(imageStrings);
                    }
                } catch (Exception e) {
                }
                list.setAdapter(new CommonAdapter<String>(CommunityCollegeInfoActivity.this, imagePaths, R.layout.layout_image_item) {
                    @Override
                    public void convert(ViewHolder holder, String s) {
                        Glide.with(mContext).load(s).error(R.drawable.ic_error).into((ImageView) holder.getView(R.id.imageView));
                    }
                });

                String[] results = getResources().getStringArray(R.array.community_college);
                if ("A".equals(communityInfo.getCommon().getCommonType())) {
                    college.setText(results[0]);
                } else if ("B".equals(communityInfo.getCommon().getCommonType())) {
                    college.setText(results[1]);
                } else if ("C".equals(communityInfo.getCommon().getCommonType())) {
                    college.setText(results[2]);
                } else if ("D".equals(communityInfo.getCommon().getCommonType())) {
                    college.setText(results[3]);
                }
                break;

            case REPLYS:
                OkHttpUtils.post().url(UrlUtil.REPLYS)
                        .addParams("commonId", getIntent().getStringExtra("commonId"))
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                try {
                                    if (0 == (int) msg.obj) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } catch (Exception e) {
                                    return true;
                                }
                            }

                            @Override
                            public Context getContext() {
                                return CommunityCollegeInfoActivity.this;
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
                                            List<Reply> communities = new Gson().fromJson(jsonObject.getString("obj"), new TypeToken<List<Reply>>() {
                                            }.getType());
                                            if (communities.size() == pageSize) {
                                                totalPages++;
                                            }
                                            replyList.addAll(communities);
                                            if (replyList.size() > 0) {
                                                hint_num.setVisibility(View.VISIBLE);
                                            }

                                            mHandler.sendEmptyMessage(SET_REPLYS);
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        MyDialog.getInstance(CommunityCollegeInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(CommunityCollegeInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                                refresh_layout.endRefreshing();
                                refresh_layout.endLoadingMore();
                            }
                        });
                break;

            case SET_REPLYS:
                if (null == adapter) {
                    adapter = new CommonAdapter<Reply>(CommunityCollegeInfoActivity.this, replyList, R.layout.layout_item_community_evaluation) {
                        @Override
                        public void convert(ViewHolder holder, Reply reply) {
                            Glide.with(mContext).load(reply.getUser().getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                            holder.setText(R.id.name, reply.getUser().getUserNickName());
                            try {
                                holder.setText(R.id.time, RelativeDateFormat.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reply.getReply().getReplyTime())));
                            } catch (ParseException e) {
                            }
                            holder.setText(R.id.mes, reply.getReply().getReplyText());
                        }
                    };
                    listview_evaluate.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;

            case ADDREPLY:
                OkHttpUtils.post().url(UrlUtil.ADDREPLY)
                        .addParams("commonId", getIntent().getStringExtra("commonId"))
                        .addParams("replyText", mes.getText().toString())
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return false;
                            }

                            @Override
                            public Context getContext() {
                                return CommunityCollegeInfoActivity.this;
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
                                        Toast.makeText(CommunityCollegeInfoActivity.this, "发送成功...", Toast.LENGTH_SHORT).show();
                                        mes.setText("");
                                    } else {
                                        MyDialog.getInstance(CommunityCollegeInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(CommunityCollegeInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_community_college_info;
    }

    @Override
    public void initView(View view) {
        setTopTitle("");
        top_right.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.ic_share).into(top_right);

        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(mes, 0, 0.03f, 0, 1);
        initRefreshLayout();

        mHandler.sendEmptyMessage(GETCOMMON);
        mHandler.sendMessage(mHandler.obtainMessage(REPLYS, 0));
    }

    @OnClick({R.id.top_right, R.id.send})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.top_right:
//                Toast.makeText(this, "分享切", Toast.LENGTH_SHORT).show();
                if (null == communityInfo) {
                    return;
                }
                Intent intent = new Intent(this, CommunityShareActivity.class);
                String imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.zoomBitmap(BitmapUtil.captureScreen(this), 400, 400));
                intent.putExtra("image", imageString);
                intent.putExtra("communityInfo", communityInfo);
//                intent.putExtra("userId", communityInfo.getUser().getUserId());
//                intent.putExtra("name", communityInfo.getUser().getUserNickName());
//                intent.putExtra("commonType", communityInfo.getCommon().getCommonType());
                startActivity(intent);
                break;
            case R.id.send:
                if (!mes.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(ADDREPLY);
//                Toast.makeText(this, "发送切", Toast.LENGTH_SHORT).show();
                break;
        }
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
        replyList.clear();
        mHandler.sendMessage(mHandler.obtainMessage(REPLYS, 0));
        refresh_layout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            mHandler.sendMessage(mHandler.obtainMessage(REPLYS, 1));
            return true;
        } else {
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }
}
