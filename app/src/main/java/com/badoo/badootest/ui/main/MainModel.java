package com.badoo.badootest.ui.main;

import com.badoo.badootest.data.DataManager;
import com.badoo.badootest.data.model.TransactionWrapper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainModel implements MainContract.Model {

    private DataManager dataManager;

    public MainModel(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Observable<List<TransactionWrapper>> getTransactions() {
        return dataManager.getGroupedTransactions()
                .subscribeOn(Schedulers.io())
                .flatMap(transactionWrappers -> {
                    // simulate delay
                    // Thread.sleep(2000);

                    //noinspection Convert2MethodRef
                    return Observable.just(transactionWrappers);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
