package com.unidadcoronaria.doctorencasa.domain;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

public class UserInfo {

    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}