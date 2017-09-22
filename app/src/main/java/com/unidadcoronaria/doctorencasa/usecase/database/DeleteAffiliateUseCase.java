package com.unidadcoronaria.doctorencasa.usecase.database;

import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.usecase.VoidUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteAffiliateUseCase extends VoidUseCase {

    private final AffiliateRepository mAffiliateRepository;

    @Inject
    public DeleteAffiliateUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    @Override
    public Completable buildUseCaseObservable() {
        return this.mAffiliateRepository.delete();
    }
}