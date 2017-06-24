package com.otw.asd.jjdt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class SchoolList implements Serializable {

    /**
     * code : 200
     * obj : [{"schoolId":"402880e55509ab43015509ab4e9e0000","schoolName":"老司机驾校0"},{"schoolId":"402880e55509ab43015509ab4ef30001","schoolName":"老司机驾校1"},{"schoolId":"402880e55509ab43015509ab4f250002","schoolName":"老司机驾校2"},{"schoolId":"402880e55509cb8b015509dcd6100000","schoolName":"老司机驾校0"},{"schoolId":"402880e55509cb8b015509dcd6ac0001","schoolName":"老司机驾校1"},{"schoolId":"402880e55509cb8b015509dcd7190002","schoolName":"老司机驾校2"}]
     * success : true
     */

    private int code;
    private boolean success;
    /**
     * schoolId : 402880e55509ab43015509ab4e9e0000
     * schoolName : 老司机驾校0
     */

    private List<ObjBean> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Serializable {
        private String schoolId;
        private String schoolName;

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }
}
