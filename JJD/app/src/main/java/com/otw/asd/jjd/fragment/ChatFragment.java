package com.otw.asd.jjd.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.PojoUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.NetUtils;
import com.hyphenate.util.PathUtil;
import com.orhanobut.hawk.Hawk;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.ChooseChatCardActivity;
import com.otw.asd.jjd.activity.RecordActivity;
import com.otw.asd.jjd.base.BaseFragment;
import com.otw.asd.jjd.base.Constant;
import com.otw.asd.jjd.entity.ChatRedordParam;
import com.otw.asd.jjd.entity.CommunityInfo;
import com.otw.asd.jjd.entity.Contact;
import com.otw.asd.jjd.util.UrlUtil;
import com.otw.asd.jjd.widget.chatrow.ChatRowCard;
import com.otw.asd.jjd.widget.chatrow.ChatRowCommunity;
import com.otw.asd.jjd.widget.chatrow.ChatRowSubject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static android.app.Activity.RESULT_FIRST_USER;

/**
 * Created by Administrator on 2016/9/19.
 */

public class ChatFragment extends BaseFragment implements EMMessageListener {
    /**
     * params to fragment
     */
    protected Bundle fragmentArgs;
    protected int chatType;
    protected String toChatUsername;

    @Bind(R.id.message_list)
    EaseChatMessageList messageList;
    @Bind(R.id.input_menu)
    EaseChatInputMenu inputMenu;

    @Bind(R.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;

    EMConversation conversation;

    InputMethodManager inputManager;


    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;
    protected static final int REQUEST_CODE_CARD = 4;
    protected static final int REQUEST_CODE_SUBJECT = 5;
    File cameraFile;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;

    private static final int MESSAGE_TYPE_SENT_COMMUNITY = 5;
    private static final int MESSAGE_TYPE_RECV_COMMUNITY = 6;
    private static final int MESSAGE_TYPE_SENT_CARD = 1;
    private static final int MESSAGE_TYPE_RECV_CARD = 2;
    private static final int MESSAGE_TYPE_SENT_SUBJECT = 3;
    private static final int MESSAGE_TYPE_RECV_SUBJECT = 4;


    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;
    static final int ITEM_CARD = 4;
    static final int ITEM_SUBJECT = 5;

    protected int[] itemStrings = {R.string.chat_phont, R.string.chat_picture, R.string.chat_location, R.string.chat_card, R.string.chat_subject};
    protected int[] itemdrawables = {R.drawable.ic_chat_takepic_selector, R.drawable.ic_chat_image_selector, R.drawable.ic_chat_location_selector, R.drawable.ic_chat_card_selector, R.drawable.ic_chat_subject_selector};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION, ITEM_CARD, ITEM_SUBJECT};

    boolean isMessageListInited;

    final int SHARE_COMMUNITY = 0;
    final int ADDSHARE = 1;

    @Override
    public void onResume() {
        super.onResume();
        if (isMessageListInited)
            messageList.refresh();
        EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        EMClient.getInstance().chatManager().addMessageListener(this);

        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SHARE_COMMUNITY:
                mHandler.sendEmptyMessage(ADDSHARE);
                sendCommunityMessage(fragmentArgs.getString("image"), JSON.toJSONString(fragmentArgs.getParcelable("communityInfo")));
                String mes = fragmentArgs.getString("mes");
                if (!"".equals(mes)) {
                    sendTextMessage(mes);
                }
                break;
            case ADDSHARE:
                new Thread() {
                    @Override
                    public void run() {
                        CommunityInfo communityInfo = fragmentArgs.getParcelable("communityInfo");
                        try {
                            OkHttpUtils.post().url(UrlUtil.ADDSHARE)
                                    .addParams("commonId", communityInfo.getCommon().getCommonId())
                                    .build()
                                    .execute();
                        } catch (IOException e) {
                        }
                    }
                }.start();
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_chat;
    }


    @Override
    public void initView(View view) {
        fragmentArgs = getArguments();
        // check if single chat or group chat 聊天类型
        chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // userId you are chat with or group id 聊天ID
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);

