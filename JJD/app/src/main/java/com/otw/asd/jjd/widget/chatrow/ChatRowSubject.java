package com.otw.asd.jjd.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asd.android.util.BitmapUtil;
import com.asd.android.util.PojoUtils;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.ChooseTeacherActivity;
import com.otw.asd.jjd.activity.TeacherInfoActivity;
import com.otw.asd.jjd.activity.UserInfoActivity;
import com.otw.asd.jjd.base.Constant;

import java.io.ByteArrayOutputStream;

public class ChatRowSubject extends EaseChatRow {

    ImageView person_image;

    public ChatRowSubject(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_SUBJECT, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.ease_row_received_subject : R.layout.ease_row_sent_subject, this);
        }
    }

    @Override
    protected void onFindViewById() {
        person_image = (ImageView) findViewById(R.id.person_image);
    }

    @Override
    protected void onSetUpView() {
        Bitmap bitmap = BitmapUtil.getInstance().convertStringToIcon(message.getStringAttribute("image", ""));
        Glide.with(context).load(BitmapUtil.getInstance().Bitmap2Bytes(bitmap)).error(R.mipmap.ic_person).into(person_image);
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onBubbleClick() {
        Intent intent = new Intent(getContext(), TeacherInfoActivity.class);
        intent.putExtra("teacherId", message.getStringAttribute("teacherId", ""));
        intent.putExtra("userId", message.getStringAttribute("userId", ""));
        getContext().startActivity(intent);
    }


}
