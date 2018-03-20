package com.keyeswest.bake_v2.di;

import android.content.Context;

import com.keyeswest.bake_v2.App;
import com.keyeswest.bake_v2.utilities.RecipeFetcher;

import javax.inject.Singleton;


import dagger.Module;
import dagger.Provides;


@Module
public  class AppModule {

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }



}
