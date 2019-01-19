package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.AffiliateCallHistory;
import com.unidadcoronaria.doctorencasa.domain.Queue;
import com.unidadcoronaria.doctorencasa.domain.VideoCall;
import com.unidadcoronaria.doctorencasa.dto.GenericResponseDTO;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface VideoCallView extends BaseView {

    void onGetAffiliateCallHistorySuccess(AffiliateCallHistory affiliateCallHistory);

    void onGetAffiliateCallHistoryError();

    void onGetQueueStatusSuccess(Queue queue);

    void onGetQueueStatusError();

    void onGetDataStart();

    void onInitCallSuccess(VideoCall videoCall);

    void onInitCallError(GenericResponseDTO response);

    void logout();

    void onCancelSuccess();

    void onCancelError();
}
