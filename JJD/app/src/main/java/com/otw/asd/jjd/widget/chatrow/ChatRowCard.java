package com.otw.asd.jjd.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.UserInfoActivity;
import com.otw.asd.jjd.base.Constant;

public class ChatRowCard extends EaseChatRow {

    ImageView person_image;
    TextView name, phone;

    public ChatRowCard(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_CARD, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.ease_row_received_card : R.layout.ease_row_sent_card, this);
        }
    }

    @Override
    protected void onFindViewById() {
        person_image = (ImageView) findViewById(R.id.person_image);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
    }

    @Override
    protected void onSetUpView() {
        name.setText(message.getStringAttribute("name", ""));
        phone.setText(message.getStringAttribute("phone", ""));
        Glide.with(context).load(message.getStringAttribute("imagePath", "")).error(R.mipmap.ic_person).into(person_image);
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onBubbleClick() {
        Intent intent = new Intent(getContext(), UserInfoActivity.class);
        intent.putExtra("flow", "other");
        intent.putExtra("userId", message.getStringAttribute("userId", ""));
        getContext().startActivity(intent);
    }


}
