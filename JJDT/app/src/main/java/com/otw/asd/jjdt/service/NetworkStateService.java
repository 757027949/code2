package com.otw.asd.jjdt.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.orhanobut.logger.Logger;
import com.otw.asd.jjdt.receiver.ConnectionChangeReceiver;


public class NetworkStateService extends Service {

	private ConnectionChangeReceiver myReceiver;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Logger.e("onCreate");
		registerReceiver();
	}

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		myReceiver = new ConnectionChangeReceiver();
		registerReceiver(myReceiver, filter);
	}

	private void unregisterReceiver() {
		unregisterReceiver(myReceiver);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Logger.e("onDestroy");
		unregisterReceiver();
	}

}
