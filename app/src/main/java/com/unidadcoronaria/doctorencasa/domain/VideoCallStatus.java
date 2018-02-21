package com.unidadcoronaria.doctorencasa.domain;

import java.io.Serializable;

/**
 * Created by agustin on 17/11/17.
 */

public enum VideoCallStatus implements Serializable {

    FINALIZADA("FINALIZADA"),
    EN_COLA("EN_COLA"),
    EN_PROGRESO("EN_PROGRESO"),
    EXPIRADA("EXPIRADA"),
    CERRADA("CERRADA"),
    CANCELADA("CANCELADA"),
    LISTA_ATENCION("LISTA_ATENCION");

    private String value;

    VideoCallStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
