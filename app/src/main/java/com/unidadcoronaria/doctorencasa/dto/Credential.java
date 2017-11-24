package com.unidadcoronaria.doctorencasa.dto;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */
public class Credential {

    private String userName;
    private String password;
    private String newPassword;
    private Integer affiliateGamId;
    private Integer providerId;
    private String email;

    private Credential(Credential.Builder builder) {
        this.userName = builder.username;
        this.password = builder.password;
        this.affiliateGamId = builder.affiliateId;
        this.providerId = builder.providerId;
        this.email = builder.email;
        this.newPassword = builder.newPassword;
    }

    public Integer getAffiliateGamId() {
        return affiliateGamId;
    }

    public void setAffiliateGamId(Integer affiliateGamId) {
        this.affiliateGamId = affiliateGamId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public static class Builder {

        private String username;
        private String password;
        private Integer affiliateId;
        private Integer providerId;
        private String email;
        private String newPassword;

        public Builder setUsername(String username){
            this.username = username;
            return this;
        }

        public Builder setPassword(String password){
            this.password = password;
            return this;
        }

        public Builder setAffiliateId(Integer affiliateId){
            this.affiliateId = affiliateId;
            return this;
        }

        public Builder setProviderId(Integer providerId){
            this.providerId = providerId;
            return this;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }

        public Builder setNewPassword(String newPassword){
            this.newPassword = newPassword;
            return this;
        }

        public Credential build(){
            return new Credential(this);
        }

    }
}
