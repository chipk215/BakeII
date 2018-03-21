package com.keyeswest.bake_v2.ui;

import com.keyeswest.bake_v2.activities.RecipeListActivity;
import com.keyeswest.bake_v2.activities.RecipeListActivityModule;
import com.keyeswest.bake_v2.fragments.RecipeListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MockActivityBuilder {
    @ContributesAndroidInjector(modules=RecipeListActivityModule.class)
    abstract RecipeListActivity bindRecipeListActivity();


    @ContributesAndroidInjector(modules = MockRecipeListFragmentModule.class)
    // or gain access to lobby activity dependencies from fragment via
    // @ContributesAndroidInjector(modules = {LobbyFragmentModule.class, LobbyActivityModule.class})
    abstract RecipeListFragment bindRecipeListFragment();


}
