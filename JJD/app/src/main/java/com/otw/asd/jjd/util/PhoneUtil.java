package com.otw.asd.jjd.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtil {
    private Context context;
    private static PhoneUtil phoneUtil;

    public PhoneUtil() {
        super();
    }

    public PhoneUtil(Context context) {
        super();
        this.context = context;
    }

    public static PhoneUtil getInstance(Context context) {
        if (null == phoneUtil) {
            phoneUtil = new PhoneUtil(context);
        }
        return phoneUtil;
    }

    /**
     * 打电话
     *
     * @param phoneno
     */
    public void call(String phoneno) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneno));
        context.startActivity(intent);
    }
}
