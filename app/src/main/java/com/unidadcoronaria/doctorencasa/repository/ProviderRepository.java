package com.unidadcoronaria.doctorencasa.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.unidadcoronaria.doctorencasa.dao.ProviderDAO;
import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.network.DefaultCallback;
import com.unidadcoronaria.doctorencasa.network.rest.ProviderService;
import com.unidadcoronaria.doctorencasa.threading.AsyncRunner;
import com.unidadcoronaria.doctorencasa.threading.DefaultObserver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class ProviderRepository {

    private final AsyncRunner mAsyncRunner;
    private ProviderService mProviderService;
    private ProviderDAO mProviderDAO;


    @Inject
    public ProviderRepository(ProviderService mProviderService, ProviderDAO providerDAO, AsyncRunner asyncRunner) {
        this.mProviderService = mProviderService;
        this.mProviderDAO = providerDAO;
        this.mAsyncRunner = asyncRunner;
    }

    public LiveData<List<Provider>> getProviderList() {
        //TODO Add validation to verify is data is updated in DB to avoid request
        mAsyncRunner.executeAsynchronous(() -> {
            try {
                mProviderDAO.save(mProviderService.get().execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return mProviderDAO.loadAll();
    }

}
