package com.unidadcoronaria.doctorencasa.network;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public abstract class DefaultCallback<T> implements Callback<T> {

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        //Nothing to do
    }
}
