package com.otw.asd.jjd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 场地地址
 * Created by Administrator on 2016/4/8.
 */
public class MarkerArea implements Serializable {

    /**
     * code : 200
     * obj : [{"addressId":null,"isRoad":"N","schoolId":"402880e6555c5df901555c5ed8810003","siteGeoHashCode":"wm7b0bdnh1yy","siteId":"402880e6555c6b4701555c6c23610000","siteLatiTude":"29.535037","siteLongiTude":"106.559324","siteName":"789"}]
     * success : true
     */

    private int code;
    private boolean success;
    /**
     * addressId : null
     * isRoad : N
     * schoolId : 402880e6555c5df901555c5ed8810003
     * siteGeoHashCode : wm7b0bdnh1yy
     * siteId : 402880e6555c6b4701555c6c23610000
     * siteLatiTude : 29.535037
     * siteLongiTude : 106.559324
     * siteName : 789
     */

    private List<ObjBean> obj;

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

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Serializable {
        private String addressId;
        private String isRoad;
        private String schoolId;
        private String siteGeoHashCode;
        private String siteId;
        private String siteLatiTude;
        private String siteLongiTude;
        private String siteName;

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getIsRoad() {
            return isRoad;
        }

        public void setIsRoad(String isRoad) {
            this.isRoad = isRoad;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSiteGeoHashCode() {
            return siteGeoHashCode;
        }

        public void setSiteGeoHashCode(String siteGeoHashCode) {
            this.siteGeoHashCode = siteGeoHashCode;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getSiteLatiTude() {
            return siteLatiTude;
        }

        public void setSiteLatiTude(String siteLatiTude) {
            this.siteLatiTude = siteLatiTude;
        }

        public String getSiteLongiTude() {
            return siteLongiTude;
        }

        public void setSiteLongiTude(String siteLongiTude) {
            this.siteLongiTude = siteLongiTude;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }
    }
}
