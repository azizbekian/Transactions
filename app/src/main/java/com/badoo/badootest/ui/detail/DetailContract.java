package com.badoo.badootest.ui.detail;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;

import com.badoo.badootest.data.model.ConvertedTransaction;
import com.badoo.badootest.ui.base.BaseContract;

import java.util.List;

import io.reactivex.Observable;

public interface DetailContract {

    interface View extends BaseContract.MvpView {

        void setupToolbar(String title);

        void setupTotalAmount(String totalAmount);

        void setupConvertedTransactionList(RecyclerView.Adapter adapter);

        void showErrorMessage(String message);

    }

    interface Model extends BaseContract.MvpModel {

        Observable<Pair<Float, List<ConvertedTransaction>>> getConvertedRates(String sku);

        String getGbpSymbol();

    }
}
