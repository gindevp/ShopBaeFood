package com.example.shopbaefood.ui.publicc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.ProductAdapter;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.model.Product;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.admin.HomeAdminActivity;
import com.example.shopbaefood.ui.merchant.HomeMerchantActivity;
import com.example.shopbaefood.ui.user.CartActivity;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.Role;
import com.example.shopbaefood.util.UtilApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

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
    ImageView merImage, merIcon, cart_product;
    TextView merName, merStatus, merAddress;
    AccountToken accountToken;
    Gson gson;
    SharedPreferences info;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mer_detail);

        progressBar = findViewById(R.id.progressBar_mer);
        intent = getIntent();
        Merchant merchant = (Merchant) intent.getSerializableExtra("merchant");

        dialog=UtilApp.showProgressBarDialog(this);
        gson = new Gson();
        info = getSharedPreferences("info", Context.MODE_PRIVATE);
        accountToken = gson.fromJson(info.getString("info", ""), AccountToken.class);

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

        if (accountToken.getRoles()[0].equals(Role.ROLE_USER)) {
            bottomNavigationView = findViewById(R.id.nav_mer_detail_user);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                intent.setClass(MerDetailActivity.this, HomeUserActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1_c:
                        onBackPressed();
                        return true;
                    case R.id.tab2_c:
                        intent.putExtra("pageToDisplay", 1); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab3_c:
                        intent.putExtra("pageToDisplay", 2); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    // Thêm các case cho các item khác nếu cần
                }
                return false;
            });
        } else if (accountToken.getRoles()[0].equals(Role.ROLE_ADMIN)) {
            bottomNavigationView = findViewById(R.id.nav_mer_detail_admin);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                intent.setClass(MerDetailActivity.this, HomeAdminActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1_m:
                        onBackPressed();
                        return true;
                    case R.id.tab2_m:
                        intent.putExtra("pageToDisplay", 1); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab3_m:
                        intent.putExtra("pageToDisplay", 2); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab4_m:
                        intent.putExtra("pageToDisplay", 3); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab5_m:
                        intent.putExtra("pageToDisplay", 4); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    // Thêm các case cho các item khác nếu cần
                }
                return false;
            });
        } else {
            bottomNavigationView = findViewById(R.id.nav_mer_detail_merchant);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                intent.setClass(MerDetailActivity.this, HomeMerchantActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1_s:
                        onBackPressed();
                        return true;
                    case R.id.tab2_s:
                        intent.putExtra("pageToDisplay", 1); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab3_s:
                        intent.putExtra("pageToDisplay", 2); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab4_s:
                        intent.putExtra("pageToDisplay", 3); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    // Thêm các case cho các item khác nếu cần
                }
                return false;
            });
        }
        rcvProduct = findViewById(R.id.recyclerView_detail);
        getProduct(merchant.getId());
        cart_product = findViewById(R.id.cart_product);
        if (accountToken.getUser() != null) {
            if(accountToken.getRoles()[0].equals(Role.ROLE_ADMIN)){
                cart_product.setVisibility(View.GONE);
            }
            cart_product.setOnClickListener(v -> {
                intent.setClass(v.getContext(), CartActivity.class);
                intent.putExtra("merchant", merchant);
                startActivity(intent);
            });
        } else {
            cart_product.setVisibility(View.GONE);
        }


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
                Notification.sweetAlert(MerDetailActivity.this, SweetAlertDialog.ERROR_TYPE, "lỗi hệ thống", "");
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
        ProductAdapter adapter = new ProductAdapter(productList, false, dialog);
        rcvProduct.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}