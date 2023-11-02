package com.example.shopbaefood.ui.publicc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.ChangeDTO;
import com.example.shopbaefood.model.dto.ContactClient;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.LoginActivity;
import com.example.shopbaefood.ui.RegisterActivity;
import com.example.shopbaefood.ui.user.CartActivity;
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
    private Button btnEdit, btnSubm, btnCl;
    private Button btnRegister;
    private Uri imageUri;
    private Button btnFavorite;
    private Button btnHistoryOrder;
    private Gson gson;
    Intent intent;
    SharedPreferences info;
    ContactClient contactClient;
    AccountToken accountToken;
    byte[] avt;

    Dialog dialog;


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
        btnCl=view.findViewById(R.id.buttonCl);
        openTime = view.findViewById(R.id.textOpentime);
        closeTime = view.findViewById(R.id.textClosetime);
        btnSubm = view.findViewById(R.id.buttonSubm);
        imgCam = view.findViewById(R.id.edit_cam);
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
            avt=contactClient.getAvartar();
            UtilApp.setImageFromBitmapByteBlob(contactClient.getAvartar(), imgUser);
            txtUserName.setText(contactClient.getName());
            txtUserAddress.setText(contactClient.getAddress());
            txtUserEmail.setText(contactClient.getEmail());
            txtUserPhone.setText(contactClient.getPhone());
        }
        if(accountToken.getMerchant()!=null){
            openTime.setText(accountToken.getMerchant().getOpenTime());
            closeTime.setText(accountToken.getMerchant().getCloseTime());
        }
        btnRegister.setOnClickListener(v -> {
            intent = new Intent(v.getContext(), RegisterActivity.class);
            v.getContext().startActivity(intent);
            getActivity().finish();
        });
        btnHistoryOrder.setOnClickListener(v->{
            intent = new Intent(v.getContext(), CartActivity.class);
            intent.putExtra("pageOrder","true");
            v.getContext().startActivity(intent);
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
        imgCam.setOnClickListener(v -> {
            selectImage();
        });
        btnCl.setOnClickListener(v->{
            btnEdit.setVisibility(View.VISIBLE);
            btnSubm.setVisibility(View.GONE);
            imgCam.setVisibility(View.INVISIBLE);
            editView.setVisibility(View.INVISIBLE);
            openTime.setEnabled(false);
            closeTime.setEnabled(false);
            txtUserAddress.setEnabled(false);
            txtUserEmail.setEnabled(false);
            txtUserName.setEnabled(false);
            txtUserPhone.setEnabled(false);
            openTime.setBackground(null);
            closeTime.setBackground(null);
            txtUserAddress.setBackground(null);
            txtUserEmail.setBackground(null);
            txtUserName.setBackground(null);
            txtUserPhone.setBackground(null);
            btnCl.setVisibility(View.GONE);
            UtilApp.setImageFromBitmapByteBlob(avt, imgUser);
        });
        btnEdit.setOnClickListener(v -> {
            btnEdit.setVisibility(View.GONE);
            btnSubm.setVisibility(View.VISIBLE);
            imgCam.setVisibility(View.VISIBLE);
            editView.setVisibility(View.VISIBLE);
            openTime.setBackgroundResource(android.R.drawable.edit_text);
            closeTime.setBackgroundResource(android.R.drawable.edit_text);
            txtUserAddress.setBackgroundResource(android.R.drawable.edit_text);
            txtUserEmail.setBackgroundResource(android.R.drawable.edit_text);
            txtUserName.setBackgroundResource(android.R.drawable.edit_text);
            txtUserPhone.setBackgroundResource(android.R.drawable.edit_text);
            openTime.setEnabled(true);
            closeTime.setEnabled(true);
            txtUserAddress.setEnabled(true);
            txtUserEmail.setEnabled(true);
            txtUserName.setEnabled(true);
            txtUserPhone.setEnabled(true);
            btnCl.setVisibility(View.VISIBLE);
        });
        btnSubm.setOnClickListener(v -> {
            uploadImage(v);
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
        closeTime.addTextChangedListener(new TextWatcher() {
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

    private void uploadImage(View view) {
        dialog.show();
        if (imageUri == null) {
            Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.WARNING_TYPE, "Phải có ảnh", "");
        } else {
            UtilApp.uploadImageToFirebaseStorage(imageUri, new UtilApp.OnImageUploadListener() {
                @Override
                public void onSuccess(String imageUrl) {
                    Toast.makeText(view.getContext(), "success", Toast.LENGTH_SHORT).show();
                    ApiService apiService = UtilApp.retrofitAuth(view.getContext()).create(ApiService.class);
                    Call<ApiResponse> call;
                    if (accountToken.getUser() != null) {
                        ChangeDTO changeDTO = new ChangeDTO(
                                accountToken.getUser().getId(),
                                imageUrl,
                                txtUserName.getText().toString(),
                                txtUserPhone.getText().toString(),
                                txtUserAddress.getText().toString(),
                                txtUserEmail.getText().toString(),
                                null,null
                        );
                        call = apiService.info(changeDTO);
                    } else {
                        ChangeDTO changeDTO = new ChangeDTO(
                                accountToken.getMerchant().getId(),
                                imageUrl,
                                txtUserName.getText().toString(),
                                txtUserPhone.getText().toString(),
                                txtUserAddress.getText().toString(),
                                txtUserEmail.getText().toString(),
                                openTime.getText().toString(),
                                closeTime.getText().toString()
                        );
                        call = apiService.info(changeDTO);
                    }
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            dialog.cancel();
                            if (!response.isSuccessful()) {
                                Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi hệ thống");
                            } else {
                                btnEdit.setVisibility(View.VISIBLE);
                                btnSubm.setVisibility(View.GONE);
                                imgCam.setVisibility(View.INVISIBLE);
                                editView.setVisibility(View.INVISIBLE);
                                openTime.setEnabled(false);
                                closeTime.setEnabled(false);
                                txtUserAddress.setEnabled(false);
                                txtUserEmail.setEnabled(false);
                                txtUserName.setEnabled(false);
                                txtUserPhone.setEnabled(false);
                                openTime.setBackground(null);
                                closeTime.setBackground(null);
                                txtUserAddress.setBackground(null);
                                txtUserEmail.setBackground(null);
                                txtUserName.setBackground(null);
                                txtUserPhone.setBackground(null);
                                btnCl.setVisibility(View.GONE);
                                Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "", 1000);
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi hệ thống server");
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(view.getContext(), "faile upload image", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgUser.setImageURI(imageUri);
        }
    }
}