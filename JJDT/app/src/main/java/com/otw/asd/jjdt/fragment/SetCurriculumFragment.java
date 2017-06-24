package com.otw.asd.jjdt.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.StringUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.BaseCustomDialog;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.pop.PointerPopupWindow;
import com.asd.util.JsonMapper;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.activity.ChooseRoteActivity;
import com.otw.asd.jjdt.activity.SetCurriculumActivity;
import com.otw.asd.jjdt.base.BaseFragment;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.ChooseRote;
import com.otw.asd.jjdt.entity.CurriculumList;
import com.otw.asd.jjdt.entity.CurriculumListParams;
import com.otw.asd.jjdt.entity.Site;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 课程安排
 * Created by ASD on 2015/12/30.
 */
public class SetCurriculumFragment extends BaseFragment {

    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.line_pm)
    LinearLayout line_pm;

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

    @Bind(R.id.commit)
    Button commit;

    @Bind(R.id.am_listview)
    ListView am_listview;
    @Bind(R.id.pm_listview)
    ListView pm_listview;

    /**
     * //0:科目二 其他:科目三
     */
    int amSubType = 0, pmSubType = 0;
    /**
     * //0:am 其他:pm
     */
    int clickSubect = 0, clickType = 0, clickAddress = 0;
    PointerPopupWindow subjectPop = null, typePop = null, addressPop = null;

    /**
     * 是否获取过条款
     */
    boolean getAmParams = false, getPmParams = false;

    SetCurriculumActivity activity;

    CurriculumList curriculumList;//获取到的课程
    List<CurriculumListParams> listParamses = new ArrayList<>();

    List<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> amCourseOrdersBeen = new ArrayList<>();
    CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> amCommonAdapter;
    List<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> pmCourseOrdersBeen = new ArrayList<>();
    CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean> pmCommonAdapter;

    List<Serializable> addresses = new ArrayList<>();
    CommonAdapter<Serializable> addressAdapter = null;

    final int ADDCOURSE = 0;
    final int GET_ADDCOURSE_PARAMS = 3;
    final int COURSEORDERLISTBYTEACHER = 1;
    final int SET_COURSEORDERLISTBYTEACHER = 2;

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
                                        mHandler.sendEmptyMessage(GET_ADDCOURSE_PARAMS);
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(mContext).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
            case SET_COURSEORDERLISTBYTEACHER:
                try {
                    if ("Y".equals(curriculumList.getObj().getIsUpdate())) {//更新过
                        commit.setVisibility(View.GONE);
                    } else {//未更新过
                        commit.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < curriculumList.getObj().getDatas().size(); i++) {
                        CurriculumList.ObjBean.DatasBean datasBean = curriculumList.getObj().getDatas().get(i);
                        if ("am".equals(datasBean.getData().getTimeType()) || 0 == i) {//am 0 == i
                            if ("Y".equals(curriculumList.getObj().getIsUpdate())) {//修改过的
                                am_subject.setOnClickListener(null);
                                am_type.setOnClickListener(null);
                                am_address.setOnClickListener(null);
                            }
                            if ("N".equals(curriculumList.getObj().getIsUpdate()) && !StringUtil.getInstance().isEmpty(datasBean.getData().getSubject())) {//未修改且安排了课程
                                am_subject.setOnClickListener(null);
                                am_type.setOnClickListener(null);
                                am_address.setOnClickListener(null);
                                getAmParams = true;
                            }
//                            am_subject.setText(StringUtil.getInstance().isEmpty(datasBean.getData().getSubject()) ? "科目二" : datasBean.getData().getSubject());
//                            am_type.setText(StringUtil.getInstance().isEmpty(datasBean.getData().getType()) ? "C1" : datasBean.getData().getType());
                            am_subject.setText(datasBean.getData().getSubject());
                            am_type.setText(datasBean.getData().getType());
                            am_address.setText(datasBean.getData().getSiteName());
                            am_address.setTag(datasBean.getData().getSiteId());
                            amCourseOrdersBeen = datasBean.getData().getCourseOrders();
                            am_listview.setAdapter(amCommonAdapter = new CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean>(mContext, amCourseOrdersBeen, R.layout.adapter_layout_curriculum_item) {
                                @Override
                                public void convert(ViewHolder holder, CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean) {
                                    holder.setText(R.id.time_start, courseOrdersBean.getCourseOrderBeginTime() + ":00");
                                    holder.setText(R.id.time_end, courseOrdersBean.getCourseOrderEndTime() + ":00");
                                    initItemImageClick(mContext, holder, courseOrdersBean, 0);
                                }
                            });
                        } else if ("pm".equals(datasBean.getData().getTimeType()) || 1 == i) {//pm 1 == i
                            if ("Y".equals(curriculumList.getObj().getIsUpdate())) {//修改过的
                                pm_subject.setOnClickListener(null);
                                pm_type.setOnClickListener(null);
                                pm_address.setOnClickListener(null);
                            }
                            if ("N".equals(curriculumList.getObj().getIsUpdate()) && !StringUtil.getInstance().isEmpty(datasBean.getData().getSubject())) {//未修改且安排了课程
                                pm_subject.setOnClickListener(null);
                                pm_type.setOnClickListener(null);
                                pm_address.setOnClickListener(null);
                                getPmParams = true;
                            }
                            pm_subject.setText(datasBean.getData().getSubject());
                            pm_type.setText(datasBean.getData().getType());
                            pm_address.setText(datasBean.getData().getSiteName());
                            pm_address.setTag(datasBean.getData().getSiteId());
                            pmCourseOrdersBeen = datasBean.getData().getCourseOrders();
                            pm_listview.setAdapter(pmCommonAdapter = new CommonAdapter<CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean>(mContext, pmCourseOrdersBeen, R.layout.adapter_layout_curriculum_item) {
                                @Override
                                public void convert(ViewHolder holder, CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean) {
                                    holder.setText(R.id.time_start, courseOrdersBean.getCourseOrderBeginTime() + ":00");
                                    holder.setText(R.id.time_end, courseOrdersBean.getCourseOrderEndTime() + ":00");
                                    initItemImageClick(mContext, holder, courseOrdersBean, 1);
                                }
                            });
                        }
                    }
                } catch (Exception e) {

                }
                break;
            case GET_ADDCOURSE_PARAMS:
                try {
                    for (int i = 0; i < curriculumList.getObj().getDatas().size(); i++) {//datas
                        CurriculumList.ObjBean.DatasBean.DataBean dataBean = curriculumList.getObj().getDatas().get(i).getData();
                        for (int j = 0; j < dataBean.getCourseOrders().size(); j++) {//data
                            CurriculumListParams curriculumListParams = new CurriculumListParams();
                            CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean = dataBean.getCourseOrders().get(j);
                            curriculumListParams.setCourseOrderBeginTime(activity.getRbTime() + " " + courseOrdersBean.getCourseOrderBeginTime());
                            curriculumListParams.setCourseOrderEndTime(activity.getRbTime() + " " + courseOrdersBean.getCourseOrderEndTime());
                            curriculumListParams.setPeopleNumber(courseOrdersBean.getPeopleNumber());
                            curriculumListParams.setCourseOrderId(courseOrdersBean.getCourseOrderId());
                            curriculumListParams.setCourseId(courseOrdersBean.getCourseId());

                            listParamses.add(curriculumListParams);
                        }
                    }
                } catch (Exception e) {
                }
