package com.otw.asd.jjd.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.asd.android.HighLight;
import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.http.okhttp.builder.PostFormBuilder;
import com.asd.android.http.okhttp.callback.StringCallback;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.DeviceInfoUtil;
import com.asd.android.util.NetworkUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.android.widget.MyRadioGroup;
import com.asd.util.JsonMapper;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.NetUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.loveplusplus.update.UpdateChecker;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Contact;
import com.otw.asd.jjd.entity.CurriculumOrderParams;
import com.otw.asd.jjd.entity.HomeConvenient;
import com.otw.asd.jjd.entity.MarkerArea;
import com.otw.asd.jjd.entity.Proclamation;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.fragment.HomeLeftFragment;
import com.otw.asd.jjd.fragment.HomeRightFragment;
import com.otw.asd.jjd.service.NetworkStateService;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;
import com.otw.asd.jjd.widget.FloatView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Request;

public class MainActivity extends SlidingFragmentActivity implements Handler.Callback, LocationSource, AMapLocationListener, AMap.OnMarkerClickListener, View.OnTouchListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    //一件学车
    @Bind(R.id.rbGroup_convenient)
    MyRadioGroup rbGroup_convenient;
    @Bind(R.id.refresh_layout)
    BGARefreshLayout refresh_layout;
    @Bind(R.id.convenient_listview)
    ListView convenient_listview;
    @Bind(R.id.line_convenient_list_bottom_hint)
    TextView line_convenient_list_bottom_hint;
    @Bind(R.id.line_convenient_list_bottom)
    LinearLayout line_convenient_list_bottom;

    /**
     * 0：人数 1：时间 2：地点
     */
    int convenientType = 0;
    View currentItemTop;//当前选中的view
    boolean currentItemTopIsOpened = false;//当前选中的view是否打开
    int convenientItemHeight = 0;//item 高度
    ViewGroup.LayoutParams params;//listview params
    boolean isInitConvenientItemHeight = false;//是否设置过Item高度

    List<HomeConvenient> homeConvenients = new ArrayList<>();
    CommonAdapter<HomeConvenient> adapter;

    /**
     * 设置一键学车
     */
    final int GET_CONVENIENT = 7;
    /**
     * 设置一键学车
     */
    final int SET_CONVENIENT = 8;

    int pageSize = 5;
    int totalPages = 1;
    int currentPage = 1;

    //一件学车


    @Bind(R.id.rel_content)
    RelativeLayout rel_content;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.home_questions)
    TextView home_questions;
    //    @Bind(R.id.home_sign_up)
//    TextView home_sign_up;
    @Bind(R.id.home_choose)
    TextView home_choose;
    @Bind(R.id.home_car_bak)
    TextView home_car_bak;
    @Bind(R.id.home_examination)
    TextView home_examination;
    @Bind(R.id.home_community)
    TextView home_community;
    @Bind(R.id.home_car)
    TextView home_car;

    @Bind(R.id.img_home_car)
    ImageView img_home_car;
    @Bind(R.id.image_temp)
    ImageView image_temp;//地图截图缓存


    @Bind(R.id.rbGroup_subject)
    MyRadioGroup rbGroup_subject;
    @Bind(R.id.rb_two)
    RadioButton rb_two;
    @Bind(R.id.rb_three)
    RadioButton rb_three;

    HomeLeftFragment homeLeftFragment;

    private Bitmap tempBitmap;

    private PopupWindow carOrderPopwindow = null;
    private int popupWidth, popupHeight = 0;//carOrderPopwindow 宽  高

    public static Handler handler;

    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    FloatView floatView;

    User user = null;
    LocalCache localCache;

    /**
     * 定位
     */
    final int FLOW_GET_LOCATION = 0;
    /**
     * 公告
     */
    public static final int FLOW_SHOW_TITLE = 1;
    /**
     * 附近场地
     */
    final int SITELISTBYNEAR = 2;
    final int JPUSH_SET_VALUE = 4;
    final int SHOW_WELCOME_VIEW = 5;

    boolean isLocation = false;//是否定位成功
    double siteLongiTude, siteLatiTude;


    final int FLOW_SESSION = -505;

    //定位间隔
    final int LOCATION_TIME = 30000;

    public static final int FLOW_GO_RECORD = 3;
    /**
     * 重新登录
     */
    public static final int RELOGIN = 6;

    /**
     * SlidingMenu打开menu 0：left  1:right
     */
    int slidingMenuStatus = 0;


    Intent serviceIntent;

    OnFragmentShowListener onFragmentShowListener;

    /**
     * 聊天初始化是否成功
     */
    boolean initEMSuccess = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        handler = new Handler(this);

//        rel_content.setPadding(0, 0, 0, DeviceInfoUtil.getVirtualFunctionKeyHeight(this));
//        getWindow().getDecorView().getSystemUiVisibility();

        initMap(savedInstanceState);
//        mapScreenShotImage();
        initLeftAndRightFragment();
        initHomeMenu();

        initFloatView();

        localCache = LocalCache.get(this);
//        user = (User) localCache.getAsObject(LocalCacheKey.LOCAL_USER);

        //是否该获取session
//        if (!LocalCacheKey.FLOW_SESSION.equals(LocalCache.get(this).getAsString(LocalCacheKey.FLOW_SESSION))) {
//        if (null != user) {
        if (!"".equals(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
            handler.sendEmptyMessageDelayed(FLOW_SESSION, 500);
        }
//        }

        handler.sendEmptyMessage(JPUSH_SET_VALUE);
        UpdateChecker.checkForDialog(this, UrlUtil.GETCLIENT, false);

//        if (null == localCache.getAsString(LocalCacheKey.FIRST_WELCOME) || "".equals(localCache.getAsString(LocalCacheKey.FIRST_WELCOME))) {
        if ("".equals(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.FIRST_WELCOME))) {
            findViewById(R.id.left_frame).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        showLeftFrameView();
                    } catch (Exception e) {
//                        localCache.put(LocalCacheKey.FIRST_WELCOME, LocalCacheKey.FIRST_WELCOME);
                        ShareUtil.getInstance(MainActivity.this).setLocalCookie(LocalCacheKey.FIRST_WELCOME, LocalCacheKey.FIRST_WELCOME);
                    }
                }
            });
        }

        getPermission();

        //一件学车
