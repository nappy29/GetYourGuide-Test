package com.example.gygapp.data.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.gygapp.data.ReviewRepository;
import com.example.gygapp.model.ReviewApiResponse;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ReviewsDataSourceFactory extends DataSource.Factory<Integer, ReviewApiResponse.Review> {

    private MutableLiveData<ReviewsDataSource> reviewsResultsLiveData;
    private ReviewRepository reviewRepository;
    private CompositeDisposable compositeDisposable;

    public ReviewsDataSourceFactory(ReviewRepository reviewRepository, CompositeDisposable compositeDisposable){
        this.reviewRepository = reviewRepository;
        this.compositeDisposable = compositeDisposable;
        reviewsResultsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ReviewsDataSource> getReviewsResultsLiveData() {
        return reviewsResultsLiveData;
    }

    @Override
    public DataSource<Integer, ReviewApiResponse.Review> create() {
        ReviewsDataSource reviewsDataSource = new ReviewsDataSource(reviewRepository, compositeDisposable);
        reviewsResultsLiveData.postValue(reviewsDataSource);

        return reviewsDataSource;
    }
}
