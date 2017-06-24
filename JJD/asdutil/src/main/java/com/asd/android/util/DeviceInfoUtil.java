package com.asd.android.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.asd.android.util.gesture.GestureUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ASD on 2016/1/18.
 */
public class DeviceInfoUtil {
    public DeviceInfoUtil() {
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//          versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取设置的IMSI
     *
     * @param mTelephonyMgr 手机管理者
     * @param context       上下文
     * @return IMSI信息
     */
    public static String getIMSI(TelephonyManager mTelephonyMgr, Context context) {
        String imsi = mTelephonyMgr.getSubscriberId();
        if (null == imsi || imsi.equals("")) {
            // IMEI: 仅仅只对Android手机有效:
            String m_szImei = mTelephonyMgr.getDeviceId();
            // 通过取出ROM版本、制造商、CPU型号、以及其他硬件信息来实现这一点。这样计算出来的ID不是唯一的（因为如果两个手机应用了同样的硬件以及Rom
            // 镜像）
            String m_szDevIDShort = "35" + Build.BOARD.length() % 10
                    + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
                    + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
                    + Build.HOST.length() % 10 + Build.ID.length() % 10
                    + Build.MANUFACTURER.length() % 10 + Build.MODEL.length()
                    % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length()
                    % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;// we
            // make
            // this
            // look
            // like
            // a
            // valid
            // IMEI
            // Returns: 9774d56d682e549c
            // 通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置。并且，如果某个Andorid手机被Root过的话，这个ID也可以被任意改变
            String m_szAndroidID = Settings.Secure.getString(
                    context.getContentResolver(), Settings.Secure.ANDROID_ID);
            WifiManager wm = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            // Returns: 00:11:22:33:44:55 (这不是一个真实的地址。而且这个地址能轻易地被伪造)
            String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth
            // adapter
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            // Returns: 43:25:78:50:93:38 . 蓝牙没有必要打开，也能读取
            // 只在有蓝牙的设备上运行。并且要加入android.permission.BLUETOOTH 权限
            String m_szBTMAC = m_BluetoothAdapter.getAddress();
            // 拼接后的计算出的MD5,产生32位的16进制数据
            String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID
                    + m_szWLANMAC + m_szBTMAC;
            // compute md5
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
            // get md5 bytes
            byte p_md5Data[] = m.digest();
            // create a hex string
            String m_szUniqueID = new String();
            for (int i = 0; i < p_md5Data.length; i++) {
                int b = (0xFF & p_md5Data[i]);
                // if it is a single digit, make sure it have 0 in front (proper
                // padding)
                if (b <= 0xF)
                    m_szUniqueID += "0";
                // add number to string
                m_szUniqueID += Integer.toHexString(b);
            } // hex string to uppercase
            m_szUniqueID = m_szUniqueID.toUpperCase();

            imsi = m_szUniqueID;
        }
        return imsi;
    }

    /**
     * 获取顶部status bar 高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }

    /**
     * 获取底部 navigation bar 高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    /**
     * 获取虚拟功能键高度
     *
     * @param activity
     * @return
     */
    public static int getVirtualFunctionKeyHeight(Activity activity) {
        return GestureUtils.getScreenPix(activity).originalHeightPixels - GestureUtils.getScreenPix(activity).heightPixels;
    }


}
