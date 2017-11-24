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
        return password.length() >= 6;
    }

    public static  boolean validEmailFormat(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
