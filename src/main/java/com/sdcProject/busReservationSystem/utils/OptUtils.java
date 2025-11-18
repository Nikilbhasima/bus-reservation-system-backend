package com.sdcProject.busReservationSystem.utils;

import java.security.SecureRandom;

public class OptUtils {
    private static final SecureRandom random = new SecureRandom();



    public static String generateOtp(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10)); // digits 0-9
        }
        return otp.toString();
    }

}
