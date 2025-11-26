package com.fucar.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(FORMATTER);
    }
    
    public static LocalDate parse(String dateString) throws DateTimeParseException {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateString, FORMATTER);
    }
    
    public static boolean isValidDate(String dateString) {
        try {
            parse(dateString);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static boolean isBeforeToday(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
    
    public static boolean isAfterToday(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }
}