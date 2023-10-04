package com.example.shopbaefood.ui.public_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.MerchantAdapter;
import com.example.shopbaefood.model.Merchant;

import java.util.ArrayList;
import java.util.List;

public class P01Fragment extends Fragment {

private RecyclerView rcvMerchant;
    public P01Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_p01, container, false);
        rcvMerchant= view.findViewById(R.id.recycler_view_p);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(view.getContext(),3);
        rcvMerchant.setLayoutManager(gridLayoutManager);
        MerchantAdapter merchantAdapter= new MerchantAdapter(getListMerchant());
        rcvMerchant.setAdapter(merchantAdapter);
        return view;
    }

    private List<Merchant> getListMerchant() {
        List<Merchant> merchantList= new ArrayList<>();

        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        merchantList.add(new Merchant("quán ăn","Đông anh"));
        return merchantList;
    }
}