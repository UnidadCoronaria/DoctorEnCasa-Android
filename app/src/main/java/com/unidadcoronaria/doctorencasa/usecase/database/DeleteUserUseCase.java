package com.unidadcoronaria.doctorencasa.usecase.database;

import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.usecase.VoidUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteUserUseCase extends VoidUseCase {

    private final AffiliateRepository mAffiliateRepository;

    @Inject
    public DeleteUserUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    @Override
    public Completable buildUseCaseObservable() {
        return this.mAffiliateRepository.delete();
    }
}