package com.unidadcoronaria.doctorencasa.util;

/**
 * @author AGUSTIN.BALA
 * @since 4.16
 */

public class ValidationUtil {

    public static boolean validUsernameFormat(String username) {
        return username.length() >= 4;
    }

    public static  boolean validPasswordFormat(String password) {
        return password.length() >= 8;
    }

    public static  boolean validEmailFormat(String username) {
        return true;
    }
}
