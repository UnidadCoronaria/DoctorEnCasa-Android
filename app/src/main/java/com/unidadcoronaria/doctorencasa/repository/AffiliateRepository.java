package com.unidadcoronaria.doctorencasa.repository;


import com.unidadcoronaria.doctorencasa.dao.AffiliateDAO;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.Credential;
import com.unidadcoronaria.doctorencasa.domain.User;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.network.rest.AffiliateService;
import com.unidadcoronaria.doctorencasa.network.rest.UserService;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by AGUSTIN.BALA on 5/25/2017.
 */

public class AffiliateRepository {

    private final AffiliateDAO mAffiliateDAO;
    private final AffiliateService mAffiliateService;


    @Inject
    public AffiliateRepository(AffiliateDAO affiliateDAO, AffiliateService affiliateService) {
        this.mAffiliateDAO = affiliateDAO;
        this.mAffiliateService = affiliateService;
    }


    public Single<Affiliate> fetchAffiliate() {
     //  return Single.fromObservable(io.reactivex.Observable.just(mAffiliateDAO.load()));
         return Single.fromCallable(() -> {
            Affiliate mAffiliate = mAffiliateDAO.load();
            if(mAffiliate != null){
                return mAffiliate;
            }
            String a = "";
            return new Affiliate();
        });
   }

    public Single<Affiliate> insert(Affiliate affiliate) {
        return Single.fromCallable(() -> {
                mAffiliateDAO.save(affiliate);
                return affiliate;
            }
        );
    }

    public Single<List<Affiliate>> getAffiliateData(String affiliateNumber, int mProviderId) {
        return Single.fromCallable(() -> mAffiliateService.getAffiliateData(mProviderId, affiliateNumber).execute().body());
    }

    public Completable delete() {
        return Completable.fromAction(() ->
            mAffiliateDAO.delete(mAffiliateDAO.load()));
    }

    public Single<Affiliate> getUser() {
        return Single.fromCallable(() ->  mAffiliateService.getAffiliate().execute().body());
    }
}
