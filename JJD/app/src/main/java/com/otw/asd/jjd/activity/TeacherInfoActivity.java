package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.RefreshUtil;
import com.asd.android.util.TimeUtil;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.MyRadioGroup;
import com.asd.util.JsonMapper;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hedgehog.ratingbar.RatingBar;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseFragmentActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Comment;
import com.otw.asd.jjd.entity.CurriculumOrderParams;
import com.otw.asd.jjd.entity.TeacherCurriculum;
import com.otw.asd.jjd.entity.TeacherInfo;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.fragment.TeacherCurriculumFragment;
import com.otw.asd.jjd.util.RelativeDateFormat;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 代理招生
 * Created by Administrator on 2016/3/31.
 */
public class TeacherInfoActivity extends BaseFragmentActivity {

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.age)
    TextView age;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.star_num)
    RatingBar star_num;
    @Bind(R.id.total)
    TextView total;

    @Bind(R.id.rbGroup_time)
    MyRadioGroup rbGroup;
    @Bind(R.id.day_today)
    TextView day_today;
    @Bind(R.id.week_today)
    TextView week_today;
    @Bind(R.id.day_nextday)
    TextView day_nextday;
    @Bind(R.id.week_nextday)
    TextView week_nextday;

    @Bind(R.id.num)
    TextView num;

    //    @Bind(R.id.listview)
