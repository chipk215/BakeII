package com.keyeswest.bake_v2.dependInjection;


import com.keyeswest.bake_v2.BakeApplication;
import com.keyeswest.bake_v2.activities.RecipeListActivity;
import com.keyeswest.bake_v2.utilities.RecipeFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public class BakeModule {
    private final BakeApplication application;

    public BakeModule(BakeApplication application){
        this.application = application;

    }

    @Provides @Singleton
    public RecipeFactory provideRecipeFactory(){
        return new RecipeFactory();
    }
}
