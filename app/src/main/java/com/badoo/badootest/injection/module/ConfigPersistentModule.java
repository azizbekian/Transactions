package com.badoo.badootest.injection.module;

import com.badoo.badootest.data.DataManager;
import com.badoo.badootest.injection.ConfigPersistent;
import com.badoo.badootest.ui.detail.DetailContract;
import com.badoo.badootest.ui.detail.DetailModel;
import com.badoo.badootest.ui.main.MainContract;
import com.badoo.badootest.ui.main.MainModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigPersistentModule {

    @Provides
    @ConfigPersistent
    MainContract.Model providesMainModel(DataManager dataManager) {
        return new MainModel(dataManager);
    }

    @Provides
    @ConfigPersistent
    DetailContract.Model providesDetailModel(DataManager dataManager) {
        return new DetailModel(dataManager);
    }

}
