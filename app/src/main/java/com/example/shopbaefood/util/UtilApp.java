package com.example.shopbaefood.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.intercepter.RetrofitClient;
import com.example.shopbaefood.service.ApiService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilApp {
    public static final String URL = "http://192.168.52.218:8080/ShopbaeFoodApi/";
    public static final String URLMock = "https://651e990a44a3a8aa4768a52a.mockapi.io/";

    public static Retrofit retrofitCF() {
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Retrofit retrofitCFMock() {
        return new Retrofit.Builder().baseUrl(URLMock).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Retrofit retrofitAuth(Context context) {
        return RetrofitClient.getClient(URL, getAuthToken(context));
    }

    private static String getAuthToken(Context context) {
        Gson gson = new Gson();
        SharedPreferences info = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        String json = info.getString("info", "");

        if (!json.isEmpty()) {
            AccountToken accountToken = gson.fromJson(json, AccountToken.class);
            Log.d("token",accountToken.getToken());
            return accountToken.getToken();
        }
        return null;
    }

    public static void getImagePicasso(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    public static void setImagePicasso(String imageUrl, ImageView imageView, int onLoad, int err) {
        // Tải hình ảnh từ URL và đặt vào ImageView
        Picasso.get()
                .load(imageUrl) // merchant.getImageUrl() là URL hình ảnh
                .placeholder(onLoad) // Ảnh mặc định trong thời gian tải
                .error(err) // Ảnh khi tải lỗi
                .into(imageView);
    }

    public static boolean isTime(String opentime, String closetime) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter fomat = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime currentTime = LocalTime.now();
                if (opentime != null && closetime != null) {
                    LocalTime startTimeDate = LocalTime.parse(opentime, fomat);
                    LocalTime endTimeDate = LocalTime.parse(closetime, fomat);

                    return currentTime.isAfter(startTimeDate) && currentTime.isBefore(endTimeDate);
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static void confirmationDialog(Context context, String title, String content,
                                              String confirmText, String cancelText,
                                              final OnConfirmationListener listener) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirmText)
                .setCancelText(cancelText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if (listener != null) {
                            listener.onConfirm();
                        }
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                        sDialog.cancel();
                    }
                })
                .show();
    }
    public interface OnConfirmationListener {
        void onConfirm();

        void onCancel();
    }
}
