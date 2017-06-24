package com.otw.asd.jjd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class AccountOtherDefault implements Serializable {

    /**
     * code : 200
     * obj : [{"accountOtherBankName":"农业银行·金穗通宝卡(银联卡)","accountOtherId":"402880e954f136d00154f13846af0000","accountOtherNumber":"6228480470783891712","accountOtherOwner":"自己","accountOtherPath":" ","accountOtherType":"bank","isDefault":"Y","userId":"402880e954eff0ef0154eff2a9fb0000"},{"accountOtherBankName":"zfb","accountOtherId":"402880e954f1749c0154f19881ad0004","accountOtherNumber":"18688888889","accountOtherOwner":"","accountOtherPath":"","accountOtherType":"zfb","isDefault":"Y","userId":"402880e954eff0ef0154eff2a9fb0000"}]
     * success : true
     */

    private int code;
    private boolean success;
    private List<AccountWithdrawal> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<AccountWithdrawal> getObj() {
        return obj;
    }

    public void setObj(List<AccountWithdrawal> obj) {
        this.obj = obj;
    }

}
