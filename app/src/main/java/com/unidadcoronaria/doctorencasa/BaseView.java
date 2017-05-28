package com.unidadcoronaria.doctorencasa;

import android.app.Activity;

/**
 * @author Agustin.Bala
 * @since 0.0.1
 */
public interface BaseView {

    void displayError(String message);

    void showLoading();

    void hideLoading();

    Activity getActivity();
}
