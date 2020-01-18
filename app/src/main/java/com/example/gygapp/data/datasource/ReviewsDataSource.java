package com.example.gygapp.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;

import com.example.gygapp.data.ReviewRepository;
import com.example.gygapp.model.ReviewApiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ReviewsDataSource extends PositionalDataSource<ReviewApiResponse.Review> {

    private static int offset = 0;
    private CompositeDisposable compositeDisposable;
    private final ReviewRepository reviewRepository;
    private final MutableLiveData<Boolean> loading ;
    private final MutableLiveData<Boolean> error;
    private final MutableLiveData<Boolean> progressStatus;

    public ReviewsDataSource(ReviewRepository reviewRepository, CompositeDisposable compositeDisposable){
        this.reviewRepository = reviewRepository;
        this.compositeDisposable =  compositeDisposable;
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        progressStatus = new MutableLiveData<>();
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback< ReviewApiResponse.Review> callback) {

        loading.postValue(true);
        int position = computeInitialLoadPosition(params, 0);
        int loadSize = computeInitialLoadSize(params, position, 0);
        reviewRepository.getReviews( position)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("totalcount", response.getTotalCount() + "");

                    Log.d("offset", offset + "");


                    offset += 5;
                    loading.postValue(false);
                    callback.onResult(response.getReviews(), position, 2);
//                    reviewResponse.setValue(response);
//                    loading.setValue(false);
//                    error.setValue(false);
                }, throwable -> {
                    Log.e("LiveData", "with error here" + throwable);
                    error.postValue(true);
                    loading.postValue(false);
                });

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ReviewApiResponse.Review> callback) {

        progressStatus.postValue(true);
        reviewRepository.getReviews(params.startPosition)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("total", response.getTotalCount() + "");
                    Log.d("review size", response.getReviews().size() + "");

                    Log.d("params", params.startPosition + "");

                    progressStatus.postValue(false);
                    callback.onResult(response.getReviews());
                }, throwable -> {
                    Log.e("LiveData", "with error here" + throwable);
                    error.postValue(true);
                    progressStatus.postValue(false);
                });
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getProgressStatus() {
        return progressStatus;
    }
}
