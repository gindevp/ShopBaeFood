package com.example.shopbaefood.ui.admin;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.ClientManagerAdapter;
import com.example.shopbaefood.databinding.FragmentUserManagerBinding;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.ClientManager;
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


public class UserManagerFragment extends Fragment {
    private FragmentUserManagerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate th e layout for this fragment
        binding= FragmentUserManagerBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        if(binding.toggleManagerUserStatus1.isChecked()){
            binding.toggleManagerUserStatus1.setClickable(false);
            getClientUser(view,1);
        }
        binding.toggleGroupManagerUser.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == binding.toggleManagerUserStatus1.getId()) {
                    getClientUser(view, 1);
                    isFalseClick();
                } else if (checkedId == binding.toggleManagerUserStatus2.getId()) {
                    getClientUser(view, 2);
                    isFalseClick();
                } else if (checkedId == binding.toggleManagerUserStatus3.getId()) {
                    getClientUser(view, 3);
                    isFalseClick();
                }
            }
        });
        return view;
    }

    private void isFalseClick() {
        if(binding.toggleManagerUserStatus1.isChecked()){
            binding.toggleManagerUserStatus1.setClickable(false);
            binding.toggleManagerUserStatus2.setClickable(true);
            binding.toggleManagerUserStatus3.setClickable(true);
            binding.progressBarUserManager.setVisibility(View.VISIBLE);
            binding.recyclerViewUserManager.setVisibility(View.GONE);
        }else if(binding.toggleManagerUserStatus2.isChecked()){
            binding.toggleManagerUserStatus1.setClickable(true);
            binding.toggleManagerUserStatus2.setClickable(false);
            binding.toggleManagerUserStatus3.setClickable(true);
            binding.progressBarUserManager.setVisibility(View.VISIBLE);
            binding.recyclerViewUserManager.setVisibility(View.GONE);
        }else if(binding.toggleManagerUserStatus3.isChecked()){
            binding.toggleManagerUserStatus1.setClickable(true);
            binding.toggleManagerUserStatus2.setClickable(true);
            binding.toggleManagerUserStatus3.setClickable(false);
            binding.progressBarUserManager.setVisibility(View.VISIBLE);
            binding.recyclerViewUserManager.setVisibility(View.GONE);
        }
    }

    private void getClientUser(View view,int status) {
        ApiService apiService=UtilApp.retrofitAuth(view.getContext()).create(ApiService.class);
        Call<ApiResponse<List<ClientManager>>> call=null;
        switch (status) {
            case 1:
                call = apiService.getUserPending();
                break;
            case 2:
                call = apiService.getUserActive();
                break;
            case 3:
                call = apiService.getUserBlock();
                break;
        }
        call.enqueue(new Callback<ApiResponse<List<ClientManager>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ClientManager>>> call, Response<ApiResponse<List<ClientManager>>> response) {
                if(response.isSuccessful()){
                    binding.progressBarUserManager.setVisibility(View.GONE);
                    binding.recyclerViewUserManager.setVisibility(View.VISIBLE);
                    handerUser(response.body().getData(),view);
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

    private void handerUser(List<ClientManager> data, View view) {
        GridLayoutManager gridLayoutManager= new GridLayoutManager(view.getContext(),1);
        binding.recyclerViewUserManager.setLayoutManager(gridLayoutManager);
        ClientManagerAdapter adapter= new ClientManagerAdapter(data, binding,true);
        binding.recyclerViewUserManager.setAdapter(adapter);
    }
}