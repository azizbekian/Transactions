package com.badoo.badootest.data;


import com.badoo.badootest.data.model.Rate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RatesManagerTest {

    private RatesManager ratesManager = new RatesManager();

    private static final List<Rate> rateList1 = new ArrayList<Rate>() {{
        add(Rate.newInstance("USD", "GBP", 1.2f));
        add(Rate.newInstance("USD", "CAD", 0.95f));
        add(Rate.newInstance("GBP", "USD", 0.8f));
        add(Rate.newInstance("GBP", "AUD", 0.6f));
        add(Rate.newInstance("CAD", "USD", 1.1f));
        add(Rate.newInstance("AUD", "GBP", 1.8f));
        add(Rate.newInstance("AMD", "CAD", 2.1f));
        add(Rate.newInstance("CHF", "JPN", 1.5f));
        add(Rate.newInstance("JPN", "CHF", 0.85f));
    }};

    private static final List<Rate> rateList2 = new ArrayList<Rate>() {{
        add(Rate.newInstance("AUD", "USD", 0.5f));
        add(Rate.newInstance("USD", "EUR", 0.5f));
        add(Rate.newInstance("EUR", "GBP", 0.5f));
        add(Rate.newInstance("GBP", "EUR", 2.0f));
        add(Rate.newInstance("EUR", "USD", 2.0f));
        add(Rate.newInstance("USD", "AUD", 2.0f));
    }};

    @Test
    public void test_1() {
        ratesManager.setupRatesMap(rateList1);
        assertEquals(1.2f, ratesManager.convertToGbpFrom("USD"), 0.0f);
        assertEquals(1.1f * 1.2f, ratesManager.convertToGbpFrom("CAD"), 0.0f);
        assertEquals(2.1f * 1.1f * 1.2f, ratesManager.convertToGbpFrom("AMD"), 0.0f);
        // converting from GPB to GPB
        assertEquals(1.0f, ratesManager.convertToGbpFrom("GBP"), 0.0f);
        // converting from a currency that has cyclic dependency (CHF -> JPN, JPN -> CHF)
        assertEquals(0f, ratesManager.convertToGbpFrom("CHF"), 0.0f);
        // converting from an unknown currency
        assertEquals(0f, ratesManager.convertToGbpFrom("ZSF"), 0.0f);
    }

    @Test
    public void test_2() {
        ratesManager.setupRatesMap(rateList2);
        assertEquals(1.0f, ratesManager.convertToGbpFrom("GBP"), 0.0f);
        assertEquals(0.5f, ratesManager.convertToGbpFrom("EUR"), 0.0f);
        assertEquals(0.5f * 0.5f, ratesManager.convertToGbpFrom("USD"), 0.0f);
        assertEquals(0.5f * 0.5f * 0.5f, ratesManager.convertToGbpFrom("AUD"), 0.0f);
    }

}