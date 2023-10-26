package com.example.shopbaefood.ui.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.ClientManagerAdapter;
import com.example.shopbaefood.databinding.FragmentMerManagerBinding;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.ClientManager;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MerManagerFragment extends Fragment {

    private FragmentMerManagerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentMerManagerBinding.inflate(inflater,container,false);
        View view= binding.getRoot();
        if(binding.toggleManagerMerStatus1.isChecked()){
            binding.toggleManagerMerStatus1.setClickable(false);
            getClientMer(view,1);
        }
        binding.toggleGroupManagerMer.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == binding.toggleManagerMerStatus1.getId()) {
                    getClientMer(view, 1);
                    isFalseClick();
                } else if (checkedId == binding.toggleManagerMerStatus2.getId()) {
                    getClientMer(view, 2);
                    isFalseClick();
                } else if (checkedId == binding.toggleManagerMerStatus3.getId()) {
                    getClientMer(view, 3);
                    isFalseClick();
                }
            }
        });
        return view;
    }

    private void isFalseClick() {
        if(binding.toggleManagerMerStatus1.isChecked()){
            binding.toggleManagerMerStatus1.setClickable(false);
            binding.toggleManagerMerStatus2.setClickable(true);
            binding.toggleManagerMerStatus3.setClickable(true);
            binding.progressBarMerManager.setVisibility(View.VISIBLE);
            binding.recyclerViewMerManager.setVisibility(View.GONE);
        }else if(binding.toggleManagerMerStatus2.isChecked()){
            binding.toggleManagerMerStatus1.setClickable(true);
            binding.toggleManagerMerStatus2.setClickable(false);
            binding.toggleManagerMerStatus3.setClickable(true);
            binding.progressBarMerManager.setVisibility(View.VISIBLE);
            binding.recyclerViewMerManager.setVisibility(View.GONE);
        }else if(binding.toggleManagerMerStatus3.isChecked()){
            binding.toggleManagerMerStatus1.setClickable(true);
            binding.toggleManagerMerStatus2.setClickable(true);
            binding.toggleManagerMerStatus3.setClickable(false);
            binding.progressBarMerManager.setVisibility(View.VISIBLE);
            binding.recyclerViewMerManager.setVisibility(View.GONE);
        }
    }

    private void getClientMer(View view, int status) {
        ApiService apiService= UtilApp.retrofitAuth(view.getContext()).create(ApiService.class);
        Call<ApiResponse<List<ClientManager>>> call=null;
        switch (status) {
            case 1:
                call = apiService.getMerPending();
                break;
            case 2:
                call = apiService.getMerActive();
                break;
            case 3:
                call = apiService.getMerBlock();
                break;
        }
        call.enqueue(new Callback<ApiResponse<List<ClientManager>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ClientManager>>> call, Response<ApiResponse<List<ClientManager>>> response) {
                if(response.isSuccessful()){
                    binding.progressBarMerManager.setVisibility(View.GONE);
                    binding.recyclerViewMerManager.setVisibility(View.VISIBLE);
                    handerMer(response.body().getData(),view);
                }else {
                    Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi 404");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ClientManager>>> call, Throwable t) {
                Notification.sweetAlert(view.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Lỗi phía server");
            }
        });
    }

    private void handerMer(List<ClientManager> data, View view) {
        GridLayoutManager gridLayoutManager= new GridLayoutManager(view.getContext(),1);
        binding.recyclerViewMerManager.setLayoutManager(gridLayoutManager);
        ClientManagerAdapter adapter= new ClientManagerAdapter(data, binding,false);
        binding.recyclerViewMerManager.setAdapter(adapter);
    }
}