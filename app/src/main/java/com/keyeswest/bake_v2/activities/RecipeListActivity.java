package com.keyeswest.bake_v2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.fragments.RecipeListFragment;
import com.keyeswest.bake_v2.models.Recipe;

public class RecipeListActivity extends AppCompatActivity  implements RecipeListFragment.OnRecipeSelected{
    private static final String TAG = "RecipeListActivity";

    private static final String SAVE_RECIPE_KEY = "save_recipe";

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // handle the savedInstanceState
        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(SAVE_RECIPE_KEY);
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Log.d(TAG, "Recipe selected.");

        mRecipe =recipe;
        Intent intent = RecipeDetailActivity.newIntent(this, mRecipe);
        startActivity(intent);


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(SAVE_RECIPE_KEY, mRecipe);
    }
}
