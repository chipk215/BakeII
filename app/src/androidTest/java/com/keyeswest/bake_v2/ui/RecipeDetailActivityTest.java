package com.keyeswest.bake_v2.ui;


import android.content.Context;
import android.content.Intent;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.activities.RecipeDetailActivity;
import com.keyeswest.bake_v2.models.Ingredient;
import com.keyeswest.bake_v2.models.IngredientViewModel;

import com.keyeswest.bake_v2.models.Step;
import com.keyeswest.bake_v2.models.StepViewModel;

import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.keyeswest.bake_v2.ui.Utils.atPosition;
import static com.keyeswest.bake_v2.ui.Utils.isTablet;
import static com.keyeswest.bake_v2.ui.Utils.matchToolbarTitle;


public class RecipeDetailActivityTest extends RecipeDataTest{


    @Before
    public void launchActivity(){
        Intent intent = RecipeDetailActivity.newIntent(getTargetContext(), TEST_RECIPE);
        mActivityTestRule.launchActivity(intent);
    }

    @Rule
    public ActivityTestRule mActivityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class,
                    false, false);


    @Test
    public void launchRecipeDetailActivityTest() {

        // check for label on ingredients list
        onView(withId(R.id.ingredient_label_tv)).check(matches(isDisplayed()));

        // check for ingredients list
        onView(withId(R.id.ingredients_recycler_view)).check(matches(isDisplayed()));

        //check for label on steps list
        onView(withId(R.id.steps_label_tv)).check(matches(isDisplayed()));

        //check for steps list
        onView(withId(R.id.steps_recycler_view)).check(matches(isDisplayed()));

        //=============== wide screen verifications    ==================
        if (isTablet(InstrumentationRegistry.getTargetContext())) {

            // check for the video player to be visible
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));

            // step description
            onView(withId(R.id.step_description_tv)).check(matches(isDisplayed()));
        }

    }


    @Test
    public void valuesTest(){

        // verify toolbar title
        String expectedTitle = TEST_RECIPE.getName();
        matchToolbarTitle(expectedTitle);

        //verify ingredients label
        onView(withId(R.id.ingredient_label_tv)).check(matches(
                withText(getTargetContext().getResources().getString(R.string.ingredients))));

        //verify steps label
        onView(withId(R.id.steps_label_tv)).check(matches(
                withText(getTargetContext().getResources().getString(R.string.recipe_steps))));

        //verify the name of the first ingredient
        // verify the text of the 1st item in the list
        IngredientViewModel viewModel = new IngredientViewModel(getTargetContext(),
                TEST_RECIPE.getIngredients().get(0));
        onView(withId(R.id.ingredients_recycler_view))
                .check(matches(atPosition(0, withText(viewModel.getIngredientInfo()))));

    }

    @Test
    public void verifyIngredientListTest(){
        Context context = getTargetContext();
        int position = 0;
        for (Ingredient i : TEST_RECIPE.getIngredients()){
            IngredientViewModel viewModel = new IngredientViewModel(context, i);
            onView(withId(R.id.ingredients_recycler_view))
                    .check(matches(atPosition(position++, withText(viewModel.getIngredientInfo()))));

        }
    }

    @Test
    public void verifyStepListTest(){
        Context context = getTargetContext();
        int position = 0;

        for (Step i : TEST_RECIPE.getSteps()){
            StepViewModel viewModel = new StepViewModel(context, i);
            onView(withId(R.id.steps_recycler_view))
                    .check(matches(atPosition(position++,
                            hasDescendant(withText(viewModel.getListLabel())))));
        }
    }

    @Test
    public void verifyStepClickNavigatesToStepDetailTest(){
        // click on the item
        onView(withId(R.id.steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // check for the video player to be visible
        onView(withId(R.id.video_view)).check(matches(isDisplayed()));
    }


}
