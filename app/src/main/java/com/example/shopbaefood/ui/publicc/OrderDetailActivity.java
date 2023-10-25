package com.example.shopbaefood.ui.publicc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.OrderDetailAdapter;
import com.example.shopbaefood.model.Order;
import com.example.shopbaefood.model.OrderDetail;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.merchant.HomeMerchantActivity;
import com.example.shopbaefood.ui.user.HomeUserActivity;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.Role;
import com.example.shopbaefood.util.UtilApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    public static final String MERCHANT_PENDING = "MERCHANT_PENDING";
    public static final String MERCHANT_RECEIVED = "MERCHANT_RECEIVED";
    public static final String MERCHANT_REFUSE = "MERCHANT_REFUSE";
    public static final String USER_RECEIVED = "USER_RECEIVED";
    public static final String USER_REFUSE = "USER_REFUSE";
    Intent intent;
    BottomNavigationView bottomNavigationView;
    RecyclerView rcvOrderDetail;
    ProgressBar progressBar;
    TextView orderMerName, orderMerTotal, orderMerAddress, orderMerNote;
    AccountToken accountToken;
    Gson gson;
    SharedPreferences sharedPreferences;

    Order order;

    ResponseBody body;
    Button merchantActive, merchantRefuse, userReceived, userRefuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        rcvOrderDetail = findViewById(R.id.recyclerView_order_detail);
        progressBar = findViewById(R.id.progressBar_order_detail);
        orderMerName = findViewById(R.id.order_detail_mer_name_val);
        orderMerTotal = findViewById(R.id.order_detail_price_total_val);
        orderMerAddress = findViewById(R.id.order_detail_address_val);
        orderMerNote = findViewById(R.id.order_detail_note_val);
        merchantActive = findViewById(R.id.btn_merchant_active);
        merchantRefuse = findViewById(R.id.btn_merchant_refuse);
        userReceived = findViewById(R.id.btn_user_received);
        userRefuse = findViewById(R.id.btn_user_refuse);

        intent = getIntent();
        if (intent.hasExtra("order")) {
            order = (Order) intent.getSerializableExtra("order");
        }
        gson = new Gson();
        getOrderDetail(order.getId());
        orderMerName.setText(order.getMerchant().getName());
        orderMerTotal.setText(String.valueOf(order.getTotalPrice()));
        orderMerAddress.setText(order.getDeliveryAddress());
        orderMerNote.setText(order.getNote());
        sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        accountToken = gson.fromJson(sharedPreferences.getString("info", ""), AccountToken.class);

        if (accountToken.getRoles()[0].equals(Role.ROLE_USER)) {
            bottomNavigationView = findViewById(R.id.nav_order_detail_user);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                intent.setClass(OrderDetailActivity.this, HomeUserActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1_c:
                        intent.putExtra("pageToDisplay", 0); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab2_c:
                        intent.putExtra("pageToDisplay", 1); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab3_c:
                        intent.putExtra("pageToDisplay", 2); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                }
                return false;
            });

            switch (order.getStatus()) {
                case MERCHANT_PENDING:
                    merchantActive.setVisibility(View.GONE);
                    merchantRefuse.setVisibility(View.GONE);
                    userRefuse.setVisibility(View.VISIBLE);
                    userReceived.setVisibility(View.GONE);
                    break;
                case MERCHANT_RECEIVED:
                    merchantActive.setVisibility(View.GONE);
                    merchantRefuse.setVisibility(View.GONE);
                    userRefuse.setVisibility(View.GONE);
                    userReceived.setVisibility(View.VISIBLE);
                    break;
                default:
                    merchantActive.setVisibility(View.GONE);
                    merchantRefuse.setVisibility(View.GONE);
                    userRefuse.setVisibility(View.GONE);
                    userReceived.setVisibility(View.GONE);
            }


        } else {
            bottomNavigationView = findViewById(R.id.nav_order_detail_merchant);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.getMenu().findItem(R.id.tab2_s).setChecked(true);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                intent.setClass(OrderDetailActivity.this, HomeMerchantActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1_s:
                        intent.putExtra("pageToDisplay", 0); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
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
                }
                return false;
            });

            switch (order.getStatus()) {
                case MERCHANT_PENDING:
                    merchantActive.setVisibility(View.VISIBLE);
                    merchantRefuse.setVisibility(View.VISIBLE);
                    userRefuse.setVisibility(View.GONE);
                    userReceived.setVisibility(View.GONE);
                    break;
                default:
                    merchantActive.setVisibility(View.GONE);
                    merchantRefuse.setVisibility(View.GONE);
                    userRefuse.setVisibility(View.GONE);
                    userReceived.setVisibility(View.GONE);
            }
        }
        merchantActive.setOnClickListener(v -> {
            ApiService apiService = UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
            Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc chắn chưa vậy", "Xác nhận", "Hủy", new Notification.OnConfirmationListener() {
                @Override
                public void onConfirm() {
                    Call<ResponseBody> call = apiService.orderMerchantReceived(order.getId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            body = response.body();
                            Log.d("Response Data", response.body().toString());
                            if (response.isSuccessful()) {
                                // Tạo một intent với hành động ACTION_CREATE_DOCUMENT
                                intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                                // Đặt loại tệp và thể loại mặc định (tùy chọn)
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("application/pdf");
                                // Đặt tên tệp (tùy chọn)
                                intent.putExtra(Intent.EXTRA_TITLE, "file_name.pdf");
                                // Gửi intent để mở hộp thoại Save As
                                startActivityForResult(intent, 79);
                                Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "", 1000);
                                merchantActive.setVisibility(View.GONE);
                                merchantRefuse.setVisibility(View.GONE);
                            } else {
                                Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi ròi bạn ơi");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi server ròi bạn ơi");
                        }
                    });
                }

                @Override
                public void onCancel() {

                }
            });

        });
        merchantRefuse.setOnClickListener(v -> {
            ApiService apiService = UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
            Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc chắn chưa vậy", "Xác nhận", "Hủy", new Notification.OnConfirmationListener() {
                @Override
                public void onConfirm() {
                    Call<ApiResponse> call = apiService.orderMerchantRefuse(order.getId());
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.isSuccessful()) {
                                Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "", 1000);
                                merchantRefuse.setVisibility(View.GONE);
                                merchantActive.setVisibility(View.GONE);
                            } else {
                                Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi ròi bạn ơi");
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi server ròi bạn ơi");
                        }
                    });
                }

                @Override
                public void onCancel() {

                }
            });

        });
        userReceived.setOnClickListener(v -> {
            ApiService apiService = UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
            Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc chắn chưa vậy", "Xác nhận", "Hủy", new Notification.OnConfirmationListener() {
                @Override
                public void onConfirm() {
                    Call<ApiResponse> call = apiService.orderUserReceived(order.getId());
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.isSuccessful()) {
                                Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "", 1000);
                                userReceived.setVisibility(View.GONE);
                            } else {
                                Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi ròi bạn ơi");
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi server ròi bạn ơi");
                        }
                    });
                }

                @Override
                public void onCancel() {

                }
            });

        });
        userRefuse.setOnClickListener(v -> {
            ApiService apiService = UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
            Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc chắn chưa vậy", "Xác nhận", "Hủy", new Notification.OnConfirmationListener() {
                @Override
                public void onConfirm() {
                    Call<ApiResponse> call = apiService.orderUserRefuse(order.getId());
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.isSuccessful()) {
                                Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "", 1000);
                                userRefuse.setVisibility(View.GONE);
                            } else {
                                Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi ròi bạn ơi");
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi server ròi bạn ơi");
                        }
                    });
                }

                @Override
                public void onCancel() {

                }
            });

        });
    }

    private void getOrderDetail(Long orderId) {
        ApiService apiService = UtilApp.retrofitAuth(OrderDetailActivity.this).create(ApiService.class);
        Call<ApiResponse<List<OrderDetail>>> call = apiService.orderDetail(orderId);
        call.enqueue(new Callback<ApiResponse<List<OrderDetail>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<OrderDetail>>> call, Response<ApiResponse<List<OrderDetail>>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    handlerOrderDetailList(response.body().getData());
                } else {
                    Notification.sweetAlert(OrderDetailActivity.this, SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi rồi thử lại đi");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<OrderDetail>>> call, Throwable t) {
                Notification.sweetAlert(OrderDetailActivity.this, SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi phía hệ thống");
            }
        });

    }

    private void handlerOrderDetailList(List<OrderDetail> data) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcvOrderDetail.setLayoutManager(gridLayoutManager);
        OrderDetailAdapter adapter = new OrderDetailAdapter(data, accountToken.getRoles()[0]);
        rcvOrderDetail.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 79 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                Log.d("URILog:",uri.toString());
                try {
                    // Mở OutputStream từ URI
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);

                    // Ghi dữ liệu từ InputStream vào OutputStream
                    if (outputStream != null) {
                        InputStream inputStream = body.byteStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }

                        // Đóng InputStream và OutputStream
                        inputStream.close();
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Mở tệp PDF với URI
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Thực hiện hành động nếu không tìm thấy ứng dụng hỗ trợ xem PDF
                    Notification.sweetAlert(this,SweetAlertDialog.ERROR_TYPE,"K thấy app hỗ trợ đọc","");
                }
            }
        }

    }

}