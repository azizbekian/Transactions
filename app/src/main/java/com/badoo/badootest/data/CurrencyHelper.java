package com.badoo.badootest.data;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CurrencyHelper {

    private static final String TAG = CurrencyHelper.class.getSimpleName();

    private SortedMap<Currency, Locale> currencyLocaleMap;
    private Map<String, String> currencyCodeToSignMap;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Inject
    CurrencyHelper() {
        setup();
    }

    private void setup() {
        currencyCodeToSignMap = new HashMap<>();
        currencyLocaleMap = new TreeMap<>((c1, c2) -> {
            return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
        });
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                currencyLocaleMap.put(currency, locale);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    String getCurrencySymbol(String currencyCode) {
        // if we already have asked for that currencyCode previously
        if (currencyCodeToSignMap.containsKey(currencyCode)) {
            return currencyCodeToSignMap.get(currencyCode);
        }
        Currency currency = Currency.getInstance(currencyCode);
        String sign = currency.getSymbol(currencyLocaleMap.get(currency));
        currencyCodeToSignMap.put(currencyCode, sign);
        return sign;
    }

    Float getFormattedCurrency(Float f) {
        return Float.valueOf(decimalFormat.format(f));
    }

}
