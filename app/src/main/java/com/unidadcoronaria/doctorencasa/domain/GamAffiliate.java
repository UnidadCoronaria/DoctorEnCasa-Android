package com.unidadcoronaria.doctorencasa.domain;

/**
 * Created by agustin on 10/2/18.
 */

public class GamAffiliate {

    private String affiliateGamId;
    private String lastName;
    private String firstName;

    public GamAffiliate(String affiliateGamId, String lastName, String firstName) {
        this.affiliateGamId = affiliateGamId;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getAffiliateGamId() {
        return affiliateGamId;
    }

    public void setAffiliateGamId(String affiliateGamId) {
        this.affiliateGamId = affiliateGamId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamAffiliate that = (GamAffiliate) o;

        return affiliateGamId == that.affiliateGamId;
    }

    @Override
    public int hashCode() {
        return affiliateGamId != null ? affiliateGamId.hashCode() : 0;
    }
}
