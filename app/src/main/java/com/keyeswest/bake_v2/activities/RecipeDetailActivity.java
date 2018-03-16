package com.keyeswest.bake_v2.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.fragments.RecipeDetailFragment;
import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.models.Step;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailFragment.OnStepSelected {

    private static final String TAG = "RecipeDetailActivity";

    private static final String EXTRA_RECIPE_BUNDLE = "com.keyeswest.bake_v2.recipe";
    private static final String SAVE_RECIPE_KEY = "save_recipe";

    public static Intent newIntent(Context packageContext, Recipe recipe){
        Intent intent = new Intent(packageContext, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_BUNDLE, recipe);
        return intent;
    }

    private int mSelectedIndex;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null){
            mRecipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE_BUNDLE);
            // create a new recipe detail fragment
            RecipeDetailFragment recipeFragment = RecipeDetailFragment.newInstance(mRecipe);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_container,recipeFragment)
                    .commit();

        }else{
            mRecipe = savedInstanceState.getParcelable(SAVE_RECIPE_KEY);
        }

        setTitle(mRecipe.getName());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable(SAVE_RECIPE_KEY, mRecipe);
    }

    @Override
    public void onStepSelected(int index) {
        // Either start an activity or update the two pane activity
        Log.d(TAG, "Step selected: " + mRecipe.getSteps().get(index).getShortDescription());

        mSelectedIndex = index;
        //phone scenario
        Intent intent = StepDetailActivity.newIntent(this,
                mRecipe.getSteps(), mSelectedIndex);


         startActivity(intent);


    }
}
