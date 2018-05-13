package com.example.lpelczar.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void whenRecyclerViewItemClickedThenOpenStepActivity() throws InterruptedException {
        Thread.sleep(SECONDS.toMillis(2));
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_details_list)).perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));
        intended(hasComponent(StepActivity.class.getName()));
    }

    @Test
    public void whenStepClickedThenAppropriateStepIsOpened() throws InterruptedException {
        Thread.sleep(SECONDS.toMillis(2));
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_details_list)).perform(RecyclerViewActions.actionOnItemAtPosition(11, click()));
        intended(hasComponent(StepActivity.class.getName()));

        String STEP_DESCRIPTION = "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of " +
                "sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and " +
                "1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.";
        onView(withId(R.id.step_description_tv)).check(matches(withText(STEP_DESCRIPTION)));
    }
}
