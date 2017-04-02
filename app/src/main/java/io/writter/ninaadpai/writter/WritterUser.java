package io.writter.ninaadpai.writter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ninaadpai on 3/22/17.
 */

public class WritterUser {
    String userName, tagLine, location, birthday;
    Object memberSince;
    List<String> preferences, placesLived;
    HashMap<String, String> work, institutions;

    public WritterUser(String userName, String tagLine, String location, Object memberSince, String birthday, List<String> preferences, HashMap<String, String> work, HashMap<String, String> institutions, List<String> placesLived) {
        this.userName = userName;
        this.tagLine = tagLine;
        this.location = location;
        this.memberSince = memberSince;
        this.birthday = birthday;
        this.preferences = preferences;
        this.work = work;
        this.institutions = institutions;
        this.placesLived = placesLived;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Object getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(Object memberSince) {
        this.memberSince = memberSince;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public HashMap<String, String> getWork() {
        return work;
    }

    public void setWork(HashMap<String, String> work) {
        this.work = work;
    }

    public HashMap<String, String> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(HashMap<String, String> institutions) {
        this.institutions = institutions;
    }

    public List<String> getPlacesLived() {
        return placesLived;
    }

    public void setPlacesLived(List<String> placesLived) {
        this.placesLived = placesLived;
    }

    @Override
    public String  toString() {
        return "WritterUser{" +
                "userName='" + userName + '\'' +
                ", tagLine='" + tagLine + '\'' +
                ", location='" + location + '\'' +
                ", memberSince=" + memberSince +
                ", birthday='" + birthday + '\'' +
                ", preferences=" + preferences +
                ", work=" + work +
                ", institutions=" + institutions +
                ", placesLived=" + placesLived +
                '}';
    }
}
