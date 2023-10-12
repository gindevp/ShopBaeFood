package com.example.shopbaefood.ui.test;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shopbaefood.databinding.ActivityTestDemoFireBaseBinding;
import com.example.shopbaefood.util.UtilApp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// DÙng tool firebase connect và truyền ảnh đọc ảnh, Uri đọc ảnh cũng ok thay vì
public class TestDemoFireBaseActivity extends AppCompatActivity {

    ActivityTestDemoFireBaseBinding binding;
    Uri imageUri;

    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTestDemoFireBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnChooseImageTest.setOnClickListener(v->{
            selectImage();
        });
        binding.btnUploadImageTest.setOnClickListener(v->{
            uploadImage();
        });


    }

    private void uploadImage() {
        UtilApp.uploadImageToFirebaseStorage(imageUri, new UtilApp.OnImageUploadListener() {
            @Override
            public void onSuccess(String imageUrl) {
                Toast.makeText(TestDemoFireBaseActivity.this,"success",Toast.LENGTH_SHORT).show();
                Toast.makeText(TestDemoFireBaseActivity.this, "Đường dẫn URL: " + imageUrl, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(TestDemoFireBaseActivity.this,"faile",Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&data!=null&&data.getData()!=null){
            imageUri =data.getData();
            binding.imageViewTest.setImageURI(imageUri);
        }
    }
}