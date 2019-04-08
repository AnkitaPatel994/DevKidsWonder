package com.iteration.devkidswonder.network;

import com.iteration.devkidswonder.model.BestSellingList;
import com.iteration.devkidswonder.model.BrandList;
import com.iteration.devkidswonder.model.CategoryList;
import com.iteration.devkidswonder.model.DeleteWishlist;
import com.iteration.devkidswonder.model.InsertCart;
import com.iteration.devkidswonder.model.InsertRecentViewProp;
import com.iteration.devkidswonder.model.InsertWishlist;
import com.iteration.devkidswonder.model.OneProductWish;
import com.iteration.devkidswonder.model.ProductImgList;
import com.iteration.devkidswonder.model.ProductSizeList;
import com.iteration.devkidswonder.model.RecentViewList;
import com.iteration.devkidswonder.model.SliderList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    @POST("json_android/one_pro_wishlist.php")
    Call<OneProductWish> getOneProductWishlistListData(@Field("customer_id") String customer_id,
                                                       @Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/deletewishlist.php")
    Call<DeleteWishlist> getDeleteWishlistData(@Field("customer_id") String customer_id,
                                               @Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/insertwishlist.php")
    Call<InsertWishlist> getInsertWishlistData(@Field("customer_id") String customer_id,
                                               @Field("pro_id") String pro_id,
                                               @Field("wishlist_size_name") String wishlist_size_name);

    @FormUrlEncoded
    @POST("json_android/insertcart.php")
    Call<InsertCart> getInsertCartData(@Field("customer_id") String customer_id,
                                       @Field("pro_id") String pro_id,
                                       @Field("pro_quantity") String pro_quantity,
                                       @Field("pro_price") String pro_price,
                                       @Field("cart_size_name") String cart_size_name);

    @FormUrlEncoded
    @POST("json_android/insert_recent_view_prop.php")
    Call<InsertRecentViewProp> getInsertRecentViewPropData(@Field("recent_pro_id") String recent_pro_id,
                                                     @Field("ip_add") String ip_add);

    @FormUrlEncoded
    @POST("json_android/recentview.php")
    Call<RecentViewList> getRecentViewListData(@Field("ip_add") String ip_add);

}
