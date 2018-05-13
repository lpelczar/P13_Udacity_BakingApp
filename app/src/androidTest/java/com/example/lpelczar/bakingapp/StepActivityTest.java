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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void whenNextButtonClickedThenNextStepIsShown() throws InterruptedException {
        Thread.sleep(SECONDS.toMillis(2));
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_details_list)).perform(RecyclerViewActions.actionOnItemAtPosition(11, click()));
        onView(withId(R.id.next_step_button)).perform(click());

        String STEP_DESCRIPTION = "3. Press the cookie crumb mixture into the prepared pie pan and" +
                " bake for 12 minutes. Let crust cool to room temperature.";
        onView(withId(R.id.step_description_tv)).check(matches(withText(STEP_DESCRIPTION)));
    }

    @Test
    public void whenPreviousButtonClickedThenPreviousStepIsShown() throws InterruptedException {
        Thread.sleep(SECONDS.toMillis(2));
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_details_list)).perform(RecyclerViewActions.actionOnItemAtPosition(11, click()));
        onView(withId(R.id.previous_step_button)).perform(click());

        String STEP_DESCRIPTION = "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.";
        onView(withId(R.id.step_description_tv)).check(matches(withText(STEP_DESCRIPTION)));
    }
}
