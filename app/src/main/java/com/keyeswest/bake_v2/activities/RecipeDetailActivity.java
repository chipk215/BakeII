package com.keyeswest.bake_v2.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.fragments.RecipeDetailFragment;
import com.keyeswest.bake_v2.fragments.StepDetailFragment;
import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements
        StepDetailFragment.OnStepNavigation,
        RecipeDetailFragment.OnStepSelected {

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

    @Nullable
    @BindView(R.id.step_divider_view)
    View mTwoPaneDivider;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            mRecipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE_BUNDLE);


        }else{
            mRecipe = savedInstanceState.getParcelable(SAVE_RECIPE_KEY);
        }

        setTitle(mRecipe.getName());
        mTwoPane = (mTwoPaneDivider != null);

        //Add UI fragment for displaying lists of ingredients and steps
        // create a new recipe detail fragment
        RecipeDetailFragment recipeFragment = RecipeDetailFragment.newInstance(mRecipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_container,recipeFragment)
                .commit();



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
        if (mTwoPane){
            // Add the step detail fragment for wide devices
            StepDetailFragment stepFragment =
                    StepDetailFragment.newInstance(mRecipe.getSteps().get(mSelectedIndex),
                            true, true);

            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.findFragmentById(R.id.step_detail_container) == null){
                // initial load of fragment
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, stepFragment)
                        .commit();
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.step_detail_container, stepFragment)
                        .commit();
            }

        }else {

            //phone scenario
            Intent intent = StepDetailActivity.newIntent(this,
                    mRecipe.getSteps(), mSelectedIndex);

            startActivity(intent);
        }


    }

    @Override
    public void onNextSelected() {
        // required but not needed
    }

    @Override
    public void onPreviousSelected() {
        // required but not needed

    }
}