        listView = messageList.getListView();
        swipeRefreshLayout = messageList.getSwipeRefreshLayout();

        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onConversationInit();
            onMessageListInit();
        }
        setRefreshLayoutListener();

        registerExtendMenuItem();
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
                sendTextMessage(content);
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
//                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });

        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if ("share".equals(fragmentArgs.getString("flow"))) {
//            Toast.makeText(mContext, fragmentArgs.getString("mes"), Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(SHARE_COMMUNITY);
        }
    }

    /**
     * 删除和某个user会话
     */
    public void clearMessage() {
        EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
        if (isMessageListInited) {
            messageList.refreshSelectLast();
        }
    }


    /**
     * 聊天记录初始化
     */
    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        conversation.markAllMessagesAsRead();
        // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
        // you can change this number
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }

    }

    /**
     * 初始化messagelist
     */
    private void onMessageListInit() {
//        messageList.init(toChatUsername, chatType, null);
        messageList.init(toChatUsername, chatType, new CustomChatRowProvider());
        //设置item里的控件的点击事件
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                //头像点击事件
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                //头像长按事件
            }

            @Override
            public void onResendClick(final EMMessage message) {
                //重发消息按钮点击事件
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                //气泡框长按事件
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                //气泡框点击事件，EaseUI有默认实现这个事件，如果需要覆盖，return值要返回true
                return false;
            }
        });

        messageList.getListView().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });
        isMessageListInited = true;
    }


    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 6;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //card
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_CARD, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_CARD : MESSAGE_TYPE_SENT_CARD;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_SUBJECT, false)) {
                    //subject
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_SUBJECT : MESSAGE_TYPE_SENT_SUBJECT;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_COMMUNITY, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_COMMUNITY : MESSAGE_TYPE_SENT_COMMUNITY;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // card
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_CARD, false)) {
                    return new ChatRowCard(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_SUBJECT, false)) {
                    //subject
                    return new ChatRowSubject(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_COMMUNITY, false)) {
                    return new ChatRowCommunity(getActivity(), message, position, adapter);
                }
            }
            return null;
        }

    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<EMMessage> messages;
                            try {
                                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                } else {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                }
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(com.hyphenate.easeui.R.string.no_more_messages),
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }


    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], new MyItemClickListener());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
                if (cameraFile != null && cameraFile.exists())
                    sendImageMessage(cameraFile.getAbsolutePath());
            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_MAP) { // location
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    sendLocationMessage(latitude, longitude, locationAddress);
                } else {
                    Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == REQUEST_CODE_CARD) {
                Map<String, Contact> contacts = Hawk.get(Contact.CONTACT_KEY);
                List<String> list = data.getStringArrayListExtra("data");
                for (String string : list) {
                    if (null != contacts && contacts.containsKey(string)) {
                        sendCardMessage(contacts.get(string));
                    }
                }
            } else if (requestCode == REQUEST_CODE_SUBJECT) {
                List<ChatRedordParam> redordParams = data.getParcelableArrayListExtra("data");
                List<String> imagesString = data.getStringArrayListExtra("images");
                for (int i = 0; i < redordParams.size(); i++) {
                    sendSubjectMessage(redordParams.get(i), imagesString.get(i));
                }
            }
        }
    }

    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

        @Override
        public void onClick(int itemId, View view) {
            switch (itemId) {
                case ITEM_TAKE_PICTURE:
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal();
                    break;
                case ITEM_LOCATION:
                    startActivityForResult(new Intent(getActivity(), EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
                    break;
                case ITEM_CARD:
                    Intent intent = new Intent(mContext, ChooseChatCardActivity.class);
                    intent.putExtra("currentUserId", toChatUsername);
                    startActivityForResult(intent, REQUEST_CODE_CARD);
                    break;

                case ITEM_SUBJECT:
                    intent = new Intent(mContext, RecordActivity.class);
                    intent.putExtra("flow", "chat");
                    startActivityForResult(intent, REQUEST_CODE_SUBJECT);
                    break;

                default:
                    break;
            }
        }

    }

    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        //noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }

    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }


    protected void sendCommunityMessage(String imageString, String communityInfo) {
        EMMessage message = EMMessage.createTxtSendMessage("社区分享", toChatUsername);
        message.setAttribute(Constant.MESSAGE_ATTR_IS_COMMUNITY, true);
        message.setAttribute("image", imageString);
        message.setAttribute("communityInfo", communityInfo);
        sendMessage(message);
    }

    protected void sendCardMessage(Contact contact) {
        EMMessage message = EMMessage.createTxtSendMessage(getResString(R.string.chat_card_mes), toChatUsername);
        message.setAttribute(Constant.MESSAGE_ATTR_IS_CARD, true);
        message.setAttribute("userId", contact.getUserId());
        message.setAttribute("imagePath", contact.getImagePath());
        message.setAttribute("name", contact.getName());
        message.setAttribute("phone", contact.getSex());
        sendMessage(message);
    }

    protected void sendSubjectMessage(ChatRedordParam param, String imageString) {
        EMMessage message = EMMessage.createTxtSendMessage(("课时分享"), toChatUsername);
        message.setAttribute(Constant.MESSAGE_ATTR_IS_SUBJECT, true);
        message.setAttribute("userId", param.getUserId());
        message.setAttribute("teacherId", param.getTeacherId());
        message.setAttribute("image", imageString);
        sendMessage(message);
    }

    protected void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
        sendMessage(message);
    }

    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        sendMessage(message);
    }

    protected void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        sendMessage(message);
    }

    protected void sendTextMessage(String content) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        sendMessage(message);
    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            message.setChatType(EMMessage.ChatType.GroupChat);
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            message.setChatType(EMMessage.ChatType.ChatRoom);
        }
        //send message
        EMClient.getInstance().chatManager().sendMessage(message);
        //refresh ui
        if (isMessageListInited) {
            messageList.refreshSelectLast();
        }
    }

    public void onMessageReceived(List<EMMessage> messages) {
        for (EMMessage message : messages) {
            String username = null;
            // group message
            if (message.getChatType() == EMMessage.ChatType.GroupChat || message.getChatType() == EMMessage.ChatType.ChatRoom) {
                username = message.getTo();
            } else {
                // single chat message
                username = message.getFrom();
            }

            // if the message is for current conversation
            if (username.equals(toChatUsername)) {
                messageList.refreshSelectLast();
                EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
            } else {
                EaseUI.getInstance().getNotifier().onNewMsg(message);
            }
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> messages) {
        if (isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> messages) {
        if (isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object change) {
        if (isMessageListInited) {
            messageList.refresh();
        }
    }

    protected void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
