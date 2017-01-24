package com.badoo.badootest.injection.component;

import com.badoo.badootest.injection.PerActivity;
import com.badoo.badootest.injection.module.ActivityModule;
import com.badoo.badootest.ui.detail.DetailActivity;
import com.badoo.badootest.ui.main.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void injectActivityComponent(MainActivity mainActivity);

    void injectActivityComponent(DetailActivity mainActivity);

}
