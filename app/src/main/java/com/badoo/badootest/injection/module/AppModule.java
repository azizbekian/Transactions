package com.badoo.badootest.injection.module;

import android.content.Context;

import com.badoo.badootest.injection.AppContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @AppContext private Context context;

    public AppModule(@AppContext Context context) {
        this.context = context;
    }

    @AppContext
    @Provides
    @Singleton
    public Context getContext() {
        return context;
    }

    @Provides
    @Singleton
    public Gson getGson() {
        return new GsonBuilder().create();
    }

}
