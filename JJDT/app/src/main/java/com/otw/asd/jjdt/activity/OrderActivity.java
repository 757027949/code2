package com.otw.asd.jjdt.activity;

import android.os.Message;
import android.view.View;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.Toast;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.cache.LocalCache;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.RefreshUtil;
import com.asd.android.widget.MyDialog;
import com.asd.util.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.base.LocalCacheKey;
import com.otw.asd.jjdt.entity.Order;
import com.otw.asd.jjdt.entity.PinnedSectionItem;
import com.otw.asd.jjdt.entity.Record;
import com.otw.asd.jjdt.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 单来了
 * Created by Administrator on 2016/4/20.
 */
public class OrderActivity extends BaseActivity {
    @Bind(R.id.no_data_layout)
    ViewStub no_data_layout;
    View no_data_layout_view = null;

    @Bind(R.id.listview)
    ListView listview;

    String flow = "";
    ArrayList<Order> orders = new ArrayList<>();
    HashMap<String, Order> orderMap = new HashMap<>();
    CommonAdapter<Order> adapter;

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void initView(View view) {
        setTopTitle("单来了");

        flow = getIntent().getStringExtra("flow");
        if ("main".equals(flow)) {//主页
            try {
                orderMap.putAll((HashMap<String, Order>) LocalCache.get(this).getAsObject(LocalCacheKey.ORDERS));
            } catch (Exception e) {
            }
        } else {
            try {
                Order order = (Order) getIntent().getSerializableExtra("order");
                orderMap.put(order.getCourseOrderId(), order);
            } catch (Exception e) {
            }
        }
        if (0 == orderMap.size()) {
            if (null == no_data_layout_view) {
                no_data_layout_view = no_data_layout.inflate();
            } else {
                no_data_layout_view.setVisibility(View.VISIBLE);
            }
        } else {
            if (null != no_data_layout_view) {
                no_data_layout_view.setVisibility(View.GONE);
            }
        }
        for (Map.Entry<String, Order> map : orderMap.entrySet()) {
            orders.add(map.getValue());
        }
        if (null == adapter) {
            listview.setAdapter(adapter = new CommonAdapter<Order>(OrderActivity.this, orders, R.layout.adapter_layout_order_item) {
                @Override
                public void convert(ViewHolder holder, Order order) {
                    try {
                        holder.setText(R.id.name, order.getUserNickName());
                        holder.setText(R.id.subject, order.getCourseSubject());
                        holder.setText(R.id.type, order.getCourseType());
                        holder.setText(R.id.day, order.getCourseOrderBeginTime().substring(0, 10));
                        holder.setText(R.id.time, order.getCourseOrderBeginTime().substring(11, 16) + "-" + order.getCourseOrderEndTime().substring(11, 16));
                        holder.setText(R.id.address, order.getSiteId());
                    } catch (Exception e) {
                    }
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void finishSelf() {
        super.finishSelf();
        if ("main".equals(flow)) {//主页
            orderMap.clear();
            LocalCache.get(this).put(LocalCacheKey.ORDERS, orderMap);
        } else {
            orderMap.putAll((HashMap<String, Order>) LocalCache.get(this).getAsObject(LocalCacheKey.ORDERS));

            if (orderMap.containsKey(orders.get(0).getCourseOrderId())) {
                orderMap.remove(orderMap.get(orders.get(0).getCourseOrderId()));
            }
        }
    }
}
