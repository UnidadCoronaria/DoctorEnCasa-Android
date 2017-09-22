package com.unidadcoronaria.doctorencasa.usecase.database;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Single;


public class LoadAffiliateUseCase extends SingleItemUseCase {

    private final AffiliateRepository mAffiliateRepository;

    @Inject
    public LoadAffiliateUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    @Override
    public Single<Affiliate> buildUseCaseObservable() {
        return this.mAffiliateRepository.fetchAffiliate();
    }
}