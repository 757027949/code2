package com.otw.asd.jjd.base;

/**
 * 本地缓存KEY键
 * Created by Administrator on 2016/4/7.
 */
public class LocalCacheKey {
    static String packgeName = "com.otw.asd.jjd";

    public static final String KEY_FIRST_INSTALL = packgeName + "first_install";
    public static final String VALUE_FIRST_INSTALLED = packgeName + "install";

    public static final String FLOAT_VIEW_X = packgeName + "FLOAT_VIEW_X";
    public static final String FLOAT_VIEW_Y = packgeName + "FLOAT_VIEW_Y";

    public static final String LOCAL_USER = packgeName + "local_user";
    public static final String LOCAL_USER_CONFIG = packgeName + "local_user_config";

    public static final int FLOW_SESSION_TIME = 60 * 60 * 12;
    public static final String FLOW_SESSION = packgeName + "flow_session";

    //登录
    public static final String FLOW_USER_ACCOUNTS = packgeName + "flow_user_accounts";
    public static final String FLOW_USER_PWD = packgeName + "flow_user_pwd";

    /**
     * 用户等级
     */
    public static final String USER_EXP = packgeName + "user_exp";

    /**
     * 默认账户 （提现）
     */
    public static final String ACCOUNTOTHERDEFAULT = packgeName + "AccountOtherDefault";

    /**
     * 公告
     */
    public static final String PROCLAMATION = packgeName + "proclamation";

    /**
     * 是否第一次进入
     */
    public static final String FIRST_WELCOME = packgeName + "first_welcome";

    /**
     * 是否约束定位
     */
    public static final String CHECK_LOCATION = packgeName + "check_location";

}
