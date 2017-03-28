package io.writter.ninaadpai.writter;

/**
 * Created by ninaadpai on 3/27/17.
 */
//CURRENTLY NOT IN USE
public class QuestionPool {
    String userName, userId, questionText, category;
    Object uploadTime;

    public QuestionPool(String userName, String userId, String questionText, String category, Object uploadTime) {
        this.userName = userName;
        this.userId = userId;
        this.questionText = questionText;
        this.category = category;
        this.uploadTime = uploadTime;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUestionText() {
        return questionText;
    }

    public void setUestionText(String uestionText) {
        this.questionText = uestionText;
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

    @Override
    public String toString() {
        return "QuestionPool{" +
                "userName" + userName +'\'' +
                "userId='" + userId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", category='" + category + '\'' +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
