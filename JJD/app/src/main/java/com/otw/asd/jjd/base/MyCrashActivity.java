package com.otw.asd.jjd.base;

import android.app.Activity;
import android.os.Bundle;

import com.asd.android.util.app.AppManager;
import com.orhanobut.logger.Logger;

/**
 * 异常Activity
 * Created by Administrator on 2016/4/21.
 */
public class MyCrashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.restartApp(this);
    }
}
