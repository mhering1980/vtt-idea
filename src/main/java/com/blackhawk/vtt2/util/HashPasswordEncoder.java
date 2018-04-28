package com.blackhawk.vtt2.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordEncoder implements PasswordEncoder {


    @Override
    public String encode(CharSequence rawPassword) {
        String password = rawPassword.toString();
        return encryptRawPassword(password);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String password = rawPassword.toString();
        return encryptRawPassword(password).equals(encodedPassword);
    }


    private String encryptRawPassword(String rawPassword) {
        String hashedPassword = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(rawPassword.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(hashedPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashedPassword;
    }
}
