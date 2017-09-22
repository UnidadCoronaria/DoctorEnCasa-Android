package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Single;


public class LoginUseCase extends SingleItemUseCase {

    private final UserRepository mUserRepository;
    private Credential credential;

    @Inject
    public LoginUseCase(UserRepository mUserRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    public Single<UserInfo> buildUseCaseObservable() {
        return this.mUserRepository.login(this.credential);
    }

    public void setData(Credential credential){
        this.credential = credential;
    }
}