package com.otw.asd.jjd.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.BitmapUtil;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.Constant;
import com.otw.asd.jjd.entity.ChatRedordParam;
import com.otw.asd.jjd.entity.CommunityInfo;
import com.otw.asd.jjd.entity.Contact;
import com.otw.asd.jjd.entity.PinnedSectionItem;
import com.otw.asd.jjd.entity.Record;
import com.otw.asd.jjd.util.EMConversationUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 社区分享
 * Created by Administrator on 2016/3/31.
 */
public class CommunityShareActivity extends BaseActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    //获取当前所有的会话
    final int GET_ALL_CONVERSATIONS = 0;
    final int SET_ALL_CONVERSATIONS = 1;

    List<Contact> contacts = new ArrayList<>();
    CommonAdapter<Contact> adapter;
    List<String> userIds = new ArrayList<>();
    Map<String, Contact> map = new HashMap<>();

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case GET_ALL_CONVERSATIONS:
                new Thread() {
                    @Override
                    public void run() {
                        contacts.clear();
                        List<EMConversation> list = EMConversationUtil.getInstance().loadConversationList();//获取聊天信息列表
                        Map<String, Contact> contactsMap = null;//聊天人信息
                        if (Hawk.contains(Contact.CONTACT_KEY)) {
                            contactsMap = Hawk.get(Contact.CONTACT_KEY);
                        }
                        if (null != list) {
                            int i = 0;
                            for (final EMConversation emConversation : list) {
                                Contact contact = new Contact();
                                if (null != contactsMap && contactsMap.containsKey(emConversation.getUserName())) {
                                    contact = contactsMap.get(emConversation.getUserName());
                                    contact.setChecked(false);
                                }
                                contact.setConversation(emConversation);
                                contact.setPosition(i++);
                                contacts.add(contact);
                                map.put(contact.getUserId(), contact);
                            }
                        }
                        mHandler.sendEmptyMessage(SET_ALL_CONVERSATIONS);
                    }
                }.start();
                break;
            case SET_ALL_CONVERSATIONS:
                if (null == adapter) {
                    recycler_view.setAdapter(adapter = new CommonAdapter<Contact>(this, R.layout.layout_item_community_share, contacts) {
                        @Override
                        protected void convert(final ViewHolder holder, final Contact s, final int position) {

                            holder.setChecked(R.id.cBox, s.isChecked());
                            Glide.with(mContext).load(s.getImagePath()).error(R.mipmap.ic_person).into((ImageView) holder.getView(R.id.person_image));
                            holder.setText(R.id.name, s.getName());

                            holder.setOnClickListener(R.id.rel_item, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    contacts.get(position).setChecked(!s.isChecked());
                                    holder.setChecked(R.id.cBox, !s.isChecked());
                                }
                            });
                            ((CheckBox) holder.getView(R.id.cBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    contacts.get(position).setChecked(isChecked);
                                    if (isChecked) {
                                        if (userIds.size() > 0) {//保证永远都只有一个对象
                                            contacts.get(map.get(userIds.get(0)).getPosition()).setChecked(false);
//                                            adapter.notifyItemChanged(map.get(userIds.get(0)).getPosition());
                                            userIds.remove(0);
                                        }
                                        userIds.add(s.getUserId());

                                        adapter.notifyDataSetChanged();
                                    } else {
                                        if (userIds.contains(s.getUserId())) {
                                            userIds.remove(s.getUserId());
                                        }
                                    }
                                }
                            });
                        }
                    });
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_community_share;
    }

    @Override
    public void initView(View view) {
        setTopTitle("分享到");

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        mHandler.sendEmptyMessage(GET_ALL_CONVERSATIONS);
    }

    @OnClick({R.id.sure})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.sure:
                if (0 == userIds.size()) {
                    Toast.makeText(this, "请选择分享人员...", Toast.LENGTH_SHORT).show();
                } else {
                    final CommunityInfo communityInfo = getIntent().getParcelableExtra("communityInfo");
                    final AlertDialog dialog = new AlertDialog.Builder(this).create();
                    LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_community_share, null);
                    dialog.setView(layout);

                    dialog.show();
                    final Window window = dialog.getWindow();
                    window.setContentView(R.layout.dialog_community_share);
//                window.findViewById(R.id.line).setAlpha(0.9f);
                    window.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    window.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EMConversation conversation = map.get(userIds.get(0)).getConversation();
                            String username = conversation.getUserName();
                            if (username.equals(EMClient.getInstance().getCurrentUser()))
                                Toast.makeText(CommunityShareActivity.this, R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                            else {
                                // start chat acitivity
                                Intent intent = new Intent(CommunityShareActivity.this, ChatActivity.class);
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
                                intent.putExtra("nickName", communityInfo.getUser().getUserNickName());
                                intent.putExtra("flow", "share");
                                intent.putExtra("image", getIntent().getStringExtra("image"));
                                intent.putExtra("communityInfo", communityInfo);
                                intent.putExtra("mes", ((FormEditText) window.findViewById(R.id.mes)).getText().toString());
                                startActivity(intent);
                            }
                            dialog.dismiss();
                        }
                    });

                    window.findViewById(R.id.line).setAlpha(0.85f);
                    Bitmap bitmap = BitmapUtil.getInstance().convertStringToIcon(getIntent().getStringExtra("image"));
                    Glide.with(this).load(BitmapUtil.getInstance().Bitmap2Bytes(bitmap)).error(R.mipmap.ic_person).into((ImageView) window.findViewById(R.id.image));
                    TextView send_who = (TextView) window.findViewById(R.id.send_who);
                    send_who.setText("分享到" + map.get(userIds.get(0)).getName());
                    TextView who = (TextView) window.findViewById(R.id.who);
                    who.setText("分享 " + communityInfo.getUser().getUserNickName() + " 的帖子");
                    TextView college = (TextView) window.findViewById(R.id.college);
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
                }
                break;
        }
    }
}
