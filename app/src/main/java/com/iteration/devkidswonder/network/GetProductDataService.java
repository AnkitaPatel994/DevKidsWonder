package com.iteration.devkidswonder.network;

import com.iteration.devkidswonder.model.BestSellingList;
import com.iteration.devkidswonder.model.BrandList;
import com.iteration.devkidswonder.model.CategoryList;
import com.iteration.devkidswonder.model.Customers;

import com.iteration.devkidswonder.model.OneProductWish;
import com.iteration.devkidswonder.model.ProductImgList;
import com.iteration.devkidswonder.model.ProductSizeList;
import com.iteration.devkidswonder.model.SliderList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetProductDataService {

    @GET("json_android/slider.php")
    Call<SliderList> getSliderData();

    @GET("json_android/category.php")
    Call<CategoryList> getCategoryData();

    @GET("json_android/brand.php")
    Call<BrandList> getBrandData();

    @GET("json_android/bestselling.php")
    Call<BestSellingList> getBestSellingData();

    @FormUrlEncoded
    @POST("json_android/product_img_view.php")
    Call<ProductImgList> getProductImgListData(@Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/product_size.php")
    Call<ProductSizeList> getProductSizeListData(@Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/insertcustomers.php")
    Call<Customers> getCustomerListData(@Field("firstname") String firstname,
                                        @Field("lastname") String lastname,
                                        @Field("email") String email,
                                        @Field("contact") String contact,
                                        @Field("password") String password);

    @POST("json_android/one_pro_wishlist.php")
    Call<OneProductWish> getOneProductWishlistListData(@Field("customer_id") String customer_id,
                                                       @Field("pro_id") String pro_id);

}
