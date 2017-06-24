package com.otw.asd.jjdt.util;

/**
 * Created by Administrator on 2016/5/13.
 */
public class UrlUtil {
//    public static final String BASE_URL = "http://192.168.0.110:8080/school_v2/";
//    public static final String BASE_IMAGE_URL = "http://192.168.0.110:8080/school_v2/";
    public static final String BASE_URL = "http://android2.jiajiandang.com/";
    public static final String BASE_IMAGE_URL = "http://android2.jiajiandang.com/";
//    public static final String BASE_URL = "http://119.84.73.131:9080/school_v2/";
//    public static final String BASE_IMAGE_URL = "http://119.84.73.131:9080/school_v2/";


    /**
     * 使用条款及隐私
     */
    public static final String TERM_AND_HIDE = BASE_URL + "app_agree";
    /**
     * 获取更新信息
     * clientPlatform//平台（android,ios,winphone）;clientTerminal终端（student,teacher）学生端，教师端
     */
    public static final String GETCLIENT = BASE_URL + "getClient?clientPlatform=android&clientTerminal=teacher";

    /**
     * 发送验证码
     */
    public static final String SENDVALIDATECODE = BASE_URL + "sendValidateCode";

    /**
     * 注册
     */
    public static final String REGISTER = BASE_URL + "register";
    /**
     * 登录
     */
    public static final String LOGIN = BASE_URL + "login";
    /**
     * 重置密码
     */
    public static final String RESETLOGINPASSWORD = BASE_URL + "resetLoginPassword";
    /**
     * 登出
     */
    public static final String LOGOUT = BASE_URL + "logout";
    /**
     * 账户信息
     */
    public static final String USERINFO = BASE_URL + "getUser";
    /**
     * 获取驾校
     */
    public static final String SCHOOLLIST = BASE_URL + "schoolList";
    /**
     * 根据学校编号获取场地列表信息
     */
    public static final String SITEBYSCHOOLID = BASE_URL + "siteListBySchoolId";
    /**
     * 根据学校编号获取路线
     */
    public static final String ROADLISTBYSCHOOLID = BASE_URL + "roadListBySchoolId";
    /**
     * 教练认证
     */
    public static final String TEACHERVALIDATE = BASE_URL + "teacherValidate";
    /**
     * 课程安排
     */
    public static final String ADDCOURSE = BASE_URL + "courseOrderPlan";
    /**
     * 获取课程
     */
    public static final String COURSEORDERLISTBYTEACHERANDDATE = BASE_URL + "courseOrderListByTeacherAndDate";

    /**
     * 账户信息
     */
    public static final String ACCOUNT = BASE_URL + "getAccount";
    /**
     * 添加账户
     */
    public static final String ADDACCOUNTOTHER = BASE_URL + "addAccountOther";
    /**
     * 获取其他账户列表
     */
    public static final String ACCOUNTOTHERLIST = BASE_URL + "accountOtherList";
    /**
     * 账户提现
     */
    public static final String ACCOUNTEXIT = BASE_URL + "accountExit";
    /**
     * 获取默认账户(提现)
     */
    public static final String GETACCOUNTOTHERDEFAULT = BASE_URL + "getAccountOtherDefault";
    /**
     * 明细列表
     */
    public static final String ACCOUNTORDERLIST = BASE_URL + "accountOrderList";
    /**
     * 我的记录
     */
    public static final String COURSEORDERLISTBYTEACHER = BASE_URL + "courseOrderListByTeacher";
    /**
     * 单来了
     */
    public static final String COURSEORDERLISTHANDLERWAIT = BASE_URL + "courseOrderListHandlerWait";
    /**
     * 处理订单
     */
    public static final String COURSEORDERHANDLER = BASE_URL + "courseOrderHandler";
    /**
     * 根据地区获取场地
     */
    public static final String SITELISTBYAREA = BASE_URL + "siteListByArea";

}
