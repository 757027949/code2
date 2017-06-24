package com.otw.asd.jjdt.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareUtil {
    private static ShareUtil shareUtil;
    private SharedPreferences sp;

    public synchronized static ShareUtil getInstance(Context context) {
        if (null == shareUtil) {
            shareUtil = new ShareUtil(context);
        }
        return shareUtil;
    }

    public ShareUtil() {
        super();
    }

    public ShareUtil(Context context) {
        try {
            sp = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public void setLocalCookie(String currentSIdkey, String currentSIdVlalue) {
        Editor editor = sp.edit();
        editor.putString(currentSIdkey, currentSIdVlalue);
        editor.commit();
    }

    public String getLocalCookie(String currentSIdKey) {
        return sp.getString(currentSIdKey, "");
    }

    /**
     * 清除缓存
     */
    public void clear() {
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


}
