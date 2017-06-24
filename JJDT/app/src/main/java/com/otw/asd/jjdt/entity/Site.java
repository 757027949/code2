package com.otw.asd.jjdt.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/1.
 */
public class Site implements Serializable {

    /**
     * addressId : null
     * schoolId : 402880e55509cb8b015509dcd7190002
     * siteGeoHashCode :
     * siteId : 402880e5550b0d8901550b0de7940001
     * siteLatiTude :
     * siteLongiTude :
     * siteName : 秋名山
     */

    private String addressId;
    private String schoolId;
    private String siteGeoHashCode;
    private String siteId;
    private String siteLatiTude;
    private String siteLongiTude;
    private String siteName;
    /**
     * isRoad : N
     */

    private String isRoad;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public String getIsRoad() {
        return isRoad;
    }

    public void setIsRoad(String isRoad) {
        this.isRoad = isRoad;
    }
}
