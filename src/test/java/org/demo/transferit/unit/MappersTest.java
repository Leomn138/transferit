package org.demo.transferit.unit;

import io.quarkus.test.junit.QuarkusTest;
import org.demo.transferit.utils.CurrencyChecker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class MappersTest {

    @Test
    public void isCurrency_returnsTrue_whenCurrencyIsValid() {
        List<String> validCurrencies = Arrays.asList(
                "JPY", "CNY", "SDG", "RON", "MKD", "MXN", "CAD", "USD",
                "ZAR", "AUD", "NOK", "ILS", "ISK", "SYP", "LYD", "UYU", "YER", "CSD",
                "EEK", "THB", "IDR", "LBP", "AED", "BOB", "QAR", "BHD", "HNL", "HRK",
                "COP", "ALL", "DKK", "MYR", "SEK", "RSD", "BGN", "DOP", "KRW", "LVL",
                "VEF", "CZK", "TND", "KWD", "VND", "JOD", "NZD", "PAB", "CLP", "PEN",
                "GBP", "DZD", "CHF", "RUB", "UAH", "ARS", "SAR", "EGP", "INR", "PYG",
                "TWD", "TRY", "BAM", "OMR", "SGD", "MAD", "BYR", "NIO", "HKD", "LTL",
                "SKK", "GTQ", "BRL", "EUR", "HUF", "IQD", "CRC", "PHP", "SVC", "PLN"
        );

        validCurrencies.forEach(currency -> assertTrue(CurrencyChecker.isCurrency(currency)));
    }

    @Test
    public void isCurrency_returnsFalse_whenCurrencyIsNotValid() {
        List<String> invalidCurrencies = Arrays.asList(null, "", "    ", "1234", "DOESNOTEXIST");

        invalidCurrencies.forEach(currency -> assertFalse(CurrencyChecker.isCurrency(currency)));
    }
}
