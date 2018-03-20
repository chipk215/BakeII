package com.keyeswest.bake_v2.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.fragments.RecipeListFragment;
import com.keyeswest.bake_v2.models.Recipe;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeListActivity extends AppCompatActivity implements
        RecipeListFragment.OnRecipeSelected,
        HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private static final String TAG = "RecipeListActivity";

    private static final String SAVE_RECIPE_KEY = "save_recipe";

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
