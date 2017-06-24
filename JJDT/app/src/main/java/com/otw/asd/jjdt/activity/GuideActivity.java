package com.otw.asd.jjdt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.util.ShareUtil;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * 欢迎页
 * Created by Administrator on 2016/4/7.
 */
public class GuideActivity extends FragmentActivity {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    TextView go;

    //显示图片资源数组
    private int[] images = {R.mipmap.p1, R.mipmap.p2, R.mipmap.p3};

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_guide);

//        if (LocalCacheKey.VALUE_FIRST_INSTALLED.equals(LocalCache.get(GuideActivity.this).getAsString(LocalCacheKey.KEY_FIRST_INSTALL))) {//非第一次进入软件
        if (!"".equals(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.KEY_FIRST_INSTALL))) {//非第一次进入软件
          /*  UserInfo userInfo = (UserInfo) LocalCache.get(GuideActivity.this).getAsObject(LocalCacheKey.KEY_USER);
            if (null == userInfo) {//需认证(第一次)
                startActivity(new Intent(GuideActivity.this, IdentificationActivity.class));
            } else if (-1 == userInfo.getIdentification()) {//认证未通过
                startActivity(new Intent(GuideActivity.this, IdentificationActivity.class));
            } else {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
            }*/
            startActivity(new Intent(GuideActivity.this, LaucherActivity.class));
            finish();
            return;
        }
        go = (TextView) findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserInfo userInfo = (UserInfo) LocalCache.get(GuideActivity.this).getAsObject(LocalCacheKey.KEY_USER);
//                if (null == userInfo) {//第一次
//                    startActivity(new Intent(GuideActivity.this, LoginActivity.class));
//                }
//                else if (-1 == userInfo.getIdentification()) {//认证未通过
//                    startActivity(new Intent(GuideActivity.this, IdentificationActivity.class));
//                } else {
//                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
//                }
//                LocalCache.get(GuideActivity.this).put(LocalCacheKey.KEY_FIRST_INSTALL, LocalCacheKey.VALUE_FIRST_INSTALLED);
                ShareUtil.getInstance(GuideActivity.this).setLocalCookie(LocalCacheKey.KEY_FIRST_INSTALL, LocalCacheKey.VALUE_FIRST_INSTALLED);
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.guide_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(adapter);

        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int i, int i1) {
                if (i1 == images.length - 1) {
                    go.setVisibility(View.VISIBLE);
                } else {
                    go.setVisibility(View.GONE);
                }
            }
        });
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            convertView.setBackgroundResource(images[position]);
            return convertView;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    };
}
