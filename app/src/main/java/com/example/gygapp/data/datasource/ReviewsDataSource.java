package com.example.gygapp.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.gygapp.data.ReviewRepository;
import com.example.gygapp.model.ReviewApiResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ReviewsDataSource extends PageKeyedDataSource<Integer, ReviewApiResponse.Review> {

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
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ReviewApiResponse.Review> callback) {

        loading.postValue(true);
        reviewRepository.getReviews( offset)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("LiveData", response.getTotalCount() + "");

                    offset++;
                    loading.postValue(false);
                    callback.onResult(response.getReviews(), null, offset);
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
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ReviewApiResponse.Review> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ReviewApiResponse.Review> callback) {

        progressStatus.postValue(true);
        reviewRepository.getReviews(params.key)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("LiveData", response.getTotalCount() + "");

                    offset = offset + 5;
                    progressStatus.postValue(false);
                    callback.onResult(response.getReviews(), params.key + 5);
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
