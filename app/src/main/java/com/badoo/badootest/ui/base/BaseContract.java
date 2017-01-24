package com.badoo.badootest.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public interface BaseContract {

    interface Presenter<V extends MvpView> {

        void create(V view);

        void create(V view, @Nullable Bundle savedInstanceState, Intent intent);

        void addDisposable(Disposable disposable);

        void dispose();

        void stop(boolean isFinishing);
    }

    class BasePresenter<T extends MvpView> implements Presenter<T> {

        private T view;
        private CompositeDisposable compositeDisposable = new CompositeDisposable();

        @CheckResult
        public boolean isViewAttached() {
            return view != null;
        }

        public T getView() {
            return view;
        }

        @Override
        public void create(T view) {
            create(view, null, null);
        }

        @Override
        public void create(T view, @Nullable Bundle savedInstanceState, Intent intent) {
            this.view = view;
        }

        @Override
        public void addDisposable(Disposable disposable) {
            compositeDisposable.add(disposable);
        }

        @Override
        public void dispose() {
            compositeDisposable.dispose();
            compositeDisposable = new CompositeDisposable();
        }

        @Override
        public void stop(boolean isFinishing) {
            if(isFinishing) dispose();
        }
    }

    interface MvpView {}

    interface MvpModel {}

}
