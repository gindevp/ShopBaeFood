package com.example.shopbaefood.model.intercepter;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private static String authToken;

    public TokenInterceptor(String token) {
        this.authToken = token;

    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        // Thêm token vào header của request
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + this.authToken)
                .build();
        return chain.proceed(newRequest);
    }
}
