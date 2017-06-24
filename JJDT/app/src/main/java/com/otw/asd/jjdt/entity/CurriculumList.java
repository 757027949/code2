package com.otw.asd.jjdt.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjdt.util.UrlUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 课程信息列表
 * Created by Administrator on 2016/6/6.
 */
public class CurriculumList implements Serializable {

    /**
     * code : 200
     * obj : {"datas":[{"data":{"siteName":"","subject":"","courseOrders":[{"courseId":"","courseOrderBeginTime":"07","courseOrderDetailId":"","courseOrderEndTime":"08","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"08","courseOrderDetailId":"","courseOrderEndTime":"09","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"09","courseOrderDetailId":"","courseOrderEndTime":"10","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"10","courseOrderDetailId":"","courseOrderEndTime":"11","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]}],"timeType":"am","type":""}},{"data":{"siteName":"","subject":"","courseOrders":[{"courseId":"402880e7552454d2015524550ab60000","courseOrderBeginTime":"14","courseOrderDetailId":"","courseOrderEndTime":"14","courseOrderId":"402880e7552454d2015524550b0e0001","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"科目三","courseTotalPrice":0,"courseType":"C1","isSccept":"","isUpdate":"N","peopleNumber":3,"siteId":"402880e5550b1df301550b2dce5a0001","teacherId":"402880e55504f25c015505f410820001","users":[{"accountOrderId":"","byUserId":"","byUserName":"","isApply":"N","userAge":0,"userContactTelephone":"","userHeadPath":"","userId":"402880e954f01d970154f01dd2ac0000","userIdCard":"","userIdCardObversePath":"","userIdCardReversePath":"","userIsTeacher":"N","userLoginPassword":"c4ca4238a0b923820dcc509a6f75849b","userNickName":"1","userPayPassword":"c4ca4238a0b923820dcc509a6f75849b","userRealName":"","userRegisterTelephone":"1","userSex":"女","userSign":""}]},{"courseId":"","courseOrderBeginTime":"11","courseOrderDetailId":"","courseOrderEndTime":"12","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"12","courseOrderDetailId":"","courseOrderEndTime":"13","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"13","courseOrderDetailId":"","courseOrderEndTime":"14","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"15","courseOrderDetailId":"","courseOrderEndTime":"16","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"16","courseOrderDetailId":"","courseOrderEndTime":"17","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"17","courseOrderDetailId":"","courseOrderEndTime":"18","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"18","courseOrderDetailId":"","courseOrderEndTime":"19","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]}],"timeType":"pm","type":""}}]}
     * success : true
     */

    private int code;
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

    public static class ObjBean implements Serializable {
        /**
         * data : {"siteName":"","subject":"","courseOrders":[{"courseId":"","courseOrderBeginTime":"07","courseOrderDetailId":"","courseOrderEndTime":"08","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"08","courseOrderDetailId":"","courseOrderEndTime":"09","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"09","courseOrderDetailId":"","courseOrderEndTime":"10","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"10","courseOrderDetailId":"","courseOrderEndTime":"11","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]}],"timeType":"am","type":""}
         */
        private String isUpdate;

        private List<DatasBean> datas;

