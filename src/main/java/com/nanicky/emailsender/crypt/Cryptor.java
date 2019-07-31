package com.nanicky.emailsender.crypt;

public class Cryptor {
    private final static String KEY = "fvwidnoklmxniubviqnfpascuirwefinq0pwcjdfvsc9uobn3wdpaoibcxsdfasdfsyrgeviouhbfojasbdyasrfgouqsa";

    public static String crypt(String password) {
        StringBuilder sb = new StringBuilder(password.length());
        for (int i = 0; i < password.length(); i++) {
            char xored = (char) (password.charAt(i) ^ KEY.charAt(i));
            sb.append(xored);
        }
        return sb.toString();
    }
}
