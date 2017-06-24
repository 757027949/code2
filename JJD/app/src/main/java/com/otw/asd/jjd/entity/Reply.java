package com.otw.asd.jjd.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 社区评论
 * Created by Administrator on 2016/10/8.
 */

public class Reply implements Parcelable {

    /**
     * commonId : 402880f157a1e7d60157a220f5af0014
     * replyAudioPath :
     * replyHref :
     * replyId : 402880f157a33dab0157a348726c0000
     * replyImagePath :
     * replyText : 333
     * replyTime : 2016-10-08 15:58:43
     * replyVideoPath :
     * userId : 402880eb5721256d0157212e23320000
     */

    private ReplyBean reply;
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

    public ReplyBean getReply() {
        return reply;
    }

    public void setReply(ReplyBean reply) {
        this.reply = reply;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class ReplyBean implements Parcelable {
        private String commonId;
        private String replyAudioPath;
        private String replyHref;
        private String replyId;
        private String replyImagePath;
        private String replyText;
        private String replyTime;
        private String replyVideoPath;
        private String userId;

        public String getCommonId() {
            return commonId;
        }

        public void setCommonId(String commonId) {
            this.commonId = commonId;
        }

        public String getReplyAudioPath() {
            return replyAudioPath;
        }

        public void setReplyAudioPath(String replyAudioPath) {
            this.replyAudioPath = replyAudioPath;
        }

        public String getReplyHref() {
            return replyHref;
        }

        public void setReplyHref(String replyHref) {
            this.replyHref = replyHref;
        }

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public String getReplyImagePath() {
            return replyImagePath;
        }

        public void setReplyImagePath(String replyImagePath) {
            this.replyImagePath = replyImagePath;
        }

        public String getReplyText() {
            return replyText;
        }

        public void setReplyText(String replyText) {
            this.replyText = replyText;
        }

        public String getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(String replyTime) {
            this.replyTime = replyTime;
        }

        public String getReplyVideoPath() {
            return replyVideoPath;
        }

        public void setReplyVideoPath(String replyVideoPath) {
            this.replyVideoPath = replyVideoPath;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.commonId);
            dest.writeString(this.replyAudioPath);
            dest.writeString(this.replyHref);
            dest.writeString(this.replyId);
            dest.writeString(this.replyImagePath);
            dest.writeString(this.replyText);
            dest.writeString(this.replyTime);
            dest.writeString(this.replyVideoPath);
            dest.writeString(this.userId);
        }

        public ReplyBean() {
        }

        protected ReplyBean(Parcel in) {
            this.commonId = in.readString();
            this.replyAudioPath = in.readString();
            this.replyHref = in.readString();
            this.replyId = in.readString();
            this.replyImagePath = in.readString();
            this.replyText = in.readString();
            this.replyTime = in.readString();
            this.replyVideoPath = in.readString();
            this.userId = in.readString();
        }

        public static final Creator<ReplyBean> CREATOR = new Creator<ReplyBean>() {
            @Override
            public ReplyBean createFromParcel(Parcel source) {
                return new ReplyBean(source);
            }

            @Override
            public ReplyBean[] newArray(int size) {
                return new ReplyBean[size];
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

        public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
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
        dest.writeParcelable(this.reply, flags);
        dest.writeParcelable(this.user, flags);
    }

    public Reply() {
    }

    protected Reply(Parcel in) {
        this.reply = in.readParcelable(ReplyBean.class.getClassLoader());
        this.user = in.readParcelable(UserBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Reply> CREATOR = new Parcelable.Creator<Reply>() {
        @Override
        public Reply createFromParcel(Parcel source) {
            return new Reply(source);
        }

        @Override
        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };
}
