package com.otw.asd.jjdt.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class Record implements Serializable {

    /**
     * date : 116-5
     * courseOrders : [{"courseId":"402880ec554db16c01554dc58a7b0003","courseOrderBeginTime":"2016-06-14 16:00:00","courseOrderDetailId":"402880ec554db16c01554dc58ab60004","courseOrderEndTime":"2016-06-14 17:00:00","courseOrderId":"402880ec554db16c01554dc58af80005","courseOrderState":"未接单","courseOrderTime":"2016-06-14 15:17:19","courseSubject":"科目三","courseTotalPrice":0,"courseType":"C2","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":3,"siteId":"","teacherId":"402880e55504f25c015505f410820001","users":[]}]
     */
    private int totalCount;

    private List<DatasBean> datas;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable {
        private String date;
        /**
         * courseId : 402880ec554db16c01554dc58a7b0003
         * courseOrderBeginTime : 2016-06-14 16:00:00
         * courseOrderDetailId : 402880ec554db16c01554dc58ab60004
         * courseOrderEndTime : 2016-06-14 17:00:00
         * courseOrderId : 402880ec554db16c01554dc58af80005
         * courseOrderState : 未接单
         * courseOrderTime : 2016-06-14 15:17:19
         * courseSubject : 科目三
         * courseTotalPrice : 0.0
         * courseType : C2
         * isFinish : N
         * isSccept :
         * isUpdate : Y
         * peopleNumber : 3
         * siteId :
         * teacherId : 402880e55504f25c015505f410820001
         * users : []
         */

        private List<CourseOrdersBean> courseOrders;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<CourseOrdersBean> getCourseOrders() {
            return courseOrders;
        }

        public void setCourseOrders(List<CourseOrdersBean> courseOrders) {
            this.courseOrders = courseOrders;
        }

        public static class CourseOrdersBean extends PinnedSectionItem implements Serializable {
            private String courseId;
            private String courseOrderBeginTime;
            private String courseOrderDetailId;
            private String courseOrderEndTime;
            private String courseOrderId;
            private String courseOrderState;
            private String courseOrderTime;
            private String courseSubject;
            private double courseTotalPrice;
            private String courseType;
            private String isFinish;
            private String isSccept;
            private String isUpdate;
            private int peopleNumber;
            private String siteId;
            private String siteName;
            private String teacherId;
            private List<User> users;

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCourseOrderBeginTime() {
                return courseOrderBeginTime;
            }

            public void setCourseOrderBeginTime(String courseOrderBeginTime) {
                this.courseOrderBeginTime = courseOrderBeginTime;
            }

            public String getCourseOrderDetailId() {
                return courseOrderDetailId;
            }

            public void setCourseOrderDetailId(String courseOrderDetailId) {
                this.courseOrderDetailId = courseOrderDetailId;
            }

            public String getCourseOrderEndTime() {
                return courseOrderEndTime;
            }

            public void setCourseOrderEndTime(String courseOrderEndTime) {
                this.courseOrderEndTime = courseOrderEndTime;
            }

            public String getCourseOrderId() {
                return courseOrderId;
            }

            public void setCourseOrderId(String courseOrderId) {
                this.courseOrderId = courseOrderId;
            }

            public String getCourseOrderState() {
                return courseOrderState;
            }

            public void setCourseOrderState(String courseOrderState) {
                this.courseOrderState = courseOrderState;
            }

            public String getCourseOrderTime() {
                return courseOrderTime;
            }

            public void setCourseOrderTime(String courseOrderTime) {
                this.courseOrderTime = courseOrderTime;
            }

            public String getCourseSubject() {
                return courseSubject;
            }

            public void setCourseSubject(String courseSubject) {
                this.courseSubject = courseSubject;
            }

            public double getCourseTotalPrice() {
                return courseTotalPrice;
            }

            public void setCourseTotalPrice(double courseTotalPrice) {
                this.courseTotalPrice = courseTotalPrice;
            }

            public String getCourseType() {
                return courseType;
            }

            public void setCourseType(String courseType) {
                this.courseType = courseType;
            }

            public String getIsFinish() {
                return isFinish;
            }

            public void setIsFinish(String isFinish) {
                this.isFinish = isFinish;
            }

            public String getIsSccept() {
                return isSccept;
            }

            public void setIsSccept(String isSccept) {
                this.isSccept = isSccept;
            }

            public String getIsUpdate() {
                return isUpdate;
            }

            public void setIsUpdate(String isUpdate) {
                this.isUpdate = isUpdate;
            }

            public int getPeopleNumber() {
                return peopleNumber;
            }

            public void setPeopleNumber(int peopleNumber) {
                this.peopleNumber = peopleNumber;
            }

            public String getSiteId() {
                return siteId;
            }

            public void setSiteId(String siteId) {
                this.siteId = siteId;
            }

            public String getSiteName() {
                return siteName;
            }

            public void setSiteName(String siteName) {
                this.siteName = siteName;
            }

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
            }

            public List<User> getUsers() {
                return users;
            }

            public void setUsers(List<User> users) {
                this.users = users;
            }

            public static class User implements Serializable{

                /**
                 * accountOrderId :
                 * addressId : 402880f155ca50680155ca514b350002
                 * byUserId :
                 * byUserNickName :
                 * clientPlatform : android
                 * courseOrders : null
                 * isApply : N
                 * isTeacher : N
                 * userAge : 0
                 * userContactTelephone :
                 * userDisableBeginTime :
                 * userDisableEndTime :
                 * userDisableExplain : 非法操作！
                 * userHeadPath :
                 * userId : 402880f155ca50680155ca51267d0000
                 * userIdCard :
                 * userIdCardObversePath :
                 * userIdCardReversePath :
                 * userLoginPassword : 6302e4aa693fcf99d012be5578001f0b
                 * userNickName :
                 * userPayPassword : 14e1b600b1fd579f47433b88e8d85291
                 * userRealName :
                 * userRegisterTelephone : 15730365901
                 * userRegisterTime : 2016-07-11 16:27:58
                 * userSex : 女
                 * userSign :
                 */

                private String accountOrderId;
                private String addressId;
                private String byUserId;
                private String byUserNickName;
                private String clientPlatform;
//                private Object courseOrders;
                private String isApply;
                private String isTeacher;
                private int userAge;
                private String userContactTelephone;
                private String userDisableBeginTime;
                private String userDisableEndTime;
                private String userDisableExplain;
                private String userHeadPath;
                private String userId;
                private String userIdCard;
                private String userIdCardObversePath;
                private String userIdCardReversePath;
                private String userLoginPassword;
                private String userNickName;
                private String userPayPassword;
                private String userRealName;
                private String userRegisterTelephone;
                private String userRegisterTime;
                private String userSex;
                private String userSign;

                public String getAccountOrderId() {
                    return accountOrderId;
                }

                public void setAccountOrderId(String accountOrderId) {
                    this.accountOrderId = accountOrderId;
                }

                public String getAddressId() {
                    return addressId;
                }

                public void setAddressId(String addressId) {
                    this.addressId = addressId;
                }

                public String getByUserId() {
                    return byUserId;
                }

                public void setByUserId(String byUserId) {
                    this.byUserId = byUserId;
                }

                public String getByUserNickName() {
                    return byUserNickName;
                }

                public void setByUserNickName(String byUserNickName) {
                    this.byUserNickName = byUserNickName;
                }

                public String getClientPlatform() {
                    return clientPlatform;
                }

                public void setClientPlatform(String clientPlatform) {
                    this.clientPlatform = clientPlatform;
                }

//                public Object getCourseOrders() {
//                    return courseOrders;
//                }
//
//                public void setCourseOrders(Object courseOrders) {
//                    this.courseOrders = courseOrders;
//                }

                public String getIsApply() {
                    return isApply;
                }

                public void setIsApply(String isApply) {
                    this.isApply = isApply;
                }

                public String getIsTeacher() {
                    return isTeacher;
                }

                public void setIsTeacher(String isTeacher) {
                    this.isTeacher = isTeacher;
                }

                public int getUserAge() {
                    return userAge;
                }

                public void setUserAge(int userAge) {
                    this.userAge = userAge;
                }

                public String getUserContactTelephone() {
                    return userContactTelephone;
                }

                public void setUserContactTelephone(String userContactTelephone) {
                    this.userContactTelephone = userContactTelephone;
                }

                public String getUserDisableBeginTime() {
                    return userDisableBeginTime;
                }

                public void setUserDisableBeginTime(String userDisableBeginTime) {
                    this.userDisableBeginTime = userDisableBeginTime;
                }

                public String getUserDisableEndTime() {
                    return userDisableEndTime;
                }

                public void setUserDisableEndTime(String userDisableEndTime) {
                    this.userDisableEndTime = userDisableEndTime;
                }

                public String getUserDisableExplain() {
                    return userDisableExplain;
                }

                public void setUserDisableExplain(String userDisableExplain) {
                    this.userDisableExplain = userDisableExplain;
                }

                public String getUserHeadPath() {
                    if (!StringUtil.getInstance().isEmpty(userHeadPath) && !userHeadPath.contains("http") && !userHeadPath.contains("storage/emulated")) {
                        userHeadPath = UrlUtil.BASE_URL + userHeadPath;
                    }
                    return userHeadPath;
                }

                public void setUserHeadPath(String userHeadPath) {
                    this.userHeadPath = userHeadPath;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getUserIdCard() {
                    return userIdCard;
                }

                public void setUserIdCard(String userIdCard) {
                    this.userIdCard = userIdCard;
                }

                public String getUserIdCardObversePath() {
                    return userIdCardObversePath;
                }

                public void setUserIdCardObversePath(String userIdCardObversePath) {
                    this.userIdCardObversePath = userIdCardObversePath;
                }

                public String getUserIdCardReversePath() {
                    return userIdCardReversePath;
                }

                public void setUserIdCardReversePath(String userIdCardReversePath) {
                    this.userIdCardReversePath = userIdCardReversePath;
                }

                public String getUserLoginPassword() {
                    return userLoginPassword;
                }

                public void setUserLoginPassword(String userLoginPassword) {
                    this.userLoginPassword = userLoginPassword;
                }

                public String getUserNickName() {
                    return userNickName;
                }

                public void setUserNickName(String userNickName) {
                    this.userNickName = userNickName;
                }

                public String getUserPayPassword() {
                    return userPayPassword;
                }

                public void setUserPayPassword(String userPayPassword) {
                    this.userPayPassword = userPayPassword;
                }

                public String getUserRealName() {
                    return userRealName;
                }

                public void setUserRealName(String userRealName) {
                    this.userRealName = userRealName;
                }

                public String getUserRegisterTelephone() {
                    return userRegisterTelephone;
                }

                public void setUserRegisterTelephone(String userRegisterTelephone) {
                    this.userRegisterTelephone = userRegisterTelephone;
                }

                public String getUserRegisterTime() {
                    return userRegisterTime;
                }

                public void setUserRegisterTime(String userRegisterTime) {
                    this.userRegisterTime = userRegisterTime;
                }

                public String getUserSex() {
                    return userSex;
                }

                public void setUserSex(String userSex) {
                    this.userSex = userSex;
                }

                public String getUserSign() {
                    return userSign;
                }

                public void setUserSign(String userSign) {
                    this.userSign = userSign;
                }
            }
        }
    }
}
