package com.example.gygapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ReviewApiResponse {

    @SerializedName("totalCount")
    @Expose
    private int totalCount;

    @SerializedName("averageRating")
    @Expose
    private float averageRating;

    @SerializedName("reviews")
    @Expose
    private List<Review> reviews;

    public int getTotalCount() {
        return totalCount;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public class Review implements Serializable {


        @SerializedName("id")
        @Expose
        private int id;

       @SerializedName("author")
       @Expose
       private Author author;

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("enjoyment")
        @Expose
        private String enjoyment;

        @SerializedName("isAnonymous")
        @Expose
        private boolean isAnonymous;

        @SerializedName("rating")
        @Expose
        private int rating;

        @SerializedName("created")
        @Expose
        private String date_created;

        @SerializedName("language")
        @Expose
        private String language;

        @SerializedName("travelerType")
        @Expose
        private String travelerType;

        public int getId() {
            return id;
        }

        public Author getAuthor() {
            return author;
        }

        public String getMessage() {
            return message;
        }

        public String getEnjoyment() {
            return enjoyment;
        }

        public boolean isAnonymous() {
            return isAnonymous;
        }

        public int getRating() {
            return rating;
        }

        public String getDate_created() {
            return date_created;
        }

        public String getLanguage() {
            return language;
        }

        public String getTravelerType() {
            return travelerType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Review)) return false;
            Review review = (Review) o;
            return getId() == review.getId() &&
                    isAnonymous() == review.isAnonymous() &&
                    getRating() == review.getRating() &&
                    Objects.equals(getAuthor(), review.getAuthor()) &&
                    Objects.equals(getMessage(), review.getMessage()) &&
                    Objects.equals(getEnjoyment(), review.getEnjoyment()) &&
                    Objects.equals(getDate_created(), review.getDate_created()) &&
                    Objects.equals(getLanguage(), review.getLanguage()) &&
                    Objects.equals(getTravelerType(), review.getTravelerType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getAuthor(), getMessage(), getEnjoyment(), isAnonymous(), getRating(), getDate_created(), getLanguage(), getTravelerType());
        }
    }

    public class Author{

        @SerializedName("fullName")
        @Expose
        private String fullName;

        @SerializedName("country")
        @Expose
        private String country;

        public String getFullName() {
            return fullName;
        }

        public String getCountry() {
            return country;
        }
    }
}
