package com.otw.asd.jjd.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 报名参数
 * Created by Administrator on 2016/5/23.
 */
public class Apply implements Parcelable {
    private float money;
    private String provinceName;//省
    private String cityName;//市名
    private String areaName;//区名
    private String userRealName;
    private String userHomeAddress;
    private String userContactTelephone;
    private String applyStudyType;
    private String applyStudySubject;

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserHomeAddress() {
        return userHomeAddress;
    }

    public void setUserHomeAddress(String userHomeAddress) {
        this.userHomeAddress = userHomeAddress;
    }

    public String getUserContactTelephone() {
        return userContactTelephone;
    }

    public void setUserContactTelephone(String userContactTelephone) {
        this.userContactTelephone = userContactTelephone;
    }

    public String getApplyStudyType() {
        return applyStudyType;
    }

    public void setApplyStudyType(String applyStudyType) {
        this.applyStudyType = applyStudyType;
    }

    public String getApplyStudySubject() {
        return applyStudySubject;
    }

    public void setApplyStudySubject(String applyStudySubject) {
        this.applyStudySubject = applyStudySubject;
    }

    public Apply() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.money);
        dest.writeString(this.provinceName);
        dest.writeString(this.cityName);
        dest.writeString(this.areaName);
        dest.writeString(this.userRealName);
        dest.writeString(this.userHomeAddress);
        dest.writeString(this.userContactTelephone);
        dest.writeString(this.applyStudyType);
        dest.writeString(this.applyStudySubject);
    }

    protected Apply(Parcel in) {
        this.money = in.readFloat();
        this.provinceName = in.readString();
        this.cityName = in.readString();
        this.areaName = in.readString();
        this.userRealName = in.readString();
        this.userHomeAddress = in.readString();
        this.userContactTelephone = in.readString();
        this.applyStudyType = in.readString();
        this.applyStudySubject = in.readString();
    }

    public static final Creator<Apply> CREATOR = new Creator<Apply>() {
        @Override
        public Apply createFromParcel(Parcel source) {
            return new Apply(source);
        }

        @Override
        public Apply[] newArray(int size) {
            return new Apply[size];
        }
    };
}
