package com.example.shopbaefood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);

        ApiService apiService = UtilApp.retrofitCF().create(ApiService.class);
        EditText text=findViewById(R.id.username);
        Button send= findViewById(R.id.registerForm);
        send.setOnClickListener(view -> {
            send.setEnabled(false);
            text.setEnabled(false);
            Notification.showToast(view,"Đang xử lý chờ tí");
            Call<ApiResponse> apiResponseCall= apiService.forgot(text.getText().toString());
            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.body().getData()!=null) {
                        Intent intent = new Intent(ForgotActivity.this, ConfirmOtpActivity.class);
                        intent.putExtra("username", text.getText().toString());
                        startActivity(intent);
                        Notification.showToast(view,"Gửi otp thành công");

                    }else if (response.body().getMessage()!=null){
                        Notification.showToast(view,"Sai tên người dùng");
                        Handler handler= new Handler();
                        Runnable runnable= new Runnable() {
                            @Override
                            public void run() {
                                send.setEnabled(true);
                                text.setEnabled(true);
                            }
                        };
                        handler.postDelayed(runnable,2000);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Notification.showToast(view,"Lỗi hện thống phía server");
                    send.setEnabled(true);
                    text.setEnabled(false);
                }
            });

        });
    }
}