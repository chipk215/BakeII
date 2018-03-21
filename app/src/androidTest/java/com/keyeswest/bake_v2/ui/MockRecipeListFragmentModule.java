package com.keyeswest.bake_v2.ui;

import com.keyeswest.bake_v2.utilities.RecipeFetcher;

import dagger.Module;
import dagger.Provides;

@Module
public class MockRecipeListFragmentModule {

    @Provides
    RecipeFetcher provideRecipeFetcher(){
        return new MockRecipeFetcher();
    }
}
