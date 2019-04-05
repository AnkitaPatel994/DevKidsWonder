package com.iteration.devkidswonder.network;

import com.iteration.devkidswonder.model.BestSellingList;
import com.iteration.devkidswonder.model.BrandList;
import com.iteration.devkidswonder.model.CategoryList;
import com.iteration.devkidswonder.model.SliderList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetProductDataService {

    @GET("api_android/slider.php")
    Call<SliderList> getSliderData();

    @GET("api_android/category.php")
    Call<CategoryList> getCategoryData();

    @GET("api_android/brand.php")
    Call<BrandList> getBrandData();

    @GET("api_android/bestselling.php")
    Call<BestSellingList> getBestSellingData();

}
