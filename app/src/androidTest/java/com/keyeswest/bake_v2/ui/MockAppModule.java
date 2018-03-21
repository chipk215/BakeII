package com.keyeswest.bake_v2.ui;


import android.content.Context;



import dagger.Module;
import dagger.Provides;

@Module
public class MockAppModule {

    @Provides
    Context provideContext(TestApp application) {
        return application.getApplicationContext();
    }
}
