package com.example.shopbaefood.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilApp {
    public static final String URL="http://192.168.52.218:8080/ShopbaeFoodApi/";
    public static final String URLMock="https://651e990a44a3a8aa4768a52a.mockapi.io/";
    public static Retrofit retrofitCF(){
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static Retrofit retrofitCFMock(){
        return new Retrofit.Builder().baseUrl(URLMock).addConverterFactory(GsonConverterFactory.create()).build();
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

    public static boolean isTime(String opentime,String closetime){
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter fomat = DateTimeFormatter.ofPattern("HH:mm");

            LocalTime currentTime = LocalTime.now();
            if(opentime!=null&& closetime!=null) {
                LocalTime startTimeDate = LocalTime.parse(opentime,fomat);
                LocalTime endTimeDate = LocalTime.parse(closetime,fomat);

                return currentTime.isAfter(startTimeDate) && currentTime.isBefore(endTimeDate);
            }else {
                return false;
            }}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
