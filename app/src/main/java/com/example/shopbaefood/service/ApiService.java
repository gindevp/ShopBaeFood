package com.example.shopbaefood.service;

import com.example.shopbaefood.model.Cart;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.model.Order;
import com.example.shopbaefood.model.OrderDetail;
import com.example.shopbaefood.model.Product;
import com.example.shopbaefood.model.dto.AccountRegisterDTO;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.LoginResponse;
import com.example.shopbaefood.model.dto.ProductForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //api public
    @POST("login")
    Call<ApiResponse> login(@Body LoginResponse response);

    @POST("forgotpass")
    Call<ApiResponse> forgot(@Query("username") String username);

    @POST("forgotpass/confirm")
    Call<ApiResponse> confirmOtp(@Query("otp") String otp, @Query("pass") String pass, @Query("username") String username);

    @POST("register")
    Call<ApiResponse> register(@Body AccountRegisterDTO accountRegisterDTO, @Query("role") String role);

    @GET("merchantp/all")
    Call<ApiResponse<List<Merchant>>> fetMerAll();

    @GET("merchantp/detail")
    Call<ApiResponse<List<Product>>> fetProAll(@Query("id") Long id);
    @GET("a/detail/order/{orderId}")
    Call<ApiResponse<List<OrderDetail>>> orderDetail(@Path("orderId") Long orderId);


    //api user
    @POST("cart/product/{productId}/user/{userId}")
    Call<ApiResponse> addToCart(@Path("productId") Long productId, @Path("userId") Long userId);

    @GET("cart/user/{userId}/merchant/{merId}")
    Call<ApiResponse<List<Cart>>> listCart(@Path("userId") Long userId, @Path("merId") Long merId);

    @GET("cart/product/{productId}/user/{userId}")
    Call<ApiResponse> downCart(@Path("productId") Long productId,@Path("userId") Long userId);

    @GET("cart/product/{productId}")
    Call<ApiResponse> remove(@Path("productId") Long productId);

    @POST("cart/order/user/{userId}/merchant/{merId}")
    Call<ApiResponse> odering(@Path("userId") Long userId,@Path("merId") Long merId,@Query("note") String note,@Query("address") String address,@Query("sum") double sum);
    @GET("cart/order/history/user/{userId}")
    Call<ApiResponse<List<Order>>> orderHistory(@Path("userId") Long userId);



    //api merchant
    @GET("merchant/order")
    Call<ApiResponse<List<Order>>> orderPending(@Query("merchantId") Long merchantId);
    @GET("merchant/order/received")
    Call<ApiResponse<List<Order>>> orderReceived(@Query("merchantId") Long merchantId);
    @GET("merchant/order/history")
    Call<ApiResponse<List<Order>>> orderHistoryMer(@Query("merchantId") Long merchantId);
    @POST("merchant/product/save")
    Call<ApiResponse> saveProduct(@Body ProductForm productForm, @Query("merchantId") Long merchantId);
    @POST("merchant/product/edit")
    Call<ApiResponse> editProduct(@Body ProductForm productForm, @Query("merchantId") Long merchantId);
    @DELETE("merchant/product/delete/{merchantId}")
    Call<ApiResponse> deleteProduct(@Path("merchantId") Long merchantId);
    @GET("merchant/product")
    Call<ApiResponse<List<Product>>> listProduct(@Query("merchantId") Long merchantId);
    // mock api
    @GET("merchant")
    Call<List<Merchant>> getMerAll();

    @GET("product")
    Call<List<Product>> getProAll();
}
