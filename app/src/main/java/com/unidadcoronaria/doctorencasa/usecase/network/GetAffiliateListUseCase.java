package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.repository.AffiliateRepository;
import com.unidadcoronaria.doctorencasa.repository.UserRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class GetAffiliateListUseCase extends SingleItemUseCase {

    private final AffiliateRepository mAffiliateRepository;
    private String mAffiliateNumber;
    private String mProviderId;

    @Inject
    public GetAffiliateListUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    @Override
    public Single<List<Affiliate>> buildUseCaseObservable() {
        return this.mAffiliateRepository.getAffiliateData(this.mAffiliateNumber, this.mProviderId);
    }

    public void setData(String affiliateNumber, String mProviderId){
        this.mAffiliateNumber = affiliateNumber;
        this.mProviderId = mProviderId;
    }


}
