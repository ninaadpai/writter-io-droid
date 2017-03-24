package io.writter.ninaadpai.writter;

import java.util.List;

/**
 * Created by ninaadpai on 3/22/17.
 */

public class WritterUser {
    String userName;
    List<String> preferences;

    public WritterUser(String userName, List<String> preferences) {
        this.userName = userName;
        this.preferences = preferences;
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

    @Override
    public String toString() {
        return "WritterUser{" +
                "userName='" + userName + '\'' +
                ", preferences=" + preferences +
                '}';
    }
}
