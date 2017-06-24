package com.otw.asd.jjd.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/30.
 */
public class CurriculumOrder implements Serializable {

    /**
     * accountId : 402880e9557b06fe01557b3212600001
     * accountOrderAfterAmount : 0.0
     * accountOrderAmount : 2000.0
     * accountOrderCurrentAmount : 0.0
     * accountOrderExplain : 课程报名
     * accountOrderId : 402880e755a03a680155a058d2e70000
     * accountOrderMethod : zfb
     * accountOrderState : N
     * accountOrderTime : 2016-06-30 16:07:03
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
