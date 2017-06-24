package com.otw.asd.jjd.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.asd.android.util.BitmapUtil;

/**
 * 分享课时参数
 * Created by Administrator on 2016/9/26.
 */

public class ChatRedordParam implements Parcelable {
    private String teacherId;
    private String userId;
    private String imageString;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.teacherId);
        dest.writeString(this.userId);
        dest.writeString(this.imageString);
    }

    public ChatRedordParam() {
    }

    protected ChatRedordParam(Parcel in) {
        this.teacherId = in.readString();
        this.userId = in.readString();
        this.imageString = in.readString();
    }

    public static final Creator<ChatRedordParam> CREATOR = new Creator<ChatRedordParam>() {
        @Override
        public ChatRedordParam createFromParcel(Parcel source) {
            return new ChatRedordParam(source);
        }

        @Override
        public ChatRedordParam[] newArray(int size) {
            return new ChatRedordParam[size];
        }
    };
}
