package com.otw.asd.jjd.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/24.
 */
public class SignUpOrder implements Parcelable {


    /**
     * code : 200
     * obj : {"apply":{"applyId":"402880eb54e2187e0154e219e3510003","applyStudySubject":"科目二","applyStudyType":"C2","applyTime":"16-5-24 下午5:30","isHandle":"N","userId":"402880eb54e0ec7c0154e0ecfad50000"},"accountOrder":{"accountId":"402880eb54e0ec7c0154e0ecfb210001","accountOrderAfterAmount":0,"accountOrderAmount":2500,"accountOrderCurrentAmount":90000,"accountOrderExplain":"报名学车","accountOrderId":"402880eb54e2187e0154e219e2670002","accountOrderMethod":"zfb","accountOrderState":"已完成","accountOrderTime":"16-5-24 下午5:30"}}
     * success : true
     */

    private int code;
    /**
     * apply : {"applyId":"402880eb54e2187e0154e219e3510003","applyStudySubject":"科目二","applyStudyType":"C2","applyTime":"16-5-24 下午5:30","isHandle":"N","userId":"402880eb54e0ec7c0154e0ecfad50000"}
     * accountOrder : {"accountId":"402880eb54e0ec7c0154e0ecfb210001","accountOrderAfterAmount":0,"accountOrderAmount":2500,"accountOrderCurrentAmount":90000,"accountOrderExplain":"报名学车","accountOrderId":"402880eb54e2187e0154e219e2670002","accountOrderMethod":"zfb","accountOrderState":"已完成","accountOrderTime":"16-5-24 下午5:30"}
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

    public static class ObjBean implements Parcelable {
        /**
         * applyId : 402880eb54e2187e0154e219e3510003
         * applyStudySubject : 科目二
         * applyStudyType : C2
         * applyTime : 16-5-24 下午5:30
         * isHandle : N
         * userId : 402880eb54e0ec7c0154e0ecfad50000
         */

        private ApplyBean apply;
        /**
         * accountId : 402880eb54e0ec7c0154e0ecfb210001
         * accountOrderAfterAmount : 0.0
         * accountOrderAmount : 2500.0
         * accountOrderCurrentAmount : 90000.0
         * accountOrderExplain : 报名学车
         * accountOrderId : 402880eb54e2187e0154e219e2670002
         * accountOrderMethod : zfb
         * accountOrderState : 已完成
         * accountOrderTime : 16-5-24 下午5:30
         */

        private AccountOrderBean accountOrder;

        public ApplyBean getApply() {
            return apply;
        }

        public void setApply(ApplyBean apply) {
            this.apply = apply;
        }

        public AccountOrderBean getAccountOrder() {
            return accountOrder;
        }

        public void setAccountOrder(AccountOrderBean accountOrder) {
            this.accountOrder = accountOrder;
        }

        public static class ApplyBean implements Parcelable {
            private String applyId;
            private String applyStudySubject;
            private String applyStudyType;
            private String applyTime;
            private String isHandle;
            private String userId;

            public String getApplyId() {
                return applyId;
            }

            public void setApplyId(String applyId) {
                this.applyId = applyId;
            }

            public String getApplyStudySubject() {
                return applyStudySubject;
            }

            public void setApplyStudySubject(String applyStudySubject) {
                this.applyStudySubject = applyStudySubject;
            }

            public String getApplyStudyType() {
                return applyStudyType;
            }

            public void setApplyStudyType(String applyStudyType) {
                this.applyStudyType = applyStudyType;
            }

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public String getIsHandle() {
                return isHandle;
            }

            public void setIsHandle(String isHandle) {
                this.isHandle = isHandle;
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
                dest.writeString(this.applyId);
                dest.writeString(this.applyStudySubject);
                dest.writeString(this.applyStudyType);
                dest.writeString(this.applyTime);
                dest.writeString(this.isHandle);
                dest.writeString(this.userId);
            }

            public ApplyBean() {
            }

            protected ApplyBean(Parcel in) {
                this.applyId = in.readString();
                this.applyStudySubject = in.readString();
                this.applyStudyType = in.readString();
                this.applyTime = in.readString();
                this.isHandle = in.readString();
                this.userId = in.readString();
            }

            public static final Creator<ApplyBean> CREATOR = new Creator<ApplyBean>() {
                @Override
                public ApplyBean createFromParcel(Parcel source) {
                    return new ApplyBean(source);
                }

                @Override
                public ApplyBean[] newArray(int size) {
                    return new ApplyBean[size];
                }
            };
        }

