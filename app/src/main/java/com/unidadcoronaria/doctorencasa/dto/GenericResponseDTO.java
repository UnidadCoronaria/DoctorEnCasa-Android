package com.unidadcoronaria.doctorencasa.dto;

import javax.annotation.Nullable;

/**
 * Created by agustin on 20/4/18.
 */

public class GenericResponseDTO {

    @Nullable
    private String message;
    @Nullable
    private String error;
    @Nullable
    private int code;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