//                Logger.e(JsonMapper.toNormalJson(listParamses));
                break;
            case ADDCOURSE:
                Map<String, String> map = new HashMap<>();
                map.put("am_subject", am_subject.getText().toString());
                map.put("am_type", am_type.getText().toString());
                map.put("am_address", (String) am_address.getTag());
                map.put("pm_subject", pm_subject.getText().toString());
                map.put("pm_type", pm_type.getText().toString());
                map.put("pm_address", (String) pm_address.getTag());
//                map.put("courseJSONStr", JsonMapper.toNormalJson(listParamses));
//                Logger.e(JsonMapper.toNormalJson(map));
                Logger.e(JsonMapper.toNormalJson(listParamses));
                OkHttpUtils.post().url(UrlUtil.ADDCOURSE)
                        .addParams("am_subject", am_subject.getText().toString())
                        .addParams("am_type", am_type.getText().toString())
                        .addParams("am_address", (String) am_address.getTag())
                        .addParams("pm_subject", pm_subject.getText().toString())
                        .addParams("pm_type", pm_type.getText().toString())
                        .addParams("pm_address", (String) pm_address.getTag())
                        .addParams("courseJSONStr", JsonMapper.toNormalJson(listParamses))
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
                                        activity.goCurriculumActivity();
                                    } else {
                                        MyDialog.getInstance(mContext).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(mContext).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
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
        activity = (SetCurriculumActivity) mContext;

        mHandler.sendEmptyMessage(COURSEORDERLISTBYTEACHER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (0 == requestCode && 0 == resultCode) {
                ChooseRote.SitesBean sitesBean = (ChooseRote.SitesBean) data.getSerializableExtra("data");
//                rb_address.setText(sitesBean.getSiteName());
//                siteId = sitesBean.getSiteId();
//                teachersBeen.clear();
//                mHandler.sendMessage(mHandler.obtainMessage(TEACHERLISTBYCOURSEORDER, 0));

                if (0 == clickAddress) {//上午
                    am_address.setText(sitesBean.getSiteName());
                    am_address.setTag(sitesBean.getSiteId());
                } else {//下午
                    pm_address.setText(sitesBean.getSiteName());
                    pm_address.setTag(sitesBean.getSiteId());
                }
            }
        }
    }

    @OnClick({R.id.am_subject, R.id.am_type, R.id.am_address, R.id.pm_subject, R.id.pm_type, R.id.pm_address, R.id.commit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.am_subject:
                clickSubect = 0;
                createSubjectPop();
                if (subjectPop.isShowing()) {
                    subjectPop.dismiss();
                } else {
                    scrollView.scrollTo(0, 0);//直接定位到对应的位置 在惯性滚动时不可以打断
                    subjectPop.showAsPointer(view);
                }
                break;
            case R.id.am_type:
                clickType = 0;
                createTypePop();
                if (typePop.isShowing()) {
                    typePop.dismiss();
                } else {
                    scrollView.scrollTo(0, 0);//直接定位到对应的位置 在惯性滚动时不可以打断
                    typePop.showAsPointer(view);
                }
                break;
            case R.id.am_address:
                clickAddress = 0;

                if (0 == amSubType) {//科目二
                    getAddressPop();
                    if (addressPop.isShowing()) {
                        addressPop.dismiss();
                    } else {
                        scrollView.scrollTo(0, 0);//直接定位到对应的位置 在惯性滚动时不可以打断
                        if (0 == addresses.size()) {
                            Toast.makeText(mContext, "未找到任何数据...", Toast.LENGTH_SHORT).show();
                        } else {
                            addressPop.showAsPointer(view);
                        }
                    }
                } else {//科目三
                    Intent intent = new Intent(mContext, ChooseRoteActivity.class);
                    startActivityForResult(intent, 0);
                }

                /*getAddressPop();
                if (addressPop.isShowing()) {
                    addressPop.dismiss();
                } else {
                    scrollView.scrollTo(0, 0);//直接定位到对应的位置 在惯性滚动时不可以打断
                    if (0 == addresses.size()) {
                        Toast.makeText(mContext, "未找到任何数据...", Toast.LENGTH_SHORT).show();
                    } else {
                        addressPop.showAsPointer(view);
                    }
                }*/
                break;
            case R.id.pm_subject:
                clickSubect = 1;
                createSubjectPop();
                if (subjectPop.isShowing()) {
                    subjectPop.dismiss();
                } else {
                    scrollView.scrollTo(0, line_pm.getTop());//smoothScrollTo 平滑滚动过去的 惯性滚动时则可以打断
                    subjectPop.showAsPointer(view);
                }
                break;
            case R.id.pm_type:
                clickType = 1;
                createTypePop();
                if (typePop.isShowing()) {
                    typePop.dismiss();
                } else {
                    scrollView.scrollTo(0, line_pm.getTop());//smoothScrollTo 平滑滚动过去的 惯性滚动时则可以打断
                    typePop.showAsPointer(view);
                }
                break;
            case R.id.pm_address:
                clickAddress = 1;

                if (0 == pmSubType) {//科目二
                    getAddressPop();
                    if (addressPop.isShowing()) {
                        addressPop.dismiss();
                    } else {
                        scrollView.scrollTo(0, line_pm.getTop());//smoothScrollTo 平滑滚动过去的 惯性滚动时则可以打断
                        if (0 == addresses.size()) {
                            Toast.makeText(mContext, "未找到任何数据...", Toast.LENGTH_SHORT).show();
                        } else {
                            addressPop.showAsPointer(view);
                        }
                    }
                } else {//科目三
                    Intent intent = new Intent(mContext, ChooseRoteActivity.class);
                    startActivityForResult(intent, 0);
                }


               /* getAddressPop();
                if (addressPop.isShowing()) {
                    addressPop.dismiss();
                } else {
                    scrollView.scrollTo(0, line_pm.getTop());//smoothScrollTo 平滑滚动过去的 惯性滚动时则可以打断
                    if (0 == addresses.size()) {
                        Toast.makeText(mContext, "未找到任何数据...", Toast.LENGTH_SHORT).show();
                    } else {
                        addressPop.showAsPointer(view);
                    }
                }*/
                break;
            case R.id.commit:
                if (StringUtil.getInstance().isEmpty(am_subject.getText().toString()) && StringUtil.getInstance().isEmpty(pm_subject.getText().toString())) {
                    Toast.makeText(mContext, "您未安排任何课程...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtil.getInstance().isEmpty(am_subject.getText().toString())) {
                    if (StringUtil.getInstance().isEmpty(am_type.getText().toString())) {
                        viewClick(am_type);
                        return;
                    }
                    if (StringUtil.getInstance().isEmpty((String) am_address.getTag())) {
                        viewClick(am_address);
                        return;
                    }
                }
                if (!StringUtil.getInstance().isEmpty(pm_subject.getText().toString())) {
                    if (StringUtil.getInstance().isEmpty(pm_type.getText().toString())) {
                        viewClick(pm_type);
                        return;
                    }
                    if (StringUtil.getInstance().isEmpty((String) pm_address.getTag())) {
                        viewClick(pm_address);
                        return;
                    }
                }

                if (null == LocalCache.get(mContext).getAsString(LocalCacheKey.SET_CURRICULUM_CHECK)) {//为同意过
                    final BaseCustomDialog dialog = new BaseCustomDialog(mContext, R.layout.dialog_set_curriculum_check);
                    dialog.getDialog().show();
                    //取消
                    dialog.getDialog().findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.getDialog().dismiss();
                        }
                    });
                    //同意
                    dialog.getDialog().findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.getDialog().dismiss();
                            LocalCache.get(mContext).put(LocalCacheKey.SET_CURRICULUM_CHECK, LocalCacheKey.SET_CURRICULUM_CHECK);
                            mHandler.sendEmptyMessage(ADDCOURSE);
                        }
                    });
                } else {
                    mHandler.sendEmptyMessage(ADDCOURSE);
                }
                break;
        }
    }

    private void resetItemImageBak(Context mContext, ImageView item_one, ImageView item_two, ImageView item_three, ImageView item_four) {
        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into(item_one);
        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into(item_two);
        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into(item_three);
        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into(item_four);
    }

    /**
     * 初始化
     *
     * @param mContext
     * @param holder
     * @param courseOrdersBean
     * @param type             0：am 1:pm
     */
    private void initItemImageClick(final Context mContext, final ViewHolder holder, CurriculumList.ObjBean.DatasBean.DataBean.CourseOrdersBean courseOrdersBean, final int type) {
        final RelativeLayout rel_item = holder.getView(R.id.rel_item);

        holder.setVisible(R.id.item_one, true);
        holder.setVisible(R.id.item_two, true);
        holder.setVisible(R.id.item_three, true);
        holder.setVisible(R.id.item_four, true);

        final ImageView item_one = holder.getView(R.id.item_one);
        final ImageView item_two = holder.getView(R.id.item_two);
        final ImageView item_three = holder.getView(R.id.item_three);
        final ImageView item_four = holder.getView(R.id.item_four);


        if (0 == courseOrdersBean.getPeopleNumber()) {
        } else if (1 == courseOrdersBean.getPeopleNumber()) {
            setSelectImageNumber(item_one, item_two, item_three, item_four, 1);
        } else if (2 == courseOrdersBean.getPeopleNumber()) {
            setSelectImageNumber(item_one, item_two, item_three, item_four, 2);
        } else if (3 == courseOrdersBean.getPeopleNumber()) {
            setSelectImageNumber(item_one, item_two, item_three, item_four, 3);
        } else if (4 == courseOrdersBean.getPeopleNumber()) {
            setSelectImageNumber(item_one, item_two, item_three, item_four, 4);
        }

        //修改过的
        if ("Y".equals(curriculumList.getObj().getIsUpdate())) {
            return;
        }

        if ("Y".equals(courseOrdersBean.getIsUpdate())) {//(不能修改了)
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "该时段不能安排课程", Toast.LENGTH_SHORT).show();
                }
            };
            holder.setOnClickListener(R.id.item_one, listener);
            holder.setOnClickListener(R.id.item_two, listener);
            holder.setOnClickListener(R.id.item_three, listener);
            holder.setOnClickListener(R.id.item_four, listener);
        } else {//第一次  或 未修改（可以修改）
            holder.setOnClickListener(R.id.item_one, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkParamsValide(type)) {
                        return;
                    }
                    resetItemImageBak(mContext, item_one, item_two, item_three, item_four);
                    if ("1".equals(rel_item.getTag() + "")) {//0:未选中 其他：选中
                        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_bak).into(item_one);
                        if (0 == type) {
                            listParamses.get(holder.getPosition()).setPeopleNumber(0);
                        } else {
                            listParamses.get(holder.getPosition() + curriculumList.getObj().getDatas().get(0).getData().getCourseOrders().size()).setPeopleNumber(0);
                        }
                        rel_item.setTag(0);
                    } else {
                        Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_one);
                        if (0 == type) {
                            listParamses.get(holder.getPosition()).setPeopleNumber(1);
                        } else {
                            listParamses.get(holder.getPosition() + curriculumList.getObj().getDatas().get(0).getData().getCourseOrders().size()).setPeopleNumber(1);
                        }
                        rel_item.setTag(1);
                    }
