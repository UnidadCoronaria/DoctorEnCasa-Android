package com.unidadcoronaria.doctorencasa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.di.component.DaggerLoginComponent;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.repository.ProviderRepository;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.threading.AsyncRunner;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class CreateAccountViewModel extends ViewModel{

    private LiveData<List<Provider>> mProviderList;
    @Inject
    protected ProviderRepository mProviderRepository;
    @Inject
    protected UserRepository mUserRepository;
    @Inject
    protected AsyncRunner mAsyncRunner;


    public CreateAccountViewModel() {
        DaggerLoginComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
        mProviderList = mProviderRepository.getProviderList();
    }

    public LiveData<List<Provider>> getProviderList() {
        return mProviderList;
    }

}