//        oneKeyConbenient();
        //一件学车
    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceIntent = new Intent(this, NetworkStateService.class);
        startService(serviceIntent);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        try {
            mapView.onResume();
            if (null != mlocationClient) {
                mlocationClient.startLocation();

            }
        } catch (Exception e) {
        }
//        user = (User) LocalCache.get(this).getAsObject(LocalCacheKey.LOCAL_USER);
        if (!"".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
            user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));
            Logger.e(JsonMapper.toNormalJson(user));

        }

        handler.sendEmptyMessage(FLOW_SHOW_TITLE);

       /* try {
            if (null != user) {
                floatView.showFloat();
            } else {
                floatView.destroyFloat();
            }
        } catch (Exception e) {
        }*/

        if (!initEMSuccess && !"".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
//        if (!"".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
            initEM();
        }

        homeConvenients.clear();
        if (null != adapter) {
            adapter.notifyDataSetChanged();
            if (homeConvenients.size() >= 5) {
                line_convenient_list_bottom_hint.setVisibility(View.VISIBLE);
            } else {
                line_convenient_list_bottom_hint.setVisibility(View.GONE);
            }
        }
//        handler.sendMessage(handler.obtainMessage(GET_CONVENIENT, false));
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        try {
            mapView.onPause();
            mlocationClient.stopLocation();
        } catch (Exception e) {
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
        try {
            mapView.onDestroy();
            if (null != mlocationClient) {
                mlocationClient.onDestroy();
            }
            floatView.destroyFloat();
        } catch (Exception e) {
        }

        //退出登录
        EMClient.getInstance().logout(false, null);
    }

    public void setInitEMSuccess(boolean initEMSuccess) {
        this.initEMSuccess = initEMSuccess;
    }

    public OnFragmentShowListener getOnFragmentShowListener() {
        return onFragmentShowListener;
    }

    public void setOnFragmentShowListener(OnFragmentShowListener onFragmentShowListener) {
        this.onFragmentShowListener = onFragmentShowListener;
    }

    /**
     * 一件学车
     */
    private void oneKeyConbenient() {
        rbGroup_convenient.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                currentPage = 1;
                totalPages = 1;
                homeConvenients.clear();
                if (null != adapter) {
                    adapter.notifyDataSetChanged();
                }
                switch (checkedId) {
                    case R.id.rb_person:
                        convenientType = 0;
                        handler.sendMessage(handler.obtainMessage(GET_CONVENIENT, true));
                        break;
                    case R.id.rb_time:
                        convenientType = 1;
                        handler.sendMessage(handler.obtainMessage(GET_CONVENIENT, true));
                        break;

                    case R.id.rb_address:
                        convenientType = 3;
                        if (isLocation) {
                            handler.sendMessage(handler.obtainMessage(GET_CONVENIENT, true));
                        } else {
                            Toast.makeText(MainActivity.this, "定位失败...", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        line_convenient_list_bottom.setOnTouchListener(this);
        initRefreshLayout();
        convenient_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (null != currentItemTop && currentItemTopIsOpened) {
                    convenientItemLeft(currentItemTop);
                    currentItemTopIsOpened = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    //一件学车
    GestureDetector gestureHDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        int FLING_MIN_DISTANCE = 50, FLING_MIN_VELOCITY = 200;

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling left
//                Toast.makeText(MainActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();
                currentItemTopIsOpened = false;
                convenientItemLeft(currentItemTop);
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling right
//                Toast.makeText(MainActivity.this, "Fling right", Toast.LENGTH_SHORT).show();
                currentItemTopIsOpened = true;
                convenientItemRight(currentItemTop);
            }
            return true;
        }
    });

    /**
     * 左滑动
     */
    private void convenientItemLeft(final View view) {
        view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.home_convenient_translate_in));
        view.getAnimation().setAnimationListener(null);
        view.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.findViewById(R.id.item_status).setRotation(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 右滑动
     */
    private void convenientItemRight(final View view) {
        view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.home_convenient_translate_out));
        view.getAnimation().setAnimationListener(null);
        view.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
                view.findViewById(R.id.item_status).setRotation(180);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    GestureDetector gestureVDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        int FLING_MIN_DISTANCE = 50, FLING_MIN_VELOCITY = 200;

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE
                    && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                // Fling TOP
                if (null != params) {
                    params.height = convenientItemHeight;
                    convenient_listview.setLayoutParams(params);
                }
                line_convenient_list_bottom_hint.setVisibility(View.GONE);

                /*TranslateAnimation animation = new TranslateAnimation(0, 0, convenientItemHeight * 5, convenientItemHeight);
                animation.setDuration(300);
                animation.setFillAfter(true);
                convenient_listview.setAnimation(animation);
                animation.start();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            } else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE
                    && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                // Fling BOTTOM
                if (null != params) {
                    if (homeConvenients.size() > 5) {
                        params.height = convenientItemHeight * 5;
                    } else {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                    convenient_listview.setLayoutParams(params);
                }

                TranslateAnimation animation = new TranslateAnimation(0, 0, null == params ? 0 : -params.height, 0);
                animation.setDuration(300);
                animation.setFillAfter(true);
                convenient_listview.setAnimation(animation);
                animation.start();

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (homeConvenients.size() >= 5) {
                            line_convenient_list_bottom_hint.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
            return true;
        }
    });

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.convenient_item_line_top:
                currentItemTop = v;
                gestureHDetector.onTouchEvent(event);
                break;
            case R.id.line_convenient_list_bottom:
                gestureVDetector.onTouchEvent(event);
                break;
        }
        return true;
    }

    private void initRefreshLayout() {
        refresh_layout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(MainActivity.this, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);

        refresh_layout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        currentPage = 1;
        totalPages = 1;
        homeConvenients.clear();
        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }
        handler.sendMessage(handler.obtainMessage(GET_CONVENIENT, false));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        currentPage++;
        if (currentPage <= totalPages) {
            handler.sendMessage(handler.obtainMessage(GET_CONVENIENT, false));
            return true;
        } else {
            Toast.makeText(MainActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
            refresh_layout.endLoadingMore();
            return false;
        }
    }

    /**
     * 获取一件学车课程
     *
     * @param type         0：人数 1：时间 2：地点
     * @param isShowDialog 是否显示Dialog
     */
    public void getCourseOrders(int type, final boolean isShowDialog) {
        String urlString = "";
        if (0 == type) {
            urlString = UrlUtil.COURSEORDERBYPEOPLE;
        } else if (1 == type) {
            urlString = UrlUtil.COURSEORDERBYTIME;
        } else {
            urlString = UrlUtil.COURSERORDERBYDISTANCE;
        }

        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(urlString)
                .addParams("everyPage", pageSize + "")
                .addParams("currentPage", currentPage + "");
        if (0 != type && 1 != type) {
            postFormBuilder.addParams("longiTude", siteLongiTude + "");//经度
            postFormBuilder.addParams("latiTude", siteLatiTude + "");//维度
        }
        postFormBuilder.build().execute(new com.asd.android.http.okhttp.MyCallBack() {
            @Override
            public boolean setIsSilence() {
                return !isShowDialog;
            }

            @Override
            public Context getContext() {
                return MainActivity.this;
            }

            @Override
            public SweetAlertDialog getLoadDialog() {
                return getLodingAlertDialog();
            }

            @Override
            public void onResponse(String response) {
                Logger.e(response);
                refresh_layout.endRefreshing();
                refresh_layout.endLoadingMore();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        List<HomeConvenient> convenients = new Gson().fromJson(jsonObject.getString("obj"), new TypeToken<List<HomeConvenient>>() {
                        }.getType());
                        if (convenients.size() == pageSize) {
                            totalPages = totalPages + 1;
                        }
                        handler.sendMessage(handler.obtainMessage(SET_CONVENIENT, convenients));
                    } else {
                        MyDialog.getInstance(MainActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                    }
                } catch (Exception e) {
                    MyDialog.getInstance(MainActivity.this).getWaringAlertDialog(getResources().getString(R.string.json_error)).show();
                }
            }

            @Override
            public void getError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                refresh_layout.endRefreshing();
                refresh_layout.endLoadingMore();
            }
        });
    }

    //一件学车

    /**
     * 聊天初始
     */
    private void initEM() {
        loginEM();

        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        //设置了昵称和头像地址
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    /**
     * 登录聊天
     */
    private void loginEM() {
        User user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));
        Logger.e(user.getObj().getUser().getUserId() + "--------------------------initEM");
        EMClient.getInstance().login(user.getObj().getUser().getUserId(), user.getObj().getUser().getUserLoginPassword(), new EMCallBack() {//回调
            //                    EMClient.getInstance().login("123","123", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
//                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Logger.e("登录聊天服务器成功！");
                initEMSuccess = true;
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Logger.e(code + "登录聊天服务器失败！");
                initEMSuccess = false;
            }
        });
    }


    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        Toast.makeText(MainActivity.this, "该帐号已在其他设备登录", Toast.LENGTH_SHORT).show();
