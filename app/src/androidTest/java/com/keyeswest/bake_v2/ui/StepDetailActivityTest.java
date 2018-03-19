package com.keyeswest.bake_v2.ui;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.activities.StepDetailActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.keyeswest.bake_v2.ui.Utils.isTablet;
import static com.keyeswest.bake_v2.ui.Utils.matchToolbarTitle;
import static org.hamcrest.CoreMatchers.not;


@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest extends RecipeDataTest {

    @Rule
    public ActivityTestRule mActivityTestRule =
            new ActivityTestRule<>(StepDetailActivity.class,
                    false, false);

    //=============== These tests only apply to narrow devices =================/
    @Test
    public void launchStepDetailActivityTest() {


        if (!isTablet(InstrumentationRegistry.getTargetContext())) {

            Intent intent = StepDetailActivity.newIntent(getTargetContext(),
                    TEST_RECIPE.getSteps(), 0, TEST_RECIPE.getName());

            mActivityTestRule.launchActivity(intent);

            // verify toolbar title
            String expectedTitle = TEST_RECIPE.getName();
            matchToolbarTitle(expectedTitle);

            // check for the video player to be visible
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));

            // step description
            onView(withId(R.id.step_description_tv)).check(matches(isDisplayed()));

            // previous button
            onView(withId(R.id.prev_button)).check(matches(isDisplayed()));

            // next button
            onView(withId(R.id.next_button)).check(matches(isDisplayed()));

        }
    }


    @Test
    public void firstStepValuesTest(){

        if (!isTablet(InstrumentationRegistry.getTargetContext())) {

            Intent intent = StepDetailActivity.newIntent(getTargetContext(),
                    TEST_RECIPE.getSteps(), 0, TEST_RECIPE.getName());

            mActivityTestRule.launchActivity(intent);

            // verify previous button is disabled
            onView(withId(R.id.prev_button)).check(matches(not(isEnabled())));

            //verify next button is enabled
            onView(withId(R.id.next_button)).check(matches(isEnabled()));

        }

    }

    @Test
    public void secondStepValuesTest(){

        if (!isTablet(InstrumentationRegistry.getTargetContext())) {

            Intent intent = StepDetailActivity.newIntent(getTargetContext(),
                    TEST_RECIPE.getSteps(), 1, TEST_RECIPE.getName());

            mActivityTestRule.launchActivity(intent);

            // verify previous button is enabled
            onView(withId(R.id.prev_button)).check(matches(isEnabled()));

            //verify next button is enabled
            onView(withId(R.id.next_button)).check(matches(isEnabled()));

        }

    }

    @Test
    public void lastStepValuesTest(){

        if (!isTablet(InstrumentationRegistry.getTargetContext())) {

            int lastIndex = TEST_RECIPE.getSteps().size()-1;

            Intent intent = StepDetailActivity.newIntent(getTargetContext(),
                    TEST_RECIPE.getSteps(), lastIndex, TEST_RECIPE.getName());

            mActivityTestRule.launchActivity(intent);

            // verify previous button is enabled
            onView(withId(R.id.prev_button)).check(matches(isEnabled()));

            //verify next button is disabled
            onView(withId(R.id.next_button)).check(matches(not(isEnabled())));

        }

    }

    @Test
    public void verifyClickingOnEnabledPreviousButtonLastItemLoadsPreviousStep(){

        if (!isTablet(InstrumentationRegistry.getTargetContext())) {

            int lastIndex = TEST_RECIPE.getSteps().size()-1;

            Intent intent = StepDetailActivity.newIntent(getTargetContext(),
                    TEST_RECIPE.getSteps(),lastIndex, TEST_RECIPE.getName());

            mActivityTestRule.launchActivity(intent);

            //verify correct step is displayed
            onView(withId(R.id.step_description_tv))
                    .check(matches(withText(TEST_RECIPE.getSteps().get(lastIndex)
                            .getDescription())));

            // verify previous button is enabled
            onView(withId(R.id.prev_button)).check(matches(isEnabled()));

            //verify next button is disabled
            onView(withId(R.id.next_button)).check(matches(not(isEnabled())));


            //click the previous button
            onView(withId(R.id.prev_button)).perform(click());

            // check the  previous step description
            onView(withId(R.id.step_description_tv))
                    .check(matches(withText(TEST_RECIPE.getSteps().get(lastIndex-1)
                            .getDescription())));

            //both navigation buttons should be enabled
            // verify previous button is enabled
            onView(withId(R.id.prev_button)).check(matches(isEnabled()));

            //verify next button is enabled
            onView(withId(R.id.next_button)).check(matches(isEnabled()));

        }

    }


    @Test
    public void verifyClickingOnEnabledNextButtonLoadsNextStep(){

        if (!isTablet(InstrumentationRegistry.getTargetContext())) {

            Intent intent = StepDetailActivity.newIntent(getTargetContext(),
                    TEST_RECIPE.getSteps(),0, TEST_RECIPE.getName());

            mActivityTestRule.launchActivity(intent);

            //verify correct step is displayed
            onView(withId(R.id.step_description_tv)).check(matches(withText(TEST_RECIPE.getSteps().get(0).getDescription())));

            // verify previous button is disabled
            onView(withId(R.id.prev_button)).check(matches(not(isEnabled())));

            //verify next button is enabled
            onView(withId(R.id.next_button)).check(matches(isEnabled()));

            //click the next button
            onView(withId(R.id.next_button)).perform(click());

            // check the  next step description
            onView(withId(R.id.step_description_tv)).check(matches(withText(TEST_RECIPE.getSteps().get(1).getDescription())));

            //both navigation buttons should be enabled
            // verify previous button is enabled
            onView(withId(R.id.prev_button)).check(matches(isEnabled()));

            //verify next button is enabled
            onView(withId(R.id.next_button)).check(matches(isEnabled()));

        }

    }




}
