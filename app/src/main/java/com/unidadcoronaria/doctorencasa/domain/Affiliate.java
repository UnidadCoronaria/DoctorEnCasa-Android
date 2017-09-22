package com.unidadcoronaria.doctorencasa.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

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
    private String firstname;
    private String lastname;
    private String email;
    private String cellphone;
    private Long birthDate;

    public Affiliate() {
    }

    private Affiliate(Builder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.cellphone = builder.cellphone;
        this.birthDate = builder.birthDate;
        this.affiliateId = builder.affiliateId;
        this.providerId = builder.providerId;
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public static class Builder {

        private Integer affiliateId;
        private Integer providerId;
        private String firstname;
        private String lastname;
        private String email;
        private String cellphone;
        private Long birthDate;

        public Builder(String firstname, String lastname, String email,
                       String cellphone, Long birthDate,
                       Integer affiliateId, Integer providerId) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.cellphone = cellphone;
            this.birthDate = birthDate;
            this.affiliateId = affiliateId;
            this.providerId = providerId;
        }

        public Affiliate build(){
            return new Affiliate(this);
        }

    }
}
