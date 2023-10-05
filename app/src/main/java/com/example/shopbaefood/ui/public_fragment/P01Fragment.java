package com.example.shopbaefood.ui.public_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.MerchantAdapter;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.UtilApp;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class P01Fragment extends Fragment {
    List<Merchant> merchantList;

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
//        LinearLayoutManager gridLayoutManager= new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false);
        rcvMerchant.setLayoutManager(gridLayoutManager);
        MerchantAdapter merchantAdapter= new MerchantAdapter(getListMerchant());
        rcvMerchant.setAdapter(merchantAdapter);

        return view;
    }

    private List<Merchant> getListMerchant() {
        List<Merchant> merchants= new ArrayList<>();
        merchantList= new ArrayList<>();
        ApiService apiService= UtilApp.retrofitCF().create(ApiService.class);
        Call<ApiResponse<List<Merchant>>> call= apiService.fetMerAll();
        call.enqueue(new Callback<ApiResponse<List<Merchant>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Merchant>>> call, Response<ApiResponse<List<Merchant>>> response) {
                if(response.isSuccessful()){
                    merchantList= response.body().getData();
                    for (Merchant m : merchantList) {
                        Log.d("data2", m.toString());
                    }
                    handleMerchantList(merchantList);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Merchant>>> call, Throwable t) {

            }
        });
        return merchants;
    }

    private void handleMerchantList(List<Merchant> merchants) {
        // Truy cập dữ liệu sau khi tải xong ở đây
        for (Merchant m : merchants) {
            Log.d("data2", m.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}