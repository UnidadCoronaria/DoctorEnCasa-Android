package com.unidadcoronaria.doctorencasa.util;

import com.unidadcoronaria.doctorencasa.App;

/**
 * Created by AGUSTIN.BALA on 6/2/2017.
 */

public class SessionUtil {

    private final static String KEY_TOKEN = "TOKEN";
    private final static String KEY_USERNAME = "USERNAME";
    private final static String KEY_PROVIDER = "PROVIDER";
    private final static String KEY_FCM_TOKEN = "FCM_TOKEN";
    private final static String KEY_SAVED_FCM_TOKEN = "SAVED_FCM_TOKEN";
    private final static String KEY_IS_TOKEN_EXPIRED = "KEY_IS_TOKEN_EXPIRED";
    private final static String KEY_ROOM_NAME = "KEY_ROOM_NAME";
    private final static String KEY_TOKEN_TWILIO = "KEY_TOKEN_TWILIO";
    private final static String DEFAULT_VALUE = "";

    public static void logout(){
        saveProvider(0);
        saveToken(DEFAULT_VALUE);
        saveUsername(DEFAULT_VALUE);
        saveTwilioToken(DEFAULT_VALUE);
        saveRoomName(DEFAULT_VALUE);
        SessionUtil.saveSavedFCMToken(false);
    }

    public static boolean isAuthenticated(){
        return getToken() != null && !getToken().isEmpty() && !getIsTokenExpired();
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

    public static void saveProvider(Integer providerId){
        SharedPreferencesHelper.putInteger(App.getInstance(), KEY_PROVIDER, providerId);
    }

    public static Integer getProvider(){
        return SharedPreferencesHelper.getInteger(App.getInstance(), KEY_PROVIDER);
    }

    public static void saveSavedFCMToken(Boolean status){
        SharedPreferencesHelper.putBoolean(App.getInstance(), KEY_SAVED_FCM_TOKEN, status);
    }

    public static Boolean getSavedFCMToken(){
        return SharedPreferencesHelper.getBoolean(App.getInstance(), KEY_SAVED_FCM_TOKEN);
    }

    public static void saveIsTokenExpired(Boolean status){
        SharedPreferencesHelper.putBoolean(App.getInstance(), KEY_IS_TOKEN_EXPIRED, status);
    }

    public static Boolean getIsTokenExpired(){
        return SharedPreferencesHelper.getBoolean(App.getInstance(), KEY_IS_TOKEN_EXPIRED);
    }

    //region Twilio
    public static void saveRoomName(String roomName){
        SharedPreferencesHelper.putString(App.getInstance(), KEY_ROOM_NAME, roomName);
    }

    public static String getRoomName(){
        return SharedPreferencesHelper.getString(App.getInstance(), KEY_ROOM_NAME);
    }
    public static void saveTwilioToken(String twilioToken){
        SharedPreferencesHelper.putString(App.getInstance(), KEY_TOKEN_TWILIO, twilioToken);
    }

    public static String getTwilioToken(){
        return SharedPreferencesHelper.getString(App.getInstance(), KEY_TOKEN_TWILIO);
    }

    public static Boolean isCallPending(){
        return !getRoomName().isEmpty() && !getTwilioToken().isEmpty();
    }


    public static void finishCall(){
        saveTwilioToken(DEFAULT_VALUE);
        saveRoomName(DEFAULT_VALUE);
    }
    //endregion
}
