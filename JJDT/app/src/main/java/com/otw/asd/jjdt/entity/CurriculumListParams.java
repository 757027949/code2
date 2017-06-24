package com.otw.asd.jjdt.entity;

/**
 * Created by Administrator on 2016/6/8.
 */
public class CurriculumListParams {
    private String courseOrderBeginTime;
    private String courseOrderEndTime;
    private int peopleNumber;
    private String courseOrderId;
    private String courseId;

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

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getCourseOrderId() {
        return courseOrderId;
    }

    public void setCourseOrderId(String courseOrderId) {
        this.courseOrderId = courseOrderId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
