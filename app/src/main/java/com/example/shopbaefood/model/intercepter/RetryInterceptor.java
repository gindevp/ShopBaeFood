package com.example.shopbaefood.model.intercepter;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
    private static final String TAG = "RetryInterceptor";
    private final int maxRetryCount;
    private final long retryDelay;

    public RetryInterceptor(int maxRetryCount, long retryDelay) {
        this.maxRetryCount = maxRetryCount;
        this.retryDelay = retryDelay;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        int retryCount = 0;

        while (retryCount < maxRetryCount) {
            try {
                response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
            } catch (SocketTimeoutException e) {
                Log.e(TAG, "Timeout occurred, retrying...");
                retryCount++;
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    response.close();
                }
                response.close();
            }
        }

        return response;
    }
}
