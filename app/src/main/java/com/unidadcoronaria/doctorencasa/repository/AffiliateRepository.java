package com.unidadcoronaria.doctorencasa.repository;


import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;
import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.dto.Credential;
import com.unidadcoronaria.doctorencasa.domain.UserInfo;
import com.unidadcoronaria.doctorencasa.network.rest.AffiliateService;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by AGUSTIN.BALA on 5/25/2017.
 */

public class AffiliateRepository {

    private final AffiliateService mAffiliateService;
    private UserDAO mUserDAO;


    @Inject
    public AffiliateRepository(AffiliateService mAffiliateService) {
        this.mAffiliateService = mAffiliateService;
    }


    public Single<UserInfo> login(Credential credential) {
        return mAffiliateService.login(credential);
    }

    public Completable logout() {
        return mAffiliateService.logout();
    }

    public Single<UserInfo> createUser(Credential credential) {
        return mAffiliateService.createUser(credential);
    }

    public Single<String> forgotPassword(Credential credential) {
        return mAffiliateService.forgotPassword(credential);
    }

    public Single<UserInfo> updateUser(Credential credential) {
        return mAffiliateService.updatePassword(credential);
    }

    public Single<Affiliate> fetchAffiliate() {
        return Single.fromCallable(() -> {
            Affiliate mAffiliate = mUserDAO.load();
            return mAffiliate;
        });
    }

    public Completable insert(Affiliate affiliate) {
        return Completable.fromAction(() -> mUserDAO.save(affiliate));
    }

    public Single<List<Affiliate>> getAffiliateData(String affiliateNumber, int mProviderId) {
        return mAffiliateService.getAffiliateData(mProviderId, affiliateNumber);
    }

    public Completable delete() {
        return Completable.fromAction(() ->
                mUserDAO.delete(mUserDAO.load()));
    }

    public Single<Affiliate> getUser() {
        return mAffiliateService.getAffiliate();
    }


    public Single<AffiliateCallHistory> getAffiliateCallHistory() {
        return mAffiliateService.getAffiliateCallHistory();
    }

    public Completable updateFCMToken(String fcmToken) {
        return mAffiliateService.updateFCMToken(fcmToken);
    }

    public Single<Affiliate> getGroupOwner(int mProviderId, String mGroupId) {
        return mAffiliateService.getGroupOwner(mProviderId, mGroupId);
    }
}
