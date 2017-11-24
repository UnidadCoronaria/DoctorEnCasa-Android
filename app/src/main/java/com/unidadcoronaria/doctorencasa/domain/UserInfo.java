package com.unidadcoronaria.doctorencasa.domain;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

public class UserInfo {

    private String token;
    private Affiliate user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Affiliate getUser() {
        return user;
    }

    public void setUser(Affiliate user) {
        this.user = user;
    }
}