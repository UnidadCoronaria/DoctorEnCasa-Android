package com.unidadcoronaria.doctorencasa.util;

import com.unidadcoronaria.doctorencasa.domain.Reason;

import java.util.List;

public class StringUtil {

    public static String splitReasonList(List<Reason> reasons) {
        String result = "";

        for (int i=0; i < reasons.size(); i++) {
            result += reasons.get(i).getName();
            if(i < reasons.size() - 1){
                result += ", ";
            }
        }
        return result;
    }


}
