package com.otw.asd.jjd.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 社区详情
 * Created by Administrator on 2016/10/8.
 */

public class CommunityInfo implements Parcelable {

    /**
     * commonAudioPath :
     * commonCoverPath : http://www.file.jiajiandang.com/img/common/cover/8577ebd0-c79c-4b12-9b08-d2b5b85ad2c7.png
     * commonHref :
     * commonId : 402880f157a1e7d60157a220f5af0014
     * commonImagePath : ["http://www.file.jiajiandang.com/img/common/common/846c412e-89e0-4f47-8679-a5b0e6fd2510.png"]
     * commonText : 贴膜特价
     * commonTime : 2016-10-08 10:30:51
     * commonTitle : 抠门
     * commonType : C
     * commonVideoPath :
     * isElite : N
     * isNative : Y
     * replyCount : 0
     * userId : 402880eb5721256d0157212e23320000
     * visitCount : 0
     */

    private CommonBean common;
    /**
     * accountOrderId : 402880f35755a4c1015755c0854a002c
     * addressId : 402880eb5721256d0157212e474d0008
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
     * userContactTelephone : 13882650194
     * userDisableBeginTime :
     * userDisableEndTime :
     * userDisableExplain :
     * userHeadPath :
     * userId : 402880eb5721256d0157212e23320000
     * userIdCard : 无
     * userIdCardObversePath :
     * userIdCardReversePath :
     * userLoginPassword : e10adc3949ba59abbe56e057f20f883e
     * userNickName : 15023343743
     * userPayPassword : ff92a240d11b05ebd392348c35f781b2
     * userRealName : 嘿嘿
     * userRegisterTelephone : 15023343743
     * userRegisterTime : 2016-09-13 09:34:14
     * userSex : 女
     * userSign : 无
     */

    private UserBean user;

    public CommonBean getCommon() {
        return common;
    }

