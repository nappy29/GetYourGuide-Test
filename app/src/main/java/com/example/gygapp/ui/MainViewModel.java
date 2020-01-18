package com.example.gygapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.gygapp.data.ReviewRepository;
import com.example.gygapp.data.datasource.ReviewsDataSource;
import com.example.gygapp.data.datasource.ReviewsDataSourceFactory;
import com.example.gygapp.model.ReviewApiResponse;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {
    private final ReviewRepository reviewRepository;
    private CompositeDisposable compositeDisposable;
    private ReviewsDataSourceFactory reviewsDataSourceFactory;

    private LiveData<PagedList<ReviewApiResponse.Review>> reviewResponseLiveData = new MutableLiveData<>();
    private LiveData<Boolean> loading = new MutableLiveData<>();
    private LiveData<Boolean> error = new MutableLiveData<>();
    private LiveData<Boolean> progressStatus = new MutableLiveData<>();

    @Inject
    public MainViewModel(ReviewRepository reviewRepository, CompositeDisposable compositeDisposable){
        this.compositeDisposable = compositeDisposable;
        this.reviewRepository = reviewRepository;

        reviewsDataSourceFactory = new ReviewsDataSourceFactory(this.reviewRepository, this.compositeDisposable);
        handlePaging();
    }

    public LiveData<PagedList<ReviewApiResponse.Review>> getReviewResponse() {
        return reviewResponseLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public LiveData<Boolean> getProgressStatus() {
        return progressStatus;
    }

    protected void handlePaging(){
        PagedList.Config pageListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(135)
                .build();

        reviewResponseLiveData = new LivePagedListBuilder<>(reviewsDataSourceFactory, pageListConfig).build();

        loading = Transformations.switchMap(reviewsDataSourceFactory.getReviewsResultsLiveData(), ReviewsDataSource::getLoading);
        error = Transformations.switchMap(reviewsDataSourceFactory.getReviewsResultsLiveData(), ReviewsDataSource::getError);
        progressStatus = Transformations.switchMap(reviewsDataSourceFactory.getReviewsResultsLiveData(), ReviewsDataSource::getProgressStatus);
    }

    public void invalidate() {
        reviewsDataSourceFactory.getReviewsResultsLiveData().getValue().invalidate();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}
