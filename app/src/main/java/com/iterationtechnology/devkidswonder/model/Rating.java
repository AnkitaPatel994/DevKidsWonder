package com.iterationtechnology.devkidswonder.model;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("rating_id")
    private String rating_id;
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_last_name")
    private String customer_last_name;
    @SerializedName("rating")
    private String rating;
    @SerializedName("review")
    private String review;

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
