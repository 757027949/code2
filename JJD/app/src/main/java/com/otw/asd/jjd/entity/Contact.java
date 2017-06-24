package com.otw.asd.jjd.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.hyphenate.chat.EMConversation;

import java.io.Serializable;

/**
 * 聊天列表对象
 * Created by Administrator on 2016/9/14.
 */
public class Contact implements Serializable {
    public static final String CONTACT_KEY = "contact_key";

    private String userId;
    private String imagePath;
    private String name;
    private String sex;
    private boolean isChecked;
    private int position;
    private EMConversation conversation;

    public Contact() {
    }


    public Contact(String userId, String imagePath, String name, String sex) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.name = name;
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EMConversation getConversation() {
        return conversation;
    }

    public void setConversation(EMConversation conversation) {
        this.conversation = conversation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