        public String getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(String isUpdate) {
            this.isUpdate = isUpdate;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements Serializable {
            /**
             * siteName :
             * subject :
             * courseOrders : [{"courseId":"","courseOrderBeginTime":"07","courseOrderDetailId":"","courseOrderEndTime":"08","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"08","courseOrderDetailId":"","courseOrderEndTime":"09","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"09","courseOrderDetailId":"","courseOrderEndTime":"10","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]},{"courseId":"","courseOrderBeginTime":"10","courseOrderDetailId":"","courseOrderEndTime":"11","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"","courseStudyState":"未完成","courseSubject":"","courseTotalPrice":0,"courseType":"","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","teacherId":"","users":[]}]
             * timeType : am
             * type :
             */

            private DataBean data;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean implements Serializable {
                private String siteId;
                private String siteName;
                private String subject;
                private String timeType;
                private String type;
                /**
                 * courseId :
                 * courseOrderBeginTime : 07
                 * courseOrderDetailId :
                 * courseOrderEndTime : 08
                 * courseOrderId :
                 * courseOrderState : 未接单
                 * courseOrderTime :
                 * courseStudyState : 未完成
                 * courseSubject :
                 * courseTotalPrice : 0
                 * courseType :
                 * isSccept :
                 * isUpdate : N
                 * peopleNumber : 0
                 * siteId :
                 * teacherId :
                 * users : [
                 * {
                 * "accountOrderId": "",
                 * "byUserId": "",
                 * "byUserName": "",
                 * "isApply": "N",
                 * "userAge": 0,
                 * "userContactTelephone": "",
                 * "userHeadPath": "",
                 * "userId": "402880e954f01d970154f01dd2ac0000",
                 * "userIdCard": "",
                 * "userIdCardObversePath": "",
                 * "userIdCardReversePath": "",
                 * "userIsTeacher": "N",
                 * "userLoginPassword": "c4ca4238a0b923820dcc509a6f75849b",
                 * "userNickName": "1",
                 * "userPayPassword": "c4ca4238a0b923820dcc509a6f75849b",
                 * "userRealName": "",
                 * "userRegisterTelephone": "1",
                 * "userSex": "女",
                 * "userSign": ""
                 * }
                 * ]
                 */

                private List<CourseOrdersBean> courseOrders;

                public String getSiteId() {
                    return siteId;
                }

                public void setSiteId(String siteId) {
                    this.siteId = siteId;
                }

                public String getSiteName() {
                    return siteName;
                }

                public void setSiteName(String siteName) {
                    this.siteName = siteName;
                }

                public String getSubject() {
                    return subject;
                }

                public void setSubject(String subject) {
                    this.subject = subject;
                }

                public String getTimeType() {
                    return timeType;
                }

                public void setTimeType(String timeType) {
                    this.timeType = timeType;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<CourseOrdersBean> getCourseOrders() {
                    return courseOrders;
                }

                public void setCourseOrders(List<CourseOrdersBean> courseOrders) {
                    this.courseOrders = courseOrders;
                }

                public static class CourseOrdersBean implements Serializable {
                    private String courseId;
                    private String courseOrderBeginTime;
                    private String courseOrderDetailId;
                    private String courseOrderEndTime;
                    private String courseOrderId;
                    private String courseOrderState;
                    private String courseOrderTime;
                    private String courseStudyState;
                    private String courseSubject;
                    private int courseTotalPrice;
                    private String courseType;
                    private String isSccept;
                    private String isUpdate;
                    private int peopleNumber;
                    private String siteId;
                    private String siteName;
                    private String teacherId;
                    private List<User> users;

                    public String getCourseId() {
                        return courseId;
                    }

                    public void setCourseId(String courseId) {
                        this.courseId = courseId;
                    }

                    public String getCourseOrderBeginTime() {
                        return courseOrderBeginTime;
                    }

                    public void setCourseOrderBeginTime(String courseOrderBeginTime) {
                        this.courseOrderBeginTime = courseOrderBeginTime;
                    }

                    public String getCourseOrderDetailId() {
                        return courseOrderDetailId;
                    }

                    public void setCourseOrderDetailId(String courseOrderDetailId) {
                        this.courseOrderDetailId = courseOrderDetailId;
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

                    public String getCourseStudyState() {
                        return courseStudyState;
                    }

                    public void setCourseStudyState(String courseStudyState) {
                        this.courseStudyState = courseStudyState;
                    }

                    public String getCourseSubject() {
                        return courseSubject;
                    }

                    public void setCourseSubject(String courseSubject) {
                        this.courseSubject = courseSubject;
                    }

                    public int getCourseTotalPrice() {
                        return courseTotalPrice;
                    }

                    public void setCourseTotalPrice(int courseTotalPrice) {
                        this.courseTotalPrice = courseTotalPrice;
                    }

                    public String getCourseType() {
                        return courseType;
                    }

                    public void setCourseType(String courseType) {
                        this.courseType = courseType;
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

                    public String getSiteName() {
                        return siteName;
                    }

                    public void setSiteName(String siteName) {
                        this.siteName = siteName;
                    }

                    public String getTeacherId() {
                        return teacherId;
                    }

                    public void setTeacherId(String teacherId) {
                        this.teacherId = teacherId;
                    }

                    public List<User> getUsers() {
                        return users;
                    }

                    public void setUsers(List<User> users) {
                        this.users = users;
                    }

                    public static class User implements Serializable{

                        /**
                         * accountOrderId :
                         * addressId : 402880ea5594872f01559489bed40000
                         * byUserId :
                         * byUserNickName : null
                         * courseOrders : null
                         * isApply : N
                         * isTeacher : N
                         * userAge : 0
                         * userContactTelephone :
                         * userHeadPath : img/user/head/46a35834-7440-4b1b-95df-350e3c720226.png
                         * userId : 402880e9557b06fe01557b3211e70000
                         * userIdCard :
                         * userIdCardObversePath :
                         * userIdCardReversePath :
                         * userLoginPassword : e10adc3949ba59abbe56e057f20f883e
                         * userNickName : 18688888888
                         * userPayPassword : 670b14728ad9902aecba32e22fa4f6bd
                         * userRealName :
                         * userRegisterTelephone : 18688888888
                         * userSex : 女
                         * userSign : 我我哭
                         */

                        private String accountOrderId;
                        private String addressId;
                        private String byUserId;
                        private Object byUserNickName;
                        private Object courseOrders;
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

                        public Object getByUserNickName() {
                            return byUserNickName;
                        }

                        public void setByUserNickName(Object byUserNickName) {
                            this.byUserNickName = byUserNickName;
                        }

                        public Object getCourseOrders() {
                            return courseOrders;
                        }

                        public void setCourseOrders(Object courseOrders) {
                            this.courseOrders = courseOrders;
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
                    }
                }
            }
        }
    }
}
