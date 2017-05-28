package com.unidadcoronaria.doctorencasa.presenter;

import android.support.annotation.CallSuper;

import com.unidadcoronaria.doctorencasa.BaseView;

/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public class BasePresenter<V extends BaseView> {

    protected final V view;

    public BasePresenter(V view) {
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


}
