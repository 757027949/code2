package com.otw.asd.jjd.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjd.util.UrlUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/27.
 */
public class AccountWithdrawal implements Serializable {

    /**
     * accountOtherBankName : zfb
     * accountOtherId : 402880e954f116ff0154f11807bb0001
     * accountOtherNumber : 18696677791
     * accountOtherOwner :
     * accountOtherType : zfb
     * userId : 402880e954eff0ef0154eff2a9fb0000
     */

    private String accountOtherBankName;
    private String accountOtherId;
    private String accountOtherNumber;
    private String accountOtherOwner;
    private String accountOtherPath;
    private String accountOtherType;
    private String isDefault;
    private String userId;

    public String getAccountOtherBankName() {
        return accountOtherBankName;
    }

    public void setAccountOtherBankName(String accountOtherBankName) {
        this.accountOtherBankName = accountOtherBankName;
    }

    public String getAccountOtherId() {
        return accountOtherId;
    }

    public void setAccountOtherId(String accountOtherId) {
        this.accountOtherId = accountOtherId;
    }

    public String getAccountOtherNumber() {
        return accountOtherNumber;
    }

    public void setAccountOtherNumber(String accountOtherNumber) {
        this.accountOtherNumber = accountOtherNumber;
    }

    public String getAccountOtherOwner() {
        return accountOtherOwner;
    }

    public void setAccountOtherOwner(String accountOtherOwner) {
        this.accountOtherOwner = accountOtherOwner;
    }

    public String getAccountOtherType() {
        return accountOtherType;
    }

    public void setAccountOtherType(String accountOtherType) {
        this.accountOtherType = accountOtherType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountOtherPath() {
        if (!StringUtil.getInstance().isEmpty(accountOtherPath) && !accountOtherPath.contains("http") && !accountOtherPath.contains("storage/emulated")) {
            accountOtherPath = UrlUtil.BASE_IMAGE_URL + accountOtherPath;
        }
        return accountOtherPath;
    }

    public void setAccountOtherPath(String accountOtherPath) {
        this.accountOtherPath = accountOtherPath;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
