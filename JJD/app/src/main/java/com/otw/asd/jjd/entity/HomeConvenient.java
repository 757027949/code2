package com.otw.asd.jjd.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */

public class HomeConvenient {

    private boolean isOpened = false;
    /**
     * courseId : 402880f357500e9b01575092cad20002
     * courseOrderBeginTime : 2016-09-22 16:00:00
     * courseOrderDetailId :
     * courseOrderEndTime : 2016-09-22 17:00:00
     * courseOrderId : 402880f357500e9b01575092cad00001
     * courseOrderPrice : 140
     * courseOrderState : 已接单
     * courseOrderTime : 2016-09-22 14:26:19
     * courseSubject : 科目二
     * courseType : C1
     * isFinish : N
     * isSccept : Y
     * isUpdate : N
     * peopleNumber : 4
     * siteId : f7d4c903564e7d7301565336a6e4004c
     * siteName : 渝快驾校（九龙坡区）训练场
     * teacherId : 402880eb572144990157216ba7d00001
     * teacherName :
     * users : []
     * validateCode : 200
     */

    private CourseOrderAndCourseBean courseOrderAndCourse;
    /**
     * accountOrderId :
     * addressId : 402880eb572144990157216f0f2d0005
     * byUserId :
     * byUserName : 未知
     * isApply : N
     * isHeChang : Y
     * isTeacher : Y
     * isValidate : Y
     * schoolId : f7d4c903564e7d7301564fa73c63001a
     * siteId : f7d4c903564e7d7301565336a6e4004c
     * teachCarPath : http://www.file.jiajiandang.com/img/teacher/car/0ebaa917-34ba-4d7c-a620-bdd259afde48.png
     * teachIsSubject2 : Y
     * teachIsSubject3 : Y
     * teachTypeC1 : Y
     * teachTypeC2 : Y
     * teacherCard : 007
     * teacherCardPath : http://www.file.jiajiandang.com/img/teacher/car/0ebaa917-34ba-4d7c-a620-bdd259afde48.png
     * teacherId : 402880eb572144990157216ba7d00001
     * teacherLevel : 1
     * teacherRunCardPath : http://www.file.jiajiandang.com/img/teacher/runCard/38e6ec66-dabb-405c-8815-7116bae39450.png
     * teacherTeachYear : 1
     * userAge : 905
     * userContactTelephone : 未知
     * userHeadPath : http://www.file.jiajiandang.com/img/teacher/car/0ebaa917-34ba-4d7c-a620-bdd259afde48.png
     * userId : 402880eb572144990157216ba74e0000
     * userIdCard : 111111111111111111
     * userIdCardObversePath : http://www.file.jiajiandang.com/img/user/IDCard/obverse/f1e8459b-0935-45b2-9076-af653d974555.png
     * userIdCardReversePath : http://www.file.jiajiandang.com/img/user/IDCard/reverse/4ab49141-5e4f-411b-8699-34173451b8a8.png
     * userLoginPassword : e10adc3949ba59abbe56e057f20f883e
     * userNickName : 无
     * userPayPassword : 670b14728ad9902aecba32e22fa4f6bd
     * userRealName : 007
     * userRegisterTelephone : 15023343749
     * userSex : 男
     * userSign : 无
     * validateExplain : null
     * validateState : 1
     */

    private UserAndTeacherBean userAndTeacher;


    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public CourseOrderAndCourseBean getCourseOrderAndCourse() {
        return courseOrderAndCourse;
    }

    public void setCourseOrderAndCourse(CourseOrderAndCourseBean courseOrderAndCourse) {
        this.courseOrderAndCourse = courseOrderAndCourse;
    }

    public UserAndTeacherBean getUserAndTeacher() {
        return userAndTeacher;
    }

    public void setUserAndTeacher(UserAndTeacherBean userAndTeacher) {
        this.userAndTeacher = userAndTeacher;
    }

    public static class CourseOrderAndCourseBean {

        private String courseId;
        private String courseOrderBeginTime;
        private String courseOrderDetailId;
        private String courseOrderEndTime;
        private String courseOrderId;
        private int courseOrderPrice;
        private double courseOrderUnitPrice;
        private String courseOrderState;
        private String courseOrderTime;
        private String courseSubject;
        private String courseType;
        private String isFinish;
        private String isSccept;
        private String isUpdate;
        private int peopleNumber;
        private String siteId;
        private String siteName;
        private String teacherId;
        private String teacherName;
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
         * userBirthDay : 2000-01-01 00:00:00
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
         * userSex : 女
         * userSign : 无
         */

