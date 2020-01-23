package com.example.gygapp.data;

import com.example.gygapp.Service.ReviewApiService;
import com.example.gygapp.model.ReviewApiResponse;

import io.reactivex.Observable;

public class ReviewRepository {

    private final ReviewApiService reviewApiService;
    public final int LIMIT_OF_FETCHED_REVIEWS = 3;

    public ReviewRepository(ReviewApiService reviewApiService) {
        this.reviewApiService = reviewApiService;
    }

    public Observable<ReviewApiResponse> getReviews( int offset) {
        return reviewApiService.getReviews(LIMIT_OF_FETCHED_REVIEWS, offset);
    }
}
