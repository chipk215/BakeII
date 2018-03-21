package com.keyeswest.bake_v2.ui;






import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        MockAppModule.class,
        MockActivityBuilder.class})
public interface MockAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(TestApp application);
        MockAppComponent build();
    }

    void inject(TestApp app);
}
