package com.example.gygapp.di.builder;

import com.example.gygapp.ui.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();
}