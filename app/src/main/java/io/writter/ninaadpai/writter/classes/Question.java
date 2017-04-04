package io.writter.ninaadpai.writter.classes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ninaadpai on 3/27/17.
 */

public class Question implements Serializable {
    String questionId, userId, questionText, category;
    boolean anonymous;
    Object uploadTime;
    List<String> likers;
    List<String> reports;
    List<Answer> answers;

    public Question(String questionId, String userId, String questionText, String category, boolean anonymous, Object uploadTime, List<String> likers, List<String> reports, List<Answer> answers) {
        this.questionId = questionId;
        this.userId = userId;
        this.questionText = questionText;
        this.category = category;
        this.anonymous = anonymous;
        this.uploadTime = uploadTime;
        this.likers = likers;
        this.reports = reports;
        this.answers = answers;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Object getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Object uploadTime) {
        this.uploadTime = uploadTime;
    }

    public List<String> getLikers() {
        return likers;
    }

    public void setLikers(List<String> likers) {
        this.likers = likers;
    }

    public List<String> getReports() {
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", userId='" + userId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", category='" + category + '\'' +
                ", anonymous=" + anonymous +
                ", uploadTime=" + uploadTime +
                ", likers=" + likers +
                ", reports=" + reports +
                ", answers=" + answers +
                '}';
    }
}