//                        loginEM();
                    }
                    /*else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //连接不到聊天服务器
                            Toast.makeText(MainActivity.this, "连接不到聊天服务器", Toast.LENGTH_SHORT).show();
                        } else {
                            //当前网络不可用，请检查网络设置
                            Toast.makeText(MainActivity.this, "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
                        }
                    }*/
                }
            });
        }
    }

    private EaseUser getUserInfo(String username) {
        // To get instance of EaseUser, here we get it from the user list in memory
        // You'd better cache it if you get it from your server
        EaseUser easeUser = null;

        Map<String, Contact> contacts;
        if (!Hawk.contains(Contact.CONTACT_KEY)) {
            contacts = new HashMap<String, Contact>();
        } else {
            contacts = Hawk.get(Contact.CONTACT_KEY);
        }
        if (!contacts.containsKey(EMClient.getInstance().getCurrentUser()) && null != user) {
//            Contact contact = new Contact(user.getObj().getUser().getUserId(), 0 != user.getObj().getUserAlbums().size() ? user.getObj().getUserAlbums().get(0).getUserAlbumPath() : "", user.getObj().getUser().getUserNickName(), user.getObj().getUser().getUserSex());
            Contact contact = new Contact(user.getObj().getUser().getUserId(), user.getObj().getUser().getUserHeadPath(), user.getObj().getUser().getUserNickName(), user.getObj().getUser().getUserSex());
            contacts.put(EMClient.getInstance().getCurrentUser(), contact);
            Hawk.put(Contact.CONTACT_KEY, contacts);
        }
        if (contacts.containsKey(username)) {
            Contact contact = contacts.get(username);
            easeUser = new EaseUser(username);
            easeUser.setAvatar(contact.getImagePath());
//            easeUser.setAvatar("http://p4.so.qhmsg.com/bdr/_240_/t018146cb047ca0115e.jpg");
            easeUser.setNick(contact.getName());
        }

        // if user is not in your contacts, set inital letter for him/her
        if (easeUser == null) {
            easeUser = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(easeUser);
        }
        return easeUser;
    }

    /**
     * 获取权限
     */
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ("".equals(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.CHECK_LOCATION))) {
                int fineLocation = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                int coarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
                int phoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
                int callPhone = checkSelfPermission(Manifest.permission.CALL_PHONE);
                int readLogs = checkSelfPermission(Manifest.permission.READ_LOGS);
                int writeExteranalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int setDebugApp = checkSelfPermission(Manifest.permission.SET_DEBUG_APP);
                int systemAlertWindow = checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW);
                int getAccounts = checkSelfPermission(Manifest.permission.GET_ACCOUNTS);

                List<String> permissions = new ArrayList<String>();
                if (fineLocation != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                }
                if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                }
                if (phoneState != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.READ_PHONE_STATE);
                }
                if (callPhone != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.CALL_PHONE);
                }
                if (readLogs != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.READ_LOGS);
                }
                if (writeExteranalStorage != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if (setDebugApp != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.SET_DEBUG_APP);
                }
                if (systemAlertWindow != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
                }
                if (getAccounts != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.GET_ACCOUNTS);
                }
                if (!permissions.isEmpty()) {
                    requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
                }
