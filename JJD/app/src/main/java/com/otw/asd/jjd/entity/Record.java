package com.otw.asd.jjd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public class Record implements Serializable {

    /**
     * date : 2016-5
     * courseOrders : [{"courseId":"402880e6555c731301555c77020b0000","courseOrderBeginTime":"2016-06-17 11:00:00","courseOrderDetailId":"402880e6555c731301555c77024f0001","courseOrderEndTime":"2016-06-17 12:00:00","courseOrderId":"402880e6555c731301555c7702880002","courseOrderState":"未接单","courseOrderTime":"2016-06-17 11:45:51","courseSubject":"科目二","courseTotalPrice":0,"courseType":"C1","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":2,"siteId":"402880e6555c6b4701555c6c23610000","siteName":"789","teacherId":"402880e6555c514801555c5301ef0005","users":[]}]
     */

    private List<DatasBean> datas;
    /**
     * totalCount : 9
     */

    private int totalCount;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public static class DatasBean implements Serializable {
        private String date;
        /**
         * courseId : 402880e6555c731301555c77020b0000
         * courseOrderBeginTime : 2016-06-17 11:00:00
         * courseOrderDetailId : 402880e6555c731301555c77024f0001
         * courseOrderEndTime : 2016-06-17 12:00:00
         * courseOrderId : 402880e6555c731301555c7702880002
         * courseOrderState : 未接单
         * courseOrderTime : 2016-06-17 11:45:51
         * courseSubject : 科目二
         * courseTotalPrice : 0
         * courseType : C1
         * isFinish : N
         * isSccept :
         * isUpdate : N
         * peopleNumber : 2
         * siteId : 402880e6555c6b4701555c6c23610000
         * siteName : 789
         * teacherId : 402880e6555c514801555c5301ef0005
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
            private boolean isShowCheckBox = false;
            private boolean isChecked = false;
            private String courseId;
            private String courseOrderBeginTime;
            private String courseOrderDetailId;
            private String courseOrderEndTime;
            private String courseOrderId;
            private String courseOrderState;
            private String courseOrderTime;
            private String courseSubject;
            private int courseTotalPrice;
            private String courseType;
            private String isFinish;
            private String isSccept;
            private String isUpdate;
            private int peopleNumber;
            private String siteId;
            private String siteName;
            private String teacherId;
            private String userId;
            private String teacherName;
            private String userHeadPath;
            /**
             * courseOrderPrice : 120
             * courseOrderUnitPrice : 80
             * users : [{"accountOrderId":"402880ef5745433101574556bc420030","addressId":"402880eb571de97d01571decd8f40000","affectiveStates":"1","byUserId":"","byUserNickName":"未知","clientPlatform":"android","constellation":"巨蟹座","courseOrders":null,"deviceCode":"0","isAdmin":"N","isApply":"Y","isDisable":"N","isTeacher":"N","remoteAddr":"127.0.0.1","userAge":16,"userBirthDay":"2000-01-01 00:00:00 00:00:00","userContactTelephone":"15023343741","userDisableBeginTime":"","userDisableEndTime":"","userDisableExplain":"","userHeadPath":"","userId":"402880eb571de45c01571de890b50000","userIdCard":"无","userIdCardObversePath":"","userIdCardReversePath":"","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"15023343741","userPayPassword":"ff92a240d11b05ebd392348c35f781b2","userRealName":"孙悦","userRegisterTelephone":"15023343741","userRegisterTime":"2016-09-12 18:19:18","userSex":"男","userSign":"无"}]
             * validateCode : 200
             */

            private int courseOrderPrice;
            private int courseOrderUnitPrice;
            private String validateCode;
            /**
             * accountOrderId : 402880ef5745433101574556bc420030
             * addressId : 402880eb571de97d01571decd8f40000
             * affectiveStates : 1
             * byUserId :
             * byUserNickName : 未知
             * clientPlatform : android
             * constellation : 巨蟹座
             * courseOrders : null
             * deviceCode : 0
             * isAdmin : N
             * isApply : Y
             * isDisable : N
             * isTeacher : N
             * remoteAddr : 127.0.0.1
             * userAge : 16
             * userBirthDay : 2000-01-01 00:00:00 00:00:00
             * userContactTelephone : 15023343741
             * userDisableBeginTime :
             * userDisableEndTime :
             * userDisableExplain :
             * userHeadPath :
             * userId : 402880eb571de45c01571de890b50000
             * userIdCard : 无
             * userIdCardObversePath :
             * userIdCardReversePath :
             * userLoginPassword : e10adc3949ba59abbe56e057f20f883e
             * userNickName : 15023343741
             * userPayPassword : ff92a240d11b05ebd392348c35f781b2
             * userRealName : 孙悦
             * userRegisterTelephone : 15023343741
             * userRegisterTime : 2016-09-12 18:19:18
             * userSex : 男
             * userSign : 无
             */

            private List<UsersBean> users;


            public boolean isShowCheckBox() {
                return isShowCheckBox;
            }

            public void setShowCheckBox(boolean showCheckBox) {
                isShowCheckBox = showCheckBox;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

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

            public int getCourseTotalPrice() {
                return courseTotalPrice;
            }

            public void setCourseTotalPrice(int courseTotalPrice) {
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }

            public String getUserHeadPath() {
                return userHeadPath;
            }

            public void setUserHeadPath(String userHeadPath) {
                this.userHeadPath = userHeadPath;
            }

            public int getCourseOrderPrice() {
                return courseOrderPrice;
            }

            public void setCourseOrderPrice(int courseOrderPrice) {
                this.courseOrderPrice = courseOrderPrice;
            }

            public int getCourseOrderUnitPrice() {
                return courseOrderUnitPrice;
            }

            public void setCourseOrderUnitPrice(int courseOrderUnitPrice) {
                this.courseOrderUnitPrice = courseOrderUnitPrice;
            }

            public String getValidateCode() {
                return validateCode;
            }

            public void setValidateCode(String validateCode) {
                this.validateCode = validateCode;
            }

            public List<UsersBean> getUsers() {
                return users;
            }

            public void setUsers(List<UsersBean> users) {
                this.users = users;
            }

            public static class UsersBean implements Serializable {
                private String accountOrderId;
                private String addressId;
                private String affectiveStates;
                private String byUserId;
                private String byUserNickName;
                private String clientPlatform;
                private String constellation;
                private Object courseOrders;
                private String deviceCode;
                private String isAdmin;
                private String isApply;
                private String isDisable;
                private String isTeacher;
                private String remoteAddr;
                private int userAge;
                private String userBirthDay;
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

                public String getAffectiveStates() {
                    return affectiveStates;
                }

                public void setAffectiveStates(String affectiveStates) {
                    this.affectiveStates = affectiveStates;
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

                public String getConstellation() {
                    return constellation;
                }

                public void setConstellation(String constellation) {
                    this.constellation = constellation;
                }

                public Object getCourseOrders() {
                    return courseOrders;
                }

                public void setCourseOrders(Object courseOrders) {
                    this.courseOrders = courseOrders;
                }

                public String getDeviceCode() {
                    return deviceCode;
                }

                public void setDeviceCode(String deviceCode) {
                    this.deviceCode = deviceCode;
                }

                public String getIsAdmin() {
                    return isAdmin;
                }

                public void setIsAdmin(String isAdmin) {
                    this.isAdmin = isAdmin;
                }

                public String getIsApply() {
                    return isApply;
                }

                public void setIsApply(String isApply) {
                    this.isApply = isApply;
                }

                public String getIsDisable() {
                    return isDisable;
                }

                public void setIsDisable(String isDisable) {
                    this.isDisable = isDisable;
                }

                public String getIsTeacher() {
                    return isTeacher;
                }

                public void setIsTeacher(String isTeacher) {
                    this.isTeacher = isTeacher;
                }

                public String getRemoteAddr() {
                    return remoteAddr;
                }

                public void setRemoteAddr(String remoteAddr) {
                    this.remoteAddr = remoteAddr;
                }

                public int getUserAge() {
                    return userAge;
                }

                public void setUserAge(int userAge) {
                    this.userAge = userAge;
                }

                public String getUserBirthDay() {
                    return userBirthDay;
                }

                public void setUserBirthDay(String userBirthDay) {
                    this.userBirthDay = userBirthDay;
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
