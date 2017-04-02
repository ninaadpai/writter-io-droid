package io.writter.ninaadpai.writter;

import java.util.List;

/**
 * Created by ninaadpai on 3/22/17.
 */

public class WritterUser {
    String userName, tagLine, location;
    List<String> preferences;

    public WritterUser(String userName, List<String> preferences, String tagLine, String location) {
        this.userName = userName;
        this.preferences = preferences;
        this.tagLine = tagLine;
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "WritterUser{" +
                "userName='" + userName + '\'' +
                ", preferences=" + preferences +
                ", tagLine='" + preferences + '\'' +
                ", location='" + preferences + '\'' +
                '}';
    }
}
