package com.otw.asd.jjd.entity;

import com.asd.android.util.StringUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/23.
 */
public class SignUp implements Serializable {

    /**
     * code : 200
     * obj : {"applyConfigCriteria":"","applyConfigId":"402880eb54db60a60154db65db210002","applyConfigItem0":"","applyConfigItem1":"","applyConfigItem2":"","applyConfigItem3":"","applyConfigItem4":"","applyConfigService":""}
     * success : true
     */

    private int code;
    /**
     * applyConfigCriteria :
     * applyConfigId : 402880eb54db60a60154db65db210002
     * applyConfigItem0 :
     * applyConfigItem1 :
     * applyConfigItem2 :
     * applyConfigItem3 :
     * applyConfigItem4 :
     * applyConfigService :
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


    /**
     * private String applyConfigId;//报名配置编号
     * private String applyConfigItem0;
     * private String applyConfigItem1;
     * private String applyConfigItem2;
     * private String applyConfigItem3;
     * private String applyConfigItem4;
     * private String applyConfigService;//服务
     * private String applyConfigCriteria;//报名条件
     */
    public static class ObjBean implements Serializable {
        private float money;
        private String applyConfigCriteria;
        private String applyConfigId;
        private String applyConfigItem0;
        private String applyConfigItem1;
        private String applyConfigItem2;
        private String applyConfigItem3;
        private String applyConfigItem4;
        private String applyConfigService;

        public float getMoney() {
            float m = 0;
            if (!StringUtil.getInstance().isEmpty(applyConfigItem0)) {
                m += Float.parseFloat(applyConfigItem0);
            }
            if (!StringUtil.getInstance().isEmpty(applyConfigItem1)) {
                m += Float.parseFloat(applyConfigItem1);
            }
            if (!StringUtil.getInstance().isEmpty(applyConfigItem2)) {
                m += Float.parseFloat(applyConfigItem2);
            }
            if (!StringUtil.getInstance().isEmpty(applyConfigItem3)) {
                m += Float.parseFloat(applyConfigItem3);
            }
            if (!StringUtil.getInstance().isEmpty(applyConfigItem4)) {
                m += Float.parseFloat(applyConfigItem4);
            }
            money = m;
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public String getApplyConfigCriteria() {
            return applyConfigCriteria;
        }

        public void setApplyConfigCriteria(String applyConfigCriteria) {
            this.applyConfigCriteria = applyConfigCriteria;
        }

        public String getApplyConfigId() {
            return applyConfigId;
        }

        public void setApplyConfigId(String applyConfigId) {
            this.applyConfigId = applyConfigId;
        }

        public String getApplyConfigItem0() {
            return applyConfigItem0;
        }

        public void setApplyConfigItem0(String applyConfigItem0) {
            this.applyConfigItem0 = applyConfigItem0;
        }

        public String getApplyConfigItem1() {
            return applyConfigItem1;
        }

        public void setApplyConfigItem1(String applyConfigItem1) {
            this.applyConfigItem1 = applyConfigItem1;
        }

        public String getApplyConfigItem2() {
            return applyConfigItem2;
        }

        public void setApplyConfigItem2(String applyConfigItem2) {
            this.applyConfigItem2 = applyConfigItem2;
        }

        public String getApplyConfigItem3() {
            return applyConfigItem3;
        }

        public void setApplyConfigItem3(String applyConfigItem3) {
            this.applyConfigItem3 = applyConfigItem3;
        }

        public String getApplyConfigItem4() {
            return applyConfigItem4;
        }

        public void setApplyConfigItem4(String applyConfigItem4) {
            this.applyConfigItem4 = applyConfigItem4;
        }

        public String getApplyConfigService() {
            return applyConfigService;
        }

        public void setApplyConfigService(String applyConfigService) {
            this.applyConfigService = applyConfigService;
        }
    }
}
