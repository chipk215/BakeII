package com.keyeswest.bake_v2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.adapters.IngredientsAdapter;
import com.keyeswest.bake_v2.adapters.StepAdapter;
import com.keyeswest.bake_v2.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Displays the details about a recipe.
 */
public class RecipeDetailFragment extends Fragment {

    private static final String TAG = "RecipeDetailFragment";
    private static final String SAVE_RECIPE_KEY = "save_recipe";

    private Recipe mRecipe;

    private Unbinder mUnbinder;

    private OnStepSelected mListener;

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;

    @BindView(R.id.steps_recycler_view)
    RecyclerView mStepsRecyclerView;

    private IngredientsAdapter mIngredientsAdapter;
    private StepAdapter mStepAdapter;


    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    public static RecipeDetailFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putParcelable(SAVE_RECIPE_KEY, recipe);

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepSelected) {
            mListener = (OnStepSelected) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeSelected");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(SAVE_RECIPE_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_detail,
                container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_list_divider));
        mIngredientsRecyclerView.addItemDecoration(itemDecorator);

        setupIngredientAdapter();


        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStepsRecyclerView.addItemDecoration(itemDecorator);

        setupStepsAdapter();


        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mUnbinder.unbind();
    }


    private void setupIngredientAdapter(){
        if (isAdded()){
            mIngredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients());

            mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);

        }
    }

    private void setupStepsAdapter(){

        if (isAdded()){

            mStepAdapter = new StepAdapter(mRecipe.getSteps(),new StepAdapter.OnStepClickListener(){

                @Override
                public void onStepClick(int index) {
                    Log.d(TAG, "Step selected");
                    mListener.onStepSelected(index);
                }
            });

            mStepsRecyclerView.setAdapter(mStepAdapter);

        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow the fragment to specify a selected step.
     *
     */
    public interface OnStepSelected {
        void onStepSelected(int index);
    }



}
