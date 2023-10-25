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
import com.example.shopbaefood.model.dto.ClientManager;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

public class ClientManagerAdapter extends RecyclerView.Adapter<ClientManagerAdapter.ClientManagerHolder> {
    private List<ClientManager> clientManagerList;

    public ClientManagerAdapter(List<ClientManager> clientManagerList) {
        this.clientManagerList = clientManagerList;
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
        UtilApp.getImagePicasso(clientManager.getAvatar(), holder.imgClient);
        holder.imgMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.edit_client_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.admin_active:,
                        //dùng swal confirm
                        return true;
                    case R.id.admin_refuse:
                        return true;
                    case R.id.admin_block:
                        return true;
                    case R.id.admin_unblock:
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
