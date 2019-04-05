package com.iteration.devkidswonder.model;

import com.google.gson.annotations.SerializedName;

public class ProductSize {

    @SerializedName("s_id")
    private String s_id;
    @SerializedName("s_pro_id")
    private String s_pro_id;
    @SerializedName("size")
    private String size;
    @SerializedName("size_qty")
    private String size_qty;
    @SerializedName("size_price")
    private String size_price;
    @SerializedName("size_discount")
    private String size_discount;
    @SerializedName("size_chart_img")
    private String size_chart_img;

    public ProductSize(String s_id, String s_pro_id, String size, String size_qty, String size_price, String size_discount, String size_chart_img) {
        this.s_id = s_id;
        this.s_pro_id = s_pro_id;
        this.size = size;
        this.size_qty = size_qty;
        this.size_price = size_price;
        this.size_discount = size_discount;
        this.size_chart_img = size_chart_img;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_pro_id() {
        return s_pro_id;
    }

    public void setS_pro_id(String s_pro_id) {
        this.s_pro_id = s_pro_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize_qty() {
        return size_qty;
    }

    public void setSize_qty(String size_qty) {
        this.size_qty = size_qty;
    }

    public String getSize_price() {
        return size_price;
    }

    public void setSize_price(String size_price) {
        this.size_price = size_price;
    }

    public String getSize_discount() {
        return size_discount;
    }

    public void setSize_discount(String size_discount) {
        this.size_discount = size_discount;
    }

    public String getSize_chart_img() {
        return size_chart_img;
    }

    public void setSize_chart_img(String size_chart_img) {
        this.size_chart_img = size_chart_img;
    }
}
