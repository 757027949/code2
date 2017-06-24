package com.otw.asd.jjd.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ChooseTeacher implements Serializable {

    /**
     * totalCount : 2
     * userAndTeachers : [{"accountOrderId":"","addressId":"402880e9557afb9f01557afc921f0003","byUserId":"","byUserName":"","isApply":"N","isHeChang":"Y","isTeacher":"Y","isValidate":"N","schoolId":"402880e9557afb9f01557aff2568000a","siteId":"402880e9557b001701557b004cb30001","teachCarPath":"img/teacher/car/0e44ddd5-122c-45fb-9237-0dd2e400a8c3.png","teachIsSubject2":"Y","teachIsSubject3":"Y","teachTypeC1":"Y","teachTypeC2":"Y","teacherCard":"8569405858868658","teacherCardPath":"img/teacher/car/0e44ddd5-122c-45fb-9237-0dd2e400a8c3.png","teacherId":"402880e9557afb9f01557afc77310001","teacherLevel":1,"teacherRunCardPath":"img/teacher/runCard/7dd4ebb3-2841-4f5f-b0eb-509ab867b102.png","teacherTeachYear":"","userAge":0,"userContactTelephone":"","userHeadPath":"","userId":"402880e9557afb9f01557afc76b60000","userIdCard":"111111111111111","userIdCardObversePath":"img/user/IDCard/obverse/5629e2db-1c5f-4b57-9051-7e34e8ee9fbc.png","userIdCardReversePath":"img/user/IDCard/reverse/7488b745-851a-49a6-9379-d57cf91f8579.png","userLoginPassword":"e10adc3949ba59abbe56e057f20f883e","userNickName":"18699999999","userPayPassword":"670b14728ad9902aecba32e22fa4f6bd","userRealName":"铁军吕","userRegisterTelephone":"18699999999","userSex":"男","userSign":"","validateExplain":"","validateState":"1"}]
     */

    private int totalCount;

    private List<Teacher> userAndTeachers;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Teacher> getUserAndTeachers() {
        return userAndTeachers;
    }

    public void setUserAndTeachers(List<Teacher> userAndTeachers) {
        this.userAndTeachers = userAndTeachers;
    }

}
