package io.greitan.mineserv.utils;

import java.security.SecureRandom;

public class RandomPassword {

    private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

    public static String generatePassword() {
        int length = 16;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(SYMBOLS.length());
            password.append(SYMBOLS.charAt(randomIndex));
        }

        return password.toString();
    }
}
