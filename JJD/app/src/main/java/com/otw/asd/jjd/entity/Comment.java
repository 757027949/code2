package com.otw.asd.jjd.entity;

import com.asd.android.util.StringUtil;
import com.otw.asd.jjd.util.UrlUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 评价对象
 * Created by Administrator on 2016/6/28.
 */
public class Comment implements Serializable {

    /**
     * commentTotalCount : 1
     * commentAndUsers : [{"commentContent":"","commentEvaScore":5,"commentId":"402880ea5594fa28015594fa4fc10000","commentTime":"2016-06-28 11:08:00","userHeadPath":"img/user/head/046c55fe-dacd-40e5-8e35-3504fa99e29a.png","userId":"402880e9557b06fe01557b3211e70000","userNickName":"18688888888"}]
     */

    private int commentTotalCount;
    /**
     * commentContent :
     * commentEvaScore : 5
     * commentId : 402880ea5594fa28015594fa4fc10000
     * commentTime : 2016-06-28 11:08:00
     * userHeadPath : img/user/head/046c55fe-dacd-40e5-8e35-3504fa99e29a.png
     * userId : 402880e9557b06fe01557b3211e70000
     * userNickName : 18688888888
     */

    private List<CommentAndUsersBean> commentAndUsers;

    public int getCommentTotalCount() {
        return commentTotalCount;
    }

    public void setCommentTotalCount(int commentTotalCount) {
        this.commentTotalCount = commentTotalCount;
    }

    public List<CommentAndUsersBean> getCommentAndUsers() {
        return commentAndUsers;
    }

    public void setCommentAndUsers(List<CommentAndUsersBean> commentAndUsers) {
        this.commentAndUsers = commentAndUsers;
    }

    public static class CommentAndUsersBean implements Serializable {
        private String commentContent;
        private int commentEvaScore;
        private String commentId;
        private String commentTime;
        private String userHeadPath;
        private String userId;
        private String userNickName;

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public int getCommentEvaScore() {
            return commentEvaScore;
        }

        public void setCommentEvaScore(int commentEvaScore) {
            this.commentEvaScore = commentEvaScore;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
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

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }
    }
}
