package com.otw.asd.jjd.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.PojoUtils;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.bumptech.glide.Glide;
import com.github.mmin18.widget.RealtimeBlurView;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.MainActivity;
import com.otw.asd.jjd.adapter.HomeRightAdapter;
import com.otw.asd.jjd.base.BaseFragment;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.Contact;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.util.EMConversationUtil;
import com.otw.asd.jjd.util.ShareUtil;
import com.otw.asd.jjd.util.UrlUtil;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/3/30.
 */
public class HomeRightFragment extends BaseFragment {

    @Bind(R.id.image_bak)
    ImageView image_bak;
    @Bind(R.id.search)
    FormEditText search;
    @Bind(R.id.blur_view)
    RealtimeBlurView blur_view;
    @Bind(R.id.recycler_view)
    SwipeMenuRecyclerView recycler_view;


    //获取当前所有的会话
    final int GET_ALL_CONVERSATIONS = 0;
    final int SET_ALL_CONVERSATIONS = 1;
    final int GETUSER = 2;

    User user;
    List<Contact> contacts = new ArrayList<>();
    HomeRightAdapter adapter;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case GET_ALL_CONVERSATIONS:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            contacts.clear();
                            List<EMConversation> list = EMConversationUtil.getInstance().loadConversationList();//获取聊天信息列表
                            Logger.e(list.size() + "----------------");
                            Map<String, Contact> contactsMap = null;//聊天人信息
                            if (Hawk.contains(Contact.CONTACT_KEY)) {
                                contactsMap = Hawk.get(Contact.CONTACT_KEY);
                            }
                            if (null != list) {
                                for (final EMConversation emConversation : list) {
                                    Contact contact = new Contact();
                                    if (null != contactsMap && contactsMap.containsKey(emConversation.getUserName())) {
                                        contact = contactsMap.get(emConversation.getUserName());
                                    } else {
                                        //获取用户信息
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                mHandler.sendMessage(mHandler.obtainMessage(GETUSER, emConversation.getUserName()));
                                            }
                                        }.start();
                                    }
                                    contact.setConversation(emConversation);
                                    contacts.add(contact);
                                }
                            }

                            mHandler.sendEmptyMessage(SET_ALL_CONVERSATIONS);
                        } catch (Exception e) {
                            Logger.e(e.getMessage());
                        }
                    }
                }.start();

                break;

            case SET_ALL_CONVERSATIONS:
                if (null == adapter) {
                    adapter = new HomeRightAdapter(mContext, contacts);
                    recycler_view.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;

            case GETUSER:
                final String userId = (String) msg.obj;
                OkHttpUtils.post().url(UrlUtil.GETUSER)
                        .addParams("userId", userId)
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return true;
                            }

                            @Override
                            public Context getContext() {
                                return mContext;
                            }

                            @Override
                            public SweetAlertDialog getLoadDialog() {
                                return getLodingAlertDialog();
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    User user = new Gson().fromJson(response, User.class);
                                    if (user.isSuccess()) {
                                        Map<String, Contact> contacts;
                                        if (!Hawk.contains(Contact.CONTACT_KEY)) {
                                            contacts = new HashMap<String, Contact>();
                                        } else {
                                            contacts = Hawk.get(Contact.CONTACT_KEY);
                                        }
//                                        Contact contact = new Contact(user.getObj().getUser().getUserId(), 0 != user.getObj().getUserAlbums().size() ? user.getObj().getUserAlbums().get(0).getUserAlbumPath() : "", user.getObj().getUser().getUserNickName(), user.getObj().getUser().getUserSex());
                                        Contact contact = new Contact(user.getObj().getUser().getUserId(), user.getObj().getUser().getUserHeadPath(), user.getObj().getUser().getUserNickName(), user.getObj().getUser().getUserSex());
                                        contacts.put(userId, contact);
                                        Hawk.put(Contact.CONTACT_KEY, contacts);

                                        mHandler.sendEmptyMessage(SET_ALL_CONVERSATIONS);
                                    }
                                } catch (Exception e) {
                                    Logger.e(e.getMessage());
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_right;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(mContext).getLocalCookie(LocalCacheKey.LOCAL_USER));
            if (null != user) {
                Glide.with(mContext).load(user.getObj().getUser().getUserHeadPath()).placeholder(R.mipmap.ic_logo).error(R.mipmap.ic_logo).into(image_bak);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void initView(View view) {
        ViewCompoundDrawableUtil viewCompoundDrawableUtil = new ViewCompoundDrawableUtil(getContext());
        viewCompoundDrawableUtil.initTextViewCompoundDrawable(search, 0, 0.03f, 0, 1);
        search.setFocusable(true);
        blur_view.setAlpha(0.85f);

        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setSwipeMenuCreator(swipeMenuCreator);
        recycler_view.setSwipeMenuItemClickListener(menuItemClickListener);
//        recycler_view.addItemDecoration(new ListViewDecoration());


        ((MainActivity) mContext).setOnFragmentShowListener(new MainActivity.OnFragmentShowListener() {
            @Override
            public void show() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(GET_ALL_CONVERSATIONS);
                    }
                }).start();
            }
        });

/*
        list.setAdapter(adapter = new CommonAdapter<String>(getContext(), strings, R.layout.layout_item_home_right) {
            @Override
            public void convert(ViewHolder holder, String s) {
                BadgeView badgeView = new BadgeView(getContext(), holder.getView(R.id.person_image));
                badgeView.setText("10");
                badgeView.setBadgeMargin(0);
                badgeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 25);
                badgeView.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
                badgeView.show();
            }
        });*/
    }


    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
//            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int width = getResources().getDimensionPixelSize(R.dimen.home_right_menu_item_width);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                SwipeMenuItem clearItem = new SwipeMenuItem(mContext)
                        .setBackgroundColor(Color.parseColor("#797979"))
                        .setText("清空聊天记录") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(clearItem);// 添加一个按钮到右侧侧菜单。

            }
        }
    };

    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();

                //删除和某个user会话，如果需要保留聊天记录，传false
                EMClient.getInstance().chatManager().deleteConversation(contacts.get(adapterPosition).getUserId(), true);
                if (Hawk.contains(Contact.CONTACT_KEY)) {
                    Map<String, Contact> contactMap = Hawk.get(Contact.CONTACT_KEY);
                    if (null != contactMap && contactMap.containsKey(contacts.get(adapterPosition).getUserId())) {
                        contactMap.remove(contacts.get(adapterPosition).getUserId());
                    }
//                    Hawk.put(Contact.CONTACT_KEY, contacts);
                }

                contacts.remove(adapterPosition);
                adapter.notifyItemRemoved(adapterPosition);
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @OnClick(R.id.search)
    public void searchClick() {
        Toast.makeText(mContext, "search", Toast.LENGTH_SHORT).show();
    }

}
