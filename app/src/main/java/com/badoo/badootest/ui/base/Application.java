package com.badoo.badootest.ui.base;

import com.badoo.badootest.injection.component.AppComponent;
import com.badoo.badootest.injection.component.DaggerAppComponent;
import com.badoo.badootest.injection.module.AppModule;

public class Application extends android.app.Application {

    static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
