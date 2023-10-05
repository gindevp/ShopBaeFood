package com.example.shopbaefood.ui.public_fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MerDetailActivity extends AppCompatActivity {
    Intent intent;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_detail);

        bottomNavigationView= findViewById(R.id.nav_mer_detail);
        intent= new Intent();
        Merchant merchant= (Merchant) intent.getSerializableExtra("merchant");
        // TODO: viết mã merchant

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                intent.setClass(MerDetailActivity.this, HomeUserActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1:
                        intent.putExtra("pageToDisplay", 0); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab2:
                        intent.putExtra("pageToDisplay", 1); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab3:
                        intent.putExtra("pageToDisplay", 2); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    // Thêm các case cho các item khác nếu cần
                }
                return false;
            }
        });

    }
}