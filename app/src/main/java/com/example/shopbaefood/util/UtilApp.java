package com.example.shopbaefood.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilApp {
    public static final String URL="http://192.168.52.218:8080/ShopbaeFoodApi/";
    public static Retrofit retrofitCF(){
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static void getImagePicasso(String imageUrl, ImageView imageView ){
        Picasso.get().load(imageUrl).into(imageView);
    }
    public static void setImagePicasso(String imageUrl, ImageView imageView, int onLoad, int err ){
        // Tải hình ảnh từ URL và đặt vào ImageView
        Picasso.get()
                .load(imageUrl) // merchant.getImageUrl() là URL hình ảnh
                .placeholder(onLoad) // Ảnh mặc định trong thời gian tải
                .error(err) // Ảnh khi tải lỗi
                .into(imageView);
    }

}