//                    checkParams();
                }
            });
            holder.setOnClickListener(R.id.item_two, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkParamsValide(type)) {
                        return;
                    }
                    setSelectImageNumber(item_one, item_two, item_three, item_four, 2);
                    if (0 == type) {
                        listParamses.get(holder.getPosition()).setPeopleNumber(2);
                    } else {
                        listParamses.get(holder.getPosition() + curriculumList.getObj().getDatas().get(0).getData().getCourseOrders().size()).setPeopleNumber(2);
                    }
                    rel_item.setTag(2);
//                    checkParams();
                }
            });
            holder.setOnClickListener(R.id.item_three, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkParamsValide(type)) {
                        return;
                    }
                    setSelectImageNumber(item_one, item_two, item_three, item_four, 3);
                    if (0 == type) {
                        listParamses.get(holder.getPosition()).setPeopleNumber(3);
                    } else {
                        listParamses.get(holder.getPosition() + curriculumList.getObj().getDatas().get(0).getData().getCourseOrders().size()).setPeopleNumber(3);
                    }
                    rel_item.setTag(3);
//                    checkParams();
                }
            });
            holder.setOnClickListener(R.id.item_four, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkParamsValide(type)) {
                        return;
                    }
                    setSelectImageNumber(item_one, item_two, item_three, item_four, 4);
                    if (0 == type) {
                        listParamses.get(holder.getPosition()).setPeopleNumber(4);
                    } else {
                        listParamses.get(holder.getPosition() + curriculumList.getObj().getDatas().get(0).getData().getCourseOrders().size()).setPeopleNumber(4);
                    }
                    rel_item.setTag(4);
