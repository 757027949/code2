package com.otw.asd.jjd.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.asd.android.util.ActivityUtil;
import com.bumptech.glide.Glide;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseFragmentActivity;
import com.otw.asd.jjd.fragment.ChatFragment;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 聊天页面
 * Created by Administrator on 2016/9/18.
 */

public class ChatActivity extends BaseFragmentActivity {

    @Bind(R.id.top_right)
    ImageView top_right;

    ChatFragment chatFragment;
    String toChatUsername;


    @Override
    public int bindLayout() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView(View view) {
        chooseLanguage();
        setTopTitle(getIntent().getStringExtra("nickName"));
        top_right.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.ic_right_menu).into(top_right);

        toChatUsername = getIntent().getExtras().getString("userId");
        chatFragment = new ChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @OnClick({R.id.top_right})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.top_right:
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setContentView(R.layout.dialog_chat_menu);
                window.findViewById(R.id.see).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChatActivity.this, UserInfoActivity.class);
                        intent.putExtra("flow", "other");
                        intent.putExtra("userId", toChatUsername);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                window.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog dialogWarning = new AlertDialog.Builder(ChatActivity.this).create();
                        dialogWarning.show();
                        Window window = dialogWarning.getWindow();
                        window.setContentView(R.layout.dialog_chat_menu_check_clear);
                        window.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogWarning.dismiss();
                            }
                        });
                        window.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chatFragment.clearMessage();
                                dialogWarning.dismiss();
                            }
                        });
                        dialog.dismiss();
                    }
                });
                window.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    /**
     * 设置语言
     */
    public void chooseLanguage() {
        String languageToLoad = "zh";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        config.locale = Locale.SIMPLIFIED_CHINESE;
        getResources().updateConfiguration(config, metrics);
    }
}
