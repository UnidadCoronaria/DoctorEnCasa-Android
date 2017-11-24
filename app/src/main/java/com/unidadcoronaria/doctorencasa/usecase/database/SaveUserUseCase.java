package com.unidadcoronaria.doctorencasa.usecase.database;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.usecase.VoidUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Completable;

public class SaveUserUseCase extends VoidUseCase {

    private final AffiliateRepository mAffiliateRepository;
    private Affiliate affiliate;

    @Inject
    public SaveUserUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    public void setAffiliate(Affiliate affiliate){
        this.affiliate = affiliate;
    }

    @Override
    public Completable buildUseCaseObservable() {
        return this.mAffiliateRepository.insert(affiliate);
    }
}