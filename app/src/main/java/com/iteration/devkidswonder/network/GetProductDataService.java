package com.iteration.devkidswonder.network;

import com.iteration.devkidswonder.model.BestSellingList;
import com.iteration.devkidswonder.model.BrandList;
import com.iteration.devkidswonder.model.CategoryList;
import com.iteration.devkidswonder.model.ProductImgList;
import com.iteration.devkidswonder.model.SliderList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetProductDataService {

    @GET("api_android/slider.php")
    Call<SliderList> getSliderData();

    @GET("api_android/category.php")
    Call<CategoryList> getCategoryData();

    @GET("api_android/brand.php")
    Call<BrandList> getBrandData();

    @GET("api_android/bestselling.php")
    Call<BestSellingList> getBestSellingData();

    @FormUrlEncoded
    @POST("api_android/product_img_view.php")
    Call<ProductImgList> getProductImgListData(@Field("pro_id") String pro_id);

}
