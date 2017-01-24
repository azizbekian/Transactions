package com.badoo.badootest.injection.component;

import com.badoo.badootest.injection.ConfigPersistent;
import com.badoo.badootest.injection.module.ActivityModule;
import com.badoo.badootest.injection.module.ConfigPersistentModule;

import dagger.Subcomponent;

@ConfigPersistent
@Subcomponent(modules = ConfigPersistentModule.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}
