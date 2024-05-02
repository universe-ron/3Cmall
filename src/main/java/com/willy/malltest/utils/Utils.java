package com.willy.malltest.utils;

import java.util.Random;

public class Utils {
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;
        return String.valueOf(code);
    }
}