package com.otw.asd.jjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.asd.android.adapter.MultiItemCommonAdapter;
import com.asd.android.adapter.MultiItemTypeSupport;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.PojoUtils;
import com.asd.android.widget.PinnedSectionListView.PinnedSectionListAdapter;
import com.bumptech.glide.Glide;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.RecordInfoActivity;
import com.otw.asd.jjd.activity.UserInfoActivity;
import com.otw.asd.jjd.entity.ChatRedordParam;
import com.otw.asd.jjd.entity.HomeConvenient;
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
public class RecordAdapter extends MultiItemCommonAdapter<PinnedSectionItem> implements PinnedSectionListAdapter {
    RecordFragment recordFragment;
    View view;

    public RecordFragment getRecordFragment() {
        return recordFragment;
    }

    public void setRecordFragment(RecordFragment recordFragment) {
        this.recordFragment = recordFragment;
    }

    public RecordAdapter(Context context, List<PinnedSectionItem> datas) {
        super(context, datas, new MultiItemTypeSupport<PinnedSectionItem>() {
            @Override
            public int getLayoutId(int position, PinnedSectionItem item) {
                if (item.getItemType() == PinnedSectionItem.SECTION) {
                    return R.layout.adapter_layout_section_item;
                } else {
                    return R.layout.layout_item_record;
//                    return R.layout.adapter_layout_record_item;
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
        return 0 == viewType;
    }

    @Override
    public void convert(final ViewHolder holder, PinnedSectionItem item) {
        switch (holder.getLayoutId()) {
            case R.layout.adapter_layout_section_item:
                holder.setText(R.id.section_item, item.getSectionString());
                break;

            case R.layout.layout_item_record:
                final Record.DatasBean.CourseOrdersBean ordersBean = (Record.DatasBean.CourseOrdersBean) item;
                Glide.with(mContext).load(ordersBean.getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                holder.setText(R.id.subject, ordersBean.getCourseType() + "/" + ordersBean.getCourseSubject());
                holder.setText(R.id.time, ordersBean.getCourseOrderBeginTime().substring(0, 16) + "-" + ordersBean.getCourseOrderEndTime().substring(11, 16));
                holder.setText(R.id.address, ordersBean.getSiteName());
                holder.setOnClickListener(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ordersBean.isShowCheckBox()) {
                            Intent intent = new Intent(mContext, RecordInfoActivity.class);
                            intent.putExtra("courseOrderId", ordersBean.getCourseOrderId());
                            mContext.startActivity(intent);
                        } else {
                            PinnedSectionItem pinnedSectionItem = recordFragment.getPinnedSectionItems().get(holder.getPosition());
                            if (pinnedSectionItem instanceof Record.DatasBean.CourseOrdersBean) {
                                boolean checked = ((Record.DatasBean.CourseOrdersBean) pinnedSectionItem).isChecked();
                                ((Record.DatasBean.CourseOrdersBean) recordFragment.getPinnedSectionItems().get(holder.getPosition())).setChecked(!checked);
                                holder.setChecked(R.id.cBox, !checked);
                            }
                        }
                    }
                });
                holder.setVisible(R.id.cBox, ordersBean.isShowCheckBox());
                holder.setChecked(R.id.cBox, ordersBean.isChecked());
                ((CheckBox) holder.getView(R.id.cBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        ((Record.DatasBean.CourseOrdersBean) recordFragment.getPinnedSectionItems().get(holder.getPosition())).setChecked(isChecked);

                        saveOrRemoveParam(ordersBean, isChecked, holder.getView(R.id.item));
                    }
                });

                SwipeMenuRecyclerView recycler_view = holder.getView(R.id.recycler_view);
                recycler_view.setLayoutManager(new GridLayoutManager(mContext, 4));
                recycler_view.setHasFixedSize(true);
                recycler_view.setItemAnimator(new DefaultItemAnimator());

                List<Record.DatasBean.CourseOrdersBean.UsersBean> usersBeanList = new ArrayList<>();
                usersBeanList.addAll(ordersBean.getUsers());
                for (int i = ordersBean.getUsers().size(); i < ordersBean.getPeopleNumber(); i++) {
                    usersBeanList.add(null);
                }
                recycler_view.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<Record.DatasBean.CourseOrdersBean.UsersBean>(mContext, R.layout.layout_item_home_convenient, usersBeanList) {

                    @Override
                    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, Record.DatasBean.CourseOrdersBean.UsersBean usersBean, int position) {
                        if (null != usersBean) {
                            Glide.with(mContext).load(usersBean.getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                            holder.getView(R.id.person_image).setTag(R.id.home_convenient_item_id, usersBean.getUserId());
                        }

                        holder.setOnClickListener(R.id.person_image, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (null != v.getTag(R.id.home_convenient_item_id)) {//有人且别人
                                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                                    intent.putExtra("flow", recordFragment.getUser().getObj().getUser().getUserId().equals(v.getTag(R.id.home_convenient_item_id)) ? "self" : "other");
                                    intent.putExtra("userId", v.getTag(R.id.home_convenient_item_id).toString());
                                    mContext.startActivity(intent);
//                                                Toast.makeText(mContext, "别个...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                break;

            case R.layout.adapter_layout_record_item:
                final Record.DatasBean.CourseOrdersBean courseOrdersBean = (Record.DatasBean.CourseOrdersBean) item;
                holder.setText(R.id.name, courseOrdersBean.getTeacherName());
                holder.setText(R.id.type, courseOrdersBean.getCourseType());
                holder.setText(R.id.day, courseOrdersBean.getCourseOrderBeginTime().substring(0, 10));
                holder.setText(R.id.time, courseOrdersBean.getCourseOrderBeginTime().substring(11, 16) + "-" + courseOrdersBean.getCourseOrderEndTime().substring(11, 16));
                holder.setText(R.id.subject, courseOrdersBean.getCourseSubject());
                holder.setText(R.id.address, courseOrdersBean.getSiteName());
                holder.setText(R.id.record_status, courseOrdersBean.getCourseOrderState());
                holder.setText(R.id.create_time, courseOrdersBean.getCourseOrderTime());

                holder.setOnClickListener(R.id.rel_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, RecordInfoActivity.class);
                        intent.putExtra("courseOrderId", courseOrdersBean.getCourseOrderId());
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    public void saveOrRemoveParam(Record.DatasBean.CourseOrdersBean ordersBean, boolean isChecked, View view) {
        if (isChecked) {
//            String imageString = BitmapUtil.getInstance().convertIconToString(BitmapUtil.convertViewToBitmap(view));
            ChatRedordParam redordParam = new ChatRedordParam();
            redordParam.setUserId(ordersBean.getUserId());
            redordParam.setTeacherId(ordersBean.getTeacherId());
            recordFragment.getRedordParamMap().put(ordersBean.getCourseId(), redordParam);
            recordFragment.getViewMap().put(ordersBean.getCourseId(), view);
        } else {
            if (recordFragment.getRedordParamMap().containsKey(ordersBean.getCourseId())) {
                recordFragment.getRedordParamMap().remove(ordersBean.getCourseId());
                recordFragment.getViewMap().remove(ordersBean.getCourseId());
            }
        }
    }
}
