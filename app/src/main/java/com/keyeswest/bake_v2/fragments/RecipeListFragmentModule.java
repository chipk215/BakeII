package com.keyeswest.bake_v2.fragments;

import com.keyeswest.bake_v2.utilities.RecipeFetcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeListFragmentModule {


    @Provides
    RecipeFetcher provideRecipeFetcher(){
        return new RecipeFetcher();
    }
}
