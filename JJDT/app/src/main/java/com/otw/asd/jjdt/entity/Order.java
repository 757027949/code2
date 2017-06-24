package com.otw.asd.jjdt.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/15.
 */
public class Order implements Serializable {

    /**
     * courseOrderBeginTime : 2016-07-02 03:00:00
     * courseOrderEndTime : 2016-07-02 04:00:00
     * courseSubject : 科目二
     * courseType : C1
     * siteId : 歌乐山
     * userNickName : 18688888888
     */

    private String courseOrderId;
    private String courseOrderBeginTime;
    private String courseOrderEndTime;
    private String courseSubject;
    private String courseType;
    private String siteId;
    private String userNickName;


    public String getCourseOrderId() {
        return courseOrderId;
    }

    public void setCourseOrderId(String courseOrderId) {
        this.courseOrderId = courseOrderId;
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

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}
