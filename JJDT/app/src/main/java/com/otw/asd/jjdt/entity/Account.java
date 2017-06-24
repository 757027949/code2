package com.otw.asd.jjdt.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/25.
 */
public class Account implements Serializable {

    /**
     * code : 200
     * obj : {"accountCouponCount":15,"account":{"accountBalance":0,"accountId":"402880eb54e5bf0f0154e5d027bf0002","accountIntegral":0,"userId":"402880eb54e5bf0f0154e5d0278c0001"}}
     * success : true
     */

    private int code;
    /**
     * accountCouponCount : 15
     * account : {"accountBalance":0,"accountId":"402880eb54e5bf0f0154e5d027bf0002","accountIntegral":0,"userId":"402880eb54e5bf0f0154e5d0278c0001"}
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
        private int accountCouponCount;
        /**
         * accountBalance : 0.0
         * accountId : 402880eb54e5bf0f0154e5d027bf0002
         * accountIntegral : 0
         * userId : 402880eb54e5bf0f0154e5d0278c0001
         */

        private AccountBean account;

        public int getAccountCouponCount() {
            return accountCouponCount;
        }

        public void setAccountCouponCount(int accountCouponCount) {
            this.accountCouponCount = accountCouponCount;
        }

        public AccountBean getAccount() {
            return account;
        }

        public void setAccount(AccountBean account) {
            this.account = account;
        }

        public static class AccountBean implements Serializable {
            private double accountBalance;
            private String accountId;
            private int accountIntegral;
            private String userId;

            public double getAccountBalance() {
                return accountBalance;
            }

            public void setAccountBalance(double accountBalance) {
                this.accountBalance = accountBalance;
            }

            public String getAccountId() {
                return accountId;
            }

            public void setAccountId(String accountId) {
                this.accountId = accountId;
            }

            public int getAccountIntegral() {
                return accountIntegral;
            }

            public void setAccountIntegral(int accountIntegral) {
                this.accountIntegral = accountIntegral;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
