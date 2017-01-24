package com.badoo.badootest.ui.main;

import com.badoo.badootest.data.model.TransactionWrapper;
import com.badoo.badootest.injection.ConfigPersistent;
import com.badoo.badootest.ui.base.BaseContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ConfigPersistent
class MainPresenter extends BaseContract.BasePresenter<MainContract.View> {

    private MainContract.Model model;
    private TransactionsAdapter adapter;

    /**
     * Indicates, whether we already created a subscription.
     * <p>
     * Is used to correctly handle configuration
     * changes and do not re-start subscription when making orientation change whilst data is being loaded.
     */
    private boolean isRunning;

    @Inject
    MainPresenter(MainContract.Model model, TransactionsAdapter adapter) {
        this.model = model;
        this.adapter = adapter;
        adapter.setTransactionClickListener(sku -> {
            MainContract.View view = getView();
            if (null != view) {
                view.navigateToDetailScreenOf(sku);
            }
        });
    }

    @Override
    public void create(MainContract.View view) {
        super.create(view);

        if (!isRunning && adapter.isEmpty()) {
            getView().showLoading();
            Disposable d = model.getTransactions()
                    .doOnSubscribe(disposable -> isRunning = true)
                    .doOnComplete(() -> isRunning = false)
                    .subscribe(this::onDataSuccess, this::onDataFailure);
            addDisposable(d);
        } else if (!adapter.isEmpty()) {
            getView().setupTransactionsList(adapter);
        }
    }

    private void onDataSuccess(List<TransactionWrapper> transactions) {
        adapter.setTransactions(transactions);
        if (isViewAttached()) {
            MainContract.View view = getView();
            view.hideLoading();
            view.setupTransactionsList(adapter);
        }
    }

    private void onDataFailure(Throwable throwable) {
        if (isViewAttached()) {
            MainContract.View view = getView();
            view.hideLoading();
            view.showErrorMessage(throwable.getMessage());
        }
    }

}
