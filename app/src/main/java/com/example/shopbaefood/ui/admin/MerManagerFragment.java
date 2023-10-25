package com.example.shopbaefood.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopbaefood.R;
import com.example.shopbaefood.databinding.FragmentMerManagerBinding;


public class MerManagerFragment extends Fragment {

    private FragmentMerManagerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentMerManagerBinding.inflate(inflater,container,false);
        View view= binding.getRoot();

        return view;
    }
}