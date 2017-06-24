package com.otw.asd.jjd.fragment;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.TeacherInfoActivity;
import com.otw.asd.jjd.activity.UserInfoActivity;
import com.otw.asd.jjd.base.BaseFragment;
import com.otw.asd.jjd.entity.CurriculumOrderParams;
import com.otw.asd.jjd.entity.TeacherCurriculum;
import com.otw.asd.jjd.util.UrlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/30.
 */
public class TeacherCurriculumFragment extends BaseFragment {

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

    final int COURSEORDERLISTREALBYTEACHERANDDATE = 0;
    final int SET_COURSEORDERLISTREALBYTEACHERANDDATE = 1;
    TeacherCurriculum teacherCurriculum;

    List<TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean> amCourseOrdersBeen = new ArrayList<>();
    CommonAdapter<TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean> amCommonAdapter;
    List<TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean> pmCourseOrdersBeen = new ArrayList<>();
    CommonAdapter<TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean> pmCommonAdapter;

    TeacherInfoActivity activity;

    /**
     * 预约课程ID
     */
    List<String> courseOrderIds = new ArrayList<>();

    //下单时间
    List<CurriculumOrderParams.OrderBean> amOrders = new ArrayList<>();
    List<CurriculumOrderParams.OrderBean> pmOrders = new ArrayList<>();
    Map<String, CurriculumOrderParams.OrderBean> map = new HashMap<>();

    /**
     * 当前选中课程人数
     */
    Map<String, Integer> personNumbers = new HashMap<>();

    /**
     * 当前选中课程人数
     */
    public Map<String, Integer> getPersonNumbers() {
        return personNumbers;
    }

    public List<String> getCourseOrderIds() {
        return courseOrderIds;
    }

    public void setCourseOrderIds(List<String> courseOrderIds) {
        this.courseOrderIds = courseOrderIds;
    }

    public TeacherCurriculum getTeacherCurriculum() {
        return teacherCurriculum;
    }

    public void setTeacherCurriculum(TeacherCurriculum teacherCurriculum) {
        this.teacherCurriculum = teacherCurriculum;
    }

    public List<CurriculumOrderParams.OrderBean> getPmOrders() {
        return pmOrders;
    }

    public void setPmOrders(List<CurriculumOrderParams.OrderBean> pmOrders) {
        this.pmOrders = pmOrders;
    }

    public List<CurriculumOrderParams.OrderBean> getAmOrders() {
        return amOrders;
    }

