package com.keyeswest.bake_v2;


import android.app.Application;

import com.keyeswest.bake_v2.dependInjection.DependencyInjector;
import com.keyeswest.bake_v2.utilities.RecipeFactory;

import javax.inject.Inject;


public class BakeApplication extends Application {

    @Inject
    protected RecipeFactory mRecipeFactory;


    @Override
    public void onCreate(){
        super.onCreate();

        DependencyInjector.initialize(this);
        DependencyInjector.bakeComponent().inject(this);

    }



}
