package com.example.shopbaefood.ui.merchant;


import android.content.Intent;
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
import com.example.shopbaefood.model.dto.ProductForm;
import com.example.shopbaefood.ui.test.TestDemoFireBaseActivity;
import com.example.shopbaefood.util.UtilApp;


public class ItemManagerFragment extends Fragment {
    private FragmentItemManagerBinding binding;
    private Uri imageUri;

    private String imageUrl;

    private ProductForm productForm;

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
        binding.uploadProductCloud.setOnClickListener(v->{
            selectImage();
        });
        binding.btnAddProduct.setOnClickListener(v->{
            uploadImage(v);
        });
        saveProduct();
        return view;
    }

    private void saveProduct() {
        productForm= new ProductForm(binding.productName.getText().toString(),
                binding.productDescription.getText().toString(),
                Double.parseDouble(binding.productOldPrice.getText().toString()),
                Double.parseDouble(binding.productNewPrice.getText().toString()),
                imageUrl,Integer.parseInt(binding.productQuantity.getText().toString()));
        //Viet api gửi product form cho api đã viết
    }

    private void uploadImage(View view) {
        UtilApp.uploadImageToFirebaseStorage(imageUri, new UtilApp.OnImageUploadListener() {
            @Override
            public void onSuccess(String imageUrl) {
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