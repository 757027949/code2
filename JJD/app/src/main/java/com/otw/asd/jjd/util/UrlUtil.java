package com.otw.asd.jjd.util;

/**
 * Created by Administrator on 2016/4/26.
 */
public class UrlUtil {
  /*  private static UrlUtil urlUtil;

    public synchronized static UrlUtil getInstance() {
        if (null == urlUtil) {
            urlUtil = new UrlUtil();
        }
        return urlUtil;
    }*/

    /**
     * 模拟考试 科目一
     */
    public static final String SUBJECT_ONE = "http://m.jxedt.com/mnks/ckm1/";

    /**
     * 模拟考试 科目四
     */
    public static final String SUBJECT_FOUR = "http://m.jxedt.com/mnks/ckm4/";

    public static final String BASE_URL = "http://android2.jiajiandang.com/";
    public static final String BASE_IMAGE_URL = "http://android2.jiajiandang.com/";
//    public static final String BASE_URL = "http://119.84.73.131:9080/school_v2/";
//    public static final String BASE_IMAGE_URL = "http://119.84.73.131:9080/school_v2/";
//    public static final String BASE_URL = "http://192.168.0.113:8080/school_v2/";
//    public static final String BASE_IMAGE_URL = "http://192.168.0.113:8080/school_v2/";
//    public static final String BASE_URL = "http://ios.jiajiandang.com/";
//    public static final String BASE_IMAGE_URL = "http://ios.jiajiandang.com/";

    /**
     * 使用条款及隐私
     */
    public static final String TERM_AND_HIDE = BASE_URL + "app_agree";

    /**
     * 获取更新信息
     * clientPlatform//平台（android,ios,winphone）;clientTerminal终端（student,teacher）学生端，教师端
     */
    public static final String GETCLIENT = BASE_URL + "getClient?clientPlatform=android&clientTerminal=student";

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
    public static final String GETUSER = BASE_URL + "getUser";
    /**
     * 更新用户信息
     */
    public static final String UPDATEUSERINFO = BASE_URL + "updateUserInfo";
    /**
     * 提交意见
     */
    public static final String SUGGESTION = BASE_URL + "suggestion";
    /**
     * 报名配置清单
     */
    public static final String APPLYREQUEST = BASE_URL + "applyRequest";
    /**
     * 支付（获取订单信息）
     */
    public static final String ACCOUNTPAY = BASE_URL + "accountPay";
    /**
     * 报名（付款成功之后调用）
     */
    public static final String APPLY = BASE_URL + "apply";
    /**
     * 获取报名信息
     */
    public static final String APPLYINFO = BASE_URL + "applyInfo";
    /**
     * 账户信息
     */
    public static final String ACCOUNT = BASE_URL + "getAccount";
    /**
     * 明细列表
     */
    public static final String ACCOUNTORDERLIST = BASE_URL + "accountOrderList";
    /**
     * 账户充值
     */
    public static final String ACCOUNTRECHARGE = BASE_URL + "accountRecharge";
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
     * 我的记录
     */
    public static final String COURSEORDERLISTBYSTUDENT = BASE_URL + "courseOrderListByStudent";
    /**
     * 记录详情
     */
    public static final String GETCOURSEORDERDETAIL = BASE_URL + "getCourseOrderDetail";
    /**
     * 获取申请退款状态
     */
    public static final String GETREFUNDAPPLYSTATE = BASE_URL + "getRefundApplyState";
    /**
     * 记录详情评价
     */
    public static final String ADDCOMMENT = BASE_URL + "addComment";
    /**
     * 申请退款
     */
    public static final String ADDREFUNDAPPLY = BASE_URL + "addRefundApply";
    /**
     * 附近场地
     */
    public static final String SITELISTBYNEAR = BASE_URL + "siteListByNear";
    /**
     * 筛选教练
     */
    public static final String TEACHERLISTBYCOURSEORDER = BASE_URL + "teacherListByCourseOrder";
    /**
     * 推荐教练
     */
    public static final String TEACHERLISTBYRECOMMEND = BASE_URL + "teacherListByRecommend";
    /**
     * 教练信息（预约教练）
     */
    public static final String GETTEACHERDETAIL = BASE_URL + "getTeacherDetail";
    /**
     * 根据地区获取场地
     */
    public static final String SITELISTBYAREA = BASE_URL + "siteListByArea";
    /**
     * 所有评论
     */
    public static final String COMMENTLISTBYUSER = BASE_URL + "commentListByUser";
    /**
     * 根据教师编号和日期获取课程列表
     */
    public static final String COURSEORDERLISTREALBYTEACHERANDDATE = BASE_URL + "courseOrderListRealByTeacherAndDate";
    /**
     * 添加订单
     */
    public static final String ADDACCOUNTORDER = BASE_URL + "addAccountOrder";
    /**
     * 课程报名
     */
    public static final String COURSEORDERAPPLY = BASE_URL + "courseOrderApply";
    /**
     * 获取支付凭证
     */
    public static final String GETCHARGE = BASE_URL + "getCharge";
    /**
     * 修改支付密码
     */
    public static final String UPDATEPAYPASSWORD = BASE_URL + "updatePayPassword";
    /**
     * 取消订单
     */
    public static final String UPDATECOURSEORDERBYCANCEL = BASE_URL + "updateCourseOrderByCancel";
    /**
     * 获取优惠券
     */
    public static final String ACCOUNTCOUPONLIST = BASE_URL + "accountCouponList";
    /**
     * 兑换优惠券
     */
    public static final String GETACCOUNTCOUPON = BASE_URL + "getAccountCoupon";
    /**
     * 添加第三方登录
     */
    public static final String ADDUSEROTHER = BASE_URL + "addUserOther";
    /**
     * 修改单个像册
     */
    public static final String UPDATEUSERALBUM = BASE_URL + "updateUserAlbum";
    /**
     * 新增像册
     */
    public static final String ADDUSERALBUM = BASE_URL + "addUserAlbum";
    /**
     * 根据人数一键学车
     */
    public static final String COURSEORDERBYPEOPLE = BASE_URL + "courseOrderByPeople";
    /**
     * 根据时间一键学车
     */
    public static final String COURSEORDERBYTIME = BASE_URL + "courseOrderByTime";
    /**
     * 根据附近一键学车
     */
    public static final String COURSERORDERBYDISTANCE = BASE_URL + "courserOrderByDistance";
    /**
     * 获取经验等级
     */
    public static final String GETEXP = BASE_URL + "getExp";
    /**
     * 社区发布动态
     */
    public static final String ADDCOMMON = BASE_URL + "addCommon";
    /**
     * 社区签到
     */
    public static final String SIGN = BASE_URL + "sign";
    /**
     * 动态列表
     */
    public static final String COMMONS = BASE_URL + "commons";
    /**
     * 动态详情
     */
    public static final String GETCOMMON = BASE_URL + "getCommon";
    /**
     * 评论，添加回复
     */
    public static final String ADDREPLY = BASE_URL + "addReply";
    /**
     * 回复列表
     */
    public static final String REPLYS = BASE_URL + "replys";
    /**
     * 分享统计
     */
    public static final String ADDSHARE = BASE_URL + "addShare";
    /**
     * 获取用户配置
     */
    public static final String GETUSERCONFIG = BASE_URL + "getUserConfig";
    /**
     * 删除像册
     */
    public static final String DELETEUSERALBUM = BASE_URL + "deleteUserAlbum";

}
