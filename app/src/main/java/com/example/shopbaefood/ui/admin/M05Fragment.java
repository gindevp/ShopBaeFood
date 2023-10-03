package com.example.shopbaefood.ui.admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopbaefood.R;


public class M05Fragment extends Fragment {


    public M05Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m05, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button= getActivity().findViewById(R.id.logoutt);
        button.setOnClickListener(v -> {
            SharedPreferences info= getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= info.edit();
            editor.remove("info");
            editor.apply();
        });

    }
}