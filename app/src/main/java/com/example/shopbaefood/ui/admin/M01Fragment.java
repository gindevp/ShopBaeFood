package com.example.shopbaefood.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.util.UtilApp;
import com.squareup.picasso.Picasso;


public class M01Fragment extends Fragment {


    public M01Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_m01, container, false);
        ImageView imageView = view.findViewById(R.id.toolbar_icon);
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/shopbaefoodappandroid.appspot.com/o/image.png?alt=media&token=ab3d1294-f93e-41b4-8bc0-649cffea5abb&_gl=1*19l18sw*_ga*MjI5MDc3NjE0LjE2OTYzODkzMzQ.*_ga_CW55HF8NVT*MTY5NjQwMDgwNy4yLjEuMTY5NjQwMjE4OC42MC4wLjA.";
        UtilApp.getImagePicasso(imageView,imageUrl);
        return view;
    }
}