package org.demo.transferit.utils;

import java.util.Currency;

public class CurrencyChecker {
    public static boolean isCurrency(String currency) {
        try {
            Currency.getInstance(currency);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
