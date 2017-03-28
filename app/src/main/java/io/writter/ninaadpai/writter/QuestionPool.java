package io.writter.ninaadpai.writter;

/**
 * Created by ninaadpai on 3/27/17.
 */
//CURRENTLY NOT IN USE
public class QuestionPool {
    String userId, questionText, category;
    Object uploadTime;
    boolean anonymous;

    public QuestionPool(String userId, String questionText, String category, Object uploadTime, boolean anonymous) {
        this.userId = userId;
        this.questionText = questionText;
        this.category = category;
        this.uploadTime = uploadTime;
        this.anonymous = anonymous;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "QuestionPool{" +
                "userId='" + userId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", category='" + category + '\'' +
                ", uploadTime=" + uploadTime  + '\'' +
                ", anonymous=" + anonymous +
                '}';
    }
}
