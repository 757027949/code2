package com.otw.asd.jjdt.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/20.
 */
public class UserInfo implements Serializable {

    /**
     * code : 200
     * obj : {"teacher":{"isValidate":"N","validateExplain":"教师未认证","validateState":"-1","isHeChang":"N","schoolId":"","siteId":"","teachCarPath":"","teachIsSubject2":"N","teachIsSubject3":"N","teachType":"","teacherCard":"","teacherCardPath":"","teacherId":"402880e55504f25c015505f410820001","teacherLevel":1,"teacherRunCardPath":"","teacherTeachYear":"","userId":"402880e55504f25c015505f4102b0000"},"address":{"addressAreaName":"","addressCityName":"","addressCountryName":"China","addressDetailName":"","addressId":"","addressProvinceName":"","userId":""},"user":{"accountOrderId":"","byUserId":"","byUserName":"","isApply":"N","userAge":0,"userContactTelephone":"","userHeadPath":"","userId":"402880e55504f25c015505f4102b0000","userIdCard":"","userIdCardObversePath":"","userIdCardReversePath":"","userIsTeacher":"Y","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"18688888889","userPayPassword":"670b14728ad9902aecba32e22fa4f6bd","userRealName":"","userRegisterTelephone":"18688888889","userSex":"女","userSign":""}}
     * success : true
     */

    private int code;
    /**
     * teacher : {"isValidate":"N","validateExplain":"教师未认证","validateState":"-1","isHeChang":"N","schoolId":"","siteId":"","teachCarPath":"","teachIsSubject2":"N","teachIsSubject3":"N","teachType":"","teacherCard":"","teacherCardPath":"","teacherId":"402880e55504f25c015505f410820001","teacherLevel":1,"teacherRunCardPath":"","teacherTeachYear":"","userId":"402880e55504f25c015505f4102b0000"}
     * address : {"addressAreaName":"","addressCityName":"","addressCountryName":"China","addressDetailName":"","addressId":"","addressProvinceName":"","userId":""}
     * user : {"accountOrderId":"","byUserId":"","byUserName":"","isApply":"N","userAge":0,"userContactTelephone":"","userHeadPath":"","userId":"402880e55504f25c015505f4102b0000","userIdCard":"","userIdCardObversePath":"","userIdCardReversePath":"","userIsTeacher":"Y","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"18688888889","userPayPassword":"670b14728ad9902aecba32e22fa4f6bd","userRealName":"","userRegisterTelephone":"18688888889","userSex":"女","userSign":""}
     */

