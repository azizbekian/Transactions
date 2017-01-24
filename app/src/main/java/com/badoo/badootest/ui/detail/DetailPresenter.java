package com.badoo.badootest.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.badoo.badootest.data.model.ConvertedTransaction;
import com.badoo.badootest.injection.ConfigPersistent;
import com.badoo.badootest.ui.base.BaseContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
public class DetailPresenter extends BaseContract.BasePresenter<DetailContract.View> {

    static final String KEY_SKU = "sku";

    private DetailContract.Model model;
    private ConvertedTransactionsAdapter adapter;
    private Float total;
    private String gbpSymbol;

    /**
     * Indicates, whether we already created a subscription.
     * <p>
     * Is used to correctly handle configuration
     * changes and do not re-start subscription when making orientation change whilst data is being loaded.
     */
    private boolean isRunning;

    @Inject
    DetailPresenter(DetailContract.Model model, ConvertedTransactionsAdapter adapter) {
        this.model = model;
        this.adapter = adapter;
    }

    @Override
    public void create(DetailContract.View view, @Nullable Bundle savedInstanceState, Intent intent) {
        super.create(view, savedInstanceState, intent);

        String sku = intent.getExtras().getString(KEY_SKU);
        getView().setupToolbar("Transactions for " + sku);
        if (!isRunning && adapter.isEmpty()) {
            Disposable d = model.getConvertedRates(sku)
                    .doOnSubscribe(disposable -> isRunning = true)
                    .doOnComplete(() -> isRunning = false)
                    .subscribe(this::onDataSuccess, this::onDataFailure);
            addDisposable(d);
        } else if (!adapter.isEmpty()) {
            showTotalAmount();
            getView().setupConvertedTransactionList(adapter);
        }
    }

    private void onDataSuccess(Pair<Float, List<ConvertedTransaction>> pair) {
        adapter.setConvertedTransactions(pair.second);
        if (isViewAttached()) {
            DetailContract.View view = getView();
            total = pair.first;
            showTotalAmount();
            view.setupConvertedTransactionList(adapter);
        }
    }

    private void onDataFailure(Throwable e) {
        e.printStackTrace();
        if (isViewAttached()) {
            getView().showErrorMessage(e.getMessage());
        }
    }

    private void showTotalAmount() {
        if (!isViewAttached()) return;

        if (null == gbpSymbol) {
            gbpSymbol = model.getGbpSymbol();
        }
        getView().setupTotalAmount(gbpSymbol + " " + total.toString());
    }
}
