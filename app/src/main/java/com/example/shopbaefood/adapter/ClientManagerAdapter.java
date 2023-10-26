package com.example.shopbaefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.databinding.FragmentUserManagerBinding;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.model.dto.ClientManager;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.StatusClient;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientManagerAdapter extends RecyclerView.Adapter<ClientManagerAdapter.ClientManagerHolder> {
    private List<ClientManager> clientManagerList;
    private FragmentUserManagerBinding binding;
    private boolean flag;

    public ClientManagerAdapter(List<ClientManager> clientManagerList, FragmentUserManagerBinding binding, boolean flag) {
        this.clientManagerList = clientManagerList;
        this.binding=binding;
        this.flag=flag;
    }

    @NonNull
    @Override
    public ClientManagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ClientManagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientManagerHolder holder, int position) {
        ClientManager clientManager = clientManagerList.get(position);
        if(!flag){
            holder.txtOpentime.setText(clientManager.getOpenTime());
            holder.txtClosetime.setText(clientManager.getCloseTime());
        }
        UtilApp.getImagePicasso(clientManager.getAvatar(), holder.imgClient);
        holder.imgMenu.setImageResource(R.drawable.menu_product);
        holder.imgMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.edit_client_menu, popupMenu.getMenu());
            switch (clientManager.getStatus()){
                case StatusClient.PENDING:
                    popupMenu.getMenu().findItem(R.id.admin_active).setVisible(true);
                    popupMenu.getMenu().findItem(R.id.admin_refuse).setVisible(true);
                    break;
                case  StatusClient.ACTIVE:
                    popupMenu.getMenu().findItem(R.id.admin_block).setVisible(true);
                    break;
                case StatusClient.BLOCK:
                    popupMenu.getMenu().findItem(R.id.admin_unblock).setVisible(true);
                    break;
            }
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.admin_active:
                        //dùng swal confirm
                        Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc không?", "Ok", "Cancel", new Notification.OnConfirmationListener() {
                            @Override
                            public void onConfirm() {
                                ApiService apiService= UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
                                Call<ApiResponse> call;
                                if (flag) {
                                    call= apiService.activeUser(clientManager.getId());
                                }else {
                                    call= apiService.activeMer(clientManager.getId());
                                }
                                binding.progressBarUserManager.setVisibility(View.VISIBLE);
                                call.enqueue(new Callback<ApiResponse>() {
                                    @Override
                                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                        if(response.isSuccessful()){
                                            binding.progressBarUserManager.setVisibility(View.GONE);
                                            int position = holder.getAdapterPosition();
                                            if (position != -1) {
                                                clientManagerList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE,"Success","");
                                        }else {
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi hệ thống ròi");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                                        Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi server ròi");
                                    }
                                });
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        return true;
                    case R.id.admin_refuse:
                        Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc không?", "Ok", "Cancel", new Notification.OnConfirmationListener() {
                            @Override
                            public void onConfirm() {
                                ApiService apiService= UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
                                Call<ApiResponse> call;
                                if (flag) {
                                    call= apiService.refuseUser(clientManager.getId());
                                }else {
                                    call= apiService.refuseMer(clientManager.getId());
                                }
                                binding.progressBarUserManager.setVisibility(View.VISIBLE);
                                call.enqueue(new Callback<ApiResponse>() {
                                    @Override
                                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                        if(response.isSuccessful()){
                                            binding.progressBarUserManager.setVisibility(View.GONE);
                                            int position = holder.getAdapterPosition();
                                            if (position != -1) {
                                                clientManagerList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE,"Success","");
                                        }else {
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi hệ thống ròi");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                                        Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi server ròi");
                                    }
                                });
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        return true;
                    case R.id.admin_block:
                        Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc không?", "Ok", "Cancel", new Notification.OnConfirmationListener() {
                            @Override
                            public void onConfirm() {
                                ApiService apiService= UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
                                Call<ApiResponse> call;
                                if (flag) {
                                    call= apiService.blockUser(clientManager.getId());
                                }else {
                                    call= apiService.blockMer(clientManager.getId());
                                }
                                binding.progressBarUserManager.setVisibility(View.VISIBLE);
                                call.enqueue(new Callback<ApiResponse>() {
                                    @Override
                                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                        if(response.isSuccessful()){
                                            binding.progressBarUserManager.setVisibility(View.GONE);
                                            int position = holder.getAdapterPosition();
                                            if (position != -1) {
                                                clientManagerList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE,"Success","");
                                        }else {
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi hệ thống ròi");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                                        Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi server ròi");
                                    }
                                });
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        return true;
                    case R.id.admin_unblock:
                        Notification.confirmationDialog(v.getContext(), "Confirm", "Bạn có chắc không?", "Ok", "Cancel", new Notification.OnConfirmationListener() {
                            @Override
                            public void onConfirm() {
                                ApiService apiService= UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
                                Call<ApiResponse> call;
                                if (flag) {
                                    call= apiService.unblockUser(clientManager.getId());
                                }else {
                                    call= apiService.unblockMer(clientManager.getId());
                                }
                                binding.progressBarUserManager.setVisibility(View.VISIBLE);
                                call.enqueue(new Callback<ApiResponse>() {
                                    @Override
                                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                        if(response.isSuccessful()){
                                            binding.progressBarUserManager.setVisibility(View.GONE);
                                            int position = holder.getAdapterPosition();
                                            if (position != -1) {
                                                clientManagerList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE,"Success","");
                                        }else {
                                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi hệ thống ròi");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                                        Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE,"Error","Lỗi server ròi");
                                    }
                                });
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
        holder.txtName.setText(clientManager.getName());
        holder.txtPhone.setText(clientManager.getPhone());
        holder.txtAddress.setText(clientManager.getAddress());
    }



    @Override
    public int getItemCount() {
        if (clientManagerList != null) {
            return clientManagerList.size();
        }
        return 0;
    }

    public class ClientManagerHolder extends RecyclerView.ViewHolder {
        private ImageView imgClient, imgMenu;
        private TextView txtName, txtPhone, txtAddress, txtOpentime, txtClosetime;

        public ClientManagerHolder(@NonNull View itemView) {
            super(itemView);
            imgClient = itemView.findViewById(R.id.img_client);
            imgMenu = itemView.findViewById(R.id.img_client_menu);
            txtName = itemView.findViewById(R.id.txt_name);
            txtAddress = itemView.findViewById(R.id.txt_client_address);
            txtPhone = itemView.findViewById(R.id.txt_client_phone);
            txtOpentime = itemView.findViewById(R.id.txt_open_time);
            txtClosetime = itemView.findViewById(R.id.txt_closetime);
        }
    }
}
