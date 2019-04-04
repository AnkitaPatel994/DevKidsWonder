package com.iteration.devkidswonder.network;

import com.iteration.devkidswonder.model.CategoryList;
import com.iteration.devkidswonder.model.SliderList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetProductDataService {

    @GET("api_android/slider.php")
    Call<SliderList> getSliderData();

    @GET("api_android/category.php")
    Call<CategoryList> getCategoryData();
}