//                    ShareUtil.getInstance(this).setLocalCookie(LocalCacheKey.CHECK_LOCATION, LocalCacheKey.CHECK_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                boolean isExit = false;
                for (int i = 0; i < permissions.length; i++) {
                   /* if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Logger.e("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Logger.e("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }*/
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        isExit = true;
                        break;
                    }
                }
                if (isExit) {
                    AppManager.AppExit(this);
                } else {
                    ShareUtil.getInstance(this).setLocalCookie(LocalCacheKey.CHECK_LOCATION, LocalCacheKey.CHECK_LOCATION);
                }
                break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public FloatView getFloatView() {
        return floatView;
    }

    public User getUser() {
        return user;
    }

    /**
     * 初始化悬浮窗
     */
    private void initFloatView() {
        floatView = new FloatView(this);
        floatView.setOnFlostViewClickListener(new FloatView.OnFlostViewClickListener() {
            @Override
            public void click(View view) {
                Toast.makeText(MainActivity.this, "click...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, IdeaActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);    //打开自定义的Activity


               /* Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setAction(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);*/

            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    private void initLeftAndRightFragment() {
        homeLeftFragment = new HomeLeftFragment();
        setBehindContentView(R.layout.layout_fragment_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_left_frame, homeLeftFragment).commit();
        SlidingMenu menu = getSlidingMenu();
        //设置侧滑菜单的位置，可选值LEFT , RIGHT , LEFT_RIGHT （两边都有菜单时设置）
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        menu.setTouchModeAbove(image_temp.getVisibility() == View.VISIBLE ? SlidingMenu.TOUCHMODE_NONE : SlidingMenu.TOUCHMODE_MARGIN);
        //根据dimension资源文件的ID来设置阴影的宽度
//        menu.setShadowWidthRes(R.dimen.size_50px);
        //根据资源文件ID来设置滑动菜单的阴影效果
//        menu.setShadowDrawable(R.drawable.ic_exit);
        // 设置滑动菜单视图的宽度
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setBehindOffset((int) (GestureUtils.getScreenPix(this).widthPixels * 0.2));
        //设置宽度
//		menu.setBehindWidth()
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        //设置SlidingMenu与下方视图的移动的速度比，当为1时同时移动，取值0-1
        menu.setBehindScrollScale(1.0f);
        //设置二级菜单的阴影效果
//        menu.setSecondaryShadowDrawable(R.drawable.ic_exit);
        //设置右边（二级）侧滑菜单
        menu.setSecondaryMenu(R.layout.layout_fragment_right);
        HomeRightFragment homeRightFragment = new HomeRightFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_right_frame, homeRightFragment).commit();

        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
//                if (null == localCache.getAsObject(LocalCacheKey.LOCAL_USER)) {
                slidingMenuStatus = 0;
                if ("".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
                    Logger.e("open...");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
//                if (null != localCache.getAsObject(LocalCacheKey.LOCAL_USER)) {
                if (0 == slidingMenuStatus) {
                    if (!"".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
                        homeLeftFragment.getmHandler().sendEmptyMessage(HomeLeftFragment.FLOW_INIT_VIEW);
                    }
//                    Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                } else if (1 == slidingMenuStatus) {
                    if (null != onFragmentShowListener) {
                        onFragmentShowListener.show();
                    }
//                    Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                }
            }
        });

        menu.setSecondaryOnOpenListner(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                slidingMenuStatus = 1;
                if ("".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
                    Logger.e("open...");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    /**
     * 滑动处理地图黑边
     */
    private void mapScreenShotImage() {
        getSlidingMenu().setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
                    @Override
                    public void onMapScreenShot(Bitmap bitmap) {
                        tempBitmap = bitmap;
                        image_temp.setImageBitmap(bitmap);
                        image_temp.setVisibility(View.VISIBLE);
                        mapView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onMapScreenShot(Bitmap bitmap, int i) {

                    }
                });
            }
        });
        getSlidingMenu().setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                if (tempBitmap != null && !tempBitmap.isRecycled()) {
                    tempBitmap.recycle();
                    tempBitmap = null;
                }
                image_temp.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initHomeMenu() {
        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(this);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(home_questions, 1, 0.035f, 0.03f, 1);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(home_choose, 1, 0.035f, 0.03f, 1);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(home_car_bak, 1, 0.035f, 0.03f, 1);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(home_examination, 1, 0.035f, 0.03f, 1);
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(home_community, 1, 0.035f, 0.03f, 1);

        viewCompoundDrawableUtil.initTextViewCompoundDrawable(home_car, 1, 0.05f, 0.03f, 1);

    }

    //, R.id.title
    @OnClick({R.id.left_frame, R.id.right_frame, R.id.rel_car, R.id.home_questions, R.id.home_examination, R.id.home_choose, R.id.home_community, R.id.rb_two, R.id.rb_three})
    public void menuClick(View view) {
        /*if (null != carOrderPopwindow && carOrderPopwindow.isShowing()) {
            carOrderPopwindow.dismiss();
            return;
        }*/
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.left_frame:
                getSlidingMenu().showMenu();
                break;
            case R.id.right_frame:
                getSlidingMenu().showSecondaryMenu();
                break;
            case R.id.title:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "http://www.baidu.com");
                intent.putExtra("flow", "proclamation");
                startActivity(intent);
                break;
            case R.id.rel_car:
                if (View.VISIBLE == rbGroup_subject.getVisibility()) {
                    resetSubAttr();
                } else {
                    startCarAnimation();
                    startSubAnimation();
                }
                /*if (null == carOrderPopwindow) {
                    carOrderChoose(view);
                } else {
                    if (carOrderPopwindow.isShowing()) {
                        carOrderPopwindow.dismiss();
                    } else {
                        carOrderPopwindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, view.getHeight() + DeviceInfoUtil.getVirtualFunctionKeyHeight(this));
                    }
                }*/
                break;
            case R.id.home_questions:
                intent = new Intent(this, ChooseSubjectActivity.class);
                startActivity(intent);
//                Toast.makeText(this, "home_questions", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_examination://https://cq.122.gov.cn/m/login?
               /* intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "https://cq.122.gov.cn/m/login?");
                startActivity(intent);*/
                if (null == user) {
                    intent = new Intent(this, LoginActivity.class);
                } else {
                    Uri url = Uri.parse("https://cq.122.gov.cn/m/login?");
                    intent = new Intent(Intent.ACTION_VIEW, url);
                    intent.setData(url);
                }
                startActivity(intent);
                break;

            case R.id.home_choose:
//                Toast.makeText(this, "精选", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, CommunityActivity.class);
                intent.putExtra("flow", "hot");
                startActivity(intent);
                break;
            case R.id.home_community:
                intent = new Intent(this, CommunityActivity.class);
                intent.putExtra("flow", "community");
                startActivity(intent);

//                SweetAlertDialog alertDialog = new SweetAlertDialog(this)
//                        .setTitleText("提示!")
//                        .setContentText("功能完善中...");
////                        .setCustomImage(R.drawable.ic_error);
//                alertDialog.show();

                break;

            case R.id.rb_two:
                if (isLocation) {
                    resetSubAttr();
                    handler.sendEmptyMessage(SITELISTBYNEAR);
                } else {
                    Toast.makeText(MainActivity.this, "定位失败...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rb_three:
                resetSubAttr();
                intent = new Intent(MainActivity.this, ChooseTeacherActivity.class);
                intent.putExtra("flow", "3");
                startActivity(intent);
                break;
        }
    }

    /**
     * 重置
     */
    private void resetSubAttr() {
        RotateAnimation rotateAnimation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        img_home_car.startAnimation(rotateAnimation);

        AnimationSet animationSet = new AnimationSet(true);
        rotateAnimation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(500);

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);

        rbGroup_subject.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rbGroup_subject.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rb_two.setChecked(false);
        rb_three.setChecked(false);
    }

    /**
     * 图片动画
     */
    private void startCarAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        //参数1：从哪个旋转角度开始
        //参数2：转到什么角度
        //后4个参数用于设置围绕着旋转的圆的圆心在哪里
        //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
        //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        //参数5：确定y轴坐标的类型
        //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
      /*  rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                RotateAnimation rotateAnimation = new RotateAnimation(180, 0,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(500);
                rotateAnimation.setFillAfter(true);
                animationSet.addAnimation(rotateAnimation);
                img_home_car.startAnimation(rotateAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        animationSet.addAnimation(rotateAnimation);
        img_home_car.startAnimation(rotateAnimation);
    }

    /**
     * 选择科目动画
     */
    private void startSubAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        //参数1：从哪个旋转角度开始
        //参数2：转到什么角度
        //后4个参数用于设置围绕着旋转的圆的圆心在哪里
        //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
        //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        //参数5：确定y轴坐标的类型
        //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);


        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);

        rbGroup_subject.setVisibility(View.VISIBLE);
        rbGroup_subject.startAnimation(animationSet);
    }

    /**
     * 选择订单类型
     */
    private void carOrderChoose(View view) {
        View popView = getLayoutInflater().inflate(R.layout.pop_layout_car, null, false);
        carOrderPopwindow = new PopupWindow(popView, (int) (GestureUtils.getScreenPix(this).widthPixels * 0.3), ViewGroup.LayoutParams.WRAP_CONTENT);
        carOrderPopwindow.setOutsideTouchable(true);
//        carOrderPopwindow.setFocusable(true);
        carOrderPopwindow.setAnimationStyle(R.style.AnimationPreview1);
        popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = view.getMeasuredWidth();
        popupHeight = view.getMeasuredHeight();
//        carOrderPopwindow.showAsDropDown(view, 0, -view.getHeight() - popupHeight);
//        carOrderPopwindow.showAtLocation(view,Gravity.LEFT,0,0);
//        carOrderPopwindow.update();
        carOrderPopwindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, view.getHeight() + DeviceInfoUtil.getVirtualFunctionKeyHeight(this));
