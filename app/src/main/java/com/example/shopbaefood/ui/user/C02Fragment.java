package com.example.shopbaefood.ui.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.ui.LoginActivity;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class C02Fragment extends Fragment {

    private ImageView imgUser;
    private TextView txtUserName;
    private TextView txtUserAddress;
    private TextView txtUserEmail;
    private TextView txtUserPhone;
    private Button btnEdit;
    private Gson gson;
    SharedPreferences info;
    public C02Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_c02, container, false);

        gson= new Gson();
        imgUser=view.findViewById(R.id.imageUser);
        txtUserName=view.findViewById(R.id.textUserName);
        txtUserAddress=view.findViewById(R.id.textUserAddress);
        txtUserEmail=view.findViewById(R.id.textUserEmail);
        txtUserPhone=view.findViewById(R.id.textUserPhone);
        btnEdit= view.findViewById(R.id.buttonEdit);

        info= view.getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
        AccountToken accountToken= gson.fromJson(info.getString("info",""), AccountToken.class);
        UtilApp.getImagePicasso(accountToken.getUser().getAvatar(),imgUser);
        txtUserName.setText(accountToken.getUser().getName());
        txtUserAddress.setText(accountToken.getUser().getAddress());
        txtUserEmail.setText(accountToken.getRoles()[0]);
        txtUserPhone.setText(accountToken.getUser().getPhone());

        Button button = view.findViewById(R.id.logout2);
        button.setOnClickListener(v -> {
            SweetAlertDialog sweetAlertDialog= new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE)
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
                        Intent intent= new Intent(v.getContext(), LoginActivity.class);
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