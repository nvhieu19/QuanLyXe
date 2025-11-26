package com.fucar.util;

import java.util.regex.Pattern;

public class ValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern MOBILE_PATTERN = 
        Pattern.compile("^0\\d{9,10}$");
    
    private static final Pattern IDENTITY_CARD_PATTERN = 
        Pattern.compile("^\\d{9,12}$");
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }
    
    public static boolean isValidIdentityCard(String identityCard) {
        return identityCard != null && IDENTITY_CARD_PATTERN.matcher(identityCard).matches();
    }
    
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static boolean isPositiveNumber(Double number) {
        return number != null && number > 0;
    }
    
    public static boolean isPositiveInteger(Integer number) {
        return number != null && number > 0;
    }
}