//        carOrderPopwindow.showAsDropDown(view, -(view.getWidth() / 2) - (popupWidth / 2), -view.getHeight() - popupHeight);

        carOrderPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                RotateAnimation rotateAnimation = new RotateAnimation(180, 0,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(500);
                rotateAnimation.setFillAfter(true);
                img_home_car.startAnimation(rotateAnimation);
            }
        });

        popView.findViewById(R.id.car_self).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carOrderPopwindow.dismiss();
                aMap.moveCamera(CameraUpdateFactory.zoomTo(14f));
//                Toast.makeText(MainActivity.this, "car_self", Toast.LENGTH_SHORT).show();
            }
        });
        popView.findViewById(R.id.car_team).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carOrderPopwindow.dismiss();
//                aMap.moveCamera(CameraUpdateFactory.zoomTo(14f));
//                aMap.clear();
                Intent intent;
                if (null == user) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, ChooseTeacherActivity.class);
                    intent.putExtra("flow", "3");
                }
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "car_team", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 关闭SlidingMenu
     *
     * @return
     */
    public void closeSlidingMenu() {
        if (getSlidingMenu().isMenuShowing()) {
            getSlidingMenu().toggle();
        }
    }

    /**
     * 初始化AMap对象
     */
    private void initMap(Bundle savedInstanceState) {
         /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        //  MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (null != carOrderPopwindow && carOrderPopwindow.isShowing()) {
                    carOrderPopwindow.dismiss();
                }
                if (View.VISIBLE == rbGroup_subject.getVisibility()) {
                    resetSubAttr();
                }
            }
        });
