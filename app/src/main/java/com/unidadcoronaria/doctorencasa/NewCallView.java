package com.unidadcoronaria.doctorencasa;

import com.sinch.android.rtc.calling.Call;
import com.unidadcoronaria.doctorencasa.service.SinchService;

/**
 * Created by AGUSTIN.BALA on 5/29/2017.
 */

public interface NewCallView extends BaseView {

    void initCall(Call call, SinchService.SinchServiceInterface serviceInterface);

    void onRankSuccess(int ranking);

    void onRankError();
;
}
