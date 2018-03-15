package com.keyeswest.bake_v2.models;


import android.content.Context;



public class RecipeViewModel {
    private final static String TAG= "RecipeViewModel";

    private Context mContext;

    private Recipe mRecipe;

    public RecipeViewModel(Context context, Recipe recipe){
        mContext = context;
        mRecipe = recipe;
    }

    public String getName(){
        return mRecipe.getName();
    }


}
