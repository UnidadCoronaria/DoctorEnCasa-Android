package com.unidadcoronaria.doctorencasa.threading;

import android.util.Log;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AGUSTIN.BALA on 5/26/2017.
 */


public class AsyncRunner {

    private final Executor mExecutor;

    @Inject
    public AsyncRunner(Executor executor){
        this.mExecutor = executor;
    }

    public <T> void executeAsynchronous(Runnable mRunnable){
        mExecutor.execute(mRunnable);
    }
}
