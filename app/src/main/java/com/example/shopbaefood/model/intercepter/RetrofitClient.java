package com.example.shopbaefood.model.intercepter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final int MAX_RETRY_COUNT = 3;
    private static final long RETRY_DELAY = 3000; // Thời gian chờ trước khi thử lại (3 giây trong ví dụ này)
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, String authToken) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(authToken)) // Sử dụng interceptor để thêm token
                .connectTimeout(30, TimeUnit.SECONDS) // Thời gian chờ kết nối
                .readTimeout(30, TimeUnit.SECONDS) // Thời gian chờ để đọc dữ liệu
                .writeTimeout(30, TimeUnit.SECONDS) // Thời gian chờ để ghi dữ liệu
                .addInterceptor(new RetryInterceptor(MAX_RETRY_COUNT,RETRY_DELAY))
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client) // Sử dụng OkHttpClient đã tạo
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}