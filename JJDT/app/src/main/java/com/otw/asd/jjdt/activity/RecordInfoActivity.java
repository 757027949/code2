package com.otw.asd.jjdt.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;
import com.otw.asd.jjdt.entity.Record;
import com.otw.asd.jjdt.util.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/11.
 */
public class RecordInfoActivity extends BaseActivity {
    @Bind(R.id.way)
    TextView way;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.subject)
    TextView subject;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.listview)
    ListView listview;

    List<Record.DatasBean.CourseOrdersBean.User> users = new ArrayList<>();
    CommonAdapter<Record.DatasBean.CourseOrdersBean.User> adapter;

    Record.DatasBean.CourseOrdersBean courseOrdersBean;

    @Override
    public int bindLayout() {
        return R.layout.activity_record_info;
    }

    @Override
    public void initView(View view) {
        setTopTitle("记录详情");

        courseOrdersBean = (Record.DatasBean.CourseOrdersBean) getIntent().getSerializableExtra("data");

        way.setText(courseOrdersBean.getPeopleNumber() > 1 ? "多人学车" : "单人学车");
        type.setText(courseOrdersBean.getCourseType());
        subject.setText(courseOrdersBean.getCourseSubject());
        time.setText(courseOrdersBean.getCourseOrderBeginTime().substring(0, courseOrdersBean.getCourseOrderBeginTime().length() - 3) + "-" +
                courseOrdersBean.getCourseOrderEndTime().substring(courseOrdersBean.getCourseOrderEndTime().length() - 8, courseOrdersBean.getCourseOrderEndTime().length() - 3));
        address.setText(courseOrdersBean.getSiteName());

        users.addAll(courseOrdersBean.getUsers());
        listview.setAdapter(adapter = new CommonAdapter<Record.DatasBean.CourseOrdersBean.User>(RecordInfoActivity.this, users, R.layout.adapter_layout_record_info_item) {
            @Override
            public void convert(ViewHolder holder, final Record.DatasBean.CourseOrdersBean.User user) {
                try {
                    holder.setText(R.id.person_name, "".equals(user.getUserRealName()) ? user.getUserNickName() : user.getUserRealName());
                    holder.setText(R.id.person_phone, user.getUserRegisterTelephone());
                    holder.setOnClickListener(R.id.call, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PhoneUtil.getInstance(mContext).call(user.getUserRegisterTelephone());
                        }
                    });
                    Glide.with(mContext).load(user.getUserHeadPath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                } catch (Exception e) {
                }
            }
        });
    }
}
