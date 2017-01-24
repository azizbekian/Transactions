package com.badoo.badootest.ui.main;

import android.support.v7.widget.RecyclerView;

import com.badoo.badootest.data.model.TransactionWrapper;
import com.badoo.badootest.ui.base.BaseContract;

import java.util.List;

import io.reactivex.Observable;

public interface MainContract {

    interface View extends BaseContract.MvpView {

        void setupTransactionsList(RecyclerView.Adapter adapter);

        void showLoading();

        void hideLoading();

        void showErrorMessage(String message);

        void navigateToDetailScreenOf(String sku);
    }

    interface Model extends BaseContract.MvpModel {

        Observable<List<TransactionWrapper>> getTransactions();

    }
}
