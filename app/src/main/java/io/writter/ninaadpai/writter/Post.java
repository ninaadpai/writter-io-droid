package io.writter.ninaadpai.writter;

/**
 * Created by ninaadpai on 3/24/17.
 */

public class Post {
    String imgUrl, uploadedBy, topicString, uploadedTime, postQuestion, postDesc;

    public Post(String imgUrl, String uploadedBy, String topicString, String uploadedTime, String postQuestion, String postDesc) {
        this.imgUrl = imgUrl;
        this.uploadedBy = uploadedBy;
        this.topicString = topicString;
        this.uploadedTime = uploadedTime;
        this.postQuestion = postQuestion;
        this.postDesc = postDesc;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getTopicString() {
        return topicString;
    }

    public void setTopicString(String topicString) {
        this.topicString = topicString;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public String getPostQuestion() {
        return postQuestion;
    }

    public void setPostQuestion(String postQuestion) {
        this.postQuestion = postQuestion;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    @Override
    public String toString() {
        return "Post{" +
                "imgUrl='" + imgUrl + '\'' +
                "uploadedBy='" + uploadedBy + '\'' +
                ", topicString='" + topicString + '\'' +
                ", uploadedTime='" + uploadedTime + '\'' +
                ", postQuestion='" + postQuestion + '\'' +
                ", postDesc='" + postDesc + '\'' +
                '}';
    }
}
