package com.unidadcoronaria.doctorencasa.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */
public class Affiliate implements Serializable {

    private Integer id;
    private Integer affiliateGamId;
    private Provider provider;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType;
    private String email;
    private String cellphone;
    private Long birthDate;
    private boolean isUser;
    private int groupNumberId;
    private String username;
    private Boolean enabled;
    private Boolean passwordExpired;
    private long lastPasswordResetDate;

    public Affiliate() {
        super();
    }

    private Affiliate(Builder builder) {
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

    public long getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public void setLastPasswordResetDate(long lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Integer getAffiliateGamId() {
        return affiliateGamId;
    }

    public void setAffiliateGamId(Integer affiliateGamId) {
        this.affiliateGamId = affiliateGamId;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public int getGroupNumberId() {
        return groupNumberId;
    }

    public void setGroupNumberId(int groupNumberId) {
        this.groupNumberId = groupNumberId;
    }

    public static class Builder {

        private String username;
        private Boolean enabled;
        private Boolean passwordExpired;
        private long lastPasswordResetDate;

        public Builder(String username) {
            this.username = username;
        }

        public Builder setEnabled(Boolean enabled){
            this.enabled = enabled;
            return this;
        }

        public Builder setLastPasswordResetDate(long lastPasswordResetDate){
            this.lastPasswordResetDate = lastPasswordResetDate;
            return this;
        }

        public Builder setPasswordExpiredDate(Boolean passwordExpired){
            this.passwordExpired = passwordExpired;
            return this;
        }

        public Affiliate build(){
            return new Affiliate(this);
        }

    }
}