    private ObjBean obj;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class ObjBean implements Serializable {
        /**
         * isValidate : N
         * validateExplain : 教师未认证
         * validateState : -1
         * isHeChang : N
         * schoolId :
         * siteId :
         * teachCarPath :
         * teachIsSubject2 : N
         * teachIsSubject3 : N
         * teachType :
         * teacherCard :
         * teacherCardPath :
         * teacherId : 402880e55504f25c015505f410820001
         * teacherLevel : 1
         * teacherRunCardPath :
         * teacherTeachYear :
         * userId : 402880e55504f25c015505f4102b0000
         */

        private TeacherBean teacher;
        /**
         * addressAreaName :
         * addressCityName :
         * addressCountryName : China
         * addressDetailName :
         * addressId :
         * addressProvinceName :
         * userId :
         */

        private AddressBean address;
        /**
         * accountOrderId :
         * byUserId :
         * byUserName :
         * isApply : N
         * userAge : 0
         * userContactTelephone :
         * userHeadPath :
         * userId : 402880e55504f25c015505f4102b0000
         * userIdCard :
         * userIdCardObversePath :
         * userIdCardReversePath :
         * userIsTeacher : Y
         * userLoginPassword : e10adc3949ba59abbe56e057f20f883e
         * userNickName : 18688888889
         * userPayPassword : 670b14728ad9902aecba32e22fa4f6bd
         * userRealName :
         * userRegisterTelephone : 18688888889
         * userSex : 女
         * userSign :
         */

        private UserBean user;

        public TeacherBean getTeacher() {
            return teacher;
        }

        public void setTeacher(TeacherBean teacher) {
            this.teacher = teacher;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class TeacherBean implements Serializable {
            private String isValidate;
            private String validateExplain;
            private String validateState;
            private String isHeChang;
            private String schoolId;
            private String siteId;
            private String teachCarPath;
            private String teachIsSubject2;
            private String teachIsSubject3;
            private String teachType;
            private String teacherCard;
            private String teacherCardPath;
            private String teacherId;
            private int teacherLevel;
            private String teacherRunCardPath;
            private String teacherTeachYear;
            private String userId;

            public String getIsValidate() {
                return isValidate;
            }

            public void setIsValidate(String isValidate) {
                this.isValidate = isValidate;
            }

            public String getValidateExplain() {
                return validateExplain;
            }

            public void setValidateExplain(String validateExplain) {
                this.validateExplain = validateExplain;
            }

            /**
             * -1:未认证或认证失败 0：认证中 1：认证通过
             *
             * @return
             */
            public String getValidateState() {
                return validateState;
            }

            /**
             * -1:未认证或认证失败 0：认证中 1：认证通过
             *
             * @param validateState
             */
            public void setValidateState(String validateState) {
                this.validateState = validateState;
            }

            public String getIsHeChang() {
                return isHeChang;
            }

            public void setIsHeChang(String isHeChang) {
                this.isHeChang = isHeChang;
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

            public String getTeachType() {
                return teachType;
            }

            public void setTeachType(String teachType) {
                this.teachType = teachType;
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

            public String getTeacherTeachYear() {
                return teacherTeachYear;
            }

            public void setTeacherTeachYear(String teacherTeachYear) {
                this.teacherTeachYear = teacherTeachYear;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }

        public static class AddressBean implements Serializable {
            private String addressAreaName;
            private String addressCityName;
            private String addressCountryName;
            private String addressDetailName;
            private String addressId;
            private String addressProvinceName;
            private String userId;

            public String getAddressAreaName() {
                return addressAreaName;
            }

            public void setAddressAreaName(String addressAreaName) {
                this.addressAreaName = addressAreaName;
            }

            public String getAddressCityName() {
                return addressCityName;
            }

            public void setAddressCityName(String addressCityName) {
                this.addressCityName = addressCityName;
            }

            public String getAddressCountryName() {
                return addressCountryName;
            }

            public void setAddressCountryName(String addressCountryName) {
                this.addressCountryName = addressCountryName;
            }

            public String getAddressDetailName() {
                return addressDetailName;
            }

            public void setAddressDetailName(String addressDetailName) {
                this.addressDetailName = addressDetailName;
            }

            public String getAddressId() {
                return addressId;
            }

            public void setAddressId(String addressId) {
                this.addressId = addressId;
            }

            public String getAddressProvinceName() {
                return addressProvinceName;
            }

            public void setAddressProvinceName(String addressProvinceName) {
                this.addressProvinceName = addressProvinceName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }

        public static class UserBean implements Serializable {
            private String accountOrderId;
            private String byUserId;
            private String byUserName;
            private String isApply;
            private int userAge;
            private String userContactTelephone;
            private String userHeadPath = "";
            private String userId;
            private String userIdCard;
            private String userIdCardObversePath;
            private String userIdCardReversePath;
            private String userIsTeacher;
            private String userLoginPassword;
            private String userNickName;
            private String userPayPassword;
            private String userRealName;
            private String userRegisterTelephone;
            private String userSex;
            private String userSign;

            public String getAccountOrderId() {
                return accountOrderId;
            }

            public void setAccountOrderId(String accountOrderId) {
                this.accountOrderId = accountOrderId;
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

            public String getUserIsTeacher() {
                return userIsTeacher;
            }

            public void setUserIsTeacher(String userIsTeacher) {
                this.userIsTeacher = userIsTeacher;
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
        }
    }
}
