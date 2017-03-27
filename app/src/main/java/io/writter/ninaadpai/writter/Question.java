package io.writter.ninaadpai.writter;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ninaadpai on 3/27/17.
 */

public class Question implements Serializable {
    String questionText, category;
    Object uploadTime;

    public Question(String questionText, Object uploadTime, String category) {
        this.questionText = questionText;
        this.category = category;
        this.uploadTime = uploadTime;
    }

    public Object getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Object uploadTime) {
        this.uploadTime = uploadTime;
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

    @Override
    public String toString() {
        return "Question{" +
                "timeStamp='" + uploadTime + '\'' +
                "questionText='" + questionText + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
