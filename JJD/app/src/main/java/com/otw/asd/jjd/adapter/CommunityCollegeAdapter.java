package com.otw.asd.jjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.asd.android.adapter.MultiItemCommonAdapter;
import com.asd.android.adapter.MultiItemTypeSupport;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.widget.PinnedSectionListView.PinnedSectionListAdapter;
import com.bumptech.glide.Glide;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.RecordInfoActivity;
import com.otw.asd.jjd.activity.UserInfoActivity;
import com.otw.asd.jjd.entity.ChatRedordParam;
import com.otw.asd.jjd.entity.PinnedSectionItem;
import com.otw.asd.jjd.entity.Record;
import com.otw.asd.jjd.fragment.RecordFragment;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录适配器
 * Created by Administrator on 2016/4/26.
 */
public class CommunityCollegeAdapter extends MultiItemCommonAdapter<String> {


    public CommunityCollegeAdapter(Context context, List<String> datas) {
        super(context, datas, new MultiItemTypeSupport<String>() {
            @Override
            public int getLayoutId(int position, String item) {
                if (0 == position % 2) {
                    return R.layout.layout_item_community_college_l;
                } else {
                    return R.layout.layout_item_community_college_s;
                }
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int postion, String item) {
                return 0 == postion % 2 ? 0 : 1;
            }
        });
    }


    @Override
    public void convert(final ViewHolder holder, String item) {
        switch (holder.getLayoutId()) {
            case R.layout.layout_item_community_college_l:

                break;

            case R.layout.layout_item_community_college_s:

                break;
        }
    }

}
