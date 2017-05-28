package com.unidadcoronaria.doctorencasa.repository;

import android.arch.lifecycle.LiveData;

import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.network.rest.ProviderService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by AGUSTIN.BALA on 5/25/2017.
 */

public class UserRepository {

    private final UserDAO mUserDAO;

    @Inject
    public UserRepository(UserDAO mUserDAO) {
        this.mUserDAO = mUserDAO;
    }


    public LiveData<User> getUser(int id) {
        return mUserDAO.load(id);
    }

    public void insert(User user) {
        mUserDAO.save(user);
    }
}
