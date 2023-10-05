package com.example.shopbaefood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.ui.LoginActivity;
import com.example.shopbaefood.ui.admin.HomeAdminActivity;
import com.example.shopbaefood.ui.merchant.HomeMerchantActivity;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.Role;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 2000;
    Gson gson;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();
        intent = new Intent();

        Log.d("Activity","mainHello");
        screenHello();
    }

    private void screenHello() {
        //Màn hình chào

        // Sử dụng Handler để tạm dừng và sau đó chuyển sang màn hình chính
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogin();

            }
        }, DELAY_TIME);
    }

    private void isLogin() {
        SharedPreferences info = getSharedPreferences("info", Context.MODE_PRIVATE);
        String json = info.getString("info", "");

        if (!json.isEmpty()) {
            AccountToken accountToken = gson.fromJson(json, AccountToken.class);
            switch (accountToken.getRoles()[0]) {
                case Role.ROLE_ADMIN:
                    Log.d("isLogin","admin");
                    intent.setClass(this, HomeAdminActivity.class);
                    break;
                case Role.ROLE_MERCHANT:
                    Log.d("isLogin","merchant");
                    intent.setClass(this, HomeMerchantActivity.class);
                    break;
                case Role.ROLE_USER:
                    Log.d("isLogin","user");
                    intent.setClass(this, HomeUserActivity.class);
                    break;
            }
            startActivity(intent);
            finish();
        }else {
            // Intent để chuyển sang màn hình chính (MainActivity)
            intent.setClass(MainActivity.this, LoginActivity.class);
            intent.setClass(MainActivity.this, HomeUserActivity.class);
            startActivity(intent);
            finish(); // Kết thúc WelcomeActivity để ngăn quay lại nó từ màn hình chính
        }
    }
}