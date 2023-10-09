package com.example.shopbaefood.ui.public_fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.ProductAdapter;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.model.Product;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerDetailActivity extends AppCompatActivity {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    private RecyclerView rcvProduct;
    private ProgressBar progressBar;
    ImageView merImage;
    ImageView merIcon;
    TextView merName;
    TextView merStatus;
    TextView merAddress;

    ImageView cart_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_detail);

        bottomNavigationView = findViewById(R.id.nav_mer_detail);
        progressBar=findViewById(R.id.progressBar_mer);;
        intent = getIntent();
        Merchant merchant = (Merchant) intent.getSerializableExtra("merchant");

        merImage = findViewById(R.id.merchantImage);
        merIcon = findViewById(R.id.icon_status_mer);
        merName = findViewById(R.id.merchantName);
        merStatus = findViewById(R.id.merchantStatus);
        merAddress = findViewById(R.id.merchantAddress);

        UtilApp.getImagePicasso(merchant.getAvatar(), merImage);
        boolean status = Boolean.parseBoolean(merchant.getStatus());
        merIcon.setImageResource(status ? R.drawable.green_shape : R.drawable.red_shape);
        merName.setText(merchant.getName());
        merAddress.setText(merchant.getAddress());
        merStatus.setText(status ? "Đang hoạt động" : "Đóng cửa rồi");


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                intent.setClass(MerDetailActivity.this, HomeUserActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1:
                        onBackPressed();
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
        rcvProduct = findViewById(R.id.recyclerView_detail);
        getProduct(merchant.getId());
        cart_product= findViewById(R.id.cart_product);
        cart_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(v.getContext(), CartActivity.class);
                intent.putExtra("merchant",merchant);
                startActivity(intent);
            }
        });

    }

    private void getProduct(Long id) {
        ApiService apiService = UtilApp.retrofitCF().create(ApiService.class);
        Call<ApiResponse<List<Product>>> call = apiService.fetProAll(id);
        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                progressBar.setVisibility(View.GONE);
                handleProductList(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Notification.sweetAlertNow(MerDetailActivity.this, SweetAlertDialog.ERROR_TYPE,"lỗi hệ thống","");
            }
        });

//        mock api
//        ApiService apiService=UtilApp.retrofitCFMock().create(ApiService.class);
//        Call<List<Product>> call= apiService.getProAll();
//        call.enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                progressBar.setVisibility(View.GONE);
//                handleProductList(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//
//            }
//        });
    }

    private void handleProductList(List<Product> productList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcvProduct.setLayoutManager(gridLayoutManager);
        ProductAdapter adapter = new ProductAdapter(productList);
        rcvProduct.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}