//        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setScrollGesturesEnabled(true);//这个方法设置了地图是否允许通过手势来移动。
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(11f));


        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.ic_location));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.BLACK);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        myLocationStyle.radiusFillColor(Color.argb(0, 255, 155, 155));
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        // 构造 LocationManagerProxy 对象
//        mAMapLocationManager = LocationManagerProxy.getInstance(LocationSourceActivity.this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        aMap.setMyLocationEnabled(true);
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Logger.e("activate...");
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(14f));
        handler.sendEmptyMessage(FLOW_GET_LOCATION);
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否只定位一次,默认为false
//            mLocationOption.setOnceLocation(true);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(LOCATION_TIME);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Logger.e("onLocationChanged...");
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(14f));
//        handler.sendEmptyMessage(FLOW_GET_LOCATION);
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//                aMap.moveCamera(CameraUpdateFactory.zoomTo(14f));
                isLocation = true;
                siteLongiTude = amapLocation.getLongitude();
                siteLatiTude = amapLocation.getLatitude();
                Logger.e("定位成功====" + amapLocation.getLatitude() + "======" + amapLocation.getLongitude());

                //定位成功回调信息，设置相关消息
                /*amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                amapLocation.getAOIName();//获取当前定位点的AOI信息*/

            } else {
                isLocation = false;
                Logger.e("定位失败======" + "AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    //anchor(29.535053f,106.23423f)

    /**
     * 设置场地信息
     *
     * @param beanList
     */
    public void initMarker(List<MarkerArea.ObjBean> beanList) {
        if (0 == beanList.size()) {
            Toast.makeText(this, "抱歉，未找到任何场地...", Toast.LENGTH_SHORT).show();
            return;
        }
        for (MarkerArea.ObjBean bean : beanList) {
            View tempView = getLayoutInflater().inflate(R.layout.layout_map_yard, null, false);
            TextView yard = (TextView) tempView.findViewById(R.id.yard);
            yard.setText(bean.getSiteName());
            aMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(bean.getSiteLatiTude()), Double.parseDouble(bean.getSiteLongiTude())))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.convertViewToBitmap(tempView)))
                    .anchor(0f, 1f).title(bean.getSiteId()));
        }

//            aMap.addMarker(new MarkerOptions().position(new LatLng(29.535165, 106.559256)).icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.convertViewToBitmap(view))).anchor(0f, 1f).title("1"));
//            aMap.addMarker(new MarkerOptions().position(new LatLng(29.535265, 106.559456)).icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.convertViewToBitmap(view))).anchor(0f, 1f).title("10"));
//            aMap.addMarker(new MarkerOptions().position(new LatLng(29.535365, 106.559556)).icon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.convertViewToBitmap(view))).anchor(0f, 1f).title("100"));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Logger.e(marker.getId());//Marker1：定位
        if (!"Marker1".equals(marker.getId())) {
            Intent intent = new Intent(this, ChooseTeacherActivity.class);
            intent.putExtra("flow", "2");
            intent.putExtra("siteId", marker.getTitle());
            startActivity(intent);
        }
        return true;
    }

    TagAliasCallback tagAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    handler.sendEmptyMessageDelayed(JPUSH_SET_VALUE, 1000 * 60);
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
            }
            Logger.e(logs + "----------------");
        }

    };

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case FLOW_SESSION:
                OkHttpUtils.post().url(UrlUtil.LOGIN)
