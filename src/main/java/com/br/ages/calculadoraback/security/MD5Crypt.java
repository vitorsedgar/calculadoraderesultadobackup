package com.br.ages.calculadoraback.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Crypt implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return charSequenceToMD5(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return charSequenceToMD5(rawPassword.toString()).equals(encodedPassword);
    }

    private String charSequenceToMD5(CharSequence charSequence) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(charSequence.toString().getBytes(), 0, charSequence.length());
        String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
        if (hashedPass.length() < 32) {
            hashedPass = "0" + hashedPass;
        }
        return hashedPass;
    }
}