        private List<UsersBean> users;

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

        public int getCourseOrderPrice() {
            return courseOrderPrice;
        }

        public void setCourseOrderPrice(int courseOrderPrice) {
            this.courseOrderPrice = courseOrderPrice;
        }

        public double getCourseOrderUnitPrice() {
            return courseOrderUnitPrice;
        }

        public void setCourseOrderUnitPrice(double courseOrderUnitPrice) {
            this.courseOrderUnitPrice = courseOrderUnitPrice;
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

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
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

        public static class UsersBean {
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

    public static class UserAndTeacherBean {
        private String accountOrderId;
        private String addressId;
        private String byUserId;
        private String byUserName;
        private String isApply;
        private String isHeChang;
        private String isTeacher;
        private String isValidate;
        private String schoolId;
        private String siteId;
        private String teachCarPath;
        private String teachIsSubject2;
        private String teachIsSubject3;
        private String teachTypeC1;
        private String teachTypeC2;
        private String teacherCard;
        private String teacherCardPath;
        private String teacherId;
        private int teacherLevel;
        private String teacherRunCardPath;
        private int teacherTeachYear;
        private int userAge;
        private String userContactTelephone;
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
        private String userSex;
        private String userSign;
        private Object validateExplain;
        private String validateState;

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

        public String getByUserName() {
            return byUserName;
        }

        public void setByUserName(String byUserName) {
            this.byUserName = byUserName;
        }

        public String getIsApply() {
            return isApply;
        }

        public void setIsApply(String isApply) {
            this.isApply = isApply;
        }

        public String getIsHeChang() {
            return isHeChang;
        }

        public void setIsHeChang(String isHeChang) {
            this.isHeChang = isHeChang;
        }

        public String getIsTeacher() {
            return isTeacher;
        }

        public void setIsTeacher(String isTeacher) {
            this.isTeacher = isTeacher;
        }

        public String getIsValidate() {
            return isValidate;
        }

        public void setIsValidate(String isValidate) {
            this.isValidate = isValidate;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getTeachCarPath() {
            return teachCarPath;
        }

        public void setTeachCarPath(String teachCarPath) {
            this.teachCarPath = teachCarPath;
        }

        public String getTeachIsSubject2() {
            return teachIsSubject2;
        }

        public void setTeachIsSubject2(String teachIsSubject2) {
            this.teachIsSubject2 = teachIsSubject2;
        }

        public String getTeachIsSubject3() {
            return teachIsSubject3;
        }

        public void setTeachIsSubject3(String teachIsSubject3) {
            this.teachIsSubject3 = teachIsSubject3;
        }

        public String getTeachTypeC1() {
            return teachTypeC1;
        }

        public void setTeachTypeC1(String teachTypeC1) {
            this.teachTypeC1 = teachTypeC1;
        }

        public String getTeachTypeC2() {
            return teachTypeC2;
        }

        public void setTeachTypeC2(String teachTypeC2) {
            this.teachTypeC2 = teachTypeC2;
        }

        public String getTeacherCard() {
            return teacherCard;
        }

        public void setTeacherCard(String teacherCard) {
            this.teacherCard = teacherCard;
        }

        public String getTeacherCardPath() {
            return teacherCardPath;
        }

        public void setTeacherCardPath(String teacherCardPath) {
            this.teacherCardPath = teacherCardPath;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public int getTeacherLevel() {
            return teacherLevel;
        }

        public void setTeacherLevel(int teacherLevel) {
            this.teacherLevel = teacherLevel;
        }

        public String getTeacherRunCardPath() {
            return teacherRunCardPath;
        }

        public void setTeacherRunCardPath(String teacherRunCardPath) {
            this.teacherRunCardPath = teacherRunCardPath;
        }

        public int getTeacherTeachYear() {
            return teacherTeachYear;
        }

        public void setTeacherTeachYear(int teacherTeachYear) {
            this.teacherTeachYear = teacherTeachYear;
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

        public Object getValidateExplain() {
            return validateExplain;
        }

        public void setValidateExplain(Object validateExplain) {
            this.validateExplain = validateExplain;
        }

        public String getValidateState() {
            return validateState;
        }

        public void setValidateState(String validateState) {
            this.validateState = validateState;
        }
    }
}
