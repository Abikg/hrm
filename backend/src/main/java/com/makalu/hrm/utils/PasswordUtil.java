package com.makalu.hrm.utils;

import java.util.Random;

public class PasswordUtil {

    public String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random rand = new Random();
        char[] password = new char[length];
        for (int i = 0; i < length; i++) {
            password[i] = characters.charAt(rand.nextInt(characters.length()));
        }
        return new String(password);
    }
}
