package com.example.gygapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gygapp.ui.MainViewModel;
import com.example.gygapp.ui.ReviewViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindsMainViewModel(MainViewModel reviewViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ReviewViewModelFactory viewModelFactory);
}