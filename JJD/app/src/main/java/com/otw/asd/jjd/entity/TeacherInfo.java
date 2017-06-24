package com.otw.asd.jjd.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/27.
 */
public class TeacherInfo implements Serializable {


    private Teacher userAndTeacher;
    /**
     * userAndTeacher : {"accountOrderId":"","addressId":"402880e75581827e015581b2c76b0006","byUserId":"","byUserName":"","isApply":"N","isHeChang":"Y","isTeacher":"Y","isValidate":"Y","schoolId":"402880e9557afb9f01557aff2568000a","siteId":"402880e9557b001701557b004cb30001","teachCarPath":"img/teacher/car/0e44ddd5-122c-45fb-9237-0dd2e400a8c3.png","teachIsSubject2":"Y","teachIsSubject3":"Y","teachTypeC1":"Y","teachTypeC2":"Y","teacherCard":"8569405858868658","teacherCardPath":"img/teacher/car/0e44ddd5-122c-45fb-9237-0dd2e400a8c3.png","teacherId":"402880e9557afb9f01557afc77310001","teacherLevel":1,"teacherRunCardPath":"img/teacher/runCard/7dd4ebb3-2841-4f5f-b0eb-509ab867b102.png","teacherTeachYear":"","userAge":0,"userContactTelephone":"","userHeadPath":"","userId":"402880e9557afb9f01557afc76b60000","userIdCard":"111111111111111","userIdCardObversePath":"img/user/IDCard/obverse/5629e2db-1c5f-4b57-9051-7e34e8ee9fbc.png","userIdCardReversePath":"img/user/IDCard/reverse/7488b745-851a-49a6-9379-d57cf91f8579.png","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"18699999999","userPayPassword":"670b14728ad9902aecba32e22fa4f6bd","userRealName":"铁军吕","userRegisterTelephone":"18699999999","userSex":"男","userSign":"","validateExplain":"","validateState":"1"}
     * courseOrderTotalCount : 6
     */

    private int courseOrderTotalCount;
    /**
     * addressId : 402880e9557b001701557b004c3b0000
     * isRoad : Y
     * schoolId : 402880e9557afb9f01557aff2568000a
     * siteGeoHashCode : wm7b0bdp1zhr
     * siteId : 402880e9557b001701557b004cb30001
     * siteLatiTude : 29.535237
     * siteLongiTude : 106.559224
     * siteName : 歌乐山
     */

    private SiteBean site;

    public Teacher getUserAndTeacher() {
        return userAndTeacher;
    }

    public void setUserAndTeacher(Teacher userAndTeacher) {
        this.userAndTeacher = userAndTeacher;
    }

    public int getCourseOrderTotalCount() {
        return courseOrderTotalCount;
    }

    public void setCourseOrderTotalCount(int courseOrderTotalCount) {
        this.courseOrderTotalCount = courseOrderTotalCount;
    }

    public SiteBean getSite() {
        return site;
    }

    public void setSite(SiteBean site) {
        this.site = site;
    }

    public static class SiteBean implements Serializable {
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
