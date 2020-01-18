package com.example.gygapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gygapp.MainActivity;
import com.example.gygapp.R;
import com.example.gygapp.adapter.ReviewAdapter;
import com.example.gygapp.adapter.callback.ReviewClickCallback;
import com.example.gygapp.databinding.MainFragmentBinding;
import com.example.gygapp.model.ReviewApiResponse;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainFragment extends DaggerFragment implements ReviewClickCallback {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainFragmentBinding binding;
    private ReviewAdapter reviewAdapter;
    private MainViewModel mainViewModel;

    private LinearLayoutManager layoutManager;

    private static int offset = 0, limit = 20;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);

        reviewAdapter = new ReviewAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
        binding.rvReview.setLayoutManager(layoutManager);
        binding.rvReview.setAdapter(reviewAdapter);
        binding.setIsLoading(true);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

//        mainViewModel.fetchReviews(limit,offset);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setUpToolbar();

        observeViewModel();
    }

    private void observeViewModel() {

        mainViewModel.getReviewResponse().observe(this, reviewAdapter::submitList);

        mainViewModel.getLoading().observe(this, isloading -> {
            if(isloading != null){
                if(isloading){
                    binding.shimmerViewContainer.startShimmerAnimation();
                }

                else{
                    binding.shimmerViewContainer.stopShimmerAnimation();
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                }
            }
        });

        mainViewModel.getProgressStatus().observe(this, isloading -> {
            if(isloading != null){
                if(isloading){
                    binding.progressView.setVisibility(View.VISIBLE);
                }

                else
                    binding.progressView.setVisibility(View.GONE);
            }
        });

        mainViewModel.getError().observe(this, isError -> {
            if(isError != null)
                if(isError) {
//                    binding.errBut.setVisibility(View.VISIBLE);
                    int textViewId = com.google.android.material.R.id.snackbar_text;
                    Snackbar snackbar = Snackbar.make(getView(), "Please ensure your device has an active internet connection", Snackbar.LENGTH_LONG);
                    TextView textView = snackbar.getView().findViewById(textViewId);

//                    textView.setTextColor(Color.parseColor("#FFC107"));
                    snackbar.show();
                }
//                else
//                    binding.errBut.setVisibility(View.GONE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }


    @Override
    public void onclick(ReviewApiResponse.Review review) {
        ((MainActivity) getActivity()).showDetailsFragment(review);
    }

    public void setUpToolbar(){

        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(R.string.main_toolbar_title);
//        binding.toolBar.setTitleTextColor(Color.parseColor("#ffffff"));
    }
}