//                    checkParams();
                }
            });
        }
    }

    /**
     * 设置选中数量
     *
     * @param item_one
     * @param item_two
     * @param item_three
     * @param item_four
     * @param number     选中数量
     */
    public void setSelectImageNumber(ImageView item_one, ImageView item_two, ImageView item_three, ImageView item_four, int number) {
        resetItemImageBak(mContext, item_one, item_two, item_three, item_four);
        if (1 == number) {
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_one);
        } else if (2 == number) {
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_one);
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_two);
        } else if (3 == number) {
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_one);
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_two);
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_three);
        } else if (4 == number) {
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_one);
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_two);
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_three);
            Glide.with(mContext).load(R.drawable.ic_teacher_bespeak_add).into(item_four);
        }
    }

    /**
     * 判断当前是否选择过相应条款
     *
     * @param type 0:am 1:pm
     * @return ture:有效  反之：无效
     */
    public boolean checkParamsValide(int type) {
        if (0 == type && !getAmParams) {
            viewClick(am_subject);
            return false;
        } else if (1 == type && !getPmParams) {
            viewClick(pm_subject);
            return false;
        }
        return true;
    }

    /**
     * 学车科目
     *
     * @return
     */
    private PointerPopupWindow createSubjectPop() {
        if (null == subjectPop) {
            final List<String> subjects = new ArrayList<>();
            subjects.add("科目二");
            subjects.add("科目三");
            subjectPop = new PointerPopupWindow(mContext, (int) (GestureUtils.getScreenPix(mContext).widthPixels), ViewGroup.LayoutParams.MATCH_PARENT);
            View timePopView = mContext.getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(new CommonAdapter<String>(mContext, subjects, R.layout.pop_layout_listview_item) {
                public void convert(ViewHolder holder, final String s) {
                    holder.setText(R.id.item, s);
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        public void onClick(View v) {
                            subjectPop.dismiss();
                            if (0 == clickSubect) {//上午
                                getAmParams = true;
                                am_subject.setText(s);
                                if (subjects.get(0).equals(s)) {
                                    amSubType = 0;
                                } else {
                                    amSubType = 1;
                                }
                            } else {//下午
                                getPmParams = true;
                                pm_subject.setText(s);
                                if (subjects.get(0).equals(s)) {
                                    pmSubType = 0;
                                } else {
                                    pmSubType = 1;
                                }
                            }
                        }
                    });
                }
            });
            subjectPop.setContentView(timePopView);
            subjectPop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);

            subjectPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (0 == clickSubect) {//上午
                        viewClick(am_type);
                    } else {//下午
                        viewClick(pm_type);
                    }
                }
            });
        }
        return subjectPop;
    }

    /**
     * 学车类型
     *
     * @return
     */
    private PointerPopupWindow createTypePop() {
        if (null == typePop) {
            List<String> types = new ArrayList<>();
            types.add("C1");
            types.add("C2");
            typePop = new PointerPopupWindow(mContext, (int) (GestureUtils.getScreenPix(mContext).widthPixels), ViewGroup.LayoutParams.MATCH_PARENT);
            View timePopView = mContext.getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ((ListView) timePopView.findViewById(R.id.listview)).setAdapter(new CommonAdapter<String>(mContext, types, R.layout.pop_layout_listview_item) {
                public void convert(ViewHolder holder, final String s) {
                    holder.setText(R.id.item, s);
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        public void onClick(View v) {
                            typePop.dismiss();
                            if (0 == clickType) {
                                am_type.setText(s);
                            } else {
                                pm_type.setText(s);
                            }
                        }
                    });
                }
            });
            typePop.setContentView(timePopView);
            typePop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);

            typePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (0 == clickType) {
                        viewClick(am_address);
                    } else {
                        viewClick(pm_address);
                    }
                }
            });
        }
        return typePop;
    }

    /**
     * 地址
     *
     * @return
     */
    private PointerPopupWindow getAddressPop() {
        if (0 == clickAddress) {//上午
            if (0 == amSubType) {//科目二
                addresses.clear();
                addresses.addAll(activity.getSites());
            } else {//科目三
                addresses.clear();
                addresses.addAll(activity.getRoads());
            }
        } else {//下午
            if (0 == pmSubType) {//科目二
                addresses.clear();
                addresses.addAll(activity.getSites());
            } else {//科目三
                addresses.clear();
                addresses.addAll(activity.getRoads());
            }
        }
        if (null == addressAdapter) {
            addressAdapter = new CommonAdapter<Serializable>(mContext, addresses, R.layout.pop_layout_listview_item) {
                public void convert(ViewHolder holder, final Serializable s) {
                    final Site site = (Site) s;
                    holder.setText(R.id.item, site.getSiteName());
                    holder.setTag(R.id.item, site.getSiteId());
                    holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addressPop.dismiss();
                            if (0 == clickAddress) {//上午
                                am_address.setText(site.getSiteName());
                                am_address.setTag(site.getSiteId());
                            } else {//下午
                                pm_address.setText(site.getSiteName());
                                pm_address.setTag(site.getSiteId());
                            }
                        }
                    });
                }
            };
        } else {
            addressAdapter.notifyDataSetChanged();
        }

        if (null == addressPop) {
            addressPop = new PointerPopupWindow(mContext, (int) (GestureUtils.getScreenPix(mContext).widthPixels), ViewGroup.LayoutParams.MATCH_PARENT);
            View timePopView = mContext.getLayoutInflater().inflate(R.layout.pop_layout_listview, null, false);
            ListView listview = (ListView) timePopView.findViewById(R.id.listview);
            if (addresses.size() > 5) {
//                addressPop.setHeight((int) (GestureUtils.getScreenPix(mContext).heightPixels * 0.25));
                listview.getLayoutParams().height = (int) (GestureUtils.getScreenPix(mContext).heightPixels * 0.37266);
            }
            listview.setAdapter(addressAdapter);
            addressPop.setContentView(timePopView);
            addressPop.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX);
        } else {
            if (addresses.size() > 5) {
//                addressPop.setHeight((int) (GestureUtils.getScreenPix(mContext).heightPixels * 0.25));
                addressPop.getContentView().findViewById(R.id.listview).getLayoutParams().height = (int) (GestureUtils.getScreenPix(mContext).heightPixels * 0.37266);
            } else {
//                addressPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                addressPop.getContentView().findViewById(R.id.listview).getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }
        }

        return addressPop;
    }


    /**
     *
     */
    public interface OnPopWindowItemClickListener {
        public void setValue(String value);
    }
}
