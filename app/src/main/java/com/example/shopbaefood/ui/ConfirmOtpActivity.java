package com.example.shopbaefood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOtpActivity extends AppCompatActivity {
    Button send;

    Intent intent;

    String username;


    ApiService apiService = UtilApp.retrofitCF().create(ApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_otp);

        confirmOtp();
        countOtp();
        ImageView img = findViewById(R.id.imageViewLogo5);
        img.setOnClickListener(v -> {
            intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
        });
    }

    private void confirmOtp() {
        Notification.sweetAlertNow(this, SweetAlertDialog.SUCCESS_TYPE,"Gửi otp thành công","");
        intent= getIntent();
        username= intent.getStringExtra("username");
        EditText pass= findViewById(R.id.pass);
        EditText editOtp=findViewById(R.id.username);
        send = findViewById(R.id.registerForm);
        send.setOnClickListener(v -> {
            Call<ApiResponse> apiResponseCall=apiService.confirmOtp(editOtp.getText().toString(),pass.getText().toString(),username);
            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.body().getData()!=null){
                        intent= new Intent();
                        intent.setClass(v.getContext(),LoginActivity.class);
                        intent.putExtra("confirmSuccess","true");
                        startActivity(intent);
                        finish();
                    }else {
                        Notification.sweetAlertNow(v.getContext(),SweetAlertDialog.ERROR_TYPE,"Không thành công sai otp ròi","");
                    }

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Notification.sweetAlertNow(v.getContext(),SweetAlertDialog.ERROR_TYPE,"Lỗi hệ thống phía server","");
                }
            });
        });
    }

    private void countOtp() {
        Resources resources= getResources();
        Call<ApiResponse> apiServiceCall= apiService.forgot(username);
        TextView count= findViewById(R.id.count);
        count.setTextColor(resources.getColor(R.color.blue));

        count.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Hiển thị số đếm ngược ban đầu (15)
                apiServiceCall.clone().enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        Notification.sweetAlertNow(v.getContext(),SweetAlertDialog.SUCCESS_TYPE,"Đã gửi lại otp","");
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Notification.sweetAlertNow(v.getContext(),SweetAlertDialog.ERROR_TYPE,"Lỗi hệ thống phía server","");
                    }
                });
                Notification.sweetAlertNow(v.getContext(),SweetAlertDialog.WARNING_TYPE,"Đang xử lý chờ 15 giây ròi ấn","");
                count.setText("Gửi lại(15)");
                count.setEnabled(false);
                count.setTextColor(resources.getColor(R.color.blueClicked));
                // Tạo một đối tượng Handler
                Handler handler = new Handler();
                // Tạo một Runnable để đếm ngược trong 15 giây
                Runnable runnable = new Runnable() {
                    int countdown = 15;
                    @Override
                    public void run() {
                        countdown--;
                        count.setText("Gửi lại("+countdown+")");
                        if (countdown == 0) {
                            // Khi số đếm ngược đạt 0, hiển thị lại nút và ẩn TextView
                            count.setEnabled(true);
                            count.setTextColor(resources.getColor(R.color.blue));
                            count.setText("Gửi lại OTP");
                        } else {
                            // Nếu số đếm ngược chưa đạt 0, đặt lịch chạy lại sau 1 giây
                            handler.postDelayed(this, 1000); // 1000 milliseconds = 1 giây
                        }
                    }
                };
                handler.postDelayed(runnable, 0);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Notification.sweetAlertNow(this,SweetAlertDialog.NORMAL_TYPE,"Không thể quay lại","",1000);
    }
}