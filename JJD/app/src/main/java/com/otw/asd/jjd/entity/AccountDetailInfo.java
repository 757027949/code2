package com.otw.asd.jjd.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/30.
 */
public class AccountDetailInfo extends PinnedSectionItem implements Serializable {

    /**
     * accountId :
     * accountOrderAfterAmount : 9998.0
     * accountOrderAmount : 1.0
     * accountOrderCurrentAmount : 9999.0
     * accountOrderExplain : 提现
     * accountOrderId : 402880e85500ca7a015500cbca400000
     * accountOrderMethod : 平台支付
     * accountOrderState : 未完成
     * accountOrderTime : 16-5-30 下午4:33
     */

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
}
