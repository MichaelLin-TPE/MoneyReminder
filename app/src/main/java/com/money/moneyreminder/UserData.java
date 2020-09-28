package com.money.moneyreminder;

import java.io.Serializable;

public class UserData implements Serializable {

    private String userEmail;

    private String userPhoto;

    private String userName;

    private long userCreateTimeMiles;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserCreateTimeMiles() {
        return userCreateTimeMiles;
    }

    public void setUserCreateTimeMiles(long userCreateTimeMiles) {
        this.userCreateTimeMiles = userCreateTimeMiles;
    }
}
