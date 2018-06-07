package com.unidadcoronaria.doctorencasa.presenter;

import android.support.annotation.CallSuper;
import android.util.Log;

import com.google.gson.Gson;
import com.unidadcoronaria.doctorencasa.BaseView;
import com.unidadcoronaria.doctorencasa.util.SessionUtil;

import java.util.concurrent.Callable;

import retrofit2.HttpException;

/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public class BasePresenter<V extends BaseView> {

    protected V view;

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }

    @CallSuper
    public void onStart() {
    }

    @CallSuper
    public void onResume() {
    }

    @CallSuper
    public void onPause() {

    }

    @CallSuper
    public void onStop() {
    }

    protected void checkTokenExpired(Throwable throwable, Callable func) {
        try {
            if (throwable instanceof HttpException) {
                // We had non-2XX http error
                HttpException e = (HttpException) throwable;
                if (e.code() == 408) {
                    SessionUtil.logout();
                    view.logout();
                    return;
                }
            }
            func.call();
        } catch (Exception e) {
            Log.e("VideoCallPresenter", e.getMessage());
        }
    }


}
