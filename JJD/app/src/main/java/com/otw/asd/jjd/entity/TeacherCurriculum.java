package com.otw.asd.jjd.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjd.util.UrlUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class TeacherCurriculum implements Serializable {


    /**
     * code : 200
     * obj : {"datas":[{"data":{"subject":"","courseOrders":[{"courseId":"","courseOrderBeginTime":"01","courseOrderDetailId":"","courseOrderEndTime":"02","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"02","courseOrderDetailId":"","courseOrderEndTime":"03","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"03","courseOrderDetailId":"","courseOrderEndTime":"04","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"04","courseOrderDetailId":"","courseOrderEndTime":"05","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"05","courseOrderDetailId":"","courseOrderEndTime":"06","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"06","courseOrderDetailId":"","courseOrderEndTime":"07","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"07","courseOrderDetailId":"","courseOrderEndTime":"08","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"08","courseOrderDetailId":"","courseOrderEndTime":"09","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"09","courseOrderDetailId":"","courseOrderEndTime":"10","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"10","courseOrderDetailId":"","courseOrderEndTime":"11","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"11","courseOrderDetailId":"","courseOrderEndTime":"12","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]}],"timeType":"am","siteId":"","siteName":"","type":""}},{"data":{"subject":"","courseOrders":[{"courseId":"","courseOrderBeginTime":"12","courseOrderDetailId":"","courseOrderEndTime":"13","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"13","courseOrderDetailId":"","courseOrderEndTime":"14","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"14","courseOrderDetailId":"","courseOrderEndTime":"15","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"15","courseOrderDetailId":"","courseOrderEndTime":"16","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"16","courseOrderDetailId":"","courseOrderEndTime":"17","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"17","courseOrderDetailId":"","courseOrderEndTime":"18","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"18","courseOrderDetailId":"","courseOrderEndTime":"19","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"19","courseOrderDetailId":"","courseOrderEndTime":"20","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]}],"timeType":"pm","siteId":"","siteName":"","type":""}}],"isUpdate":"N"}
     * success : true
     */

    private int code;
    /**
     * datas : [{"data":{"subject":"","courseOrders":[{"courseId":"","courseOrderBeginTime":"01","courseOrderDetailId":"","courseOrderEndTime":"02","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"02","courseOrderDetailId":"","courseOrderEndTime":"03","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"03","courseOrderDetailId":"","courseOrderEndTime":"04","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"04","courseOrderDetailId":"","courseOrderEndTime":"05","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"05","courseOrderDetailId":"","courseOrderEndTime":"06","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"06","courseOrderDetailId":"","courseOrderEndTime":"07","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"07","courseOrderDetailId":"","courseOrderEndTime":"08","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"08","courseOrderDetailId":"","courseOrderEndTime":"09","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"09","courseOrderDetailId":"","courseOrderEndTime":"10","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"10","courseOrderDetailId":"","courseOrderEndTime":"11","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"11","courseOrderDetailId":"","courseOrderEndTime":"12","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]}],"timeType":"am","siteId":"","siteName":"","type":""}},{"data":{"subject":"","courseOrders":[{"courseId":"","courseOrderBeginTime":"12","courseOrderDetailId":"","courseOrderEndTime":"13","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"13","courseOrderDetailId":"","courseOrderEndTime":"14","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"14","courseOrderDetailId":"","courseOrderEndTime":"15","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"15","courseOrderDetailId":"","courseOrderEndTime":"16","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"16","courseOrderDetailId":"","courseOrderEndTime":"17","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"17","courseOrderDetailId":"","courseOrderEndTime":"18","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"18","courseOrderDetailId":"","courseOrderEndTime":"19","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"19","courseOrderDetailId":"","courseOrderEndTime":"20","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"N","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]}],"timeType":"pm","siteId":"","siteName":"","type":""}}]
     * isUpdate : N
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

    public static class ObjBean implements Serializable {
        private double courseOrderS2UnitPrice;
        private double courseOrderS3UnitPrice;
        private String isUpdate;
        /**
         * data : {"subject":"","courseOrders":[{"courseId":"","courseOrderBeginTime":"01","courseOrderDetailId":"","courseOrderEndTime":"02","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"02","courseOrderDetailId":"","courseOrderEndTime":"03","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"03","courseOrderDetailId":"","courseOrderEndTime":"04","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"04","courseOrderDetailId":"","courseOrderEndTime":"05","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"05","courseOrderDetailId":"","courseOrderEndTime":"06","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"06","courseOrderDetailId":"","courseOrderEndTime":"07","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"07","courseOrderDetailId":"","courseOrderEndTime":"08","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"08","courseOrderDetailId":"","courseOrderEndTime":"09","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"09","courseOrderDetailId":"","courseOrderEndTime":"10","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"10","courseOrderDetailId":"","courseOrderEndTime":"11","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"11","courseOrderDetailId":"","courseOrderEndTime":"12","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]}],"timeType":"am","siteId":"","siteName":"","type":""}
         */

        private List<DatasBean> datas;

        public double getCourseOrderS2UnitPrice() {
            return courseOrderS2UnitPrice;
        }

        public void setCourseOrderS2UnitPrice(double courseOrderS2UnitPrice) {
            this.courseOrderS2UnitPrice = courseOrderS2UnitPrice;
        }

        public double getCourseOrderS3UnitPrice() {
            return courseOrderS3UnitPrice;
        }

        public void setCourseOrderS3UnitPrice(double courseOrderS3UnitPrice) {
            this.courseOrderS3UnitPrice = courseOrderS3UnitPrice;
        }

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
             * subject :
             * courseOrders : [{"courseId":"","courseOrderBeginTime":"01","courseOrderDetailId":"","courseOrderEndTime":"02","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"02","courseOrderDetailId":"","courseOrderEndTime":"03","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"03","courseOrderDetailId":"","courseOrderEndTime":"04","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"04","courseOrderDetailId":"","courseOrderEndTime":"05","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"05","courseOrderDetailId":"","courseOrderEndTime":"06","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"06","courseOrderDetailId":"","courseOrderEndTime":"07","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"07","courseOrderDetailId":"","courseOrderEndTime":"08","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"08","courseOrderDetailId":"","courseOrderEndTime":"09","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"09","courseOrderDetailId":"","courseOrderEndTime":"10","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"10","courseOrderDetailId":"","courseOrderEndTime":"11","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]},{"courseId":"","courseOrderBeginTime":"11","courseOrderDetailId":"","courseOrderEndTime":"12","courseOrderId":"","courseOrderState":"未接单","courseOrderTime":"2016-06-28 17:52:21","courseSubject":"","courseTotalPrice":0,"courseType":"","isFinish":"N","isSccept":"","isUpdate":"Y","peopleNumber":0,"siteId":"","siteName":"","teacherId":"","teacherName":"","users":[]}]
             * timeType : am
             * siteId :
             * siteName :
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
                private String subject;
                private String timeType;
                private String siteId;
                private String siteName;
                private String type;
                /**
                 * courseId :
                 * courseOrderBeginTime : 01
                 * courseOrderDetailId :
                 * courseOrderEndTime : 02
                 * courseOrderId :
                 * courseOrderState : 未接单
                 * courseOrderTime : 2016-06-28 17:52:21
                 * courseSubject :
                 * courseTotalPrice : 0
                 * courseType :
                 * isFinish : N
                 * isSccept :
                 * isUpdate : Y
                 * peopleNumber : 0
                 * siteId :
                 * siteName :
                 * teacherId :
                 * teacherName :
                 * users : []
                 */

                private List<CourseOrdersBean> courseOrders;

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
                    private String courseSubject;
                    private double courseOrderPrice;
                    private String courseType;
                    private String isFinish;
                    private String isSccept;
                    private String validateCode;
                    private String isUpdate;
                    private int peopleNumber;
                    private String siteId;
                    private String siteName;
                    private String teacherId;
                    private String teacherName;
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

                    public String getCourseSubject() {
                        return courseSubject;
                    }

                    public void setCourseSubject(String courseSubject) {
                        this.courseSubject = courseSubject;
                    }

                    public double getCourseOrderPrice() {
                        return courseOrderPrice;
                    }

                    public void setCourseOrderPrice(double courseOrderPrice) {
                        this.courseOrderPrice = courseOrderPrice;
                    }

                    public String getCourseType() {
                        return courseType;
                    }

                    public void setCourseType(String courseType) {
                        this.courseType = courseType;
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

                    /**
                     * @return 200：可以 500：已预约 501：过时
                     */
                    public String getValidateCode() {
                        return validateCode;
                    }

                    public void setValidateCode(String validateCode) {
                        this.validateCode = validateCode;
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

                    public String getTeacherName() {
                        return teacherName;
                    }

                    public void setTeacherName(String teacherName) {
                        this.teacherName = teacherName;
                    }

                    public List<User> getUsers() {
                        return users;
                    }

                    public void setUsers(List<User> users) {
                        this.users = users;
                    }

                    public static class User implements Serializable {

                        /**
                         * accountOrderId :
                         * addressId : 402880ea5594872f01559489bed40000
                         * byUserId :
                         * byUserName :
                         * courseOrders : null
                         * isApply : N
                         * isTeacher : N
                         * userAge : 0
                         * userContactTelephone :
                         * userHeadPath : img/user/head/d056f86c-aa8d-4822-a821-f7c2ac10c1e9.png
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
                        private String byUserName;
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

                        public String getByUserName() {
                            return byUserName;
                        }

                        public void setByUserName(String byUserName) {
                            this.byUserName = byUserName;
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
