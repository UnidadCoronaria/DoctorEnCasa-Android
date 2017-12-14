package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class GetAffiliateUseCase extends SingleItemUseCase {

    private final AffiliateRepository mAffiliateRepository;

    @Inject
    public GetAffiliateUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    @Override
    public Single<Affiliate> buildUseCaseObservable() {
        return this.mAffiliateRepository.getUser();
    }

}
