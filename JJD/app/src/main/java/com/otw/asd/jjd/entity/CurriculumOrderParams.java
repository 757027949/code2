package com.otw.asd.jjd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class CurriculumOrderParams implements Serializable {
    private double courseOrderS2UnitPrice;
    private double courseOrderS3UnitPrice;
    private String amSubject;
    private String amTimeType;
    private String amSiteId;
    private String amSiteName;
    private String amType;
    private String pmSubject;
    private String pmTimeType;
    private String pmSiteId;
    private String pmSiteName;
    private String pmType;
    private List<OrderBean> amOrders;
    private List<OrderBean> pmOrders;

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

    public String getAmSubject() {
        return amSubject;
    }

    public void setAmSubject(String amSubject) {
        this.amSubject = amSubject;
    }

    public String getAmTimeType() {
        return amTimeType;
    }

    public void setAmTimeType(String amTimeType) {
        this.amTimeType = amTimeType;
    }

    public String getAmSiteId() {
        return amSiteId;
    }

    public void setAmSiteId(String amSiteId) {
        this.amSiteId = amSiteId;
    }

    public String getAmSiteName() {
        return amSiteName;
    }

    public void setAmSiteName(String amSiteName) {
        this.amSiteName = amSiteName;
    }

    public String getAmType() {
        return amType;
    }

    public void setAmType(String amType) {
        this.amType = amType;
    }

    public String getPmSubject() {
        return pmSubject;
    }

    public void setPmSubject(String pmSubject) {
        this.pmSubject = pmSubject;
    }

    public String getPmTimeType() {
        return pmTimeType;
    }

    public void setPmTimeType(String pmTimeType) {
        this.pmTimeType = pmTimeType;
    }

    public String getPmSiteId() {
        return pmSiteId;
    }

    public void setPmSiteId(String pmSiteId) {
        this.pmSiteId = pmSiteId;
    }

    public String getPmSiteName() {
        return pmSiteName;
    }

    public void setPmSiteName(String pmSiteName) {
        this.pmSiteName = pmSiteName;
    }

    public String getPmType() {
        return pmType;
    }

    public void setPmType(String pmType) {
        this.pmType = pmType;
    }

    public List<OrderBean> getAmOrders() {
        return amOrders;
    }

    public void setAmOrders(List<OrderBean> amOrders) {
        this.amOrders = amOrders;
    }

    public List<OrderBean> getPmOrders() {
        return pmOrders;
    }

    public void setPmOrders(List<OrderBean> pmOrders) {
        this.pmOrders = pmOrders;
    }

    public static class OrderBean implements Serializable {
        private String timeString;
        private String orderId;
        private double price;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTimeString() {
            return timeString;
        }

        public void setTimeString(String timeString) {
            this.timeString = timeString;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
