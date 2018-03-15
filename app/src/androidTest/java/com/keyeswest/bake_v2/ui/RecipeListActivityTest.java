package com.keyeswest.bake_v2.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.activities.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    // Launch the MainActivity prior to each test
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);


    @Test
    public void launchTest(){
        onView(withId(R.id.recipe_recycler_view)).check(matches(isDisplayed()));
    }


    //-- Without dependency injection of the RecipeFactory to use test recipe data
    // we can't validate the recipe view data

    // revisit if time permits



}
