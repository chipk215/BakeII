package com.keyeswest.bake_v2.ui;

import android.content.Context;

import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;

import com.keyeswest.bake_v2.models.Recipe;

import org.junit.BeforeClass;

import java.io.InputStream;



public abstract class RecipeDataTest{

    protected static Recipe TEST_RECIPE;

    @BeforeClass
    public static void readRecipeData() throws Exception{
        Context ctx = InstrumentationRegistry.getContext();
        InputStream is = ctx.getResources().getAssets().open("recipe.json");
        String jsonString = Utils.readTextStream(is);
        Gson gson = new Gson();
        Recipe[] recipes = gson.fromJson(jsonString, Recipe[].class);
        TEST_RECIPE = recipes[0];

    }

}
