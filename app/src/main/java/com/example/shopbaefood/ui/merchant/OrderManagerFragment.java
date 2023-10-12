package com.example.shopbaefood.ui.merchant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.OrderAdapter;
import com.example.shopbaefood.model.Order;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.gson.Gson;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderManagerFragment extends Fragment {
    RecyclerView recyclerView;
    MaterialButtonToggleGroup toggleGroup;
    MaterialButton btnPending, btnReceived, btnHistory;
    Gson gson;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    public OrderManagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_order_manager, container, false);

        toggleGroup= view.findViewById(R.id.toggle_group_manager_order);
        btnPending= view.findViewById(R.id.toggle_manager_order_status1);
        btnReceived= view.findViewById(R.id.toggle_manager_order_status2);
        btnHistory= view.findViewById(R.id.toggle_manager_order_status3);
        recyclerView= view.findViewById(R.id.recyclerView_order_manager);
        progressBar= view.findViewById(R.id.progressBar_order_manager);

        gson= new Gson();
        sharedPreferences= view.getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        AccountToken accountToken=gson.fromJson(sharedPreferences.getString("info",""), AccountToken.class);

        if(btnPending.isChecked()){
        getOrderPending(accountToken.getMerchant().getId(),view);}
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(isChecked) {
                if (checkedId == R.id.toggle_manager_order_status1) {
                    getOrderPending(accountToken.getMerchant().getId(),view);
                } else if (checkedId == R.id.toggle_manager_order_status2) {

                } else if (checkedId == R.id.toggle_manager_order_status3) {

                }
            }
        });
        return view;
    }

    private void getOrderPending(Long id,View view) {
        ApiService apiService= UtilApp.retrofitAuth(view.getContext()).create(ApiService.class);
        Call<ApiResponse<List<Order>>> call= apiService.orderPending(id);
        call.enqueue(new Callback<ApiResponse<List<Order>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Order>>> call, Response<ApiResponse<List<Order>>> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    handlerOderPending(response.body().getData(), view);
                }else {
                    Log.d("Http",String.valueOf(response.code()));
                    Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi 404");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Order>>> call, Throwable t) {
                Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi phía server");
            }
        });
    }

    private void handlerOderPending(List<Order> data, View view) {
        GridLayoutManager gridLayoutManager= new GridLayoutManager(view.getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        OrderAdapter adapter= new OrderAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}