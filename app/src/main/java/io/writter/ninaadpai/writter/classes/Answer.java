package io.writter.ninaadpai.writter.classes;

import java.util.List;

/**
 * Created by ninaadpai on 4/2/17.
 */

public class Answer {
    String providerId, providerName, questionId, answerText;
    Object timeStamp;
    boolean anonymous;
    List<String> likers;

    public Answer(String providerId, String providerName,String questionId, String answerText, Object timeStamp, boolean anonymous, List<String> likers) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.questionId = questionId;
        this.answerText = answerText;
        this.timeStamp = timeStamp;
        this.anonymous = anonymous;
        this.likers = likers;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<String> getLikers() {
        return likers;
    }

    public void setLikers(List<String> likers) {
        this.likers = likers;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "providerId='" + providerId + '\'' +
                ", providerName='" + providerName + '\'' +
                ", questionId='" + questionId + '\'' +
                ", answerText='" + answerText + '\'' +
                ", timeStamp=" + timeStamp +
                ", anonymous=" + anonymous +
                ", likers=" + likers +
                '}';
    }
}
