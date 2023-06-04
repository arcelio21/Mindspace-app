package com.mindspace.app.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encrypt {

    public static String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            // Asegurar que el hash resultante tenga 32 caracteres rellenando con ceros si es necesario
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }

            return md5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
