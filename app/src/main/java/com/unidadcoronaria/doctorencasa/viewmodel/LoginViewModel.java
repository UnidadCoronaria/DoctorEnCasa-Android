package com.unidadcoronaria.doctorencasa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

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

public class LoginViewModel extends ViewModel implements LifecycleObserver {

    @Inject
    protected UserRepository mUserRepository;
    @Inject
    protected AsyncRunner mAsyncRunner;
    private LiveData<User> mUser;
    private Integer userId;

    public LoginViewModel(Integer userId) {
        this.userId = userId;
        DaggerLoginComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
        mUser = mUserRepository.getUser(userId);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        Log.d("LoginViewModel","onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {  Log.d("LoginViewModel", "onStart"); }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.d("LoginViewModel","onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.d("LoginViewModel","onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        Log.d("LoginViewModel","onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        Log.d("LoginViewModel","onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(){
        Log.d("LoginViewModel","OnAny");
    }

    public LiveData<User> getUser() {
        return mUser;
    }

    public void createUser(User user){
        mAsyncRunner.executeAsynchronous(() -> mUserRepository.insert(user));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final int userId;

        public Factory(Integer userId) {
            this.userId = userId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LoginViewModel(userId);
        }
    }
}
