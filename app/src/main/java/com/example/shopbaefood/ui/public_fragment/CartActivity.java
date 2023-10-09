package com.example.shopbaefood.ui.public_fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.CartAdapter;
import com.example.shopbaefood.model.Cart;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.model.User;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.UtilApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    private RecyclerView rcvCart;
    private ProgressBar progressBar;

    private ScrollView scrollView;

    private Button payUp;
    private Button payDown;
    private TextView merchantName;
    private TextView price;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        price=findViewById(R.id.cart_price_total_val);
        merchantName=findViewById(R.id.cart_mer_name_val);
        payUp=findViewById(R.id.payUp);
        payDown=findViewById(R.id.payDown);
        scrollView= findViewById(R.id.checkout);
        bottomNavigationView= findViewById(R.id.nav_cart_detail);
        progressBar= findViewById(R.id.progressBar_cart);
        rcvCart= findViewById(R.id.recyclerView_cart);
        gson= new Gson();
        intent = getIntent();
        Merchant merchant = (Merchant) intent.getSerializableExtra("merchant");
        merchantName.setText(merchant.getName());
        SharedPreferences info= getSharedPreferences("info",MODE_PRIVATE);
        AccountToken user= gson.fromJson(info.getString("info",""),AccountToken.class);
        getCart(user.getUser().getId(),merchant.getId());
        Log.d("logCart","userid:"+user.getUser().getId()+"merId"+merchant.getId());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                intent.setClass(CartActivity.this, HomeUserActivity.class);
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
        payUp.setOnClickListener(v-> {
            scrollView.setVisibility(View.VISIBLE);
            payUp.setVisibility(View.GONE);
            payDown.setVisibility(View.VISIBLE);
        });
        payDown.setOnClickListener(v-> {
            scrollView.setVisibility(View.GONE);
            payUp.setVisibility(View.VISIBLE);
            payDown.setVisibility(View.GONE);
        });
    }

    private void getCart(Long userId, Long merId) {
        ApiService apiService= UtilApp.retrofitAuth(this).create(ApiService.class);
        Call<ApiResponse<List<Cart>>> call= apiService.listCart(userId,merId);
    call.enqueue(new Callback<ApiResponse<List<Cart>>>() {
        @Override
        public void onResponse(Call<ApiResponse<List<Cart>>> call, Response<ApiResponse<List<Cart>>> response) {
            progressBar.setVisibility(View.GONE);
            handleCartList(response.body().getData());
            int total=0;
            for (Cart  cart: response.body().getData()
                 ) {
               total+=cart.getTotalPrice();
            }
            price.setText(total+" đ");
        }

        @Override
        public void onFailure(Call<ApiResponse<List<Cart>>> call, Throwable t) {

        }
    });
    }

    private void handleCartList(List<Cart> data) {
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,1);
        rcvCart.setLayoutManager(gridLayoutManager);
        CartAdapter adapter= new CartAdapter(data);
        rcvCart.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}