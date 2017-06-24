package com.otw.asd.jjdt.base;

import android.app.Activity;
import android.os.Bundle;

import com.asd.android.util.app.AppManager;

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
