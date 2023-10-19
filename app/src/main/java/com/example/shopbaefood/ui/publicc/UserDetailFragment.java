package com.example.shopbaefood.ui.publicc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ContactClient;
import com.example.shopbaefood.ui.LoginActivity;
import com.example.shopbaefood.ui.RegisterActivity;
import com.example.shopbaefood.util.ContactClientDataSource;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class UserDetailFragment extends Fragment {

    private ImageView imgUser;
    private TextView txtUserName, txtUserAddress, txtUserEmail, txtUserPhone;
    private Button btnEdit;
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
        intent= getActivity().getIntent();
        if (intent.hasExtra("success")) {
            intent.removeExtra("success");
            Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.WARNING_TYPE, "Đăng ký thành công", "", 1000);
        }
        gson = new Gson();
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

        if(accountToken.getUser()==null){
            btnRegister.setVisibility(View.GONE);
            btnFavorite.setVisibility(View.GONE);
            btnHistoryOrder.setVisibility(View.GONE);
        }

        return view;
    }
}