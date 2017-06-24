package com.otw.asd.jjdt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class ChooseRote implements Serializable {

    /**
     * siteTotalCount : 2
     * sites : [{"addressId":"402880e755817e460155817e60230000","isRoad":"Y","schoolId":"402880e9557afb9f01557aff2568000a","siteGeoHashCode":"wm7b0b9zz9cg","siteId":"402880e755817e460155817e60b20001","siteLatiTude":"29.535337","siteLongiTude":"106.559124","siteName":"观音桥"},{"addressId":"402880e755817efe0155817f14f30000","isRoad":"Y","schoolId":"402880e9557afb9f01557aff2568000a","siteGeoHashCode":"wm7b0b9zz9cg","siteId":"402880e755817efe0155817f15540001","siteLatiTude":"29.535337","siteLongiTude":"106.559124","siteName":"观音桥"}]
     */

    private int siteTotalCount;
    /**
     * addressId : 402880e755817e460155817e60230000
     * isRoad : Y
     * schoolId : 402880e9557afb9f01557aff2568000a
     * siteGeoHashCode : wm7b0b9zz9cg
     * siteId : 402880e755817e460155817e60b20001
     * siteLatiTude : 29.535337
     * siteLongiTude : 106.559124
     * siteName : 观音桥
     */

    private List<SitesBean> sites;

    public int getSiteTotalCount() {
        return siteTotalCount;
    }

    public void setSiteTotalCount(int siteTotalCount) {
        this.siteTotalCount = siteTotalCount;
    }

    public List<SitesBean> getSites() {
        return sites;
    }

    public void setSites(List<SitesBean> sites) {
        this.sites = sites;
    }

    public static class SitesBean implements Serializable {
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
