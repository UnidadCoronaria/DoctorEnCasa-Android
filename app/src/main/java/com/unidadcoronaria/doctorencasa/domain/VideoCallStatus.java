package com.unidadcoronaria.doctorencasa.domain;

/**
 * Created by agustin on 17/11/17.
 */

public enum VideoCallStatus {

    FINALIZADA("FINALIZADA"),
    EN_COLA("EN_COLA"),
    EN_PROGRESO("EN_PROGRESO"),
    EXPIRADA("EXPIRADA"),
    LISTA_ATENCION("LISTA_ATENCION");

    private String value;

    VideoCallStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
