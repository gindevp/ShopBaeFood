package com.example.shopbaefood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.LoginResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.admin.HomeAdminActivity;
import com.example.shopbaefood.ui.merchant.HomeMerchantActivity;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Intent intent;
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MERCHANT = "ROLE_MERCHANT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        intent = new Intent();
        intent.setClass(this,HomeAdminActivity.class);
        startActivity(intent);
        loginClick();
        forgotClick();
        registerClick();

    }

    private void registerClick() {
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            intent.setClass(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void forgotClick() {
        TextView forgotView = findViewById(R.id.forgotpass);
        forgotView.setOnClickListener(v -> {
            EditText username = findViewById(R.id.username);
            intent.setClass(this, ForgotActivity.class);
            intent.putExtra("username", username.getText().toString());
            startActivity(intent);
        });
    }

    private void loginClick() {
        ApiService apiService = UtilApp.retrofitCF().create(ApiService.class);
        Button submit = findViewById(R.id.login);
        submit.setOnClickListener(v -> {
            EditText userName = findViewById(R.id.username);
            EditText passWord = findViewById(R.id.password);

            LoginResponse login = new LoginResponse();
            login.setUserName(userName.getText().toString());
            login.setPassword(passWord.getText().toString());

            Call<ApiResponse> call = apiService.login(login);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        ApiResponse apiResponse = response.body();
                        if (apiResponse.getData() != null) {
                            Log.d("login", response.body().getData().toString());
                            Notification.showToast(v, "Đăng nhập thành công");
                            Gson gson= new Gson();
                            SharedPreferences info= getSharedPreferences("info", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=info.edit();
                            editor.putString("info",gson.toJson(response.body().getData()));
                            editor.apply();
                            AccountToken accountToken = gson.fromJson(gson.toJson(response.body().getData()), AccountToken.class);

                            switch (accountToken.getRoles()[0]){
                                case ROLE_ADMIN:
                                    intent.setClass(v.getContext(), HomeAdminActivity.class);
                                    break;
                                case ROLE_MERCHANT:
                                    intent.setClass(v.getContext(), HomeMerchantActivity.class);
                                    break;
                                case ROLE_USER:
                                    intent.setClass(v.getContext(), HomeUserActivity.class);
                                    break;
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("login", response.body().getMessage());
                            Notification.showToast(v, response.body().getMessage());
                        }

                    } else {
                        Log.d("login", "sai");
                        Notification.showToast(v, "Đăng nhập không thành công");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.d("t", t.getMessage().toString());
                    Log.d("login", "fail");
                    Notification.showToast(v, "Lỗi hệ thống bên server");
                }
            });
        });
    }
}