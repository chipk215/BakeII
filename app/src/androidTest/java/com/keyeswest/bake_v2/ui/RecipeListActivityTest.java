package com.keyeswest.bake_v2.ui;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.activities.RecipeListActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    // Launch the MainActivity prior to each test
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class, false, false);

    @Before
    public void init() throws InterruptedException{
        mActivityTestRule.launchActivity(null);

        // I'm finding I have to delay between tests. I suspect the culprit is with the asynch
        // network fetching of recipe data.  The best fix, I think is to inject a fake
        // resource fetcher so that the network access is eliminated during testing.


        Thread.sleep(300);
    }


    @Test
    public void launchRecipeListActivityTest(){

        onView(withId(R.id.recipe_recycler_view)).check(matches(isDisplayed()));

        //-- Without dependency injection of the RecipeFetcher the test data is the
        // data being returned from the network fetch. Inappropriate, but I spent a couple
        // of days on Dagger and I was able to configure injection for
        // the app but was not able to successfully inject a fake ReviewFetcher for testing.

        // Does Udacity hae any info on how to test with Dagger2?
    }

    @Test
    public void launchRecipeDetailActivityTest(){
        // launch the corresponding detail activity when a recipe is clicked

        onView(withId(R.id.recipe_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.ingredient_label_tv)).check(matches(isDisplayed()));


    }


}
