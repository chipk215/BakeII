package com.keyeswest.bake_v2.ui;


import android.support.test.runner.AndroidJUnit4;


import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.utilities.RecipeFactory;
import com.keyeswest.bake_v2.tasks.RecipeJsonDeserializer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getTargetContext;


@RunWith(AndroidJUnit4.class)
public abstract class StepBaseTest {

    protected Recipe mRecipe;
    protected CountDownLatch mSignal;
    private RecipeFactory mRecipeFactory = new RecipeFactory();

    @Before
    public void initRecipe() throws Throwable{

        mSignal = new CountDownLatch(1);

        mRecipeFactory.readNetworkRecipes(getTargetContext(),new RecipeJsonDeserializer.RecipeResultsCallback() {
            @Override
            public void recipeResult(List<Recipe> recipeList) {
                mRecipe = recipeList.get(0);

                // clear shared Ingredients preferences
                getTargetContext().getSharedPreferences(mRecipe.getSharedPreferencesIngredientFileName(),
                        0).edit().clear().commit();

                // clear shared Ingredients preferences
                getTargetContext().getSharedPreferences(mRecipe.getSharedPreferencesStepsFileName(),
                        0).edit().clear().commit();


                mSignal.countDown();

            }
        });

        // returns false if time out occurs
        boolean result = mSignal.await(30, TimeUnit.SECONDS);
        Assert.assertTrue("Signal timed out waiting for task response",result);

    }


}
