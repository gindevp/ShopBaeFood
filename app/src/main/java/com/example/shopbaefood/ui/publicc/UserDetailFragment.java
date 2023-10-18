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
import com.example.shopbaefood.model.dto.ContactClient;
import com.example.shopbaefood.ui.LoginActivity;
import com.example.shopbaefood.util.ContactClientDataSource;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class UserDetailFragment extends Fragment {

    private ImageView imgUser;
    private TextView txtUserName, txtUserAddress, txtUserEmail, txtUserPhone;
    private Button btnEdit;
    private Gson gson;
    SharedPreferences info;
//    AccountToken accountToken;

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

//        ContactClientDataSource dataSource = new ContactClientDataSource(view.getContext());
//        dataSource.open();
//        ContactClient contactClient = dataSource.getContactById(1);
//        Log.d("contacClient:",contactClient.toString());
//        dataSource.close();

        gson = new Gson();
        imgUser = view.findViewById(R.id.imageUser);
        txtUserName = view.findViewById(R.id.textUserName);
        txtUserAddress = view.findViewById(R.id.textUserAddress);
        txtUserEmail = view.findViewById(R.id.textUserEmail);
        txtUserPhone = view.findViewById(R.id.textUserPhone);
        btnEdit = view.findViewById(R.id.buttonEdit);

//        info = view.getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
//        accountToken = gson.fromJson(info.getString("info", ""), AccountToken.class);

//        UtilApp.setImageFromBitmapByteBlob(contactClient.getAvartar(), imgUser);
//        txtUserName.setText(contactClient.getName() + " (" + contactClient.getUsername() + ")");
//        txtUserAddress.setText(contactClient.getAddress());
//        txtUserEmail.setText(contactClient.getEmail());
//        txtUserPhone.setText(contactClient.getPhone());


        Button button = view.findViewById(R.id.logout2);
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
        return view;
    }
}