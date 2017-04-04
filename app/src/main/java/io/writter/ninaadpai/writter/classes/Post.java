package io.writter.ninaadpai.writter.classes;

import java.util.List;

/**
 * Created by ninaadpai on 3/24/17.
 */

public class Post {
    Question question;
    List<Answer> answer;

    public Post(Question question, List<Answer> answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Post{" +
                "question=" + question +
                ", answer=" + answer +
                '}';
    }

    //    String questionId, topicString, postQuestion, postDesc;
//    long uploadedTime;
//
//    public Post(String questionId, String topicString, long uploadedTime, String postQuestion, String postDesc) {
//        this.questionId = questionId;
//        this.topicString = topicString;
//        this.uploadedTime = uploadedTime;
//        this.postQuestion = postQuestion;
//        this.postDesc = postDesc;
//    }
//    public String getQuestionId() {
//        return questionId;
//    }
//
//    public void setQuestionId(String questionId) {
//        this.questionId = questionId;
//    }
//
//    public String getTopicString() {
//        return topicString;
//    }
//
//    public void setTopicString(String topicString) {
//        this.topicString = topicString;
//    }
//
//    public long getUploadedTime() {
//        return uploadedTime;
//    }
//
//    public void setUploadedTime(long uploadedTime) {
//        this.uploadedTime = uploadedTime;
//    }
//
//    public String getPostQuestion() {
//        return postQuestion;
//    }
//
//    public void setPostQuestion(String postQuestion) {
//        this.postQuestion = postQuestion;
//    }
//
//    public String getPostDesc() {
//        return postDesc;
//    }
//
//    public void setPostDesc(String postDesc) {
//        this.postDesc = postDesc;
//    }
//
//    @Override
//    public String toString() {
//        return "Post{" +
//                "questionId='" + questionId + '\'' +
//                ", topicString='" + topicString + '\'' +
//                ", postQuestion='" + postQuestion + '\'' +
//                ", postDesc='" + postDesc + '\'' +
//                ", uploadedTime=" + uploadedTime +
//                '}';
//    }
}
