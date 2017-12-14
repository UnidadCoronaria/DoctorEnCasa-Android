package com.unidadcoronaria.doctorencasa;

import com.unidadcoronaria.doctorencasa.domain.ClinicHistory;

import java.util.List;

/**
 * Created by AGUSTIN.BALA on 6/4/2017.
 */

public interface ClinicHistoryView extends BaseView {

    void onClinicHistoryListRetrieved(List<ClinicHistory> clinicHistoryList);

    void onClinicHistoryListError();
}
