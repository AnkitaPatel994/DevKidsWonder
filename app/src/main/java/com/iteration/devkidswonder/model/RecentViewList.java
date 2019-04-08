package com.iteration.devkidswonder.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecentViewList {

    @SerializedName("recentview")
    private ArrayList<Product> recentviewList;

    public ArrayList<Product> getRecentviewList() {
        return recentviewList;
    }

    public void setRecentviewList(ArrayList<Product> recentviewList) {
        this.recentviewList = recentviewList;
    }

}
