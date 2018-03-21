package com.keyeswest.bake_v2.ui;


import android.content.Context;

import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.utilities.RecipeFetcher;

import java.util.ArrayList;
import java.util.List;

public class MockRecipeFetcher extends RecipeFetcher {

    @Override
    public void readNetworkRecipes(final Context context,
                                   final RecipeResultsCallback  results){

        List<Recipe> recipes = new ArrayList<Recipe>();
        Recipe recipe = new Recipe();
        recipe.setName("Test Recipe");
        recipes.add(recipe);
        results.recipeResult(recipes);
    }
}
