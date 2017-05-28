package com.unidadcoronaria.doctorencasa.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.BaseView;
import com.unidadcoronaria.doctorencasa.di.component.DaggerLoginComponent;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.repository.ProviderRepository;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.threading.AsyncRunner;
import com.unidadcoronaria.doctorencasa.threading.DefaultObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class LoginViewModel extends ViewModel {

    private LiveData<List<Provider>> mProviderList;
    @Inject
    protected ProviderRepository mProviderRepository;
    @Inject
    protected UserRepository mUserRepository;
    @Inject
    protected AsyncRunner mAsyncRunner;


    public void init(){
        DaggerLoginComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
        mProviderList = mProviderRepository.getProviderList();
    }

    public LiveData<List<Provider>> getProviderList() {
        return mProviderList;
    }

    public void createUser(User user){
        mAsyncRunner.executeAsynchronous(() -> mUserRepository.insert(user));
    }
}
