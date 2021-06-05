package com.lear.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public static void main(String[] args) {
        String password="123";
        System.out.println(BCryptPasswordEncoderUtils.encodePassword(password));
        System.out.println(BCryptPasswordEncoderUtils.encodePassword(password));
        String passwoord1="12";
        System.out.println(BCryptPasswordEncoderUtils.encodePassword(passwoord1));
    }
}
