package com.badoo.badootest.data;

import com.badoo.badootest.data.model.Rate;
import com.badoo.badootest.data.model.Transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    RatesManager ratesManager;

    @Mock
    AssetsManager assetsManager;

    @Mock
    CurrencyHelper currencyHelper;

    @InjectMocks
    DataManager dataManager;

    private List<Rate> rates = new ArrayList<Rate>() {{
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

    private List<Transaction> transactions = new ArrayList<Transaction>() {{
        add(new Transaction("A1", 1.1f, "USD"));
        add(new Transaction("B2", 2.2f, "USD"));
        add(new Transaction("C3", 3.3f, "USD"));
        add(new Transaction("D4", 4.4f, "USD"));
        add(new Transaction("E5", 5.5f, "USD"));
        add(new Transaction("F6", 6.6f, "USD"));
    }};

    @Test
    @SuppressWarnings("unchecked")
    public void getRates() {

        when(assetsManager.getRates()).thenReturn(Observable.fromIterable(rates));

        TestObserver<List<Rate>> testObserver = new TestObserver<>();
        dataManager.getRates().subscribe(testObserver);

        testObserver.assertResult(rates);
        testObserver.assertNoErrors();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getRates_empty() {

        when(assetsManager.getRates()).thenReturn(Observable.fromIterable(Collections.emptyList()));

        TestObserver<List<Rate>> testObserver = new TestObserver<>();
        dataManager.getRates().subscribe(testObserver);

        testObserver.assertResult(Collections.emptyList());
        testObserver.assertNoErrors();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransactions() {
        when(assetsManager.getTransactions()).thenReturn(Observable.fromIterable(transactions));

        TestObserver<List<Transaction>> testObserver = new TestObserver<>();
        dataManager.getTransactions().toList().subscribe(testObserver);

        testObserver.assertResult(transactions);
        testObserver.assertNoErrors();
    }

}