//    ListView listview;
    @Bind(R.id.listview_mes)
    ListView listview_mes;

    @Bind(R.id.line_mes)
    LinearLayout line_mes;

    ViewCompoundDrawableUtil viewCompoundDrawableUtil;

    List<Comment.CommentAndUsersBean> commentAndUsersBeanList = new ArrayList<>();
    CommonAdapter<Comment.CommentAndUsersBean> adapter;

    TeacherInfo teacherInfo;

    TeacherCurriculumFragment todayTeacherCurriculumFragment, nextdayTeacherCurriculumFragment;

    int pageSize = 5;
    int totalPages = 1;
    int currentPage = 1;
    Comment comment;

    final int GETTEACHERDETAIL = 0;
    final int SET_GETTEACHERDETAIL = 1;
    final int COMMENTLISTBYUSER = 2;
    final int SET_COMMENTLISTBYUSER = 3;
    final int COURSEORDERAPPLY = 4;

    User user = null;
    String teacherId, courseOrderDate;
    /**
     * 当前选中天数类  0：今天 其他：明天
     */
    int dayType = 0;

    CurriculumOrderParams curriculumOrderParams;

    /**
     * 是否约束优惠券
     */
    boolean isCheckVoucher = true;

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(GETTEACHERDETAIL);
        mHandler.sendEmptyMessage(COMMENTLISTBYUSER);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseOrderDate() {
        return courseOrderDate;
    }

    public void setCourseOrderDate(String courseOrderDate) {
        this.courseOrderDate = courseOrderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case GETTEACHERDETAIL:
                OkHttpUtils.post().url(UrlUtil.GETTEACHERDETAIL)
                        .addParams("teacherId", getTeacherId())
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
                                            teacherInfo = new Gson().fromJson(jsonObject.getString("obj"), TeacherInfo.class);

                                            mHandler.sendEmptyMessage(SET_GETTEACHERDETAIL);
                                        } catch (JsonSyntaxException e) {//没有符合教练
                                            Toast.makeText(TeacherInfoActivity.this, getResString(R.string.json_error), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        MyDialog.getInstance(TeacherInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(TeacherInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case SET_GETTEACHERDETAIL:
                try {
                    name.setText(teacherInfo.getUserAndTeacher().getUserRealName());
                    age.setText(teacherInfo.getUserAndTeacher().getUserAge() + "");
                    time.setText(teacherInfo.getUserAndTeacher().getTeacherTeachYear() + "年");
                    address.setText(teacherInfo.getSite().getSiteName());

                    Drawable halfDrawable = getResources().getDrawable(R.mipmap.ic_star_half);
                    star_num.setStarHalfDrawable(halfDrawable);
                    star_num.setStar(teacherInfo.getUserAndTeacher().getTeacherLevel());

                    total.setText(teacherInfo.getCourseOrderTotalCount() + "");

                    Glide.with(this).load(teacherInfo.getUserAndTeacher().getUserHeadPath()).error(R.mipmap.ic_person).into(image);
                } catch (Exception e) {
                }
                break;

            case COMMENTLISTBYUSER:
                OkHttpUtils.post().url(UrlUtil.COMMENTLISTBYUSER)
                        .addParams("everyPage", pageSize + "")
                        .addParams("currentPage", currentPage + "")
                        .addParams("userId", getIntent().getStringExtra("userId"))
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(false);
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
                                        } catch (JsonSyntaxException e) {//没有评论
                                        }
                                    } else {
                                        MyDialog.getInstance(TeacherInfoActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (JSONException e) {
                                    MyDialog.getInstance(TeacherInfoActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case SET_COMMENTLISTBYUSER:
                if (comment.getCommentAndUsers().size() > 0) {
                    line_mes.setVisibility(View.VISIBLE);
                    num.setText(comment.getCommentTotalCount() + "条");
                    commentAndUsersBeanList.clear();
                    commentAndUsersBeanList.addAll(comment.getCommentAndUsers());
                    if (null == adapter) {
                        listview_mes.setAdapter(adapter = new CommonAdapter<Comment.CommentAndUsersBean>(this, commentAndUsersBeanList, R.layout.layout_item_teacher_mes) {
                            Drawable halfDrawable = getResources().getDrawable(R.mipmap.ic_star_half);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            @Override
                            public void convert(ViewHolder holder, Comment.CommentAndUsersBean bean) {
//                                if (null == viewCompoundDrawableUtil) {
//                                    viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(TeacherInfoActivity.this);
//                                }
//                                viewCompoundDrawableUtil.initTextViewCompoundDrawable((TextView) holder.getView(R.id.laud), 0, (int) (GestureUtils.getScreenPix(TeacherInfoActivity.this).widthPixels * 0.05));
//                                viewCompoundDrawableUtil.initTextViewCompoundDrawable((TextView) holder.getView(R.id.reply), 0, (int) (GestureUtils.getScreenPix(TeacherInfoActivity.this).widthPixels * 0.05));

                                try {
                                    holder.setText(R.id.name, bean.getUserNickName());
                                    RatingBar bar = holder.getView(R.id.star_num);
                                    bar.setStarHalfDrawable(halfDrawable);
                                    bar.setStar(bean.getCommentEvaScore());
                                    holder.setText(R.id.mes, bean.getCommentContent());
                                    holder.setText(R.id.pass_time, RelativeDateFormat.format(format.parse(bean.getCommentTime())));
                                    Logger.e(bean.getUserHeadPath());
                                    Glide.with(mContext).load(bean.getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                                } catch (Exception e) {
                                }
                            }
                        });
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
                break;

            case COURSEORDERAPPLY:
                Logger.e(JsonMapper.toNormalJson(curriculumOrderParams));

                Intent intent = new Intent(TeacherInfoActivity.this, MakeOrderActivity.class);
                if (0 == dayType) {
                    intent.putExtra("day", day_today.getText().toString());
                    intent.putStringArrayListExtra("courseOrderIds", (ArrayList<String>) todayTeacherCurriculumFragment.getCourseOrderIds());
                    if (todayTeacherCurriculumFragment.getPersonNumbers().size() > 1) {//多个课程不约束
                        isCheckVoucher = false;
                    } else {
                        try {
                            isCheckVoucher = todayTeacherCurriculumFragment.getPersonNumbers().get(todayTeacherCurriculumFragment.getCourseOrderIds().get(0)) > 2 ? true : false;//一个课程 人数多于2人以上
                        } catch (Exception e) {
                        }
                    }
                    intent.putExtra("isCheckVoucher", isCheckVoucher);
                } else {
                    intent.putExtra("day", day_nextday.getText().toString());
                    intent.putStringArrayListExtra("courseOrderIds", (ArrayList<String>) nextdayTeacherCurriculumFragment.getCourseOrderIds());
                    if (nextdayTeacherCurriculumFragment.getPersonNumbers().size() > 1) {
                        isCheckVoucher = false;
                    } else {
                        try {
                            isCheckVoucher = nextdayTeacherCurriculumFragment.getPersonNumbers().get(nextdayTeacherCurriculumFragment.getCourseOrderIds().get(0)) > 2 ? true : false;
                        } catch (Exception e) {
                        }
                    }
                    intent.putExtra("isCheckVoucher", isCheckVoucher);
                }
                intent.putExtra("data", curriculumOrderParams);
                startActivity(intent);

                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_teacher_info;
    }

    @Override
    public void initView(View view) {
        AppManager.addActivity(this);
        setTopTitle("教练");

//        user = (User) LocalCache.get(this).getAsObject(LocalCacheKey.LOCAL_USER);
        user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));
        setTeacherId(getIntent().getStringExtra("teacherId"));
        initTeacherTimeMenu();

    }

    /**
     * 教员时间选项
     */
    private void initTeacherTimeMenu() {
        day_today.setText(TimeUtil.getInstall().getCurrentTimeFormat("yyyy-MM-dd"));
        week_today.setText(TimeUtil.getInstall().getWeekOfDate(0));
        day_nextday.setText(TimeUtil.getInstall().getTime("yyyy-MM-dd", 1));
        week_nextday.setText(TimeUtil.getInstall().getWeekOfDate(1));
        rbGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                selectBar(checkedId);
            }
        });
        selectBar(R.id.rb_today);
    }

    public void selectBar(int checkedId) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager
                .beginTransaction();
        hideFragment(beginTransaction);
        switch (checkedId) {
            case R.id.rb_today:
                dayType = 0;
                setCourseOrderDate(day_today.getText().toString());
                if (null == todayTeacherCurriculumFragment) {
                    todayTeacherCurriculumFragment = new TeacherCurriculumFragment();
                    beginTransaction.add(R.id.frame, todayTeacherCurriculumFragment);
                } else {
                    beginTransaction.show(todayTeacherCurriculumFragment);
                }
                break;

            case R.id.rb_nextday:
                dayType = 1;
                setCourseOrderDate(day_nextday.getText().toString());
                if (null == nextdayTeacherCurriculumFragment) {
                    nextdayTeacherCurriculumFragment = new TeacherCurriculumFragment();
                    beginTransaction.add(R.id.frame, nextdayTeacherCurriculumFragment);
                } else {
                    beginTransaction.show(nextdayTeacherCurriculumFragment);
                }
                break;
        }
        beginTransaction.commit();

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, 0);
            }
        });
    }


    /**
     * 隐藏Fragment
     *
     * @param beginTransaction
     */
    private void hideFragment(FragmentTransaction beginTransaction) {
        if (null != todayTeacherCurriculumFragment) {
            beginTransaction.hide(todayTeacherCurriculumFragment);
        }
        if (null != nextdayTeacherCurriculumFragment) {
            beginTransaction.hide(nextdayTeacherCurriculumFragment);
        }
    }

    @OnClick({R.id.submit, R.id.mes_more})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.submit:
                if (0 == dayType) {
                    if (0 == todayTeacherCurriculumFragment.getCourseOrderIds().size()) {//为预约课程
                        Toast.makeText(this, "您还未预约任何课程", Toast.LENGTH_SHORT).show();
                    } else {
//                        courseOrderIds = JsonMapper.toNormalJson(todayTeacherCurriculumFragment.getCourseOrderIds());
                        getCurriculumOrderParams(todayTeacherCurriculumFragment);

                        mHandler.sendEmptyMessage(COURSEORDERAPPLY);
                    }
                } else {
                    if (0 == nextdayTeacherCurriculumFragment.getCourseOrderIds().size()) {//为预约课程
                        Toast.makeText(this, "您还未预约任何课程", Toast.LENGTH_SHORT).show();
                    } else {
//                        courseOrderIds = JsonMapper.toNormalJson(nextdayTeacherCurriculumFragment.getCourseOrderIds());
                        getCurriculumOrderParams(nextdayTeacherCurriculumFragment);

                        mHandler.sendEmptyMessage(COURSEORDERAPPLY);
                    }
                }
                break;
            case R.id.mes_more:
                Intent intent = new Intent(this, TeacherCommentsActivity.class);
                intent.putExtra("userId", getIntent().getStringExtra("userId"));
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取课程参数
     *
     * @param curriculumFragment
     */
    private void getCurriculumOrderParams(TeacherCurriculumFragment curriculumFragment) {
        curriculumOrderParams = new CurriculumOrderParams();
        curriculumOrderParams.setCourseOrderS2UnitPrice(curriculumFragment.getTeacherCurriculum().getObj().getCourseOrderS2UnitPrice());
        curriculumOrderParams.setCourseOrderS3UnitPrice(curriculumFragment.getTeacherCurriculum().getObj().getCourseOrderS3UnitPrice());
        TeacherCurriculum.ObjBean.DatasBean adatasBean = curriculumFragment.getTeacherCurriculum().getObj().getDatas().get(0);
        curriculumOrderParams.setAmSiteId(adatasBean.getData().getSiteId());
        curriculumOrderParams.setAmSiteName(adatasBean.getData().getSiteName());
        curriculumOrderParams.setAmSubject(adatasBean.getData().getSubject());
        curriculumOrderParams.setAmTimeType(adatasBean.getData().getTimeType());
        curriculumOrderParams.setAmType(adatasBean.getData().getType());
        curriculumOrderParams.setAmOrders(curriculumFragment.getAmOrders());
        TeacherCurriculum.ObjBean.DatasBean pdatasBean = curriculumFragment.getTeacherCurriculum().getObj().getDatas().get(1);
        curriculumOrderParams.setPmSiteId(pdatasBean.getData().getSiteId());
        curriculumOrderParams.setPmSiteName(pdatasBean.getData().getSiteName());
        curriculumOrderParams.setPmSubject(pdatasBean.getData().getSubject());
        curriculumOrderParams.setPmTimeType(pdatasBean.getData().getTimeType());
        curriculumOrderParams.setPmType(pdatasBean.getData().getType());
        curriculumOrderParams.setPmOrders(curriculumFragment.getPmOrders());
    }
}
