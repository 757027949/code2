package com.otw.asd.jjd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.PojoUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseFragmentActivity;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Contact;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.util.ShareUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 选择名片
 * Created by Administrator on 2016/9/18.
 */

public class ChooseChatCardActivity extends BaseFragmentActivity {
    @Bind(R.id.listview)
    ListView listview;

    Map<String, Contact> contacts;
    List<Contact> contactList = new ArrayList<>();
    CommonAdapter<Contact> adapter;

    Map<String, String> contactsParams = new HashMap<>();

    User user = null;

    @Override
    public int bindLayout() {
        return R.layout.activity_choose_chat_card;
    }

    @Override
    public void initView(View view) {
        setTopTitle("选择名片");
        user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(this).getLocalCookie(LocalCacheKey.LOCAL_USER));

        if (Hawk.contains(Contact.CONTACT_KEY)) {
            contacts = Hawk.get(Contact.CONTACT_KEY);
        }
        if (null != contacts) {
            contactList.addAll(contacts.values());
        }
        Contact currentContact = null, toContact = null;
        for (Contact contact : contactList) {
            if (contact.getUserId().equals(user.getObj().getUser().getUserId())) {
                currentContact = contact;
            } else if (contact.getUserId().equals(getIntent().getStringExtra("currentUserId"))) {
                toContact = contact;
            }
            if (null != currentContact && null != toContact) {
                break;
            }
        }
        try {
            contactList.remove(currentContact);
            contactList.remove(toContact);
        } catch (Exception e) {
        }

        listview.setAdapter(adapter = new CommonAdapter<Contact>(ChooseChatCardActivity.this, contactList, R.layout.adapter_layout_choose_chat_card_item) {
            @Override
            public void convert(final ViewHolder holder, final Contact contact) {
                holder.getView(R.id.line_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = holder.getView(R.id.cBox);
                        checkBox.setChecked(!checkBox.isChecked());
                        if (checkBox.isChecked()) {
                            contactsParams.put(contact.getUserId(), contact.getUserId());
                        } else {
                            if (contactsParams.containsKey(contact.getUserId())) {
                                contactsParams.remove(contact.getUserId());
                            }
                        }
                    }
                });
                CheckBox checkBox = holder.getView(R.id.cBox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            contactsParams.put(contact.getUserId(), contact.getUserId());
                        } else {
                            if (contactsParams.containsKey(contact.getUserId())) {
                                contactsParams.remove(contact.getUserId());
                            }
                        }
                    }
                });
                Glide.with(mContext).load(contact.getImagePath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                holder.setText(R.id.person_name, contact.getName());

            }
        });
    }

    @OnClick({R.id.sure})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.sure:
                Intent intent = new Intent();
                List<String> list = new ArrayList<>();
                list.addAll(contactsParams.values());
                intent.putStringArrayListExtra("data", (ArrayList<String>) list);
                setResult(RESULT_OK, intent);
                finishSelf();
                break;
        }
    }
}
