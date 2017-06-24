package com.asd.android.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.asd.android.util.NetworkUtil;

/**
 * 网络变化监听广播
 *
 * @author 123
 *
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (null == mobNetInfo) {
			if (!wifiNetInfo.isConnected()) {
				NetworkUtil.setIsConnected(false);
			}else{
				NetworkUtil.setIsConnected(true);
			}
		} else {
			if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {// 网络不可以用
				NetworkUtil.setIsConnected(false);
				// Toast.makeText(context, "网络不可以用...",
				// Toast.LENGTH_SHORT).show();
				// 改变背景或者 处理网络的全局变量
			} else {
				NetworkUtil.setIsConnected(true);
				// Toast.makeText(context, "网络可用...",
				// Toast.LENGTH_SHORT).show();
				// 改变背景或者 处理网络的全局变量
			}
		}
	}

}
