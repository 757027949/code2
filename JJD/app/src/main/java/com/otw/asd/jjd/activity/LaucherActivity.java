package com.otw.asd.jjd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.asd.android.cache.LocalCache;
import com.asd.android.util.PojoUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.LocalCacheKey;
import com.otw.asd.jjd.entity.User;
import com.otw.asd.jjd.util.ShareUtil;


/**
 * 加载页面
 *
 * @author 123
 */
public class LaucherActivity extends Activity {

    ImageView imageView1;

    class Myhandler extends Handler {
        public Myhandler() {
        }

        public Myhandler(Looper lp) {

            super(lp);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x11) {
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                new Thread() {
                    @Override
                    public void run() {
                        if (!"".equals(ShareUtil.getInstance(LaucherActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER))) {
                            User user = (User) PojoUtils.getInstance().getObject(ShareUtil.getInstance(LaucherActivity.this).getLocalCookie(LocalCacheKey.LOCAL_USER));
                            EMClient.getInstance().login(user.getObj().getUser().getUserId(), user.getObj().getUser().getUserLoginPassword(), new EMCallBack() {//回调
                                //                    EMClient.getInstance().login("123","123", new EMCallBack() {//回调
                                @Override
                                public void onSuccess() {
                                    EMClient.getInstance().chatManager().loadAllConversations();
//                        Logger.e("登录聊天服务器成功！");
                                }

                                @Override
                                public void onProgress(int progress, String status) {

                                }

                                @Override
                                public void onError(int code, String message) {
//                        Logger.e(code + "登录聊天服务器失败！");
                                }
                            });
                        }
                    }
                }.start();
                tologinpage();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_laucher);

        imageView1 = (ImageView) findViewById(R.id.imageView1);

        //启动网络状态变化监听
//		if (!ActivityUtil.isServiceRunning(this,
//				"com.example.xutiltext.service.NetworkStateService")) {
//			Intent i = new Intent(this, NetworkStateService.class);
//			startService(i);
//		}

        HandlerThread ht = new HandlerThread("" + System.currentTimeMillis());
        ht.start();
        Myhandler mu = new Myhandler(ht.getLooper());
        Message ms = mu.obtainMessage();
        ms.what = 0x11;
        ms.sendToTarget();

    }

    // 跳转到登陆页面
    public void tologinpage() {
        Intent intent = new Intent();
        /*if (null == LocalCache.get(this).getAsObject(LocalCacheKey.LOCAL_USER)) {//本地不存在用户
            intent.setClass(LaucherActivity.this, LoginActivity.class);
        } else {
            intent.setClass(LaucherActivity.this, MainActivity.class);
//            intent.setClass(LaucherActivity.this, UpdateTaskActivity.class);
        }*/
        intent.setClass(LaucherActivity.this, MainActivity.class);

        startActivity(intent);

        finish();
        // overridePendingTransition(R.anim.enteralpha,R.anim.exitalpha);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        BitmapUtil.releaseImageViewResouce(imageView1);
    }
}
