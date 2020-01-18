package com.example.gygapp.adapter.callback;

import com.example.gygapp.model.ReviewApiResponse;

public interface ReviewClickCallback {
    void onclick(ReviewApiResponse.Review review);
}
