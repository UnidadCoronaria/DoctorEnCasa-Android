package com.unidadcoronaria.doctorencasa.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

public class User extends Affiliate implements Serializable {

    private String username;
    private Boolean enabled;
    private Boolean passwordExpired;
    private Date lastPasswordResetDate;


    private User(Builder builder) {
        this.username = builder.username;
        this.enabled = builder.enabled;
        this.lastPasswordResetDate = builder.lastPasswordResetDate;
        this.passwordExpired = builder.passwordExpired;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    public static class Builder {

        private String username;
        private Boolean enabled;
        private Boolean passwordExpired;
        private Date lastPasswordResetDate;

        public Builder(String username) {
            this.username = username;
        }

        public Builder setEnabled(Boolean enabled){
            this.enabled = enabled;
            return this;
        }

        public Builder setLastPasswordResetDate(Date lastPasswordResetDate){
            this.lastPasswordResetDate = lastPasswordResetDate;
            return this;
        }

        public Builder setPasswordExpiredDate(Boolean passwordExpired){
            this.passwordExpired = passwordExpired;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }
}