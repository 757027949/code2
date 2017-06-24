package com.otw.asd.jjdt.base;

/**
 * 本地缓存KEY键
 * Created by Administrator on 2016/4/20.
 */
public class LocalCacheKey {
    static String packgeName = "com.otw.asd.jjdt";

    /**
     * 应用第一次安装对应KEY
     */
    public static final String KEY_FIRST_INSTALL = packgeName + "first_install";
    /**
     * 应用第一次安装对于那个VALUE
     */
    public static final String VALUE_FIRST_INSTALLED = packgeName + "install";

    /**
     * 获取当前用户信息
     */
    public static final String KEY_USER = packgeName + "key_user_t";

    public static final int FLOW_SESSION_TIME = 60 * 60 * 12;
    public static final String FLOW_SESSION = packgeName + "flow_session_t";

    //登录
    public static final String FLOW_USER_ACCOUNTS = packgeName + "flow_user_accounts_t";
    public static final String FLOW_USER_PWD = packgeName + "flow_user_pwd_t";

    /**
     * 默认账户 （提现）
     */
    public static final String ACCOUNTOTHERDEFAULT = packgeName + "AccountOtherDefault_t";
    /**
     * 订单
     */
    public static final String ORDERS = packgeName + "orders";
    /**
     * 安排课程是否同意过
     */
    public static final String SET_CURRICULUM_CHECK = packgeName + "set_curriculum_check";
}
