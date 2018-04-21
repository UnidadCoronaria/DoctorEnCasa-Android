package com.unidadcoronaria.doctorencasa.dto;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */
public class Credential {

    private String username;
    private String password;
    private String newPassword;
    private Integer groupNumber;
    private Integer providerId;
    private String email;

    private Credential(Credential.Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.groupNumber = builder.groupNumberId;
        this.providerId = builder.providerId;
        this.email = builder.email;
        this.newPassword = builder.newPassword;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        private Integer groupNumberId;
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

        public Builder setGroupNumberId(Integer groupNumberId){
            this.groupNumberId = groupNumberId;
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
