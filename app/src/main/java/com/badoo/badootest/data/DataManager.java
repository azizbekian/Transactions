package com.badoo.badootest.data;

import android.support.v4.util.Pair;

import com.badoo.badootest.data.model.ConvertedTransaction;
import com.badoo.badootest.data.model.Rate;
import com.badoo.badootest.data.model.Transaction;
import com.badoo.badootest.data.model.TransactionWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

@Singleton
public class DataManager {

    private AssetsManager assetsManager;
    private RatesManager ratesManager;
    private CurrencyHelper currencyHelper;

    @Inject
    DataManager(AssetsManager assetsManager, RatesManager ratesManager, CurrencyHelper currencyHelper) {
        this.assetsManager = assetsManager;
        this.ratesManager = ratesManager;
        this.currencyHelper = currencyHelper;
    }

    Observable<List<Rate>> getRates() {
        return assetsManager.getRates()
                .toList()
                .toObservable()
                .doOnNext(rates -> ratesManager.setupRatesMap(rates));
    }

    Observable<Transaction> getTransactions() {
        return assetsManager.getTransactions();
    }

    public Observable<Pair<Float, List<ConvertedTransaction>>> getConvertedTransaction(String sku) {

        //noinspection unchecked
        return Observable.concat(
                getRates(),
                getTransactions()
                        .filter(transaction -> transaction.getSku().equals(sku))
                        .toList()
                        .toObservable()
                        .map(transactions -> {
                            List<ConvertedTransaction> convertedTransactions = new ArrayList<>(transactions.size());
                            float totalAmount = 0;
                            for (Transaction t : transactions) {
                                float conversionRate = ratesManager.convertToGbpFrom(t.getCurrency());
                                float convertedAmount = ratesManager.convertToRate(t.getAmount(), conversionRate);
                                totalAmount += convertedAmount;
                                convertedTransactions.add(ConvertedTransaction.newInstance(t,
                                        currencyHelper.getFormattedCurrency(convertedAmount)));
                            }
                            return Pair.create(currencyHelper.getFormattedCurrency(totalAmount), convertedTransactions);
                        }))
                .lastElement()
                .map(o -> (Pair<Float, List<ConvertedTransaction>>) o)
                .toObservable();
    }

    public Observable<List<TransactionWrapper>> getGroupedTransactions() {

        return getTransactions()
                .groupBy(Transaction::getSku)
                .flatMap(new Function<GroupedObservable<String, Transaction>, Observable<TransactionWrapper>>() {
                    @Override
                    public Observable<TransactionWrapper> apply(GroupedObservable<String, Transaction> grouped) throws Exception {
                        return grouped.count().toObservable().map(count -> TransactionWrapper.newInstance(grouped.getKey(), count));
                    }
                }).toList().toObservable();

    }

    public String getCurrencySymbol(String currencyCode) {
        return currencyHelper.getCurrencySymbol(currencyCode);
    }

    public String getGbpCurrencySymbol() {
        return getCurrencySymbol(RatesManager.CURRENCY_GBP);
    }

}
