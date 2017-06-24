package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.RefreshUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.pop.PointerPopupWindow;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hedgehog.ratingbar.RatingBar;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.ChooseRote;
import com.otw.asd.jjd.entity.ChooseTeacher;
import com.otw.asd.jjd.entity.Teacher;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 选择教员
 * Created by Administrator on 2016/3/31.
 */
public class ChooseTeacherActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;

    @Bind(R.id.no_data)
    LinearLayout no_data;

    @Bind(R.id.rb_time)
    RadioButton rb_time;
    @Bind(R.id.rb_way)
    RadioButton rb_way;
    @Bind(R.id.rb_type)
    RadioButton rb_type;
    @Bind(R.id.rb_address)
    RadioButton rb_address;
    @Bind(R.id.gridview)
    GridView gridview;
//    @Bind(R.id.listview)
//    ListView listview;

    @Bind(R.id.recommend)
    TextView recommend;//查看推荐教练

    @Bind(R.id.no_data_layout)
    ViewStub no_data_layout;
    View no_data_layout_view = null;


    PointerPopupWindow timePop = null, stylePop = null, typePop = null;

    String timeParam = "", styleParam = "", typeParm = "", lineParm = "";

    /**
     * 当前查找类型  0：匹配  1：推荐
     */
    int currentType = 0;
    String courseSubject;//当前科目
    String siteId = "";//场地或线路ID

    int pageSize = 6;
    int totalPages = 1;
    int currentPage = 1;
    @Bind(R.id.load_more)
    TextView load_more;
    final int TEACHERLISTBYCOURSEORDER = 0;
    final int SET_TEACHERLISTBYCOURSEORDER = 1;
    final int TEACHERLISTBYRECOMMEND = 2;

    List<Teacher> teachersBeen = new ArrayList<>();
    CommonAdapter<Teacher> commonAdapter = null;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        Logger.e(currentPage + "----------");
        switch (msg.what) {
            case TEACHERLISTBYCOURSEORDER:
                currentType = 0;
                final int type = (int) msg.obj;
                OkHttpUtils.post().url(UrlUtil.TEACHERLISTBYCOURSEORDER)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("siteId", siteId)
                        .addParams("courseOrderBeginTime", getResString(R.string.bespeak_teacher_no_check).equals(timeParam) || "".equals(timeParam) ? "" : timeParam.substring(0, 2))//课程开始时间
                        .addParams("courseOrderEndTime", getResString(R.string.bespeak_teacher_no_check).equals(timeParam) || "".equals(timeParam) ? "" : timeParam.substring(6, 8))//课程结束时间
                        .addParams("isMany", getResString(R.string.bespeak_teacher_no_check).equals(styleParam) || "".equals(styleParam) ? "" : ("单人".equals(styleParam) ? "N" : "Y"))//是否多人
                        .addParams("courseType", getResString(R.string.bespeak_teacher_no_check).equals(typeParm) || "".equals(typeParm) ? "" : typeParm)//课程类型
//                        .addParams("courseSubject", courseSubject)//报名科目
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
                                            ChooseTeacher chooseTeacher = new Gson().fromJson(jsonObject.getString("obj"), ChooseTeacher.class);

                                            totalPages = RefreshUtil.getTotalPages(chooseTeacher.getTotalCount(), pageSize);
                                            teachersBeen.addAll(chooseTeacher.getUserAndTeachers());
                                            mHandler.sendEmptyMessage(SET_TEACHERLISTBYCOURSEORDER);
                                        } catch (JsonSyntaxException e) {//没有符合教练
                                            teachersBeen.clear();
                                            totalPages = 1;
                                            load_more.setVisibility(View.GONE);
                                            if (null != commonAdapter) {
                                                commonAdapter.notifyDataSetChanged();
                                            }
                                            Toast.makeText(ChooseTeacherActivity.this, "提示：没有完全符合您找的教练", Toast.LENGTH_LONG).show();
                                            recommend.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        MyDialog.getInstance(ChooseTeacherActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(ChooseTeacherActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
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
                                Toast.makeText(ChooseTeacherActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case SET_TEACHERLISTBYCOURSEORDER:
                if (currentPage == totalPages) {
                    load_more.setVisibility(View.GONE);
                } else {
                    if (load_more.getVisibility() == View.GONE) {
                        load_more.setVisibility(View.VISIBLE);
                    }
                }
                if (null == commonAdapter) {
                    commonAdapter = new CommonAdapter<Teacher>(ChooseTeacherActivity.this, teachersBeen, R.layout.layout_item_bespeak_teacher) {
                        Drawable halfDrawable = getResources().getDrawable(R.mipmap.ic_star_half);

                        @Override
                        public void convert(ViewHolder holder, final Teacher teacher) {
                            try {
                                holder.setVisible(R.id.authentication, "Y".equals(teacher.getIsValidate()) ? true : false);
                                holder.setText(R.id.name, teacher.getUserRealName());
                                holder.setText(R.id.years, teacher.getTeacherTeachYear());

                                RatingBar bar = holder.getView(R.id.ratingbar);
                                bar.setStarHalfDrawable(halfDrawable);
                                bar.setStar(teacher.getTeacherLevel());
                                holder.setOnClickListener(R.id.line_item, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        if (null == LocalCache.get(mContext).getAsObject(LocalCacheKey.LOCAL_USER)) {//未登录
                                        if ("".equals(ShareUtil.getInstance(ChooseTeacherActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {//未登录
                                            Intent intent = new Intent(mContext, LoginActivity.class);
//                                            intent.putExtra("flow", "teacherCurriculum");
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(ChooseTeacherActivity.this, TeacherInfoActivity.class);
                                            intent.putExtra("teacherId", teacher.getTeacherId());
                                            intent.putExtra("userId", teacher.getUserId());
                                            startActivity(intent);
                                        }
                                    }
                                });
                                Glide.with(ChooseTeacherActivity.this).load(teacher.getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.image));
                            } catch (Exception e) {

                            }
                        }
                    };
                    gridview.setAdapter(commonAdapter);
                } else {
                    commonAdapter.notifyDataSetChanged();
                }
                recommend.setVisibility(View.GONE);
                break;
            case TEACHERLISTBYRECOMMEND:
                currentType = 1;
                final int type1 = (int) msg.obj;
                OkHttpUtils.post().url(UrlUtil.TEACHERLISTBYRECOMMEND)
                        .addParams("siteId", siteId)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("courseOrderBeginTime", getResString(R.string.bespeak_teacher_no_check).equals(timeParam) || "".equals(timeParam) ? "" : timeParam.substring(0, 2))//课程开始时间
                        .addParams("courseOrderEndTime", getResString(R.string.bespeak_teacher_no_check).equals(timeParam) || "".equals(timeParam) ? "" : timeParam.substring(6, 8))//课程结束时间
                        .addParams("isMany", getResString(R.string.bespeak_teacher_no_check).equals(styleParam) || "".equals(styleParam) ? "" : ("单人".equals(styleParam) ? "N" : "Y"))//是否多人
                        .addParams("courseType", getResString(R.string.bespeak_teacher_no_check).equals(typeParm) || "".equals(typeParm) ? "" : typeParm)//课程类型
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
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        try {
                                            ChooseTeacher chooseTeacher = new Gson().fromJson(jsonObject.getString("obj"), ChooseTeacher.class);
                                            if (null != no_data_layout_view) {
                                                no_data_layout_view.setVisibility(View.GONE);
                                            }
                                            totalPages = RefreshUtil.getTotalPages(chooseTeacher.getTotalCount(), pageSize);
                                            teachersBeen.addAll(chooseTeacher.getUserAndTeachers());
                                            mHandler.sendEmptyMessage(SET_TEACHERLISTBYCOURSEORDER);
                                        } catch (JsonSyntaxException e) {//没有符合教练
                                            if (null == no_data_layout_view) {
                                                no_data_layout_view = no_data_layout.inflate();
                                            } else {
                                                no_data_layout_view.setVisibility(View.VISIBLE);
                                            }

                                            teachersBeen.clear();
                                            if (null != commonAdapter) {
                                                commonAdapter.notifyDataSetChanged();
                                            }
                                            recommend.setVisibility(View.GONE);
                                        }
                                    } else {
                                        MyDialog.getInstance(ChooseTeacherActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(ChooseTeacherActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                                if (2 == type1) {
                                    refresh_layout.endLoadingMore();
                                }
                            }

                            @Override
                            public void getError(String error) {
                                super.getError(error);
                                if (2 == type1) {
                                    refresh_layout.endLoadingMore();
                                }
//                                Toast.makeText(ChooseTeacherActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (0 == requestCode && 0 == resultCode) {
                ChooseRote.SitesBean sitesBean = (ChooseRote.SitesBean) data.getSerializableExtra("data");
                rb_address.setText(sitesBean.getSiteName());
                siteId = sitesBean.getSiteId();
                teachersBeen.clear();
                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 0));
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_choose_teacher;
    }

    @Override
    public void initView(View view) {
        AppManager.addActivity(this);

        if ("2".equals(getIntent().getStringExtra("flow"))) {
            setTopTitle("科目二");
            courseSubject = "科目二";
            rb_address.setVisibility(View.GONE);
            setOptionItemWidth(2);
            siteId = getIntent().getStringExtra("siteId");

            mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 0));
        } else {
            setTopTitle("科目三");
            courseSubject = "科目三";
            rb_address.setVisibility(View.VISIBLE);
            setOptionItemWidth(3);
        }
        initRefreshLayout();

      /*  if (false) {
            no_data.setVisibility(View.VISIBLE);
        } else {
            no_data.setVisibility(View.GONE);
        }*/


        /*  gridview.setAdapter();

        listview.setAdapter(new CommonAdapter<String>(this, strings, R.layout.layout_item_bespeak_teacher_v) {
            @Override
            public void convert(ViewHolder holder, String s) {

            }
        });*/

        createTimePop(getTimeData(6, 20));
        createStylePop();
        createTypePop();

        if ("".equals(siteId)) {//科目三
            Intent intent = new Intent(this, ChooseRoteActivity.class);
            startActivityForResult(intent, 0);
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

    /**
     * 设置选项宽度
     *
     * @param subject 2: 1/3  否则：1/4
     */
    private void setOptionItemWidth(int subject) {
        int width = 0;
        if (2 == subject) {
            width = GestureUtils.getScreenPix(this).widthPixels / 3 + 1;
        } else {
            width = GestureUtils.getScreenPix(this).widthPixels / 4 + 1;
            rb_address.setWidth(width);
        }
        rb_time.setWidth(width);
        rb_way.setWidth(width);
        rb_type.setWidth(width);

    }


    /**
     * 获取时间集合
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    private List<String> getTimeData(int startTime, int endTime) {
        List<String> list = new ArrayList<>();
        list.add(getResString(R.string.bespeak_teacher_no_check));
        for (int i = startTime; i < endTime; i++) {
            String time = (i < 10 ? "0" : "") + i + ":00-" + (i + 1 < 10 ? "0" : "") + (i + 1) + ":00";
            list.add(time);
        }
        return list;
    }

    /**
     * 获取时间pop
     *
     * @param strings
     * @return
     */
    private PointerPopupWindow createTimePop(final List<String> strings) {
        if (null == timePop) {
            timePop = new PointerPopupWindow(this, GestureUtils.getScreenPix(this).widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
            View timePopView = getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ListView listview = (ListView) timePopView.findViewById(R.id.listview);
            if (strings.size() > 5) {
//                timePop.setHeight((int) (GestureUtils.getScreenPix(this).heightPixels * 0.25));
                listview.getLayoutParams().height = (int) (GestureUtils.getScreenPix(this).heightPixels * 0.37266);
            }
            listview.setAdapter(new CommonAdapter<String>(ChooseTeacherActivity.this, strings, R.layout.pop_layout_listview_item) {
                public void convert(ViewHolder holder, final String s) {
                    holder.setText(R.id.item, s);
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        public void onClick(View v) {
                            timePop.dismiss();
                            rb_time.setText(s);
                            if (!s.equals(timeParam)) {
                                timeParam = s;
                                teachersBeen.clear();
                                no_data.setVisibility(View.GONE);
                                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 0));
                            }
                        }
                    });

                }
            });
            timePop.setContentView(timePopView);
            timePop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
//            timePop.setOffsetMode(PointerPopupWindow.AlignMode.CENTER_FIX);
//            timePop.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_car));
//            timePop.setPointerImageRes(R.drawable.ic_car);
        }
        return timePop;
    }

    /**
     * 获取方式pop
     *
     * @return
     */
    private PointerPopupWindow createStylePop() {
        if (null == stylePop) {
            List<String> styles = new ArrayList<>();
            styles.add(getResString(R.string.bespeak_teacher_no_check));
            styles.add("单人");
            styles.add("拼单");
//            stylePop = new PointerPopupWindow(this, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.4));
            stylePop = new PointerPopupWindow(this, GestureUtils.getScreenPix(this).widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
            View timePopView = getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(new CommonAdapter<String>(ChooseTeacherActivity.this, styles, R.layout.pop_layout_listview_item) {
                public void convert(ViewHolder holder, final String s) {
                    holder.setText(R.id.item, s);
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        public void onClick(View v) {
                            stylePop.dismiss();
                            rb_way.setText(s);
                            if (!s.equals(styleParam)) {
                                styleParam = s;
                                teachersBeen.clear();
                                no_data.setVisibility(View.GONE);
                                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 0));
                            }
                        }
                    });
                }
            });
            stylePop.setContentView(timePopView);
            stylePop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
        }
        return stylePop;
    }

    /**
     * 学车类型
     *
     * @return
     */
    private PointerPopupWindow createTypePop() {
        if (null == typePop) {
            List<String> styles = new ArrayList<>();
            styles.add(getResString(R.string.bespeak_teacher_no_check));
            styles.add("C1");
            styles.add("C2");
//            stylePop = new PointerPopupWindow(this, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.4));
            typePop = new PointerPopupWindow(this, GestureUtils.getScreenPix(this).widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
            View timePopView = getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(new CommonAdapter<String>(ChooseTeacherActivity.this, styles, R.layout.pop_layout_listview_item) {
                public void convert(ViewHolder holder, final String s) {
                    holder.setText(R.id.item, s);
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        public void onClick(View v) {
                            typePop.dismiss();
                            rb_type.setText(s);
                            if (!s.equals(typeParm)) {
                                typeParm = s;
                                teachersBeen.clear();
                                no_data.setVisibility(View.GONE);
                                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 0));
                            }
                        }
                    });
                }
            });
            typePop.setContentView(timePopView);
            typePop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
        }
        return typePop;
    }

    @OnClick({R.id.rb_time, R.id.rb_way, R.id.rb_type, R.id.rb_address, R.id.recommend})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
