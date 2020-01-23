package com.example.gygapp;

import android.os.Bundle;

import com.example.gygapp.model.ReviewApiResponse;
import com.example.gygapp.ui.DetailsFragment;
import com.example.gygapp.ui.MainFragment;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    public void showDetailsFragment(ReviewApiResponse.Review review) {
        DetailsFragment detailsFragment = DetailsFragment.createFragmentInstance(review);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailsFragment, null)
                .addToBackStack(null)
                .commit();

    }
}
