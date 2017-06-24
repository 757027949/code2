package com.otw.asd.jjd.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/27.
 */

public class UserConfig implements Serializable {

    /**
     * isBindQQ :
     * isBindWechat :
     * userConfigId : 402880f157a8d0740157a8d087c00000
     * userId : 402880e65643e205015643e3718b0000
     */

    private String isBindQQ;
    private String isBindWechat;
    private String userConfigId;
    private String userId;

    public String getIsBindQQ() {
        return isBindQQ;
    }

    public void setIsBindQQ(String isBindQQ) {
        this.isBindQQ = isBindQQ;
    }

    public String getIsBindWechat() {
        return isBindWechat;
    }

    public void setIsBindWechat(String isBindWechat) {
        this.isBindWechat = isBindWechat;
    }

    public String getUserConfigId() {
        return userConfigId;
    }

    public void setUserConfigId(String userConfigId) {
        this.userConfigId = userConfigId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
