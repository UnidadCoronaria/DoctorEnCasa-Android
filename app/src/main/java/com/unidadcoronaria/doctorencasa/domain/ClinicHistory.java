package com.unidadcoronaria.doctorencasa.domain;

import java.io.Serializable;

/**
 * Created by agustin on 7/11/17.
 */

public class ClinicHistory implements Serializable{

    private int id;
    private String comment;

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
}
