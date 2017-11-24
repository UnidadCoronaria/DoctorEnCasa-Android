package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.usecase.VoidUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class UpdateFCMTokenUseCase extends VoidUseCase {

    private final AffiliateRepository mAffiliateRepository;
    private String fcmToken;

    @Inject
    public UpdateFCMTokenUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    @Override
    public Completable buildUseCaseObservable() {
        return this.mAffiliateRepository.updateFCMToken(this.fcmToken);
    }

    public void setData(String fcmToken){
        this.fcmToken = fcmToken;
    }

}