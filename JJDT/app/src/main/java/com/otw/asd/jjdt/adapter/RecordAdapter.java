package com.otw.asd.jjdt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.asd.android.adapter.MultiItemCommonAdapter;
import com.asd.android.adapter.MultiItemTypeSupport;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.widget.PinnedSectionListView.PinnedSectionListAdapter;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.activity.RecordInfoActivity;
import com.otw.asd.jjdt.entity.PinnedSectionItem;
import com.otw.asd.jjdt.entity.Record;

import java.util.List;

/**
 * 记录适配器
 * Created by Administrator on 2016/4/26.
 */
public class RecordAdapter extends MultiItemCommonAdapter<PinnedSectionItem> implements PinnedSectionListAdapter {
    public RecordAdapter(Context context, List<PinnedSectionItem> datas) {
        super(context, datas, new MultiItemTypeSupport<PinnedSectionItem>() {
            @Override
            public int getLayoutId(int position, PinnedSectionItem item) {
                if (item.getItemType() == PinnedSectionItem.SECTION) {
                    return R.layout.adapter_layout_section_item;
                } else {
                    return R.layout.adapter_layout_record_item;
                }
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int postion, PinnedSectionItem item) {
                return item.getItemType();
            }
        });
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return PinnedSectionItem.SECTION == viewType;
    }

    @Override
    public void convert(ViewHolder holder, PinnedSectionItem item) {
        switch (holder.getLayoutId()) {
            case R.layout.adapter_layout_section_item:
                holder.setText(R.id.section_item, item.getSectionString());
                break;

            case R.layout.adapter_layout_record_item:
                final Record.DatasBean.CourseOrdersBean courseOrdersBean = (Record.DatasBean.CourseOrdersBean) item;
                holder.setText(R.id.way, courseOrdersBean.getPeopleNumber() > 1 ? "多人学车" : "单人学车");
                holder.setText(R.id.type, courseOrdersBean.getCourseType());
                holder.setText(R.id.subject, courseOrdersBean.getCourseSubject());
                holder.setText(R.id.time, courseOrdersBean.getCourseOrderBeginTime().substring(8, courseOrdersBean.getCourseOrderBeginTime().length() - 3) + "-" +
                        courseOrdersBean.getCourseOrderEndTime().substring(courseOrdersBean.getCourseOrderEndTime().length() - 8, courseOrdersBean.getCourseOrderEndTime().length() - 3));
                holder.setText(R.id.address, courseOrdersBean.getSiteName());
                holder.setOnClickListener(R.id.rel_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, RecordInfoActivity.class);
                        intent.putExtra("data", courseOrdersBean);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }
}