//        if (line_address.getVisibility() == View.VISIBLE) {
//            line_address.setVisibility(View.GONE);
//            return;
//        }
        switch (view.getId()) {
            case R.id.rb_time:
                if (null != timePop) {
                    if (timePop.isShowing()) {
                        timePop.dismiss();
                    } else {
                        timePop.showAsPointer(view);
                    }
                }
                break;
            case R.id.rb_way:
                if (null != stylePop) {
                    if (stylePop.isShowing()) {
                        stylePop.dismiss();
                    } else {
                        stylePop.showAsPointer(view);
                    }
                }
                break;
            case R.id.rb_type:
                if (null != typePop) {
                    if (typePop.isShowing()) {
                        typePop.dismiss();
                    } else {
                        typePop.showAsPointer(view);
                    }
                }
                break;
            case R.id.rb_address:
//                line_address.setVisibility(line_address.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                Intent intent = new Intent(this, ChooseRoteActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.recommend:
                teachersBeen.clear();
//                recommend.setVisibility(View.GONE);
                currentPage = 1;
                no_data.setVisibility(View.VISIBLE);
                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYRECOMMEND, 0));
                break;
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        currentPage = 1;
        teachersBeen.clear();
        recommend.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);
        mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            if (no_data.getVisibility() == View.VISIBLE) {
                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYRECOMMEND, 2));
            } else {
                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 2));
            }
            return true;
        } else {
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
            load_more.setVisibility(View.GONE);
            refresh_layout.endLoadingMore();
            if (0 == currentType) {
                recommend.setVisibility(View.VISIBLE);
            }
            return false;
        }
    }

}
