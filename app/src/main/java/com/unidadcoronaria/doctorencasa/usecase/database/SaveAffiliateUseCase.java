package com.unidadcoronaria.doctorencasa.usecase.database;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Single;

public class SaveAffiliateUseCase extends SingleItemUseCase {

    private final AffiliateRepository mAffiliateRepository;
    private Affiliate affiliate;

    @Inject
    public SaveAffiliateUseCase(AffiliateRepository affiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = affiliateRepository;
    }

    public void setAffiliate(Affiliate affiliate){
        this.affiliate = affiliate;
    }

    @Override
    public Single<Affiliate> buildUseCaseObservable() {
        return this.mAffiliateRepository.insert(affiliate);
    }
}