package com.keyeswest.bake_v2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.adapters.RecipeAdapter;
import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.models.RecipeFactory;
import com.keyeswest.bake_v2.tasks.RecipeJsonDeserializer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Handles the displaying of recipes.
 */
public class RecipeListFragment extends Fragment {

    private static final String TAG = "RecipeListFragment";

    private Unbinder mUnbinder;

    private RecipeAdapter mRecipeAdapter;
    private List<Recipe> mRecipes = new ArrayList<>();

    private RecipeFactory mRecipeFactory = new RecipeFactory();



    @BindView(R.id.recipe_recycler_view)
    RecyclerView mRecipeRecyclerView;

    private OnRecipeSelected mListener;

    public RecipeListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list,
                container, false);

        mUnbinder = ButterKnife.bind(this, rootView);
        int columns = getResources().getInteger(R.integer.recipe_grid_columns);
        mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),columns));

        setupRecipeAdapter();



        mRecipeFactory.readNetworkRecipes(getContext(),new RecipeJsonDeserializer.RecipeResultsCallback() {
            @Override
            public void recipeResult(List<Recipe> recipeList) {
                mRecipes = recipeList;

                // Updating the recipe adaptor is not working
                //mRecipeAdapter.notifyDataSetChanged();

                //creating a new adaptor does work, hmmm, why?
                setupRecipeAdapter();
            }
        });

        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeSelected) {
            mListener = (OnRecipeSelected) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mUnbinder.unbind();
    }



    private void setupRecipeAdapter(){

        if (isAdded()){

            Log.d(TAG, "Fragment added, setting up adapter");

            mRecipeAdapter = new RecipeAdapter(mRecipes, new RecipeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Recipe recipe) {

                    mListener.onRecipeSelected(recipe);
                }
            });

            mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        }else{
            Log.d(TAG, "Fragment NOT added, not setting up adapter");

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow the fragment to specify a selected recipe.
     *
     */
    public interface OnRecipeSelected {
        void onRecipeSelected(Recipe recipe);
    }
}
