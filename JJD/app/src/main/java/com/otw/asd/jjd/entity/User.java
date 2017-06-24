package com.otw.asd.jjd.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjd.util.UrlUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class User implements Serializable {

    /**
     * code : 200
     * obj : {"address":{"addressAreaName":"","addressCityName":"","addressCountryName":"China","addressDetailName":"","addressId":"","addressProvinceName":"","userId":""},"user":{"accountOrderId":"402880eb54e21ebc0154e22769df0002","byUserId":"","byUserName":"无","isApply":"Y","userAge":20,"userContactTelephone":"18388868685","userHeadPath":"留点言吧！","userId":"402880eb54e0ec7c0154e0ecfad50000","userIdCard":"","userIdCardObversePath":"","userIdCardReversePath":"","userIsTeacher":"N","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"18696677791","userPayPassword":"670b14728ad9902aecba32e22fa4f6bd","userRealName":"啦lol","userRegisterTelephone":"18696677791","userSex":"男","userSign":"留点言吧！"}}
     * success : true
     */

    private int code;
    /**
     * address : {"addressAreaName":"","addressCityName":"","addressCountryName":"China","addressDetailName":"","addressId":"","addressProvinceName":"","userId":""}
     * user : {"accountOrderId":"402880eb54e21ebc0154e22769df0002","byUserId":"","byUserName":"无","isApply":"Y","userAge":20,"userContactTelephone":"18388868685","userHeadPath":"留点言吧！","userId":"402880eb54e0ec7c0154e0ecfad50000","userIdCard":"","userIdCardObversePath":"","userIdCardReversePath":"","userIsTeacher":"N","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"18696677791","userPayPassword":"670b14728ad9902aecba32e22fa4f6bd","userRealName":"啦lol","userRegisterTelephone":"18696677791","userSex":"男","userSign":"留点言吧！"}
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
         * accountOrderId : 402880eb54e21ebc0154e22769df0002
         * byUserId :
         * byUserName : 无
         * isApply : Y
         * userAge : 20
         * userContactTelephone : 18388868685
         * userHeadPath : 留点言吧！
         * userId : 402880eb54e0ec7c0154e0ecfad50000
         * userIdCard :
         * userIdCardObversePath :
         * userIdCardReversePath :
         * userIsTeacher : N
         * userLoginPassword : e10adc3949ba59abbe56e057f20f883e
         * userNickName : 18696677791
         * userPayPassword : 670b14728ad9902aecba32e22fa4f6bd
         * userRealName : 啦lol
         * userRegisterTelephone : 18696677791
         * userSex : 男
         * userSign : 留点言吧！
         */

        private UserBean user;
        private List<AlbumBean> userAlbums;

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

        public List<AlbumBean> getUserAlbums() {
            return userAlbums;
        }

        public void setUserAlbums(List<AlbumBean> userAlbums) {
            this.userAlbums = userAlbums;
        }

        public static class AddressBean implements Serializable {
            private String addressAreaName = "";
            private String addressCityName = "";
            private String addressCountryName;
            private String addressDetailName = "";
            private String addressId;
            private String addressProvinceName = "";

            public String getAddressAreaName() {
                return addressAreaName;
            }

            public void setAddressAreaName(String addressAreaName) {
                if("".equals(addressAreaName)){
                    return;
                }
                this.addressAreaName = addressAreaName;
            }

            public String getAddressCityName() {
                return addressCityName;
            }

            public void setAddressCityName(String addressCityName) {
                if ("".equals(addressCityName)) {
                    return;
                }
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
                if("".equals(addressDetailName)){
                    return;
                }
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
                if ("".equals(addressProvinceName)) {
                    return;
                }
                this.addressProvinceName = addressProvinceName;
            }

        }

        public static class UserBean implements Serializable {
            private String accountOrderId;
            private String byUserId;
            private String byUserNickName;
            private String isApply;
            private int userAge;
            private String userContactTelephone;
            private String userHeadPath;
            private String userId;
            private String userIdCard;
            private String userIdCardObversePath;
            private String userIdCardReversePath;
            private String isTeacher;
            private String userLoginPassword;
            private String userNickName = "";
            private String userPayPassword;
            private String userRealName;
            private String userRegisterTelephone;
            private String userSex = "女";
            private String userSign = "";
            /**
             * addressId : 402880e65643e205015643e388040007
             * affectiveStates : null
             * clientPlatform : android
             * constellation : null
             * courseOrders : null
             * deviceCode : 5066047983205182_jjd
             * isAdmin : Y
             * isDisable : N
             * remoteAddr : 192.168.0.108
             * userAlbums : []
             * userBirthDay : null
             * userDisableBeginTime :
             * userDisableEndTime :
             * userDisableExplain : 非法操作！
             * userRegisterTime : 2016-08-01 10:16:34
             */

            private String addressId;
            private String affectiveStates = "";
            private String clientPlatform;
            private String constellation = "";
            private String courseOrders;
            private String deviceCode;
            private String isAdmin;
            private String isDisable;
            private String remoteAddr;
            private String userBirthDay = "";
            private String userDisableBeginTime;
            private String userDisableEndTime;
            private String userDisableExplain;
            private String userRegisterTime;


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
                return byUserNickName;
            }

            public void setByUserName(String byUserName) {
                this.byUserNickName = byUserName;
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
                    userHeadPath = UrlUtil.BASE_IMAGE_URL + userHeadPath;
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
                return isTeacher;
            }

            public void setUserIsTeacher(String userIsTeacher) {
                this.isTeacher = userIsTeacher;
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
                if ("".equals(userNickName)) {
                    return;
                }
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
                if ("".equals(userSex)) {
                    return;
                }
                this.userSex = userSex;
            }

            public String getUserSign() {
                return userSign;
            }

            public void setUserSign(String userSign) {
                if ("".equals(userSign)) {
                    return;
                }
                this.userSign = userSign;
            }

            public String getAddressId() {
                return addressId;
            }

            public void setAddressId(String addressId) {
                this.addressId = addressId;
            }

            /**
             * @return 1:单身，2，恋爱中，3，已婚，4，离婚，5丧偶
             */
            public String getAffectiveStates() {
                return affectiveStates;
            }

            public void setAffectiveStates(String affectiveStates) {
                if ("".equals(affectiveStates)) {
                    return;
                }
                this.affectiveStates = affectiveStates;
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
                if ("".equals(constellation)) {
                    return;
                }
                this.constellation = constellation;
            }

            public String getCourseOrders() {
                return courseOrders;
            }

            public void setCourseOrders(String courseOrders) {
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

            public String getIsDisable() {
                return isDisable;
            }

            public void setIsDisable(String isDisable) {
                this.isDisable = isDisable;
            }

            public String getRemoteAddr() {
                return remoteAddr;
            }

            public void setRemoteAddr(String remoteAddr) {
                this.remoteAddr = remoteAddr;
            }

            public String getUserBirthDay() {
                return userBirthDay;
            }

            public void setUserBirthDay(String userBirthDay) {
                if ("".equals(userBirthDay)) {
                    return;
                }
                this.userBirthDay = userBirthDay;
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

            public String getUserRegisterTime() {
                return userRegisterTime;
            }

            public void setUserRegisterTime(String userRegisterTime) {
                this.userRegisterTime = userRegisterTime;
            }

        }

        public static class AlbumBean implements Serializable {
            private String userAlbumId = "";//用户配置编号
            private String userId = "";//用户编号
            private int userAlbumSerial = 0;//序列号
            private String userAlbumPath = "";//路径
            private String userAlbumExplain = "";//说明，描述

            public String getUserAlbumId() {
                return userAlbumId;
            }

            public void setUserAlbumId(String userAlbumId) {
                this.userAlbumId = userAlbumId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getUserAlbumSerial() {
                return userAlbumSerial;
            }

            public void setUserAlbumSerial(int userAlbumSerial) {
                this.userAlbumSerial = userAlbumSerial;
            }

            public String getUserAlbumPath() {
                return userAlbumPath;
            }

            public void setUserAlbumPath(String userAlbumPath) {
                this.userAlbumPath = userAlbumPath;
            }

            public String getUserAlbumExplain() {
                return userAlbumExplain;
            }

            public void setUserAlbumExplain(String userAlbumExplain) {
                this.userAlbumExplain = userAlbumExplain;
            }
        }
    }
}
