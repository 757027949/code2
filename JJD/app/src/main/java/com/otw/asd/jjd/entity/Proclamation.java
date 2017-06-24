package com.otw.asd.jjd.entity;

import java.io.Serializable;

/**
 * 公告
 * Created by Administrator on 2016/7/13.
 */
public class Proclamation implements Serializable {
    private String noticeId = "";//编号
    private String noticeName = "";//公告名字
    private String noticeTime = "";//公告时间
    private String noticeShowBeginTime = "";//公告开始时间
    private String noticeShowEndTime = "";//公告结束时间
    private long noticeShowIntervalTime = 0;//公告显示间隔时间(秒)
    private String noticeTitle = "";//公告标题
    private String noticeContent = "";//公告内容
    private long noticShowTime = 0;//公告显示时间（秒）
    private String noticeTarget = "all";//公告目标
    private String noticePlatform = "all";//公告平台android ios
    private String noticeTerminal = "all";//公告终端，学生端，老师端

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getNoticeShowBeginTime() {
        return noticeShowBeginTime;
    }

    public void setNoticeShowBeginTime(String noticeShowBeginTime) {
        this.noticeShowBeginTime = noticeShowBeginTime;
    }

    public String getNoticeShowEndTime() {
        return noticeShowEndTime;
    }

    public void setNoticeShowEndTime(String noticeShowEndTime) {
        this.noticeShowEndTime = noticeShowEndTime;
    }

    public long getNoticeShowIntervalTime() {
        return noticeShowIntervalTime;
    }

    public void setNoticeShowIntervalTime(long noticeShowIntervalTime) {
        this.noticeShowIntervalTime = noticeShowIntervalTime;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public long getNoticShowTime() {
        return noticShowTime;
    }

    public void setNoticShowTime(long noticShowTime) {
        this.noticShowTime = noticShowTime;
    }

    public String getNoticeTarget() {
        return noticeTarget;
    }

    public void setNoticeTarget(String noticeTarget) {
        this.noticeTarget = noticeTarget;
    }

    public String getNoticePlatform() {
        return noticePlatform;
    }

    public void setNoticePlatform(String noticePlatform) {
        this.noticePlatform = noticePlatform;
    }

    public String getNoticeTerminal() {
        return noticeTerminal;
    }

    public void setNoticeTerminal(String noticeTerminal) {
        this.noticeTerminal = noticeTerminal;
    }
}
