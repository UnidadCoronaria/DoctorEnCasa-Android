package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class UpdateUserUseCase extends SingleItemUseCase {

    private final UserRepository mUserRepository;
    private Credential mCredential;


    @Inject
    public UpdateUserUseCase(UserRepository mUserRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    public Single<UserInfo> buildUseCaseObservable() {
        return this.mUserRepository.updateUser(this.mCredential);
    }

    public void setData(Credential credential){
        this.mCredential = credential;
    }


}
