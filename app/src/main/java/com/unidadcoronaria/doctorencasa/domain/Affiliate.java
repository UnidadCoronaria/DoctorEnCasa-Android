package com.unidadcoronaria.doctorencasa.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

@Entity
public class Affiliate implements Serializable {

    @PrimaryKey
    private Integer id;
    private Integer affiliateId;
    private Integer providerId;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String documentType;
    private String email;
    private String cellphone;
    private Long birthDate;
    private boolean isUser;

    public Affiliate() {
    }

    private Affiliate(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.documentNumber = builder.documentNumber;
        this.documentType = builder.documentType;
        this.cellphone = builder.cellphone;
        this.birthDate = builder.birthDate;
        this.affiliateId = builder.affiliateId;
        this.providerId = builder.providerId;
        this.isUser = builder.isUser;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAffiliateId() {
        return affiliateId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public void setAffiliateId(Integer affiliateId) {
        this.affiliateId = affiliateId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public static class Builder {

        private Integer affiliateId;
        private Integer providerId;
        private String firstName;
        private String lastName;
        private String documentNumber;
        private String documentType;
        private String email;
        private String cellphone;
        private Long birthDate;
        private boolean isUser;

        public Builder(String firstName, String lastName, String email,
                       String documentNumber, String documentType,
                       String cellphone, Long birthDate,
                       Integer affiliateId, Integer providerId, boolean isUser) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.documentNumber = documentNumber;
            this.documentType = documentType;
            this.cellphone = cellphone;
            this.birthDate = birthDate;
            this.affiliateId = affiliateId;
            this.providerId = providerId;
            this.isUser = isUser;
        }

        public Affiliate build(){
            return new Affiliate(this);
        }

    }
}
