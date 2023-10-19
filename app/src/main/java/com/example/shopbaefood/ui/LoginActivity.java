package com.example.shopbaefood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.ContactClient;
import com.example.shopbaefood.model.dto.LoginResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.admin.HomeAdminActivity;
import com.example.shopbaefood.ui.merchant.HomeMerchantActivity;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.ContactClientDataSource;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.Role;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Intent intent;
    Gson gson;
    ContactClientDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Log.d("Activity", "login");
        intent = getIntent();
        gson = new Gson();
        loginClick();
        forgotClick();
        registerClick();
        if (intent.hasExtra("success")) {
            Notification.sweetAlertNow(this, SweetAlertDialog.WARNING_TYPE, "Đăng ký thành công", "", 1000);
        }
        // Lấy đối tượng SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);

// Lấy danh sách tất cả các key và giá trị trong SharedPreferences
        Map<String, ?> allEntries = sharedPreferences.getAll();

// Duyệt qua danh sách key và giá trị
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Đoạn code xử lý với key và value tại đây
            Log.d("SharedPreferences", "Key: " + key + ", Value: " + value.toString());
        }
    }


    private void registerClick() {
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            intent.setClass(this, RegisterActivity.class);
            intent.putExtra("role","user");
            startActivity(intent);
            finish();
        });
    }

    private void forgotClick() {
        TextView forgotView = findViewById(R.id.forgotpass);
        forgotView.setOnClickListener(v -> {
            EditText username = findViewById(R.id.username);
            intent.setClass(this, ForgotActivity.class);
            intent.putExtra("username", username.getText().toString());
            startActivity(intent);
            finish();
        });
    }

    private void loginClick() {
        ApiService apiService = UtilApp.retrofitCF().create(ApiService.class);
        Button submit = findViewById(R.id.login);
        submit.setOnClickListener(v -> {
            EditText userName = findViewById(R.id.username);
            EditText passWord = findViewById(R.id.password);
            TextView valid = findViewById(R.id.login_valid_Error);
            if (!userName.getText().toString().isEmpty() && !passWord.getText().toString().isEmpty()) {
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

                                SharedPreferences info = getSharedPreferences("info", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = info.edit();
                                editor.putString("info", gson.toJson(response.body().getData()));
                                editor.apply();
                                AccountToken accountToken = gson.fromJson(gson.toJson(response.body().getData()), AccountToken.class);
                                try {
                                    addContactClientToSqlite(accountToken);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                intent.putExtra("logSuccess", "true");
                                switch (accountToken.getRoles()[0]) {
                                    case Role.ROLE_ADMIN:
                                        intent.setClass(v.getContext(), HomeAdminActivity.class);
                                        break;
                                    case Role.ROLE_MERCHANT:
                                        intent.setClass(v.getContext(), HomeMerchantActivity.class);
                                        break;
                                    case Role.ROLE_USER:
                                        intent.setClass(v.getContext(), HomeUserActivity.class);
                                        break;
                                }
                                startActivity(intent);
                                finish();
                            } else {
                                Log.d("login", response.body().getMessage());
                                Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.WARNING_TYPE, response.body().getMessage(), "");
                            }

                        } else {
                            Log.d("login", "sai");
                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Đăng nhập không thành công", "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Log.d("t", t.getMessage().toString());
                        Log.d("login", "fail");
                        Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Lỗi hệ thống bên server", "");
                    }
                });
            } else {
                valid.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Notification.sweetAlert(this, SweetAlertDialog.ERROR_TYPE, "NO", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("logggg", "onResume");
        intent = getIntent();
        Log.d("logggg", ((Boolean) intent.hasExtra("confirmSuccess")).toString());
        if (intent.hasExtra("confirmSuccess")) {
            Log.d("alert", "onResume");
            Notification.sweetAlertNow(this, SweetAlertDialog.SUCCESS_TYPE, "Đổi thành công", "", 1000);
        }
    }

    private void addContactClientToSqlite(AccountToken accountToken) throws IOException {
        dataSource = new ContactClientDataSource(this);
        dataSource.open();
        if (accountToken.getUser() != null) {
            UtilApp.loadImageAndConvertToByteArray(this, accountToken.getUser().getAvatar(), byteArray -> {
                if(byteArray!=null){
                    Long idClient=dataSource.checkAndAddOrUpdateContactClient(new ContactClient(
                            accountToken.getUser().getName(),
                            accountToken.getUsername(),
                            accountToken.getUser().getPhone(),
                            accountToken.getUser().getAddress(),
                            accountToken.getEmail(),byteArray
                    ));
                    SharedPreferences info = getSharedPreferences("info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = info.edit();
                    editor.putString("idClient",idClient.toString());
                    editor.apply();
                }
            });

        } else {
            UtilApp.loadImageAndConvertToByteArray(this, accountToken.getMerchant().getAvatar(), byteArray -> {
                if(byteArray!=null){
                    Long idClient=dataSource.checkAndAddOrUpdateContactClient(new ContactClient(
                                    accountToken.getMerchant().getName(),
                                    accountToken.getUsername(),
                                    accountToken.getMerchant().getPhone(),
                                    accountToken.getMerchant().getAddress(),
                                    accountToken.getEmail(),byteArray
                            )
                    );
                    SharedPreferences info = getSharedPreferences("info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = info.edit();
                    editor.putString("idClient",idClient.toString());
                    editor.apply();
                }
            });

        }
        List<ContactClient> contactClient = dataSource.getAllContacts();
        for (ContactClient x: contactClient
             ) {
            Log.d("contactlient:",x.toString());
        }
        dataSource.close();
    }
}