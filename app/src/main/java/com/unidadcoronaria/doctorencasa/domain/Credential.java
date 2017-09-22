package com.unidadcoronaria.doctorencasa.domain;

import java.util.Date;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */
public class Credential {

    private String username;
    private String password;
    private Integer affiliateId;
    private Integer providerId;

    private Credential(Credential.Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.affiliateId = builder.affiliateId;
        this.providerId = builder.providerId;
    }

    public Integer getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(Integer affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder {

        private String username;
        private String password;
        private Integer affiliateId;
        private Integer providerId;

        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder setAffiliateId(Integer affiliateId){
            this.affiliateId = affiliateId;
            return this;
        }

        public Builder setProviderId(Integer providerId){
            this.providerId = providerId;
            return this;
        }

        public Credential build(){
            return new Credential(this);
        }

    }
}
