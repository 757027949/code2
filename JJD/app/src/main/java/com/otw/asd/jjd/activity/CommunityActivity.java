package com.otw.asd.jjd.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.android.http.okhttp.MyCallBack;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.liulishuo.magicprogresswidget.MagicProgressBar;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.BaseFragmentActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.fragment.CommunityCollegeFragment;
import com.otw.asd.jjd.fragment.CommunityHotFragment;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 社区
 * Created by Administrator on 2016/3/31.
 */
public class CommunityActivity extends BaseFragmentActivity {
    @Bind(R.id.top_right)
    ImageView top_right;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.no_data_layout)
    ViewStub no_data_layout;
    View no_data_layout_view = null;

    final int SIGN = 0;

    boolean isHot = false;//是否直接选择热门精选

    /**
     * 显示未找到数据
     */
    public void showEmpty() {
        if (null == no_data_layout_view) {
            no_data_layout_view = no_data_layout.inflate();
        } else {
            no_data_layout_view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏未找到数据
     */
    public void hiddeEmpty() {
        if (null != no_data_layout_view) {
            no_data_layout_view.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SIGN:
                OkHttpUtils.post().url(UrlUtil.SIGN)
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return false;
                            }

                            @Override
                            public Context getContext() {
                                return CommunityActivity.this;
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
                                        jsonObject = new JSONObject(jsonObject.getString("obj"));//{"currentLevel":3,"currentExp":9,"nextLevelExp":20,"signTotalCount":0}
                                        final AlertDialog dialog = new AlertDialog.Builder(CommunityActivity.this).create();
                                        dialog.show();
                                        dialog.setCancelable(true);
                                        dialog.setCanceledOnTouchOutside(true);
                                        Window window = dialog.getWindow();
                                        window.setContentView(R.layout.dialog_community_sing_in);
                                        TextView number = (TextView) window.findViewById(R.id.number);
                                        TextView current_exp = (TextView) window.findViewById(R.id.current_exp);
                                        TextView need_exp = (TextView) window.findViewById(R.id.need_exp);
                                        number.setText(jsonObject.getString("signTotalCount"));
                                        current_exp.setText(jsonObject.getString("currentExp"));
                                        need_exp.setText(jsonObject.getString("nextLevelExp"));
                                        ((MagicProgressBar) window.findViewById(R.id.bar)).setSmoothPercent(getPercent(Integer.parseInt(current_exp.getText().toString()), Integer.parseInt(need_exp.getText().toString())));
                                    } else {
                                        MyDialog.getInstance(CommunityActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(CommunityActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_community;
    }

    @Override
    public void initView(View view) {
        chooseLanguage();
        if ("hot".equals(getIntent().getStringExtra("flow"))) {
            isHot = true;
            setTopTitle("热门精选");
            tabLayout.setVisibility(View.GONE);
        } else {
            isHot = false;
            setTopTitle(R.string.home_community);
        }

        top_right.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.ic_community_right).into(top_right);

        setupViewPager(mViewPager);
        if (!isHot) {
            tabLayout.setupWithViewPager(mViewPager);
        }

    }

    @OnClick({R.id.top_right, R.id.release})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        if ("".equals(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        switch (view.getId()) {
            case R.id.top_right:
//                Toast.makeText(this, "签到切", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(SIGN);
                break;

            case R.id.release:
//                Toast.makeText(this, "发布切...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CommunityReleaseActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取经验百分比
     *
     * @param currentExp 当前经验值
     * @param needExp    当前等级经验值
     * @return
     */
    public float getPercent(int currentExp, int needExp) {
        return ((float) currentExp / (float) needExp);
    }

    private void setupViewPager(ViewPager mViewPager) {
        String[] results = getResources().getStringArray(R.array.community_college);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CommunityHotFragment(), "热门精选");
        if (!isHot) {
            adapter.addFragment(new CommunityCollegeFragment().setCommonType("A"), results[0]);
            adapter.addFragment(new CommunityCollegeFragment().setCommonType("B"), results[1]);
            adapter.addFragment(new CommunityCollegeFragment().setCommonType("C"), results[2]);
            adapter.addFragment(new CommunityCollegeFragment().setCommonType("D"), results[3]);
        }
        mViewPager.setAdapter(adapter);
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    /**
     * 设置语言
     */
    public void chooseLanguage() {
        String languageToLoad = "zh";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        config.locale = Locale.SIMPLIFIED_CHINESE;
        getResources().updateConfiguration(config, metrics);
    }
}
