package com.example.gygapp.di.module;


import com.example.gygapp.Service.ReviewApiService;
import com.example.gygapp.data.ReviewRepository;
import com.example.gygapp.ui.MainViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(ReviewApiService.base_url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    ReviewApiService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(ReviewApiService.class);
    }

    @Singleton
    @Provides
    ReviewRepository provideCurrencyRepository(ReviewApiService currencyApiService) {
        return new ReviewRepository(currencyApiService);
    }

    @Provides
    @Singleton
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

//    @Singleton
//    @Provides
//    MainViewModel provideMainViewModel(ReviewRepository reviewRepository){
//        return new MainViewModel(reviewRepository);
//    }
}
