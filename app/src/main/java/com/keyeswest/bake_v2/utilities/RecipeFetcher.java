package com.keyeswest.bake_v2.utilities;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.tasks.RecipeJsonDeserializer;

import java.util.List;



public class RecipeFetcher {

    @Nullable
    private final IdlingResource mIdlingResource =
            EspressoTestingIdlingResource.getIdlingResource();

    private static final String RECIPE_URL_STRING
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public interface RecipeResultsCallback extends  RecipeJsonDeserializer.RecipeResultsCallback{
        void downloadErrorOccurred();

        @Override
        void recipeResult(List<Recipe> recipeList);
    }


    public RecipeFetcher(){}

    public void readNetworkRecipes(final Context context,
                                   final RecipeResultsCallback  results){

        if (! NetworkUtilities.isNetworkAvailable(context)){
            results.downloadErrorOccurred();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Use volley to fetch json data as a string since I already have a gson serializer set up
        // which takes a string as input
        if (mIdlingResource != null){
            EspressoTestingIdlingResource.increment();
        }
        StringRequest recipeRequest = new StringRequest(Request.Method.GET,
                RECIPE_URL_STRING,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // Just to be sure deserialize on a bg thread
                            RecipeJsonDeserializer deserializer =
                                    new RecipeJsonDeserializer(context, results, mIdlingResource);

                            deserializer.execute(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            results.downloadErrorOccurred();
                            if (mIdlingResource!= null) {
                                EspressoTestingIdlingResource.decrement();
                            }

                        }
                    });

        requestQueue.add(recipeRequest);

    }

}
