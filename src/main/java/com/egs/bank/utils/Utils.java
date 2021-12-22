package com.egs.bank.utils;

import java.security.SecureRandom;

public class Utils {

    public static final String randomNumberAlphabet= "0123456789";
    public static final String randomStringAlphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String randomNumber(int len) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(randomNumberAlphabet.charAt(rnd.nextInt(randomNumberAlphabet.length())));
        }
        return sb.toString();
    }

    public static String randomString(int len) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(randomStringAlphabet.charAt(rnd.nextInt(randomStringAlphabet.length())));
        }
        return sb.toString();
    }
}
