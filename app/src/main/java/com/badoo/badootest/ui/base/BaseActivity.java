package com.badoo.badootest.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.badoo.badootest.injection.component.ActivityComponent;
import com.badoo.badootest.injection.component.ConfigPersistentComponent;
import com.badoo.badootest.injection.module.ActivityModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * All activities should inherit from this, in order to easily survive configuration changes.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "activity_id";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> componentsMap = new HashMap<>();

    private ActivityComponent activityComponent;
    private long activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // creates ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change
        activityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!componentsMap.containsKey(activityId)) {
            configPersistentComponent = Application.getAppComponent().getConfigPersistentComponent();
            componentsMap.put(activityId, configPersistentComponent);
        } else {
            configPersistentComponent = componentsMap.get(activityId);
        }
        activityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, activityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) componentsMap.remove(activityId);
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return activityComponent;
    }

}

