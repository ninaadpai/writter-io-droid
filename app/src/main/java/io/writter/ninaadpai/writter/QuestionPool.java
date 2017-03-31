package io.writter.ninaadpai.writter;

import java.util.List;

/**
 * Created by ninaadpai on 3/27/17.
 */
//CURRENTLY NOT IN USE
public class QuestionPool {
    String imgUrl, userId, userName, questionText, category;
    Object uploadTime;
    boolean anonymous;
    List<String> likers;

    public QuestionPool(String imgUrl, String userId, String userName, String questionText, String category, Object uploadTime, boolean anonymous) {
        this.imgUrl = imgUrl;
        this.userId = userId;
        this.userName = userName;
        this.questionText = questionText;
        this.category = category;
        this.uploadTime = uploadTime;
        this.anonymous = anonymous;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Object uploadTime) {
        this.uploadTime = uploadTime;
    }

    public boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean uploadTime) {
        this.anonymous = anonymous;
    }

    public List<String> getLikers() {
        return likers;
    }

    public void setLikers(List<String> likers) {
        this.likers = likers;
    }


    @Override
    public String toString() {
        return "QuestionPool{" +
                "imgUrl='" + imgUrl + '\'' +
                "userId='" + userId + '\'' +
                "userName='" + userName + '\'' +
                ", questionText='" + questionText + '\'' +
                ", category='" + category + '\'' +
                ", uploadTime=" + uploadTime  + '\'' +
                ", anonymous=" + anonymous +
                '}';
    }
}