//                        .addParams("userRegisterTelephone", localCache.getAsString(LocalCacheKey.FLOW_USER_ACCOUNTS))
//                        .addParams("userLoginPassword", localCache.getAsString(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("userRegisterTelephone", ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_ACCOUNTS))
                        .addParams("userLoginPassword", ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.FLOW_USER_PWD))
                        .addParams("deviceCode", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjd")
                        .addParams("clientPlatform", "android")
                        .addParams("userIsTeacher", "N")
                        .addParams("userOtherType", "platform")
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void onResponse(String response) {
                                Logger.e("FLOW_SESSION===========" + response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        LocalCache.get(MainActivity.this).put(LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION, LocalCacheKey.FLOW_SESSION_TIME);
                                    } else {
//                                        MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
//                                    MyDialog.getInstance(BaseActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
            case FLOW_GET_LOCATION:
                aMap.moveCamera(CameraUpdateFactory.zoomTo(14f));
                break;

            case JPUSH_SET_VALUE:
                JPushInterface.init(getApplicationContext());
                String tag = "student";
                String alias = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "_jjd";
                Logger.e(tag + "====" + alias);
                Set<String> set = new LinkedHashSet<String>();
                set.add(tag);
//                set.add(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)+"_jjd");
                JPushInterface.setAliasAndTags(getApplicationContext(), alias, set, tagAliasCallback);
//                JPushInterface.setTags(getApplicationContext(), set, tagAliasCallback);
                break;

            case FLOW_SHOW_TITLE:
                Proclamation proclamation = (Proclamation) localCache.getAsObject(LocalCacheKey.PROCLAMATION);
                if (null != proclamation) {
                    title.setText(proclamation.getNoticeContent());
                } else {
                    title.setText("");
                }
                break;


            case RELOGIN:
                Intent intent = new Intent(this, ReloginActivity.class);
                intent.putExtra("mes", msg.obj.toString());
                startActivity(intent);
               /* try {
                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示！！")
                            .setContentText(msg.obj.toString())
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    MyApplication.getInstance().finishAllActivity();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                } catch (Exception e) {
                }*/
                break;

            case SITELISTBYNEAR:
                OkHttpUtils.post().url(UrlUtil.SITELISTBYNEAR)
                        .addParams("siteLongiTude", siteLongiTude + "")//经度
                        .addParams("siteLatiTude", siteLatiTude + "")//纬度
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    MarkerArea area = new Gson().fromJson(response, MarkerArea.class);
                                    if (area.isSuccess()) {
                                        initMarker(area.getObj());
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(MainActivity.this).getWaringAlertDialog(getResources().getString(R.string.json_error)).show();
                                }
                            }
                        });
                break;

            case FLOW_GO_RECORD:
                if (null == user) {
                    if (!"".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
                        user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));
                    }
                    if (null == user) {
                        intent = new Intent(this, LoginActivity.class);
                    } else {
                        intent = new Intent(this, RecordActivity.class);
                    }
                } else {
                    intent = new Intent(this, RecordActivity.class);
                }
                startActivity(intent);
                break;

            case GET_CONVENIENT:
                getCourseOrders(convenientType, (Boolean) msg.obj);
                break;

            case SET_CONVENIENT:
                homeConvenients.addAll((List<HomeConvenient>) msg.obj);

                if (null == adapter) {
                    adapter = new CommonAdapter<HomeConvenient>(MainActivity.this, homeConvenients, R.layout.adapter_layout_home_convenient_item) {

                        @Override
                        public void convert(final ViewHolder holder, final HomeConvenient convenient) {
                            holder.getView(R.id.rel_person_image).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, TeacherInfoActivity.class);
                                    intent.putExtra("teacherId", convenient.getUserAndTeacher().getTeacherId());
                                    intent.putExtra("userId", convenient.getUserAndTeacher().getUserId());
                                    startActivity(intent);
                                }
                            });
                            holder.getView(R.id.convenient_item_line_top).setOnTouchListener(MainActivity.this);


                            Glide.with(mContext).load(convenient.getUserAndTeacher().getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                            holder.setText(R.id.person_name, convenient.getUserAndTeacher().getUserRealName().substring(0, 1) + "教练");
                            holder.setText(R.id.teacher_name, convenient.getUserAndTeacher().getUserRealName());
                            holder.setText(R.id.teacher_sex, convenient.getUserAndTeacher().getUserSex());
                            holder.setText(R.id.teacher_age, convenient.getUserAndTeacher().getUserAge() + "");
                            holder.setText(R.id.teacher_s_year, convenient.getUserAndTeacher().getTeacherTeachYear() + "年");
                            holder.setText(R.id.teacher_site, convenient.getCourseOrderAndCourse().getSiteName());
                            holder.setText(R.id.course_type, convenient.getCourseOrderAndCourse().getCourseType());
                            holder.setText(R.id.course_sub, convenient.getCourseOrderAndCourse().getCourseSubject());
                            holder.setText(R.id.course_time, convenient.getCourseOrderAndCourse().getCourseOrderBeginTime().substring(11, 16) + "-" + convenient.getCourseOrderAndCourse().getCourseOrderEndTime().substring(11, 16));

                            SwipeMenuRecyclerView recycler_view = holder.getView(R.id.recycler_view);
                            recycler_view.setLayoutManager(new GridLayoutManager(mContext, 4));
                            recycler_view.setHasFixedSize(true);
                            recycler_view.setItemAnimator(new DefaultItemAnimator());


                            final List<HomeConvenient.CourseOrderAndCourseBean.UsersBean> usersBeanList = new ArrayList<>();
                            usersBeanList.addAll(convenient.getCourseOrderAndCourse().getUsers());
                            for (int i = convenient.getCourseOrderAndCourse().getUsers().size(); i < convenient.getCourseOrderAndCourse().getPeopleNumber(); i++) {
                                usersBeanList.add(null);
                            }

                            recycler_view.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<HomeConvenient.CourseOrderAndCourseBean.UsersBean>(mContext, R.layout.layout_item_home_convenient, usersBeanList) {
                                @Override
                                protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, HomeConvenient.CourseOrderAndCourseBean.UsersBean usersBean, int position) {
                                    if (null != usersBean) {
                                        Glide.with(mContext).load(usersBean.getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                                        holder.getView(R.id.person_image).setTag(R.id.home_convenient_item_id, usersBean.getUserId());
                                    }

                                    holder.getView(R.id.person_image).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if ("".equals(ShareUtil.getInstance(MainActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
                                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            } else if (null != v.getTag(R.id.home_convenient_item_id) && !user.getObj().getUser().getUserId().equals(v.getTag(R.id.home_convenient_item_id))) {//有人且别人
                                                Intent intent = new Intent(mContext, UserInfoActivity.class);
                                                intent.putExtra("flow", "other");
                                                intent.putExtra("userId", v.getTag(R.id.home_convenient_item_id).toString());
                                                startActivity(intent);
//                                                Toast.makeText(mContext, "别个...", Toast.LENGTH_SHORT).show();
                                            } else {//自己或没人
                                                for (HomeConvenient.CourseOrderAndCourseBean.UsersBean bean : convenient.getCourseOrderAndCourse().getUsers()) {
                                                    if (bean.getUserId().equals(user.getObj().getUser().getUserId())) {
                                                        Toast.makeText(mContext, "您已报名该课程...", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                }

                                                Intent intent = new Intent(MainActivity.this, MakeOrderActivity.class);
                                                intent.putExtra("day", convenient.getCourseOrderAndCourse().getCourseOrderBeginTime().substring(0, 10));
                                                List<String> courseOrderIds = new ArrayList<String>();
                                                courseOrderIds.add(convenient.getCourseOrderAndCourse().getCourseOrderId());
                                                intent.putStringArrayListExtra("courseOrderIds", (ArrayList<String>) courseOrderIds);
                                                intent.putExtra("isCheckVoucher", convenient.getCourseOrderAndCourse().getPeopleNumber() > 2 ? true : false);

                                                CurriculumOrderParams.OrderBean orderBean = new CurriculumOrderParams.OrderBean();
                                                orderBean.setTimeString(convenient.getCourseOrderAndCourse().getCourseOrderBeginTime().substring(11, 16) + " - " + convenient.getCourseOrderAndCourse().getCourseOrderEndTime().substring(11, 16));
                                                orderBean.setOrderId(convenient.getCourseOrderAndCourse().getCourseOrderId());
                                                orderBean.setPrice(convenient.getCourseOrderAndCourse().getCourseOrderPrice());
                                                List<CurriculumOrderParams.OrderBean> orders = new ArrayList<CurriculumOrderParams.OrderBean>();
                                                orders.add(orderBean);
                                                CurriculumOrderParams curriculumOrderParams = new CurriculumOrderParams();
                                                curriculumOrderParams.setCourseOrderS2UnitPrice(convenient.getCourseOrderAndCourse().getCourseOrderUnitPrice());
                                                curriculumOrderParams.setCourseOrderS3UnitPrice(convenient.getCourseOrderAndCourse().getCourseOrderUnitPrice());
                                                if (Integer.parseInt(convenient.getCourseOrderAndCourse().getCourseOrderEndTime().substring(11, 13)) < 13) {//上午
                                                    curriculumOrderParams.setAmSiteId(convenient.getCourseOrderAndCourse().getSiteId());
                                                    curriculumOrderParams.setAmSiteName(convenient.getCourseOrderAndCourse().getSiteName());
                                                    curriculumOrderParams.setAmSubject(convenient.getCourseOrderAndCourse().getCourseSubject());
                                                    curriculumOrderParams.setAmTimeType("am");
                                                    curriculumOrderParams.setAmType(convenient.getCourseOrderAndCourse().getCourseType());
                                                    curriculumOrderParams.setAmOrders(orders);
                                                } else {
                                                    curriculumOrderParams.setPmSiteId(convenient.getCourseOrderAndCourse().getSiteId());
                                                    curriculumOrderParams.setPmSiteName(convenient.getCourseOrderAndCourse().getSiteName());
                                                    curriculumOrderParams.setPmSubject(convenient.getCourseOrderAndCourse().getCourseSubject());
                                                    curriculumOrderParams.setPmTimeType("pm");
                                                    curriculumOrderParams.setPmType(convenient.getCourseOrderAndCourse().getCourseType());
                                                    curriculumOrderParams.setPmOrders(orders);
                                                }
                                                intent.putExtra("data", curriculumOrderParams);
                                                startActivity(intent);
//                                                Toast.makeText(mContext, "报名切...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    };
                    convenient_listview.setAdapter(adapter);

                    params = convenient_listview.getLayoutParams();
                    if (homeConvenients.size() > 0) {
                        View view = adapter.getView(0, null, convenient_listview);
                        view.measure(0, 0);
                        params.height = convenientItemHeight = view.getMeasuredHeight();
                        convenient_listview.setLayoutParams(params);
                        Logger.e(view.getMeasuredHeight() + "················");
                    } else {
                        params.height = convenientItemHeight = 0;
                        convenient_listview.setLayoutParams(params);
                    }
                } else {
                    if (0 == convenientItemHeight && homeConvenients.size() > 0) {
                        View view = adapter.getView(0, null, convenient_listview);
                        view.measure(0, 0);
                        convenientItemHeight = view.getMeasuredHeight();
                    }
                    if (0 == homeConvenients.size()) {
                        params.height = convenientItemHeight = 0;
                        convenient_listview.setLayoutParams(params);
                    } else if (0 < homeConvenients.size() && homeConvenients.size() < 5) {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        convenient_listview.setLayoutParams(params);
                    } else {
                        params.height = convenientItemHeight * 5;
                        convenient_listview.setLayoutParams(params);
                    }

                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }


    private int mBackKeyPressedTimes = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBackKeyPressedTimes == 0) {
                Snackbar.make(getWindow().getDecorView(), "再按一次退出程序 ", Snackbar.LENGTH_SHORT).show();
                mBackKeyPressedTimes = 1;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            mBackKeyPressedTimes = 0;
                        }
                    }
                }.start();
                return false;
            } else {
                AppManager.AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    SweetAlertDialog loadDialog;

    /**
     * 显示Loading对话框
     *
     * @return
     */
    public SweetAlertDialog getLodingAlertDialog() {
        if (null == loadDialog) {
            loadDialog = MyDialog.getInstance(this).getLoadingAlertDialog();
        }
        return loadDialog;
    }

    public abstract class MyCallBack extends StringCallback {
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            getLodingAlertDialog().show();
        }

        @Override
        public void onAfter() {
            super.onAfter();
            try {
                getLodingAlertDialog().dismiss();
            } catch (Exception e) {
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            if (!NetworkUtil.isNetAvailable(MainActivity.this)) {// 当前无网络！！
                MyDialog.getInstance(MainActivity.this).getWaringAlertDialog("当前无网络！！").show();
            } else if (e instanceof ConnectTimeoutException) {// 服务器链接超时
                MyDialog.getInstance(MainActivity.this).getWaringAlertDialog("链接超时").show();
            } else if (e instanceof SocketTimeoutException) {// 服务器未响应
                MyDialog.getInstance(MainActivity.this).getWaringAlertDialog("服务器未响应").show();
            } else {//服务器异常
                MyDialog.getInstance(MainActivity.this).getWaringAlertDialog("服务器异常").show();
            }
        }
    }

    /**
     * 做菜单
     */
    private void showLeftFrameView() {
        HighLight highLight = new HighLight(MainActivity.this)
//                .anchor(findViewById(R.id.rel_content))
                .addHighLight(R.id.left_frame, R.layout.highlight_top_left, HighLight.ViewPosInfo.DRAW_STYLE_CIRCLE, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.topMargin = rectF.bottom;
                    }
                }).setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        showHomeMenuQuestions();
                    }
                });
        highLight.show();
    }

    /**
     * 题库
     */
    private void showHomeMenuQuestions() {
        HighLight highLight = new HighLight(MainActivity.this)
                .addHighLight(R.id.home_questions, R.layout.highlight_home_menu_questions, HighLight.ViewPosInfo.DRAW_STYLE_CIRCLE, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.bottomMargin = bottomMargin + rectF.height() + 15;
//                        marginInfo.leftMargin = rectF.right - rectF.width() / 3;
                    }
                }).setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        showHomeMenuExamination();
                    }
                });
        highLight.show();
    }

    /**
     * 考试
     */
    private void showHomeMenuExamination() {
        HighLight highLight = new HighLight(MainActivity.this)
                .addHighLight(R.id.home_examination, R.layout.highlight_home_menu_examination, HighLight.ViewPosInfo.DRAW_STYLE_CIRCLE, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.bottomMargin = bottomMargin + rectF.height() + 15;
                    }
                }).setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
                        showHomeMenuCar();
                    }
                });
        highLight.show();
    }

    /**
     * 车车
     */
    private void showHomeMenuCar() {
        HighLight highLight = new HighLight(MainActivity.this)
                .addHighLight(R.id.rel_car_bak, R.layout.highlight_home_menu_car, HighLight.ViewPosInfo.DRAW_STYLE_CIRCLE, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.bottomMargin = bottomMargin + rectF.height() + 15;
                    }
                }).setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
//                        showHomeMenuRecord();
                        ShareUtil.getInstance(MainActivity.this).setLocalCookie(LocalCacheKey.FIRST_WELCOME, LocalCacheKey.FIRST_WELCOME);
                    }
                });
        highLight.show();
    }

  /*  private void showHomeMenuRecord() {
        HighLight highLight = new HighLight(MainActivity.this)
                .addHighLight(R.id.home_record, R.layout.highlight_home_menu_record, HighLight.ViewPosInfo.DRAW_STYLE_CIRCLE, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.bottomMargin = bottomMargin + rectF.height() + 15;
                    }
                }).setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {

                    }
                });
        highLight.show();
    }*/


    /**
     * 做菜单显示监听
     */
    public interface OnFragmentShowListener {
        void show();
    }
}
