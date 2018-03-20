package com.keyeswest.bake_v2.di;

import com.keyeswest.bake_v2.activities.RecipeListActivity;
import com.keyeswest.bake_v2.activities.RecipeListActivityModule;
import com.keyeswest.bake_v2.fragments.RecipeListFragment;
import com.keyeswest.bake_v2.fragments.RecipeListFragmentModule;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;



@Module
public abstract class ActivityBuilder {



    @ContributesAndroidInjector(modules=RecipeListActivityModule.class)
    abstract RecipeListActivity bindRecipeListActivity();


    @ContributesAndroidInjector(modules = RecipeListFragmentModule.class)
    // or gain access to lobby activity dependencies from fragment via
    // @ContributesAndroidInjector(modules = {LobbyFragmentModule.class, LobbyActivityModule.class})
    abstract RecipeListFragment bindRecipeListFragment();

}
