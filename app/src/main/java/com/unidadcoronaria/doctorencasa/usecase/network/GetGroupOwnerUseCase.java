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

public class GetGroupOwnerUseCase extends SingleItemUseCase {

    private final AffiliateRepository mAffiliateRepository;
    private String mGroupId;
    private int mProviderId;

    @Inject
    public GetGroupOwnerUseCase(AffiliateRepository mAffiliateRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mAffiliateRepository = mAffiliateRepository;
    }

    @Override
    public Single<Affiliate> buildUseCaseObservable() {
        return this.mAffiliateRepository.getGroupOwner(mProviderId, mGroupId);
    }

    public void setData(int providerId, String mGroupId) {
        this.mGroupId = mGroupId;
        this.mProviderId = providerId;
    }
}
