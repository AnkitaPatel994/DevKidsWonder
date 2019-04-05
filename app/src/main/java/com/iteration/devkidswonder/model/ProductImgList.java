package com.iteration.devkidswonder.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductImgList {

    @SerializedName("ProductImg")
    private ArrayList<ProductImg> ProductImgList;

    public ArrayList<ProductImg> getProductImgArrayList() {
        return ProductImgList;
    }

    public void setProductImgList(ArrayList<ProductImg> productImgList) {
        ProductImgList = productImgList;
    }
}
