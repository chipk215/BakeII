package com.keyeswest.bake_v2.models;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.keyeswest.bake_v2.tasks.RecipeJsonDeserializer;



public class RecipeFactory {
    private static final String RECIPE_URL_STRING
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    RequestQueue mRequestQueue;



    public void readNetworkRecipes(final Context context,
                                   final RecipeJsonDeserializer.RecipeResultsCallback  results){

        mRequestQueue = Volley.newRequestQueue(context);

        // use volley to fetch json data as a string since i already have a gson serializer set up
        // which takes a string as input
        StringRequest recipeRequest = new StringRequest(Request.Method.GET,
                RECIPE_URL_STRING,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // Just to be sure deserialize on a bg thread
                            RecipeJsonDeserializer deserializer =
                                    new RecipeJsonDeserializer(context, results);

                            deserializer.execute(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO handle network errors
                        }
                    });

        mRequestQueue.add(recipeRequest);

    }

}
