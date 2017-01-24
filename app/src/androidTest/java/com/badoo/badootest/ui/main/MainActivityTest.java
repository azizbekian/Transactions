package com.badoo.badootest.ui.main;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.badoo.badootest.R;
import com.badoo.badootest.ui.detail.DetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onItemClickLaunchesDetailScreen() {
        Intents.init();

        onView(withId(R.id.recycler_transactions))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Intents.intended(hasComponent(DetailActivity.class.getName()));
    }
}
