package com.example.gygapp.Service;

import com.example.gygapp.model.ReviewApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReviewApiService {

    String base_url = "https://travelers-api.getyourguide.com";
    @GET("/activities/23776/reviews")
    Observable<ReviewApiResponse> getReviews(@Query("limit") int limit,
                                             @Query("offset") int offset);
}
