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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.adapters.RecipeAdapter;
import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.utilities.RecipeFetcher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;


/**
 * Handles the displaying of recipes.
 */
public class RecipeListFragment extends Fragment implements RecipeFetcher.RecipeResultsCallback {

    private static final String TAG = "RecipeListFragment";

    private Unbinder mUnbinder;

    private RecipeAdapter mRecipeAdapter;

    private List<Recipe> mRecipes = new ArrayList<>();

    @Inject
    public RecipeFetcher mRecipeFetcher;

    private OnRecipeSelected mListener;

    @BindView(R.id.recipe_recycler_view)
    RecyclerView mRecipeRecyclerView;

    @BindView(R.id.error_layout)
    LinearLayout mErrorLayout;

    @BindView(R.id.error_txt_cause)
    TextView mErrorText;

    @BindView(R.id.error_btn_retry)Button mRetryButton;

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

      //  mRecipeFetcher = new RecipeFetcher();
        fetchRecipes();

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipes();
            }
        });

        return rootView;
    }


    public void fetchRecipes(){
        mErrorLayout.setVisibility(View.GONE);
        mRecipeRecyclerView.setVisibility(View.VISIBLE);
        mRecipeFetcher.readNetworkRecipes(getContext(), this   );
    }


    @Override
    public void onAttach(Context context) {

        AndroidSupportInjection.inject(this);
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



    @Override
    public void downloadErrorOccurred() {

        if (mErrorLayout.getVisibility() == View.GONE){
            mErrorText.setText(getResources().getString(R.string.internet_error));
            mErrorLayout.setVisibility(View.VISIBLE);
            mRecipeRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void recipeResult(List<Recipe> recipeList) {
        mRecipes = recipeList;

        setupRecipeAdapter();
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
