package com.otw.asd.jjd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠券
 * Created by Administrator on 2016/7/14.
 */
public class Voucher implements Serializable {
    private int totalCount;

    private List<DatasBean> accountCoupons;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<DatasBean> getDatas() {
        return accountCoupons;
    }

    public void setDatas(List<DatasBean> datas) {
        this.accountCoupons = datas;
    }

    public static class DatasBean implements Serializable {
        private String accountCouponId = "";//优惠券编号
        private String accountCouponName = "";//优惠卷名称
        private String accountCouponType = "rebate";//优惠券类型1，折扣券：rebate，2，代金券：replace
        private String accountCouponImagePath = "";//优惠券图片路径
        private String accountCouponUseState = "N";//优惠券是否使用
        private String accountCouponIsValid = "Y";//是否有效
        private String accountCouponTime = "";//优惠券创建时间
        private String accountCouponBeginTime = "";//优惠券使用开始时间
        private String accountCouponEndTime = "";//优惠券使用结束时间
        private String accountCouponExplain = "可用";//状态说明
        private double accountCouponReplace = 0;//金额
        private double accountCouponRebate = 1.0;//折扣率
        private String userId = "";//用户编号
        private boolean isChecked = false;//当前是否被选中

        public String getAccountCouponId() {
            return accountCouponId;
        }

        public void setAccountCouponId(String accountCouponId) {
            this.accountCouponId = accountCouponId;
        }

        public String getAccountCouponName() {
            return accountCouponName;
        }

        public void setAccountCouponName(String accountCouponName) {
            this.accountCouponName = accountCouponName;
        }

        public String getAccountCouponType() {
            return accountCouponType;
        }

        public void setAccountCouponType(String accountCouponType) {
            this.accountCouponType = accountCouponType;
        }

        public String getAccountCouponImagePath() {
            return accountCouponImagePath;
        }

        public void setAccountCouponImagePath(String accountCouponImagePath) {
            this.accountCouponImagePath = accountCouponImagePath;
        }

        public String getAccountCouponUseState() {
            return accountCouponUseState;
        }

        public void setAccountCouponUseState(String accountCouponUseState) {
            this.accountCouponUseState = accountCouponUseState;
        }

        public String getAccountCouponIsValid() {
            return accountCouponIsValid;
        }

        public void setAccountCouponIsValid(String accountCouponIsValid) {
            this.accountCouponIsValid = accountCouponIsValid;
        }

        public String getAccountCouponTime() {
            return accountCouponTime;
        }

        public void setAccountCouponTime(String accountCouponTime) {
            this.accountCouponTime = accountCouponTime;
        }

        public String getAccountCouponBeginTime() {
            return accountCouponBeginTime;
        }

        public void setAccountCouponBeginTime(String accountCouponBeginTime) {
            this.accountCouponBeginTime = accountCouponBeginTime;
        }

        public String getAccountCouponEndTime() {
            return accountCouponEndTime;
        }

        public void setAccountCouponEndTime(String accountCouponEndTime) {
            this.accountCouponEndTime = accountCouponEndTime;
        }

        public String getAccountCouponExplain() {
            return accountCouponExplain;
        }

        public void setAccountCouponExplain(String accountCouponExplain) {
            this.accountCouponExplain = accountCouponExplain;
        }

        public double getAccountCouponReplace() {
            return accountCouponReplace;
        }

        public void setAccountCouponReplace(double accountCouponReplace) {
            this.accountCouponReplace = accountCouponReplace;
        }

        public double getAccountCouponRebate() {
            return accountCouponRebate;
        }

        public void setAccountCouponRebate(double accountCouponRebate) {
            this.accountCouponRebate = accountCouponRebate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
