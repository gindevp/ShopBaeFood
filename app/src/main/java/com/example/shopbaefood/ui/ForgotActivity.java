package com.example.shopbaefood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  ForgotActivity extends AppCompatActivity {

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);

        dialog=UtilApp.showProgressBarDialog(this);
        ApiService apiService = UtilApp.retrofitCF().create(ApiService.class);
        EditText text=findViewById(R.id.username);
        Button send= findViewById(R.id.registerForm);
        send.setOnClickListener(view -> {
            dialog.show();
            send.setEnabled(false);
            text.setEnabled(false);

            Call<ApiResponse> apiResponseCall= apiService.forgot(text.getText().toString());
            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    dialog.cancel();
                    if(response.body().getData()!=null) {
                        Intent intent = new Intent(ForgotActivity.this, ConfirmOtpActivity.class);
                        intent.putExtra("username", text.getText().toString());
                        startActivity(intent);

                    }else if (response.body().getMessage()!=null){
                        Handler handler= new Handler();
                        Runnable runnable= () -> {
                            Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.ERROR_TYPE,"Sai tên người dùng","");
                            send.setEnabled(true);
                            text.setEnabled(true);
                        };
                        handler.postDelayed(runnable,1000);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    dialog.cancel();
                    Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.ERROR_TYPE,"Lỗi hện thống phía server","");
                    send.setEnabled(true);
                    text.setEnabled(false);
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