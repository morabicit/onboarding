package com.example.demo.utils;


import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

public class CardUtils {

    public static String maskCardNumber(String fullCardNumber) {
        // Example: Input "4111111111111111" -> Output "**** **** **** 1111"
        if (fullCardNumber == null || fullCardNumber.length() < 4) {
            throw new IllegalArgumentException("Invalid card number");
        }
        String lastFourDigits = fullCardNumber.substring(fullCardNumber.length() - 4);
        return "**** **** **** " + lastFourDigits;
    }

    public static boolean validateCardNumber(String fullCardNumber) {
        // Validate using the Luhn algorithm
        int sum = 0;
        boolean alternate = false;

        for (int i = fullCardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(fullCardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
    public static boolean isValidExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy")
                    .withResolverStyle(ResolverStyle.SMART);

            YearMonth cardExpiry = YearMonth.parse(expiryDate, formatter);
            YearMonth now = YearMonth.now();

            return cardExpiry.isAfter(now);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}

