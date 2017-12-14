package com.unidadcoronaria.doctorencasa.util;

import com.unidadcoronaria.doctorencasa.App;

/**
 * Created by AGUSTIN.BALA on 6/2/2017.
 */

public class SessionUtil {

    private final static String KEY_TOKEN = "TOKEN";
    private final static String KEY_USERNAME = "USERNAME";
    private final static String KEY_FCM_TOKEN = "FCM_TOKEN";
    private final static String KEY_SAVED_FCM_TOKEN = "SAVED_FCM_TOKEN";
    private final static String DEFAULT_VALUE = "";

    public static void logout(){
        saveToken(DEFAULT_VALUE);
        saveUsername(DEFAULT_VALUE);
        SessionUtil.saveSavedFCMToken(false);
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

    public static void saveFCMToken(String fcmToken){
        SharedPreferencesHelper.putString(App.getInstance(), KEY_FCM_TOKEN, fcmToken);
    }

    public static String getFCMToken(){
        return SharedPreferencesHelper.getString(App.getInstance(), KEY_FCM_TOKEN);
    }

    public static void saveSavedFCMToken(Boolean status){
        SharedPreferencesHelper.putBoolean(App.getInstance(), KEY_SAVED_FCM_TOKEN, status);
    }

    public static Boolean getSavedFCMToken(){
        return SharedPreferencesHelper.getBoolean(App.getInstance(), KEY_SAVED_FCM_TOKEN);
    }
}
