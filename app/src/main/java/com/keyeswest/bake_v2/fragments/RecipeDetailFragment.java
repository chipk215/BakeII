package com.keyeswest.bake_v2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.adapters.IngredientsAdapter;
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

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;

    @BindView(R.id.steps_recycler_view)
    RecyclerView mStepsRecyclerView;

    private IngredientsAdapter mIngredientsAdapter;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(SAVE_RECIPE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_detail,
                container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_list_divider));
        mIngredientsRecyclerView.addItemDecoration(itemDecorator);

        setupIngredientAdapter();




        return view;
    }


    private void setupIngredientAdapter(){
        if (isAdded()){
            mIngredientsAdapter = new IngredientsAdapter(mRecipe.getIngredients());

            mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);

        }
    }



}