        public static class AccountOrderBean implements Parcelable {
            private String accountId;
            private double accountOrderAfterAmount;
            private double accountOrderAmount;
            private double accountOrderCurrentAmount;
            private String accountOrderExplain;
            private String accountOrderId;
            private String accountOrderMethod;
            private String accountOrderState;
            private String accountOrderTime;

            public String getAccountId() {
                return accountId;
            }

            public void setAccountId(String accountId) {
                this.accountId = accountId;
            }

            public double getAccountOrderAfterAmount() {
                return accountOrderAfterAmount;
            }

            public void setAccountOrderAfterAmount(double accountOrderAfterAmount) {
                this.accountOrderAfterAmount = accountOrderAfterAmount;
            }

            public double getAccountOrderAmount() {
                return accountOrderAmount;
            }

            public void setAccountOrderAmount(double accountOrderAmount) {
                this.accountOrderAmount = accountOrderAmount;
            }

            public double getAccountOrderCurrentAmount() {
                return accountOrderCurrentAmount;
            }

            public void setAccountOrderCurrentAmount(double accountOrderCurrentAmount) {
                this.accountOrderCurrentAmount = accountOrderCurrentAmount;
            }

            public String getAccountOrderExplain() {
                return accountOrderExplain;
            }

            public void setAccountOrderExplain(String accountOrderExplain) {
                this.accountOrderExplain = accountOrderExplain;
            }

            public String getAccountOrderId() {
                return accountOrderId;
            }

            public void setAccountOrderId(String accountOrderId) {
                this.accountOrderId = accountOrderId;
            }

            public String getAccountOrderMethod() {
                return accountOrderMethod;
            }

            public void setAccountOrderMethod(String accountOrderMethod) {
                this.accountOrderMethod = accountOrderMethod;
            }

            public String getAccountOrderState() {
                return accountOrderState;
            }

            public void setAccountOrderState(String accountOrderState) {
                this.accountOrderState = accountOrderState;
            }

            public String getAccountOrderTime() {
                return accountOrderTime;
            }

            public void setAccountOrderTime(String accountOrderTime) {
                this.accountOrderTime = accountOrderTime;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.accountId);
                dest.writeDouble(this.accountOrderAfterAmount);
                dest.writeDouble(this.accountOrderAmount);
                dest.writeDouble(this.accountOrderCurrentAmount);
                dest.writeString(this.accountOrderExplain);
                dest.writeString(this.accountOrderId);
                dest.writeString(this.accountOrderMethod);
                dest.writeString(this.accountOrderState);
                dest.writeString(this.accountOrderTime);
            }

            public AccountOrderBean() {
            }

            protected AccountOrderBean(Parcel in) {
                this.accountId = in.readString();
                this.accountOrderAfterAmount = in.readDouble();
                this.accountOrderAmount = in.readDouble();
                this.accountOrderCurrentAmount = in.readDouble();
                this.accountOrderExplain = in.readString();
                this.accountOrderId = in.readString();
                this.accountOrderMethod = in.readString();
                this.accountOrderState = in.readString();
                this.accountOrderTime = in.readString();
            }

            public static final Creator<AccountOrderBean> CREATOR = new Creator<AccountOrderBean>() {
                @Override
                public AccountOrderBean createFromParcel(Parcel source) {
                    return new AccountOrderBean(source);
                }

                @Override
                public AccountOrderBean[] newArray(int size) {
                    return new AccountOrderBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.apply, flags);
            dest.writeParcelable(this.accountOrder, flags);
        }

        public ObjBean() {
        }

        protected ObjBean(Parcel in) {
            this.apply = in.readParcelable(ApplyBean.class.getClassLoader());
            this.accountOrder = in.readParcelable(AccountOrderBean.class.getClassLoader());
        }

        public static final Creator<ObjBean> CREATOR = new Creator<ObjBean>() {
            @Override
            public ObjBean createFromParcel(Parcel source) {
                return new ObjBean(source);
            }

            @Override
            public ObjBean[] newArray(int size) {
                return new ObjBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeParcelable(this.obj, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public SignUpOrder() {
    }

    protected SignUpOrder(Parcel in) {
        this.code = in.readInt();
        this.obj = in.readParcelable(ObjBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SignUpOrder> CREATOR = new Parcelable.Creator<SignUpOrder>() {
        @Override
        public SignUpOrder createFromParcel(Parcel source) {
            return new SignUpOrder(source);
        }

        @Override
        public SignUpOrder[] newArray(int size) {
            return new SignUpOrder[size];
        }
    };
}
