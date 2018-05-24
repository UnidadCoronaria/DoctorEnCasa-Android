package com.unidadcoronaria.doctorencasa.domain;

import java.io.Serializable;

/**
 * Created by agustin on 6/2/18.
 */

public class Reason implements Serializable {

    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
