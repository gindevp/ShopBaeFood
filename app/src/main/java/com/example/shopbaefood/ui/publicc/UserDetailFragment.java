package com.example.shopbaefood.ui.publicc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.ContactClient;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.LoginActivity;
import com.example.shopbaefood.ui.RegisterActivity;
import com.example.shopbaefood.util.ContactClientDataSource;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.Role;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserDetailFragment extends Fragment {

    private ImageView imgUser, imgCam;
    private View editView;
    private EditText txtUserName, txtUserAddress, txtUserEmail, txtUserPhone, openTime, closeTime;
    private Button btnEdit,btnSubm;
    private Button btnRegister;

    private Button btnFavorite;
    private Button btnHistoryOrder;
    private Gson gson;
    Intent intent;
    SharedPreferences info;
    ContactClient contactClient;
    AccountToken accountToken;

    public UserDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        intent = getActivity().getIntent();
        if (intent.hasExtra("success")) {
            intent.removeExtra("success");
            Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.WARNING_TYPE, "Đăng ký thành công", "", 1000);
        }
        gson = new Gson();
        openTime=view.findViewById(R.id.textOpentime);
        closeTime=view.findViewById(R.id.textClosetime);
        btnSubm=view.findViewById(R.id.buttonSubm);
        imgCam=view.findViewById(R.id.edit_cam);
        editView = view.findViewById(R.id.edit_viewImag);
        imgUser = view.findViewById(R.id.imageUser);
        txtUserName = view.findViewById(R.id.textUserName);
        txtUserAddress = view.findViewById(R.id.textUserAddress);
        txtUserEmail = view.findViewById(R.id.textUserEmail);
        txtUserPhone = view.findViewById(R.id.textUserPhone);
        btnEdit = view.findViewById(R.id.buttonEdit);
        btnRegister = view.findViewById(R.id.buttonRegister);
        btnFavorite = view.findViewById(R.id.buttonFavorite);
        btnHistoryOrder = view.findViewById(R.id.buttonOrderHistory);

        info = view.getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        accountToken = gson.fromJson(info.getString("info", ""), AccountToken.class);

        info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        if (info.getString("idClient", null) != null) {
            long id = Long.parseLong(info.getString("idClient", ""));
            ContactClientDataSource dataSource = new ContactClientDataSource(view.getContext());
            dataSource.open();
            contactClient = dataSource.getContactById((int) id);
            dataSource.close();
            UtilApp.setImageFromBitmapByteBlob(contactClient.getAvartar(), imgUser);
            txtUserName.setText(contactClient.getName() + " (" + contactClient.getUsername() + ")");
            txtUserAddress.setText(contactClient.getAddress());
            txtUserEmail.setText(contactClient.getEmail());
            txtUserPhone.setText(contactClient.getPhone());
        }
        btnRegister.setOnClickListener(v -> {
            intent = new Intent(v.getContext(), RegisterActivity.class);
            v.getContext().startActivity(intent);
            getActivity().finish();
        });

        ImageView button = view.findViewById(R.id.logout);
        button.setOnClickListener(v -> {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Logout?")
                    .setContentText("Bạn có chắc chắn muốn đăng xuất khỏi thiết bị này?")
                    .setConfirmText("Có")
                    .setConfirmClickListener(sDialog -> {
                        // Xử lý khi người dùng xác nhận
                        sDialog.dismissWithAnimation(); // Đóng hộp thoại sau khi xử lý
                        // Thêm mã xử lý xóa dữ liệu ở đây
                        info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = info.edit();
                        editor.remove("info");
                        editor.apply();
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);
                    })
                    .setCancelText("Hủy")
                    .setCancelClickListener(sDialog -> {
                        // Xử lý khi người dùng hủy bỏ
                        sDialog.cancel();
                    });
            sweetAlertDialog.show();
        });

        if (accountToken.getUser() == null) {
            btnRegister.setVisibility(View.GONE);
            btnFavorite.setVisibility(View.GONE);
            btnHistoryOrder.setVisibility(View.GONE);
        } else {
            if (accountToken.getRoles()[0].equals(Role.ROLE_ADMIN)) {
                btnRegister.setVisibility(View.GONE);
                btnFavorite.setVisibility(View.GONE);
                btnHistoryOrder.setVisibility(View.GONE);
            }
        }

        btnEdit.setOnClickListener(v -> {
            btnEdit.setVisibility(View.GONE);
            btnSubm.setVisibility(View.VISIBLE);
            imgCam.setVisibility(View.VISIBLE);
            editView.setVisibility(View.VISIBLE);
            txtUserPhone.setBackgroundResource(R.drawable.shape1);
            txtUserAddress.setBackgroundResource(R.drawable.shape1);
            txtUserEmail.setBackgroundResource(R.drawable.shape1);
            txtUserName.setBackgroundResource(R.drawable.shape1);
            openTime.setEnabled(true);
            closeTime.setEnabled(true);
            txtUserAddress.setEnabled(true);
            txtUserEmail.setEnabled(true);
            txtUserName.setEnabled(true);
            txtUserPhone.setEnabled(true);
        });
        btnSubm.setOnClickListener(v->{
            ApiService apiService= UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
            Call<ApiResponse> call;
            if(accountToken.getUser()!=null){
                call= apiService.info(accountToken.getUser().getId());
            }else {
                call= apiService.info(accountToken.getMerchant().getId());
            }
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(!response.isSuccessful()){
                        Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi hệ thống");
                    }else {
                        btnEdit.setVisibility(View.GONE);
                        btnSubm.setVisibility(View.VISIBLE);
                        imgCam.setVisibility(View.VISIBLE);
                        editView.setVisibility(View.VISIBLE);
                        openTime.setEnabled(true);
                        closeTime.setEnabled(true);
                        txtUserAddress.setEnabled(true);
                        txtUserEmail.setEnabled(true);
                        txtUserName.setEnabled(true);
                        txtUserPhone.setEnabled(true);
                    Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "", 1000);}
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi hệ thống server");
                }
            });
        });
        openTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (s.length() == 2 && !input.contains(":")) {
                    openTime.setText(input + ":");
                    openTime.setSelection(openTime.getText().length());
                }
            }
        });
        return view;
    }
}