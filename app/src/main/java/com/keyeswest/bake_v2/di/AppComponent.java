package com.keyeswest.bake_v2.di;

import com.keyeswest.bake_v2.App;

import javax.inject.Singleton;


import dagger.BindsInstance;
import dagger.Component;


import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent
{
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);
        AppComponent build();
    }

    void inject(App app);

}

