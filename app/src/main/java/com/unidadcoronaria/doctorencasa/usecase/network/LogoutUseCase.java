package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.usecase.VoidUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class LogoutUseCase extends VoidUseCase {

    private final UserRepository mUserRepository;

    @Inject
    public LogoutUseCase(UserRepository mUserRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    public Completable buildUseCaseObservable() {
        return this.mUserRepository.logout();
    }

}