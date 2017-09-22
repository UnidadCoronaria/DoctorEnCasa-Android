package com.unidadcoronaria.doctorencasa.usecase.network;

import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.repository.ProviderRepository;
import com.unidadcoronaria.doctorencasa.usecase.SingleItemUseCase;
import com.unidadcoronaria.doctorencasa.usecase.executor.PostExecutionThread;
import com.unidadcoronaria.doctorencasa.usecase.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public class GetProviderListUseCase extends SingleItemUseCase {

    private final ProviderRepository mProviderRepository;

    @Inject
    public GetProviderListUseCase(ProviderRepository mProviderRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mProviderRepository = mProviderRepository;
    }

    @Override
    public Single<List<Provider>> buildUseCaseObservable() {
        return this.mProviderRepository.getProviderList();
    }

}
