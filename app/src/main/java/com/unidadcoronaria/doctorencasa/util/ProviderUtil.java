package com.unidadcoronaria.doctorencasa.util;

import com.unidadcoronaria.doctorencasa.R;

public class ProviderUtil {

    public static String getTelephoneNumber(){
        String tel = "";
        switch (SessionUtil.getProvider()) {
            case 1:  tel += "1142577777"; break;
            case 2:  tel += "1142240202"; break;
            case 5:  tel += "08102227100"; break;
            case 4:  tel += "1148607000"; break;
            case 3:  tel += "1142243600"; break;
            default:
                tel += "1142577777"; break;
        }

        return tel;
    }

    public static int getIcon(int id){
        switch (id) {
            case 1: return R.drawable.logo_unidad_coronaria;
            case 2: return R.drawable.logo_emeca_salud;
            case 3: return R.drawable.logo_ayuda_medica;
            case 4: return R.drawable.logo_sume_salud;
            case 5: return R.drawable.logo_ayuda_medica;
            default:
                return  R.drawable.ic_company_logo;
        }
    }
}
