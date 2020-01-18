package com.example.gygapp.data;

import com.example.gygapp.Service.ReviewApiService;
import com.example.gygapp.model.ReviewApiResponse;

import io.reactivex.Observable;

public class ReviewRepository {

    private final ReviewApiService reviewApiService;

    public ReviewRepository(ReviewApiService reviewApiService) {
        this.reviewApiService = reviewApiService;
    }

    public Observable<ReviewApiResponse> getReviews( int offset) {
        return reviewApiService.getReviews(3, offset);
    }
}
