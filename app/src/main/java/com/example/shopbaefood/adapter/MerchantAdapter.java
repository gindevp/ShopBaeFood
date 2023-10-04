package com.example.shopbaefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Merchant;

import java.util.List;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.MerchantViewHolder> {
    private List<Merchant> merchantList;

    public MerchantAdapter(List<Merchant> merchantList) {
        this.merchantList = merchantList;
    }

    @NonNull
    @Override
    public MerchantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant,parent,false);
        return new MerchantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantViewHolder holder, int position) {
        Merchant merchant=merchantList.get(position);
        if(merchant == null){
            return;
        }
        holder.imgMerchant.setImageResource(R.drawable.book);
        holder.tvNameMerchant.setText(merchant.getName());
        holder.tvAddressMerchant.setText(merchant.getAddress());
    }

    @Override
    public int getItemCount() {
        if(merchantList!=null){
            return merchantList.size();
        }
        return 0;
    }

    public class MerchantViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgMerchant;
        private TextView tvNameMerchant;
        private TextView tvAddressMerchant;
        public MerchantViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMerchant= itemView.findViewById(R.id.img_merchant);
            tvNameMerchant= itemView.findViewById(R.id.tv_name_merchant);
            tvAddressMerchant= itemView.findViewById(R.id.tv_address_merchant);

        }
    }
}
