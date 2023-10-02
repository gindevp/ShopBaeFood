package com.example.shopbaefood.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilApp {
    public static final String URL="http://192.168.52.218:8080/ShopbaeFoodApi/";
    public static Retrofit retrofitCF(){
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
