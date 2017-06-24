/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.otw.asd.jjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.DateUtils;
import com.orhanobut.hawk.Hawk;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.ChatActivity;
import com.otw.asd.jjd.activity.UserInfoActivity;
import com.otw.asd.jjd.base.Constant;
import com.otw.asd.jjd.entity.Contact;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;


/**
 * Created by YOLANDA on 2016/7/22.
 */
public class HomeRightAdapter extends SwipeMenuAdapter<HomeRightAdapter.DefaultViewHolder> {
    Context context;

    private List<Contact> contacts;


    public HomeRightAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }


    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_home_right, parent, false);
    }

    @Override
    public HomeRightAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(HomeRightAdapter.DefaultViewHolder holder, int position) {
        final Contact contact = contacts.get(position);
        final EMConversation conversation = contact.getConversation();

        if (null != contact.getUserId() && !"".equals(contact.getUserId())) {
            Glide.with(context).load(contact.getImagePath()).error(R.mipmap.ic_logo).into(holder.person_image);
            holder.name.setText(contact.getName());
        } else {
            if (Hawk.contains(Contact.CONTACT_KEY)) {
                Map<String, Contact> contacts = Hawk.get(Contact.CONTACT_KEY);
                if (contacts.containsKey(conversation.getUserName())) {
                    Contact c = contacts.get(conversation.getUserName());
                    Glide.with(context).load(c.getImagePath()).error(R.mipmap.ic_logo).into(holder.person_image);
                    holder.name.setText(c.getName());
                }
            }
            /*if (!Hawk.contains(Contact.CONTACT_KEY)) {
                Map<String, Contact> contacts = new HashMap<String, Contact>();
                if (contacts.containsKey(conversation.getUserName())) {
                    Contact c = contacts.get(conversation.getUserName());
                    Glide.with(context).load(c.getImagePath()).error(R.mipmap.ic_logo).into(holder.person_image);
                    holder.name.setText(c.getName());
                }
            }*/
        }

        if (null != conversation && conversation.getAllMsgCount() != 0) {
            // show the content of latest message
            EMMessage lastMessage = conversation.getLastMessage();
            holder.mes.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, (context))),
                    TextView.BufferType.SPANNABLE);
        }

        holder.rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(context, R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(context, ChatActivity.class);
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    intent.putExtra("nickName", contact.getName());
                    intent.putExtra("flow", "right");
                    context.startActivity(intent);
                }
            }
        });
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rel_item;
        ImageView person_image;
        TextView name, mes;

        public DefaultViewHolder(View itemView) {
            super(itemView);

            rel_item = (RelativeLayout) itemView.findViewById(R.id.rel_item);
            person_image = (ImageView) itemView.findViewById(R.id.person_image);
            name = (TextView) itemView.findViewById(R.id.name);
            mes = (TextView) itemView.findViewById(R.id.mes);
        }
    }

}
