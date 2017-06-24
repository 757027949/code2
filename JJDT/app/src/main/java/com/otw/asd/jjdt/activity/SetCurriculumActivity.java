package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.TimeUtil;
import com.asd.android.widget.MyRadioGroup;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseFragmentActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.Site;
import com.otw.asd.jjdt.entity.UserInfo;
import com.otw.asd.jjdt.fragment.SetCurriculumFragment;
import com.otw.asd.jjdt.util.ShareUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 课程安排
 * Created by Administrator on 2016/4/20.
 */
public class SetCurriculumActivity extends BaseFragmentActivity {

    @Bind(R.id.rbGroup_time)
    MyRadioGroup rbGroup_time;
    @Bind(R.id.rb_today)
    RadioButton rb_today;
    @Bind(R.id.rb_nextday)
    RadioButton rb_nextday;

    UserInfo userInfo;

    SetCurriculumFragment setTodayCurriculumFragment, setNextdayCurriculumFragment;

    final int SITEBYSCHOOLID = 0;
    final int ROADLISTBYSCHOOLID = 1;

    OnGetDataListListener onGetSitesListListener, onGetRoadsListListener;
    List<Site> sites = new ArrayList<>();//场地
    List<Site> roads = new ArrayList<>();//线路
    /**
     * 当前选中日期
     */
    String rbTime;

    public List<Site> getSites() {
        return sites;
    }

    public List<Site> getRoads() {
        return roads;
    }

    public OnGetDataListListener getOnGetSitesListListener() {
        return onGetSitesListListener;
    }

    public void setOnGetSitesListListener(OnGetDataListListener onGetSitesListListener) {
        this.onGetSitesListListener = onGetSitesListListener;
    }

    public OnGetDataListListener getOnGetRoadsListListener() {
        return onGetRoadsListListener;
    }

    public void setOnGetRoadsListListener(OnGetDataListListener onGetRoadsListListener) {
        this.onGetRoadsListListener = onGetRoadsListListener;
    }

    public String getRbTime() {
        return rbTime;
    }

    public void setRbTime(String rbTime) {
        this.rbTime = rbTime;
    }

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SITEBYSCHOOLID:
                OkHttpUtils.post().url(UrlUtil.SITEBYSCHOOLID)
                        .addParams("schoolId", userInfo.getObj().getTeacher().getSchoolId())
                        .addParams("isRoad", "N")//是否路线
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
                                        if (null != onGetSitesListListener) {
                                            onGetSitesListListener.onfinished();
                                        }
                                        try {
                                            List<Site> currentSites = new Gson().fromJson(jsonObject.getString("obj"), new TypeToken<List<Site>>() {
                                            }.getType());
                                            sites.addAll(currentSites);
                                        } catch (JsonSyntaxException e) {//转化失败
                                            mHandler.sendEmptyMessageDelayed(SITEBYSCHOOLID, 1000);
//                                            sites = null;
//                                            MyDialog.getInstance(IdentificationActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                        }
                                    } else {
                                        mHandler.sendEmptyMessageDelayed(SITEBYSCHOOLID, 1000);
//                                        MyDialog.getInstance(SetCurriculumActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    mHandler.sendEmptyMessageDelayed(SITEBYSCHOOLID, 1000);
//                                    MyDialog.getInstance(SetCurriculumActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case ROADLISTBYSCHOOLID:
                OkHttpUtils.post().url(UrlUtil.SITEBYSCHOOLID)
                        .addParams("schoolId", userInfo.getObj().getTeacher().getSchoolId())
                        .addParams("isRoad", "Y")
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
                                        if (null != onGetRoadsListListener) {
                                            onGetRoadsListListener.onfinished();
                                        }
                                        try {
                                            List<Site> currentRoads = new Gson().fromJson(jsonObject.getString("obj"), new TypeToken<List<Site>>() {
                                            }.getType());
                                            roads.addAll(currentRoads);
                                        } catch (JsonSyntaxException e) {//转化失败
                                            mHandler.sendEmptyMessageDelayed(ROADLISTBYSCHOOLID, 1000);
//                                            roads = null;
                                        }
                                    }
                                } catch (Exception e) {
                                    mHandler.sendEmptyMessageDelayed(ROADLISTBYSCHOOLID, 1000);
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_set_curriculum;
    }

    @Override
    public void initView(View view) {
        setTopTitle("课程安排");

        initRbTime();
        rbGroup_time.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                selectBar(checkedId);
            }
        });
        selectBar(R.id.rb_today);

//        userInfo = (UserInfo) LocalCache.get(this).getAsObject(LocalCacheKey.KEY_USER);
        userInfo = (UserInfo) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.KEY_USER));

//        mHandler.sendEmptyMessage(FLOW_SESSION);
        mHandler.sendEmptyMessage(SITEBYSCHOOLID);
        mHandler.sendEmptyMessage(ROADLISTBYSCHOOLID);
    }


    /**
     * 初始化课程安排显示时间
     */
    private void initRbTime() {
        rb_today.setText(TimeUtil.getInstall().getCurrentTimeFormat("yyyy-MM-dd"));
        rb_nextday.setText(TimeUtil.getInstall().getTime("yyyy-MM-dd", 1));
    }

    public void selectBar(int checkedId) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager
                .beginTransaction();
        hideFragment(beginTransaction);
        switch (checkedId) {
            case R.id.rb_today:
                setRbTime(rb_today.getText().toString());
                if (null == setTodayCurriculumFragment) {
                    setTodayCurriculumFragment = new SetCurriculumFragment();
                    beginTransaction.add(R.id.frame, setTodayCurriculumFragment);
                } else {
                    beginTransaction.show(setTodayCurriculumFragment);
                }
                break;

            case R.id.rb_nextday:
                setRbTime(rb_nextday.getText().toString());
                if (null == setNextdayCurriculumFragment) {
                    setNextdayCurriculumFragment = new SetCurriculumFragment();
                    beginTransaction.add(R.id.frame, setNextdayCurriculumFragment);
                } else {
                    beginTransaction.show(setNextdayCurriculumFragment);
                }
                break;
        }
        beginTransaction.commit();
    }


    /**
     * 隐藏Fragment
     *
     * @param beginTransaction
     */
    private void hideFragment(FragmentTransaction beginTransaction) {
        if (null != setTodayCurriculumFragment) {
            beginTransaction.hide(setTodayCurriculumFragment);
        }
        if (null != setNextdayCurriculumFragment) {
            beginTransaction.hide(setNextdayCurriculumFragment);
        }
    }

    /**
     * 我的课程（安排课程完成）
     */
    public void goCurriculumActivity() {
        Intent intent = new Intent(this, CurriculumActivity.class);
        startActivity(intent);
        finishSelf();
    }

}
