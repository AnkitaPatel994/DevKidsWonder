package com.iterationtechnology.devkidswonder.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RatingList {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("rating")
    private ArrayList<Rating> ratingList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(ArrayList<Rating> ratingList) {
        this.ratingList = ratingList;
    }
}
