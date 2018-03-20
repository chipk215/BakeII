package com.keyeswest.bake_v2.tasks;


import android.content.Context;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;


import com.google.gson.Gson;
import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.utilities.EspressoTestingIdlingResource;

;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeJsonDeserializer extends AsyncTask<String, Void, List<Recipe>> {
    private final static String TAG = "RecipeJsonDeserializer";


    private List<Recipe> recipes;
    final private RecipeResultsCallback mCallback;
    final private Context mContext;

    @Nullable
    final private IdlingResource mIdlingResource;

    public interface RecipeResultsCallback{
        void recipeResult(List<Recipe> recipeList);
    }

    public RecipeJsonDeserializer(Context context, RecipeResultsCallback callback, @Nullable
    IdlingResource idlingResource ){

        mCallback = callback;
        recipes = new ArrayList<>();
        mContext = context;
        mIdlingResource = idlingResource;
    }

    @Override
    protected List<Recipe> doInBackground(String... data) {

        if ((data == null) || (data[0] == null)){
            return null;
        }


        Gson gson = new Gson();
        Recipe[] recipeArray = gson.fromJson(data[0], Recipe[].class);
            recipes = Arrays.asList(recipeArray);

        return recipes;
    }


    @Override
    protected void onPostExecute(List<Recipe> recipeList){
        mCallback.recipeResult(recipeList);

        if (mIdlingResource!= null) {
            EspressoTestingIdlingResource.decrement();
        }
    }


}
