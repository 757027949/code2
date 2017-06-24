package com.otw.asd.jjd.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjd.util.UrlUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class RecordInfo implements Serializable {

    /**
     * isComment : Y
     * teacher : {"isHeChang":"Y","isValidate":"Y","schoolId":"402880e9557afb9f01557aff2568000a","siteId":"402880e9557b001701557b004cb30001","teachCarPath":"img/teacher/car/0e44ddd5-122c-45fb-9237-0dd2e400a8c3.png","teachIsSubject2":"Y","teachIsSubject3":"Y","teachTypeC1":"Y","teachTypeC2":"Y","teacherCard":"8569405858868658","teacherCardPath":"img/teacher/teachCard/119accc1-f7ac-4faa-8661-68d58700844d.png","teacherId":"402880e9557afb9f01557afc77310001","teacherLevel":1,"teacherRunCardPath":"img/teacher/runCard/7dd4ebb3-2841-4f5f-b0eb-509ab867b102.png","teacherTeachYear":0,"userId":"402880e9557afb9f01557afc76b60000","validateExplain":"","validateState":"1"}
     * address : {"addressAreaName":"江北区","addressCityName":"重庆市","addressCountryName":"China","addressDetailName":"观音桥","addressId":"402880e755817e460155817e60230000","addressProvinceName":"重庆市"}
     * userTeacher : {"accountOrderId":"","addressId":"402880ea559649c50155966482220000","byUserId":"","byUserNickName":" ","clientPlatform":" ","courseOrders":[],"isApply":"N","isTeacher":"Y","userAge":0,"userContactTelephone":"","userHeadPath":"","userId":"402880e9557afb9f01557afc76b60000","userIdCard":"111111111111111","userIdCardObversePath":"img/user/IDCard/obverse/5629e2db-1c5f-4b57-9051-7e34e8ee9fbc.png","userIdCardReversePath":"img/user/IDCard/reverse/7488b745-851a-49a6-9379-d57cf91f8579.png","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"18699999999","userPayPassword":"670b14728ad9902aecba32e22fa4f6bd","userRealName":"铁军吕","userRegisterTelephone":"18699999999","userSex":"男","userSign":""}
     * courseOrderUnitPrice : 100
     * courseOrderDetail : null
     * courseOrder : {"course":{"courseId":"402880eb55babfad0155baeb5922002b","courseSubject":"科目三","courseType":"C2"},"courseOrderBeginTime":"2016-07-06 18:00:00","courseOrderEndTime":"2016-07-06 19:00:00","courseOrderId":"402880eb55babfad0155baeb5961002c","courseOrderPrice":100,"courseOrderState":"未接单","courseOrderTime":"2016-07-05 19:57:13","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":4,"siteId":"402880e755817e460155817e60b20001","teacherId":"402880e9557afb9f01557afc77310001","users":[]}
     * courseOrderTotalCount : 16
     */

    private String isComment;
    /**
     * isHeChang : Y
     * isValidate : Y
     * schoolId : 402880e9557afb9f01557aff2568000a
     * siteId : 402880e9557b001701557b004cb30001
     * teachCarPath : img/teacher/car/0e44ddd5-122c-45fb-9237-0dd2e400a8c3.png
     * teachIsSubject2 : Y
     * teachIsSubject3 : Y
     * teachTypeC1 : Y
     * teachTypeC2 : Y
     * teacherCard : 8569405858868658
     * teacherCardPath : img/teacher/teachCard/119accc1-f7ac-4faa-8661-68d58700844d.png
     * teacherId : 402880e9557afb9f01557afc77310001
     * teacherLevel : 1
     * teacherRunCardPath : img/teacher/runCard/7dd4ebb3-2841-4f5f-b0eb-509ab867b102.png
     * teacherTeachYear : 0
     * userId : 402880e9557afb9f01557afc76b60000
     * validateExplain :
     * validateState : 1
     */

    private TeacherBean teacher;
    /**
     * accountOrderId :
     * addressId : 402880ea559649c50155966482220000
     * byUserId :
     * byUserNickName :
     * clientPlatform :
     * courseOrders : []
     * isApply : N
     * isTeacher : Y
     * userAge : 0
     * userContactTelephone :
     * userHeadPath :
     * userId : 402880e9557afb9f01557afc76b60000
     * userIdCard : 111111111111111
     * userIdCardObversePath : img/user/IDCard/obverse/5629e2db-1c5f-4b57-9051-7e34e8ee9fbc.png
     * userIdCardReversePath : img/user/IDCard/reverse/7488b745-851a-49a6-9379-d57cf91f8579.png
     * userLoginPassword : e10adc3949ba59abbe56e057f20f883e
     * userNickName : 18699999999
     * userPayPassword : 670b14728ad9902aecba32e22fa4f6bd
     * userRealName : 铁军吕
     * userRegisterTelephone : 18699999999
     * userSex : 男
     * userSign :
     */

    private UserTeacherBean userTeacher;
    private int courseOrderUnitPrice;
//    private Object courseOrderDetail;
    /**
     * course : {"courseId":"402880eb55babfad0155baeb5922002b","courseSubject":"科目三","courseType":"C2"}
     * courseOrderBeginTime : 2016-07-06 18:00:00
     * courseOrderEndTime : 2016-07-06 19:00:00
     * courseOrderId : 402880eb55babfad0155baeb5961002c
     * courseOrderPrice : 100
     * courseOrderState : 未接单
     * courseOrderTime : 2016-07-05 19:57:13
     * isFinish : N
     * isSccept :
     * isUpdate : N
     * peopleNumber : 4
     * siteId : 402880e755817e460155817e60b20001
     * teacherId : 402880e9557afb9f01557afc77310001
     * users : []
     */

    private CourseOrderBean courseOrder;
    private int courseOrderTotalCount;
    /**
     * isCancel : true
     */

    private boolean isCancel;
    /**
     * address : {"addressAreaName":"江北区","addressCityName":"重庆市","addressCountryName":"China","addressDetailName":"不晓得哪里","addressId":"402880ef55de25940155de271dd70003","addressProvinceName":"重庆市"}
     * isRoad : N
     * schoolId : 402880ef55de25940155de2662fe0001
     * siteGeoHashCode : wm7b0bf8frhs
     * siteId : 402880ef55de25940155de271e490004
     * siteLatiTude : 29.535537
     * siteLongiTude : 106.5599324
     * siteName : 秋名山
     */

    private SiteBean site;

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public UserTeacherBean getUserTeacher() {
        return userTeacher;
    }

    public void setUserTeacher(UserTeacherBean userTeacher) {
        this.userTeacher = userTeacher;
    }

    public int getCourseOrderUnitPrice() {
        return courseOrderUnitPrice;
    }

    public void setCourseOrderUnitPrice(int courseOrderUnitPrice) {
        this.courseOrderUnitPrice = courseOrderUnitPrice;
    }

//    public Object getCourseOrderDetail() {
//        return courseOrderDetail;
//    }
//
//    public void setCourseOrderDetail(Object courseOrderDetail) {
//        this.courseOrderDetail = courseOrderDetail;
//    }

    public CourseOrderBean getCourseOrder() {
        return courseOrder;
    }

    public void setCourseOrder(CourseOrderBean courseOrder) {
        this.courseOrder = courseOrder;
    }

    public int getCourseOrderTotalCount() {
        return courseOrderTotalCount;
    }

    public void setCourseOrderTotalCount(int courseOrderTotalCount) {
        this.courseOrderTotalCount = courseOrderTotalCount;
    }

    public boolean isIsCancel() {
        return isCancel;
    }

    public void setIsCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public SiteBean getSite() {
        return site;
    }

    public void setSite(SiteBean site) {
        this.site = site;
    }

    public static class TeacherBean implements Serializable {
        private String isHeChang;
        private String isValidate;
        private String schoolId;
        private String siteId;
        private String teachCarPath;
        private String teachIsSubject2;
        private String teachIsSubject3;
        private String teachTypeC1;
        private String teachTypeC2;
        private String teacherCard;
        private String teacherCardPath;
        private String teacherId;
        private int teacherLevel;
        private String teacherRunCardPath;
        private int teacherTeachYear;
        private String userId;
        private String validateExplain;
        private String validateState;

        public String getIsHeChang() {
            return isHeChang;
        }

        public void setIsHeChang(String isHeChang) {
            this.isHeChang = isHeChang;
        }

        public String getIsValidate() {
            return isValidate;
        }

        public void setIsValidate(String isValidate) {
            this.isValidate = isValidate;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getTeachCarPath() {
            return teachCarPath;
        }

        public void setTeachCarPath(String teachCarPath) {
            this.teachCarPath = teachCarPath;
        }

        public String getTeachIsSubject2() {
            return teachIsSubject2;
        }

        public void setTeachIsSubject2(String teachIsSubject2) {
            this.teachIsSubject2 = teachIsSubject2;
        }

        public String getTeachIsSubject3() {
            return teachIsSubject3;
        }

        public void setTeachIsSubject3(String teachIsSubject3) {
            this.teachIsSubject3 = teachIsSubject3;
        }

        public String getTeachTypeC1() {
            return teachTypeC1;
        }

        public void setTeachTypeC1(String teachTypeC1) {
            this.teachTypeC1 = teachTypeC1;
        }

        public String getTeachTypeC2() {
            return teachTypeC2;
        }

        public void setTeachTypeC2(String teachTypeC2) {
            this.teachTypeC2 = teachTypeC2;
        }

        public String getTeacherCard() {
            return teacherCard;
        }

        public void setTeacherCard(String teacherCard) {
            this.teacherCard = teacherCard;
        }

        public String getTeacherCardPath() {
            return teacherCardPath;
        }

        public void setTeacherCardPath(String teacherCardPath) {
            this.teacherCardPath = teacherCardPath;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public int getTeacherLevel() {
            return teacherLevel;
        }

        public void setTeacherLevel(int teacherLevel) {
            this.teacherLevel = teacherLevel;
        }

        public String getTeacherRunCardPath() {
            return teacherRunCardPath;
        }

        public void setTeacherRunCardPath(String teacherRunCardPath) {
            this.teacherRunCardPath = teacherRunCardPath;
        }

        public int getTeacherTeachYear() {
            return teacherTeachYear;
        }

        public void setTeacherTeachYear(int teacherTeachYear) {
            this.teacherTeachYear = teacherTeachYear;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getValidateExplain() {
            return validateExplain;
        }

        public void setValidateExplain(String validateExplain) {
            this.validateExplain = validateExplain;
        }

        public String getValidateState() {
            return validateState;
        }

        public void setValidateState(String validateState) {
            this.validateState = validateState;
        }
    }


    public static class UserTeacherBean implements Serializable {
        private String accountOrderId;
        private String addressId;
        private String byUserId;
        private String byUserNickName;
        private String clientPlatform;
        private String isApply;
        private String isTeacher;
        private int userAge;
        private String userContactTelephone;
        private String userHeadPath;
        private String userId;
        private String userIdCard;
        private String userIdCardObversePath;
        private String userIdCardReversePath;
        private String userLoginPassword;
        private String userNickName;
        private String userPayPassword;
        private String userRealName;
        private String userRegisterTelephone;
        private String userSex;
        private String userSign;
        private List<?> courseOrders;

        public String getAccountOrderId() {
            return accountOrderId;
        }

        public void setAccountOrderId(String accountOrderId) {
            this.accountOrderId = accountOrderId;
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getByUserId() {
            return byUserId;
        }

        public void setByUserId(String byUserId) {
            this.byUserId = byUserId;
        }

        public String getByUserNickName() {
            return byUserNickName;
        }

        public void setByUserNickName(String byUserNickName) {
            this.byUserNickName = byUserNickName;
        }

        public String getClientPlatform() {
            return clientPlatform;
        }

        public void setClientPlatform(String clientPlatform) {
            this.clientPlatform = clientPlatform;
        }

        public String getIsApply() {
            return isApply;
        }

        public void setIsApply(String isApply) {
            this.isApply = isApply;
        }

        public String getIsTeacher() {
            return isTeacher;
        }

        public void setIsTeacher(String isTeacher) {
            this.isTeacher = isTeacher;
        }

        public int getUserAge() {
            return userAge;
        }

        public void setUserAge(int userAge) {
            this.userAge = userAge;
        }

        public String getUserContactTelephone() {
            return userContactTelephone;
        }

        public void setUserContactTelephone(String userContactTelephone) {
            this.userContactTelephone = userContactTelephone;
        }

        public String getUserHeadPath() {
            if (!StringUtil.getInstance().isEmpty(userHeadPath) && !userHeadPath.contains("http") && !userHeadPath.contains("storage/emulated")) {
                userHeadPath = UrlUtil.BASE_IMAGE_URL + userHeadPath;
            }
            return userHeadPath;
        }

        public void setUserHeadPath(String userHeadPath) {
            this.userHeadPath = userHeadPath;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserIdCard() {
            return userIdCard;
        }

        public void setUserIdCard(String userIdCard) {
            this.userIdCard = userIdCard;
        }

        public String getUserIdCardObversePath() {
            return userIdCardObversePath;
        }

        public void setUserIdCardObversePath(String userIdCardObversePath) {
            this.userIdCardObversePath = userIdCardObversePath;
        }

        public String getUserIdCardReversePath() {
            return userIdCardReversePath;
        }

        public void setUserIdCardReversePath(String userIdCardReversePath) {
            this.userIdCardReversePath = userIdCardReversePath;
        }

        public String getUserLoginPassword() {
            return userLoginPassword;
        }

        public void setUserLoginPassword(String userLoginPassword) {
            this.userLoginPassword = userLoginPassword;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserPayPassword() {
            return userPayPassword;
        }

        public void setUserPayPassword(String userPayPassword) {
            this.userPayPassword = userPayPassword;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getUserRegisterTelephone() {
            return userRegisterTelephone;
        }

        public void setUserRegisterTelephone(String userRegisterTelephone) {
            this.userRegisterTelephone = userRegisterTelephone;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getUserSign() {
            return userSign;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public List<?> getCourseOrders() {
            return courseOrders;
        }

        public void setCourseOrders(List<?> courseOrders) {
            this.courseOrders = courseOrders;
        }
    }

    public static class CourseOrderBean implements Serializable {
        /**
         * courseId : 402880eb55babfad0155baeb5922002b
         * courseSubject : 科目三
         * courseType : C2
         */

        private CourseBean course;
        private String courseOrderBeginTime;
        private String courseOrderEndTime;
        private String courseOrderId;
        private int courseOrderPrice;
        private String courseOrderState;
        private String courseOrderTime;
        private String isFinish;
        private String isSccept;
        private String isUpdate;
        private int peopleNumber;
        private String siteId;
        private String teacherId;
        private List<?> users;

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public String getCourseOrderBeginTime() {
            return courseOrderBeginTime;
        }

        public void setCourseOrderBeginTime(String courseOrderBeginTime) {
            this.courseOrderBeginTime = courseOrderBeginTime;
        }

        public String getCourseOrderEndTime() {
            return courseOrderEndTime;
        }

        public void setCourseOrderEndTime(String courseOrderEndTime) {
            this.courseOrderEndTime = courseOrderEndTime;
        }

        public String getCourseOrderId() {
            return courseOrderId;
        }

        public void setCourseOrderId(String courseOrderId) {
            this.courseOrderId = courseOrderId;
        }

        public int getCourseOrderPrice() {
            return courseOrderPrice;
        }

        public void setCourseOrderPrice(int courseOrderPrice) {
            this.courseOrderPrice = courseOrderPrice;
        }

        public String getCourseOrderState() {
            return courseOrderState;
        }

        public void setCourseOrderState(String courseOrderState) {
            this.courseOrderState = courseOrderState;
        }

        public String getCourseOrderTime() {
            return courseOrderTime;
        }

        public void setCourseOrderTime(String courseOrderTime) {
            this.courseOrderTime = courseOrderTime;
        }

        public String getIsFinish() {
            return isFinish;
        }

        public void setIsFinish(String isFinish) {
            this.isFinish = isFinish;
        }

        public String getIsSccept() {
            return isSccept;
        }

        public void setIsSccept(String isSccept) {
            this.isSccept = isSccept;
        }

        public String getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(String isUpdate) {
            this.isUpdate = isUpdate;
        }

        public int getPeopleNumber() {
            return peopleNumber;
        }

        public void setPeopleNumber(int peopleNumber) {
            this.peopleNumber = peopleNumber;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public List<?> getUsers() {
            return users;
        }

        public void setUsers(List<?> users) {
            this.users = users;
        }

        public static class CourseBean implements Serializable {
            private String courseId;
            private String courseSubject;
            private String courseType;

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCourseSubject() {
                return courseSubject;
            }

            public void setCourseSubject(String courseSubject) {
                this.courseSubject = courseSubject;
            }

            public String getCourseType() {
                return courseType;
            }

            public void setCourseType(String courseType) {
                this.courseType = courseType;
            }
        }
    }

    public static class SiteBean implements Serializable{
        /**
         * addressAreaName : 江北区
         * addressCityName : 重庆市
         * addressCountryName : China
         * addressDetailName : 不晓得哪里
         * addressId : 402880ef55de25940155de271dd70003
         * addressProvinceName : 重庆市
         */

        private AddressBean address;
        private String isRoad;
        private String schoolId;
        private String siteGeoHashCode;
        private String siteId;
        private String siteLatiTude;
        private String siteLongiTude;
        private String siteName;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
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

        public static class AddressBean implements Serializable{
            private String addressAreaName;
            private String addressCityName;
            private String addressCountryName;
            private String addressDetailName;
            private String addressId;
            private String addressProvinceName;

            public String getAddressAreaName() {
                return addressAreaName;
            }

            public void setAddressAreaName(String addressAreaName) {
                this.addressAreaName = addressAreaName;
            }

            public String getAddressCityName() {
                return addressCityName;
            }

            public void setAddressCityName(String addressCityName) {
                this.addressCityName = addressCityName;
            }

            public String getAddressCountryName() {
                return addressCountryName;
            }

            public void setAddressCountryName(String addressCountryName) {
                this.addressCountryName = addressCountryName;
            }

            public String getAddressDetailName() {
                return addressDetailName;
            }

            public void setAddressDetailName(String addressDetailName) {
                this.addressDetailName = addressDetailName;
            }

            public String getAddressId() {
                return addressId;
            }

            public void setAddressId(String addressId) {
                this.addressId = addressId;
            }

            public String getAddressProvinceName() {
                return addressProvinceName;
            }

            public void setAddressProvinceName(String addressProvinceName) {
                this.addressProvinceName = addressProvinceName;
            }
        }
    }
}
