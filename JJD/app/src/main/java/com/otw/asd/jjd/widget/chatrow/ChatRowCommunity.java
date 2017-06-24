package com.otw.asd.jjd.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.PojoUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.exceptions.HyphenateException;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.CommunityCollegeInfoActivity;
import com.otw.asd.jjd.activity.CommunityHotInfoActivity;
import com.otw.asd.jjd.activity.TeacherInfoActivity;
import com.otw.asd.jjd.base.Constant;
import com.otw.asd.jjd.entity.CommunityInfo;

public class ChatRowCommunity extends EaseChatRow {

    ImageView person_image;
    TextView who, title, college;
    CommunityInfo communityInfo;

    public ChatRowCommunity(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_COMMUNITY, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.ease_row_received_community : R.layout.ease_row_sent_community, this);
        }
    }

    @Override
    protected void onFindViewById() {
        person_image = (ImageView) findViewById(R.id.person_image);
        who = (TextView) findViewById(R.id.who);
        title = (TextView) findViewById(R.id.title);
        college = (TextView) findViewById(R.id.college);
    }

    @Override
    protected void onSetUpView() {
        Bitmap bitmap = BitmapUtil.getInstance().convertStringToIcon(message.getStringAttribute("image", ""));
        Glide.with(context).load(BitmapUtil.getInstance().Bitmap2Bytes(bitmap)).error(R.mipmap.ic_person).into(person_image);
        try {
            communityInfo = JSON.parseObject(message.getStringAttribute("communityInfo"),CommunityInfo.class);
            who.setText("分享 " + communityInfo.getUser().getUserNickName() + " 的帖子");
            title.setText(communityInfo.getCommon().getCommonTitle());
            String[] results = getResources().getStringArray(R.array.community_college);
            if ("A".equals(communityInfo.getCommon().getCommonType())) {
                college.setText(results[0]);
            } else if ("B".equals(communityInfo.getCommon().getCommonType())) {
                college.setText(results[1]);
            } else if ("C".equals(communityInfo.getCommon().getCommonType())) {
                college.setText(results[2]);
            } else if ("D".equals(communityInfo.getCommon().getCommonType())) {
                college.setText(results[3]);
            }
        } catch (HyphenateException e) {
        }
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onBubbleClick() {
        if (null == communityInfo) {
            return;
        }
        Intent intent;
        if ("ALL".equals(communityInfo.getCommon().getCommonType())) {
            intent = new Intent(getContext(), CommunityHotInfoActivity.class);
        } else {
            intent = new Intent(getContext(), CommunityCollegeInfoActivity.class);
        }
        intent.putExtra("commonId", communityInfo.getCommon().getCommonId());
        getContext().startActivity(intent);
    }


}
