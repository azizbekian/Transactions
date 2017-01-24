package com.badoo.badootest.ui.detail;

import android.support.v4.util.Pair;

import com.badoo.badootest.data.DataManager;
import com.badoo.badootest.data.model.ConvertedTransaction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailModel implements DetailContract.Model {

    private DataManager dataManager;

    public DetailModel(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Observable<Pair<Float, List<ConvertedTransaction>>> getConvertedRates(String sku) {
        return dataManager.getConvertedTransaction(sku)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public String getGbpSymbol() {
        return dataManager.getGbpCurrencySymbol();
    }

}
