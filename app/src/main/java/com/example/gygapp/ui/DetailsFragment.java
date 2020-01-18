package com.example.gygapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.gygapp.R;
import com.example.gygapp.databinding.ReviewDetailsBinding;
import com.example.gygapp.model.ReviewApiResponse;

public class DetailsFragment extends Fragment {


    public static final String Review_Object = "reviewObject";
    private ReviewDetailsBinding binding;

    private ReviewApiResponse.Review review;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.review_details, container, false);

        setupToolbar();
        binding.setReview(review);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        review = (ReviewApiResponse.Review) getArguments().getSerializable(Review_Object);
    }

    public static DetailsFragment createFragmentInstance(ReviewApiResponse.Review review) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();

        args.putSerializable(Review_Object, review);
        fragment.setArguments(args);

        return fragment;
    }

    private void setupToolbar(){
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setTitle(R.string.details_fragment_title);
        binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));

        binding.toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
    }

}
