package com.keyeswest.bake_v2.dependInjection;


import com.keyeswest.bake_v2.BakeApplication;
import com.keyeswest.bake_v2.fragments.RecipeListFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = { BakeModule.class})
public interface BakeComponent  {
    void inject(BakeApplication application);

    void inject(RecipeListFragment fragment);
}

