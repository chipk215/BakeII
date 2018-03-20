package com.keyeswest.bake_v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.fragments.StepDetailFragment;
import com.keyeswest.bake_v2.models.Step;

import java.util.ArrayList;
import java.util.List;

public class StepDetailActivity extends AppCompatActivity implements
        // Invoked by StepDetailFragment when user wants to access previous or next step
        StepDetailFragment.OnStepNavigation{

    public static final String TAG="StepDetailActivity";
    public static final String EXTRA_STEP_BUNDLE = "com.keyeswest.bake.step";
    public static final String STEPS_KEY = "stepsKey";
    public static final String SELECTED_INDEX_KEY="selectedIndexKey";
    public static final String RECIPE_NAME_KEY = "recipeNameKey";

    public static Intent newIntent(Context packageContext, List<Step> steps,
                                   int selectedIndex, String recipeName){
        Intent intent = new Intent(packageContext, StepDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS_KEY, (ArrayList<Step>)steps);
        bundle.putInt(SELECTED_INDEX_KEY, selectedIndex);
        bundle.putString(RECIPE_NAME_KEY, recipeName);
        intent.putExtra(EXTRA_STEP_BUNDLE, bundle);

        return intent;
    }

    private List<Step> mSteps;
    private int mSelectedIndex;
    private String mRecipeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreateInvoked");
        super.onCreate(savedInstanceState);
        // Layout file activity_step_detail.xml
        setContentView(R.layout.activity_step_detail);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailFragment stepDetailFragment;

        if (savedInstanceState != null){
            mSteps = savedInstanceState.getParcelableArrayList(STEPS_KEY);
            mSelectedIndex = savedInstanceState.getInt(SELECTED_INDEX_KEY);
            mRecipeName = savedInstanceState.getString(RECIPE_NAME_KEY);

        }else {
            Bundle bundle = getIntent().getParcelableExtra(EXTRA_STEP_BUNDLE);
            mSteps = bundle.getParcelableArrayList(STEPS_KEY);
            mSelectedIndex = bundle.getInt(SELECTED_INDEX_KEY);
            mRecipeName = bundle.getString(RECIPE_NAME_KEY);

            stepDetailFragment = configureFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }

        setTitle(mRecipeName);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:


                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * User wants the next Step in the recipe
     *
     */
    @Override
    public void onNextSelected() {
        mSelectedIndex += 1;
        StepDetailFragment stepDetailFragment = configureFragment();
            replaceFragment(stepDetailFragment);

    }


    /**
     * User wants the previous step in the recipe
     *
     */
    @Override
    public void onPreviousSelected() {
        mSelectedIndex -= 1;

        StepDetailFragment stepDetailFragment = configureFragment();

        replaceFragment(stepDetailFragment);

    }




    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(STEPS_KEY, (ArrayList<Step>)mSteps);
        savedInstanceState.putInt(SELECTED_INDEX_KEY, mSelectedIndex);
        savedInstanceState.putString(RECIPE_NAME_KEY, mRecipeName);
    }


    /**
     * Helper function for unwrapping the intent data returned to the calling Activity.
     * @param data
     * @return
     */
    public static List<Step> getUpdatedSteps(Intent data){
        Bundle bundle =  data.getBundleExtra(EXTRA_STEP_BUNDLE);
        return bundle.getParcelableArrayList(STEPS_KEY);

    }


    private void replaceFragment(StepDetailFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();
    }


    private StepDetailFragment configureFragment(){
        boolean previousButtonEnable = mSelectedIndex > 0;
        boolean nextButtonEnable = mSelectedIndex < mSteps.size()-1;
        StepDetailFragment stepDetailFragment =
                StepDetailFragment.newInstance(mSteps.get(mSelectedIndex),previousButtonEnable,
                        nextButtonEnable);

        return stepDetailFragment;

    }

}
