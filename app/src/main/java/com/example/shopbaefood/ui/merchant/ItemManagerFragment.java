package com.example.shopbaefood.ui.merchant;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopbaefood.R;
import com.example.shopbaefood.databinding.FragmentItemManagerBinding;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.ProductForm;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.ui.test.TestDemoFireBaseActivity;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemManagerFragment extends Fragment {
    private FragmentItemManagerBinding binding;
    private Uri imageUri;

    private String imageURL;

    private ProductForm productForm;
    private AccountToken accountToken;
    private Gson gson;

    public ItemManagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemManagerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        gson= new Gson();
        SharedPreferences sharedPreferences= view.getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        accountToken= gson.fromJson(sharedPreferences.getString("info",""), AccountToken.class);

        binding.uploadProductCloud.setOnClickListener(v->{
            selectImage();
        });
        binding.btnAddProduct.setOnClickListener(v->{
            uploadImage(v);
            saveProduct(v);
        });

        return view;
    }

    private void saveProduct(View view) {
        productForm= new ProductForm(binding.productName.getText().toString(),
                binding.productDescription.getText().toString(),
                Double.parseDouble(binding.productOldPrice.getText().toString()),
                Double.parseDouble(binding.productNewPrice.getText().toString()),
                imageURL,Integer.parseInt(binding.productQuantity.getText().toString()));

        ApiService apiService= UtilApp.retrofitAuth(view.getContext()).create(ApiService.class);
        Call<ApiResponse> call= apiService.saveProduct(accountToken.getMerchant().getId());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    Notification.sweetAlertNow(view.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success","Thêm sản phẩm thành công",1000);
                }else {
                    Notification.sweetAlert(view.getContext(),SweetAlertDialog.ERROR_TYPE,"Error","Lỗi ròi không thêm được");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Notification.sweetAlert(view.getContext(),SweetAlertDialog.ERROR_TYPE,"Error","Lỗi server không kết nối được");
            }
        });
    }

    private void uploadImage(View view) {
        UtilApp.uploadImageToFirebaseStorage(imageUri, new UtilApp.OnImageUploadListener() {
            @Override
            public void onSuccess(String imageUrl) {
                imageURL= imageUrl;
                Toast.makeText(view.getContext(), "success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(view.getContext(), "faile",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void selectImage() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&data!=null&&data.getData()!=null){
            imageUri =data.getData();
            binding.showwImageProduct.setImageURI(imageUri);
        }
    }
}