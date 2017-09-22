package com.unidadcoronaria.doctorencasa.util;

import com.unidadcoronaria.doctorencasa.App;

/**
 * Created by AGUSTIN.BALA on 6/2/2017.
 */

public class SessionUtil {

    private final static String KEY_TOKEN = "TOKEN";
    private final static String KEY_USERNAME = "USERNAME";

    public static void logout(){
        saveToken("");
        saveUsername("");
    }

    public static boolean isAuthenticated(){
        return getToken() != null && !getToken().isEmpty();
    }

    public static void saveToken(String token){
        SharedPreferencesHelper.putString(App.getInstance(), KEY_TOKEN, token);
    }

    public static String getToken(){
        return SharedPreferencesHelper.getString(App.getInstance(), KEY_TOKEN);
    }

    public static void saveUsername(String username){
        SharedPreferencesHelper.putString(App.getInstance(), KEY_USERNAME, username);
    }

    public static String getUsername(){
        return SharedPreferencesHelper.getString(App.getInstance(), KEY_USERNAME);
    }
}
