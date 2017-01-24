package com.badoo.badootest.injection.component;

import android.content.Context;

import com.badoo.badootest.injection.AppContext;
import com.badoo.badootest.injection.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @AppContext
    Context getApplicationContext();

    ConfigPersistentComponent getConfigPersistentComponent();
}
