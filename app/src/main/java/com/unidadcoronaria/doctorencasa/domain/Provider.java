package com.unidadcoronaria.doctorencasa.domain;


import java.io.Serializable;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */
public class Provider implements Serializable {

    private int id;
    private String name;
    private String zones;


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

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }
}