    public void setCommon(CommonBean common) {
        this.common = common;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class CommonBean implements Parcelable {
        private String commonAudioPath;
        private String commonCoverPath;
        private String commonHref;
        private String commonId;
        private String commonImagePath;
        private String commonText;
        private String commonTime;
        private String commonTitle;
        private String commonType;
        private String commonVideoPath;
        private String isElite;
        private String isNative;
        private int replyCount;
        private String userId;
        private int visitCount;
        /**
         * commonCoverHeight : 640
         * commonCoverWidth : 360
         * commonImagePath1 : ["http://www.file.jiajiandang.com/img/common/common/e234233e-da9b-462d-beff-f74769da4d32.png"]
         * commonImagePath2 : ["http://www.file.jiajiandang.com/img/common/common/dcb2bed7-efa5-4a3f-b99f-c99e42ea1521.png"]
         */

        private int commonCoverHeight;
        private int commonCoverWidth;
        private String commonImagePath1;
        private String commonImagePath2;

        public String getCommonAudioPath() {
            return commonAudioPath;
        }

        public void setCommonAudioPath(String commonAudioPath) {
            this.commonAudioPath = commonAudioPath;
        }

        public String getCommonCoverPath() {
            return commonCoverPath;
        }

        public void setCommonCoverPath(String commonCoverPath) {
            this.commonCoverPath = commonCoverPath;
        }

        public String getCommonHref() {
            return commonHref;
        }

        public void setCommonHref(String commonHref) {
            this.commonHref = commonHref;
        }

        public String getCommonId() {
            return commonId;
        }

        public void setCommonId(String commonId) {
            this.commonId = commonId;
        }

        public String getCommonImagePath() {
            return commonImagePath;
        }

        public void setCommonImagePath(String commonImagePath) {
            this.commonImagePath = commonImagePath;
        }

        public String getCommonText() {
            return commonText;
        }

        public void setCommonText(String commonText) {
            this.commonText = commonText;
        }

        public String getCommonTime() {
            return commonTime;
        }

        public void setCommonTime(String commonTime) {
            this.commonTime = commonTime;
        }

        public String getCommonTitle() {
            return commonTitle;
        }

        public void setCommonTitle(String commonTitle) {
            this.commonTitle = commonTitle;
        }

        public String getCommonType() {
            return commonType;
        }

        public void setCommonType(String commonType) {
            this.commonType = commonType;
        }

        public String getCommonVideoPath() {
            return commonVideoPath;
        }

        public void setCommonVideoPath(String commonVideoPath) {
            this.commonVideoPath = commonVideoPath;
        }

        public String getIsElite() {
            return isElite;
        }

        public void setIsElite(String isElite) {
            this.isElite = isElite;
        }

        public String getIsNative() {
            return isNative;
        }

        public void setIsNative(String isNative) {
            this.isNative = isNative;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getVisitCount() {
            return visitCount;
        }

        public void setVisitCount(int visitCount) {
            this.visitCount = visitCount;
        }

        public CommonBean() {
        }

        public int getCommonCoverHeight() {
            return commonCoverHeight;
        }

        public void setCommonCoverHeight(int commonCoverHeight) {
            this.commonCoverHeight = commonCoverHeight;
        }

        public int getCommonCoverWidth() {
            return commonCoverWidth;
        }

        public void setCommonCoverWidth(int commonCoverWidth) {
            this.commonCoverWidth = commonCoverWidth;
        }

        public String getCommonImagePath1() {
            return commonImagePath1;
        }

        public void setCommonImagePath1(String commonImagePath1) {
            this.commonImagePath1 = commonImagePath1;
        }

        public String getCommonImagePath2() {
            return commonImagePath2;
        }

        public void setCommonImagePath2(String commonImagePath2) {
            this.commonImagePath2 = commonImagePath2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.commonAudioPath);
            dest.writeString(this.commonCoverPath);
            dest.writeString(this.commonHref);
            dest.writeString(this.commonId);
            dest.writeString(this.commonImagePath);
            dest.writeString(this.commonText);
            dest.writeString(this.commonTime);
            dest.writeString(this.commonTitle);
            dest.writeString(this.commonType);
            dest.writeString(this.commonVideoPath);
            dest.writeString(this.isElite);
            dest.writeString(this.isNative);
            dest.writeInt(this.replyCount);
            dest.writeString(this.userId);
            dest.writeInt(this.visitCount);
            dest.writeInt(this.commonCoverHeight);
            dest.writeInt(this.commonCoverWidth);
            dest.writeString(this.commonImagePath1);
            dest.writeString(this.commonImagePath2);
        }

        protected CommonBean(Parcel in) {
            this.commonAudioPath = in.readString();
            this.commonCoverPath = in.readString();
            this.commonHref = in.readString();
            this.commonId = in.readString();
            this.commonImagePath = in.readString();
            this.commonText = in.readString();
            this.commonTime = in.readString();
            this.commonTitle = in.readString();
            this.commonType = in.readString();
            this.commonVideoPath = in.readString();
            this.isElite = in.readString();
            this.isNative = in.readString();
            this.replyCount = in.readInt();
            this.userId = in.readString();
            this.visitCount = in.readInt();
            this.commonCoverHeight = in.readInt();
            this.commonCoverWidth = in.readInt();
            this.commonImagePath1 = in.readString();
            this.commonImagePath2 = in.readString();
        }

        public static final Creator<CommonBean> CREATOR = new Creator<CommonBean>() {
            @Override
            public CommonBean createFromParcel(Parcel source) {
                return new CommonBean(source);
            }

            @Override
            public CommonBean[] newArray(int size) {
                return new CommonBean[size];
            }
        };
    }

    public static class UserBean implements Parcelable {
        private String accountOrderId;
        private String addressId;
        private String affectiveStates;
        private String byUserId;
        private String byUserNickName;
        private String clientPlatform;
        private String constellation;
        //        private Object courseOrders;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.accountOrderId);
            dest.writeString(this.addressId);
            dest.writeString(this.affectiveStates);
            dest.writeString(this.byUserId);
            dest.writeString(this.byUserNickName);
            dest.writeString(this.clientPlatform);
            dest.writeString(this.constellation);
            dest.writeString(this.deviceCode);
            dest.writeString(this.isAdmin);
            dest.writeString(this.isApply);
            dest.writeString(this.isDisable);
            dest.writeString(this.isTeacher);
            dest.writeString(this.remoteAddr);
            dest.writeInt(this.userAge);
            dest.writeString(this.userBirthDay);
            dest.writeString(this.userContactTelephone);
            dest.writeString(this.userDisableBeginTime);
            dest.writeString(this.userDisableEndTime);
            dest.writeString(this.userDisableExplain);
            dest.writeString(this.userHeadPath);
            dest.writeString(this.userId);
            dest.writeString(this.userIdCard);
            dest.writeString(this.userIdCardObversePath);
            dest.writeString(this.userIdCardReversePath);
            dest.writeString(this.userLoginPassword);
            dest.writeString(this.userNickName);
            dest.writeString(this.userPayPassword);
            dest.writeString(this.userRealName);
            dest.writeString(this.userRegisterTelephone);
            dest.writeString(this.userRegisterTime);
            dest.writeString(this.userSex);
            dest.writeString(this.userSign);
        }

        public UserBean() {
        }

        protected UserBean(Parcel in) {
            this.accountOrderId = in.readString();
            this.addressId = in.readString();
            this.affectiveStates = in.readString();
            this.byUserId = in.readString();
            this.byUserNickName = in.readString();
            this.clientPlatform = in.readString();
            this.constellation = in.readString();
            this.deviceCode = in.readString();
            this.isAdmin = in.readString();
            this.isApply = in.readString();
            this.isDisable = in.readString();
            this.isTeacher = in.readString();
            this.remoteAddr = in.readString();
            this.userAge = in.readInt();
            this.userBirthDay = in.readString();
            this.userContactTelephone = in.readString();
            this.userDisableBeginTime = in.readString();
            this.userDisableEndTime = in.readString();
            this.userDisableExplain = in.readString();
            this.userHeadPath = in.readString();
            this.userId = in.readString();
            this.userIdCard = in.readString();
            this.userIdCardObversePath = in.readString();
            this.userIdCardReversePath = in.readString();
            this.userLoginPassword = in.readString();
            this.userNickName = in.readString();
            this.userPayPassword = in.readString();
            this.userRealName = in.readString();
            this.userRegisterTelephone = in.readString();
            this.userRegisterTime = in.readString();
            this.userSex = in.readString();
            this.userSign = in.readString();
        }

        public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
            @Override
            public UserBean createFromParcel(Parcel source) {
                return new UserBean(source);
            }

            @Override
            public UserBean[] newArray(int size) {
                return new UserBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.common, flags);
        dest.writeParcelable(this.user, flags);
    }

    public CommunityInfo() {
    }

    protected CommunityInfo(Parcel in) {
        this.common = in.readParcelable(CommonBean.class.getClassLoader());
        this.user = in.readParcelable(UserBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommunityInfo> CREATOR = new Parcelable.Creator<CommunityInfo>() {
        @Override
        public CommunityInfo createFromParcel(Parcel source) {
            return new CommunityInfo(source);
        }

        @Override
        public CommunityInfo[] newArray(int size) {
            return new CommunityInfo[size];
        }
    };
}
