package com.example.shopbaefood.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.intercepter.RetrofitClient;
import com.example.shopbaefood.service.ApiService;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
                .setConfirmClickListener(sDialog -> {
                    if (listener != null) {
                        listener.onConfirm();
                    }
                    sDialog.dismissWithAnimation();
                })
                .setCancelClickListener(sDialog -> {
                    if (listener != null) {
                        listener.onCancel();
                    }
                    sDialog.cancel();
                })
                .show();
    }
    public interface OnConfirmationListener {
        void onConfirm();

        void onCancel();
    }

    public static void uploadImageToFirebaseStorage(Uri imageUri, OnImageUploadListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CHINESE);
        Date now = new Date();
        String filename = formatter.format(now);

        StorageReference storageRef = storage.getReference().child("images/" + filename);

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl()
                            .addOnSuccessListener(downloadUri -> {
                                String imageUrl = downloadUri.toString();
                                listener.onSuccess(imageUrl);
                            })
                            .addOnFailureListener(e -> {
                                listener.onFailure("Failed to get image URL.");
                            });
                })
                .addOnFailureListener(e -> {
                    listener.onFailure("Failed to upload image.");
                });
    }

    public interface OnImageUploadListener {
        void onSuccess(String imageUrl);

        void onFailure(String error);
    }

    public static void closeKeyBoard(View v){
        try {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }catch (Exception e){
        }
    }
}
