package com.unidadcoronaria.doctorencasa.util;

import com.google.gson.Gson;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;

import java.io.IOException;

import retrofit2.adapter.rxjava2.HttpException;

public class ErrorUtil {

    protected static Gson gson = new Gson();


    public static GenericResponseDTO getError(Throwable throwable){
        try {
            return gson.fromJson(((HttpException) throwable).response().errorBody().string(), GenericResponseDTO.class);
        } catch (IOException e) {
            return null;
        }
    }
}
