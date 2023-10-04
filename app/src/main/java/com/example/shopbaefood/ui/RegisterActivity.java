package com.example.shopbaefood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountRegisterDTO;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button register = findViewById(R.id.registerForm);
        EditText username = findViewById(R.id.usernameForm);
        EditText password = findViewById(R.id.passwordForm);
        EditText email = findViewById(R.id.email);
        EditText name = findViewById(R.id.name);
        EditText phone = findViewById(R.id.phone);
        EditText address = findViewById(R.id.address);
        register.setOnClickListener(v -> {
            register.setEnabled(false);
            username.setEnabled(false);
            password.setEnabled(false);
            email.setEnabled(false);
            name.setEnabled(false);
            phone.setEnabled(false);
            address.setEnabled(false);

            AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO(username.getText().toString(),
                                            password.getText().toString(),
                                            email.getText().toString(),
                                            name.getText().toString(),
                                            phone.getText().toString(),
                                            address.getText().toString());
            ApiService apiService = UtilApp.retrofitCF().create(ApiService.class);
            String role="user";
            Call<ApiResponse> call= apiService.register(accountRegisterDTO, role);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.body().getData()!=null){
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("success","true");
                        startActivity(intent);
                        finish();
                    }else {
                        Notification.sweetAlertNow(v.getContext(),SweetAlertDialog.ERROR_TYPE,"Đăng ký thất bại","");
                        register.setEnabled(true);
                        username.setEnabled(true);
                        password.setEnabled(true);
                        email.setEnabled(true);
                        name.setEnabled(true);
                        phone.setEnabled(true);
                        address.setEnabled(true);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Notification.sweetAlertNow(v.getContext(),SweetAlertDialog.ERROR_TYPE,"Lỗi hệ thống","");
                    register.setEnabled(true);
                    username.setEnabled(true);
                    password.setEnabled(true);
                    email.setEnabled(true);
                    name.setEnabled(true);
                    phone.setEnabled(true);
                    address.setEnabled(true);
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}