    public void setAmOrders(List<CurriculumOrderParams.OrderBean> amOrders) {
        this.amOrders = amOrders;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case COURSEORDERLISTREALBYTEACHERANDDATE:
                OkHttpUtils.post().url(UrlUtil.COURSEORDERLISTREALBYTEACHERANDDATE)
                        .addParams("teacherId", activity.getTeacherId())
                        .addParams("courseOrderDate", activity.getCourseOrderDate())
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsShowLoadingDialog() {
                                setShowLoadingDialolg(true);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    teacherCurriculum = new Gson().fromJson(response, TeacherCurriculum.class);
                                    if (teacherCurriculum.isSuccess()) {
                                        mHandler.sendEmptyMessage(SET_COURSEORDERLISTREALBYTEACHERANDDATE);
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(mContext).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case SET_COURSEORDERLISTREALBYTEACHERANDDATE:
                for (int i = 0; i < teacherCurriculum.getObj().getDatas().size(); i++) {
                    TeacherCurriculum.ObjBean.DatasBean datasBean = teacherCurriculum.getObj().getDatas().get(i);
                    if ("am".equals(datasBean.getData().getTimeType()) || 0 == i) {//am 0 == i
                        am_subject.setText(datasBean.getData().getSubject());
                        am_type.setText(datasBean.getData().getType());
                        am_address.setText(datasBean.getData().getSiteName());
                        amCourseOrdersBeen.clear();
                        amCourseOrdersBeen.addAll(datasBean.getData().getCourseOrders());
                        if (null == amCommonAdapter) {
                            amCommonAdapter = new CommonAdapter<TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean>(mContext, amCourseOrdersBeen, R.layout.layout_item_teacher_time) {
                                @Override
                                public void convert(ViewHolder holder, TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean) {
                                    holder.setText(R.id.time_start, courseOrdersBean.getCourseOrderBeginTime() + ":00");
                                    holder.setText(R.id.time_end, courseOrdersBean.getCourseOrderEndTime() + ":00");
                                    setImageNumber(holder, courseOrdersBean, 0);
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
                            pmCommonAdapter = new CommonAdapter<TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean>(mContext, pmCourseOrdersBeen, R.layout.layout_item_teacher_time) {
                                @Override
                                public void convert(ViewHolder holder, TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean) {
                                    holder.setText(R.id.time_start, courseOrdersBean.getCourseOrderBeginTime() + ":00");
                                    holder.setText(R.id.time_end, courseOrdersBean.getCourseOrderEndTime() + ":00");
                                    setImageNumber(holder, courseOrdersBean, 1);
                                }
                            };
                            pm_listview.setAdapter(pmCommonAdapter);
                        } else {
                            pmCommonAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_teacher_curriculum;
    }

    @Override
    public void initView(View view) {
        activity = (TeacherInfoActivity) mContext;

        mHandler.sendEmptyMessage(COURSEORDERLISTREALBYTEACHERANDDATE);
    }

    /**
     * 设置item头像
     *
     * @param holder
     * @param courseOrdersBean
     * @param type             0：am  其他：pm
     */
    public void setImageNumber(ViewHolder holder, final TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean, final int type) {
        ImageView item_one = holder.getView(R.id.item_one);
        ImageView item_two = holder.getView(R.id.item_two);
        ImageView item_three = holder.getView(R.id.item_three);
        ImageView item_four = holder.getView(R.id.item_four);

        Glide.with(this).load(R.mipmap.ic_teacher_bespeak_bak_light).into(item_one);
        Glide.with(this).load(R.mipmap.ic_teacher_bespeak_bak_light).into(item_two);
        Glide.with(this).load(R.mipmap.ic_teacher_bespeak_bak_light).into(item_three);
        Glide.with(this).load(R.mipmap.ic_teacher_bespeak_bak_light).into(item_four);
        item_one.setVisibility(View.INVISIBLE);
        item_two.setVisibility(View.INVISIBLE);
        item_three.setVisibility(View.INVISIBLE);
        item_four.setVisibility(View.INVISIBLE);
        item_one.setTag(R.id.teacher_curriculum_id, null);
        item_two.setTag(R.id.teacher_curriculum_id, null);
        item_three.setTag(R.id.teacher_curriculum_id, null);
        item_four.setTag(R.id.teacher_curriculum_id, null);

        for (int j = 1; j <= courseOrdersBean.getPeopleNumber(); j++) {
            if (1 == j) {
//                holder.setVisible(R.id.item_one, true);
                item_one.setVisibility(View.VISIBLE);
            } else if (2 == j) {
//                holder.setVisible(R.id.item_two, true);
                item_two.setVisibility(View.VISIBLE);
            } else if (3 == j) {
//                holder.setVisible(R.id.item_three, true);
                item_three.setVisibility(View.VISIBLE);
            } else if (4 == j) {
//                holder.setVisible(R.id.item_four, true);
                item_four.setVisibility(View.VISIBLE);
            }
        }

        List<TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean.User> users = courseOrdersBean.getUsers();
        if (null != users) {
            for (int i = 0; i < users.size(); i++) {
                if (0 == i) {
                    item_one.setTag(R.id.teacher_curriculum_id, users.get(i).getUserId());
                    Glide.with(this).load(users.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into(item_one);
                } else if (1 == i) {
                    item_two.setTag(R.id.teacher_curriculum_id, users.get(i).getUserId());
                    Glide.with(this).load(users.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into(item_two);
                } else if (2 == i) {
                    item_three.setTag(R.id.teacher_curriculum_id, users.get(i).getUserId());
                    Glide.with(this).load(users.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into(item_three);
                } else if (3 == i) {
                    item_four.setTag(R.id.teacher_curriculum_id, users.get(i).getUserId());
                    Glide.with(this).load(users.get(i).getUserHeadPath()).error(R.mipmap.ic_person).into(item_four);
                }
            }
        }


        item_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItmeValue(courseOrdersBean, v, type);
            }
        });
        item_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItmeValue(courseOrdersBean, v, type);
            }
        });
        item_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItmeValue(courseOrdersBean, v, type);
            }
        });
        item_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItmeValue(courseOrdersBean, v, type);
            }
        });
    }

    /**
     * 获取当前ITEM是否有效
     *
     * @param courseOrdersBean
     * @param view
     * @param type             0：am  其他：pm
     */
    private void checkItmeValue(TeacherCurriculum.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean, View view, int type) {
//        Logger.e(view.getTag(R.id.teacher_curriculum_id).toString());

        Logger.e(view.getTag(R.id.teacher_curriculum_id) + "==============" + activity.getUser().getObj().getUser().getUserId());
        if (null != view.getTag(R.id.teacher_curriculum_id)) {//当前有人选中
            if (!activity.getUser().getObj().getUser().getUserId().equals(view.getTag(R.id.teacher_curriculum_id).toString())) {//非当前用户
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                intent.putExtra("flow", "other");
                intent.putExtra("userId", view.getTag(R.id.teacher_curriculum_id).toString());
                startActivity(intent);
                return;
            } else {//当前用户
                if (!courseOrderIds.contains(courseOrdersBean.getCourseOrderId())) {//已预约过
                    return;
                } else {
                    courseOrderIds.remove(courseOrdersBean.getCourseOrderId());
                    view.setTag(R.id.teacher_curriculum_id, null);

                    if (0 == type) {
                        amOrders.remove(map.get(courseOrdersBean.getCourseOrderId()));
                    } else {
                        pmOrders.remove(map.get(courseOrdersBean.getCourseOrderId()));
                    }
                    personNumbers.remove(courseOrdersBean.getCourseOrderId());
                    Glide.with(this).load(R.mipmap.ic_teacher_bespeak_bak_light).error(R.mipmap.ic_person).into((ImageView) view);
                }
            }
        } else {
            if ("501".equals(courseOrdersBean.getValidateCode())) {
                Toast.makeText(mContext, "该时间不能预约...", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!courseOrderIds.contains(courseOrdersBean.getCourseOrderId())) {//未选择
                if ("500".equals(courseOrdersBean.getValidateCode())) {
                    Toast.makeText(mContext, "该时间已预约...", Toast.LENGTH_SHORT).show();
                    return;
                }
                courseOrderIds.add(courseOrdersBean.getCourseOrderId());
                view.setTag(R.id.teacher_curriculum_id, activity.getUser().getObj().getUser().getUserId());

                CurriculumOrderParams.OrderBean orderBean = new CurriculumOrderParams.OrderBean();
                orderBean.setTimeString(courseOrdersBean.getCourseOrderBeginTime() + ":00 - " + courseOrdersBean.getCourseOrderEndTime() + ":00");
                orderBean.setOrderId(courseOrdersBean.getCourseOrderId());
                orderBean.setPrice(courseOrdersBean.getCourseOrderPrice());
                if (0 == type) {
                    amOrders.add(orderBean);
                    map.put(courseOrdersBean.getCourseOrderId(), orderBean);
                } else {
                    pmOrders.add(orderBean);
                    map.put(courseOrdersBean.getCourseOrderId(), orderBean);
                }
                personNumbers.put(courseOrdersBean.getCourseOrderId(), courseOrdersBean.getPeopleNumber());
                try {
                    Logger.e(activity.getUser().getObj().getUser().getUserHeadPath());
                    Glide.with(this).load(activity.getUser().getObj().getUser().getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) view);
                } catch (Exception e) {
                }
            } else {
                Toast.makeText(mContext, "您已选择该课程...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
