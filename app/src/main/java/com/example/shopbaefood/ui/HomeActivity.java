package com.example.shopbaefood.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.util.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MERCHANT = "ROLE_MERCHANT";
    private String token;
    private String role;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        SharedPreferences info = getSharedPreferences("info", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        AccountToken accountToken = gson.fromJson(info.getString("info", ""), AccountToken.class);
        token = accountToken.getToken();
        role = accountToken.getRoles()[0];
        Log.d("role", role);
        if (role.equals(ROLE_ADMIN)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_nav_home, new NavAdminFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_nav_home,new NavUserFragment())
                    .commit();
        }
        Button loggout= findViewById(R.id.logout);
        loggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=getIntent();
                intent.setClass(v.getContext(), LoginActivity.class);
                startActivity(intent);
                SharedPreferences.Editor editor= info.edit();
                editor.remove("info");
            }
        });

//        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return false;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        Notification.showToast(new View(this),"bạn đã đăng nhập, cần đăng xuất để login lại");

    }
}