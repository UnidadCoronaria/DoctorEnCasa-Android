package com.unidadcoronaria.doctorencasa.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by agustin on 7/11/17.
 */

public class ClinicHistory implements Serializable{

    private int id;
    private String affiliateGamId;
    private String comment;
    private String lastName;
    private String firstName;
    private VideoCall videocall;
    private List<Diagnostic> diagnostics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public VideoCall getVideocall() {
        return videocall;
    }

    public void setVideocall(VideoCall videocall) {
        this.videocall = videocall;
    }

    public List<Diagnostic> getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(List<Diagnostic> diagnostics) {
        this.diagnostics = diagnostics;
    }


}
