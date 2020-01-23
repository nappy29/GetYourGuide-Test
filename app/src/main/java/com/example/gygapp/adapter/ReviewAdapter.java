package com.example.gygapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gygapp.R;
import com.example.gygapp.adapter.callback.ReviewClickCallback;
import com.example.gygapp.databinding.ReviewRowBinding;
import com.example.gygapp.model.ReviewApiResponse;

import java.util.List;

public class ReviewAdapter extends PagedListAdapter<ReviewApiResponse.Review, ReviewAdapter.ReviewViewHolder> {

    List<? extends ReviewApiResponse.Review> reviewList;
    private final ReviewClickCallback reviewClickCallback;

    public ReviewAdapter(@Nullable ReviewClickCallback reviewClickCallback) {
        super(DIFF_CALLBACK);
        this.reviewClickCallback = reviewClickCallback;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ReviewRowBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.review_row,
                        viewGroup, false);

        binding.setCallback(reviewClickCallback);

        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder reviewViewHolder, int position) {

        reviewViewHolder.bind(getItem(position));

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        final ReviewRowBinding binding;

        public ReviewViewHolder(ReviewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ReviewApiResponse.Review review){
            binding.setReview(review);
            binding.executePendingBindings();

        }
    }

    public static DiffUtil.ItemCallback<ReviewApiResponse.Review> DIFF_CALLBACK = new DiffUtil.ItemCallback<ReviewApiResponse.Review>() {

        @Override
        public boolean areItemsTheSame(@NonNull ReviewApiResponse.Review oldItem, @NonNull ReviewApiResponse.Review newItem) {

            return oldItem.getId()
                    == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReviewApiResponse.Review oldItem, @NonNull ReviewApiResponse.Review newItem) {
            return oldItem.equals(newItem);
        }
    };
}
