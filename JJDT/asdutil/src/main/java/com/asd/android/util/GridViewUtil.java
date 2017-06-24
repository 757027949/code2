package com.asd.android.util;

import android.content.Context;
import android.widget.GridView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASD on 2016/1/11.
 */
public abstract class GridViewUtil<T> {
    private Context context;
    private GridView gridView;
    private List<T> tList = new ArrayList<>();
    CommonAdapter<T> adapter;
    private final int layoutId;

    /**
     *
     * @param context
     * @param gridView
     * @param tList
     * @param layoutId
     */
    public GridViewUtil(Context context, GridView gridView, List<T> tList, int layoutId) {
        this.context = context;
        this.gridView = gridView;
        this.layoutId = layoutId;
        this.tList.clear();
        this.tList.addAll(tList);
    }

    public void initGridView() {
        gridView.setAdapter(adapter = new CommonAdapter<T>(context, tList, layoutId) {
            @Override
            public void convert(ViewHolder holder, T t) {
                convert(holder, t);
            }
        });
    }

    public abstract void convert(ViewHolder holder, T t);
}
