package com.example.shopbaefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.OrderDetail;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private List<OrderDetail> orderDetails;
    private String role;

    public OrderDetailAdapter(List<OrderDetail> orderDetails, String role) {
        this.orderDetails = orderDetails;
        this.role=role;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail,parent,false);

        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderDetail orderDetail= orderDetails.get(position);
        if(orderDetail==null){
            return;
        }
        UtilApp.getImagePicasso(orderDetail.getProduct().getImage(),holder.imgProduct);
        holder.txtProName.setText(orderDetail.getProduct().getName());
        holder.txtPrice.setText(String.valueOf(orderDetail.getProduct().getNewPrice()));
        holder.txtQuan.setText(String.valueOf(orderDetail.getQuantity()));
        holder.txtTotal.setText(String.valueOf(orderDetail.getProduct().getNewPrice()*orderDetail.getQuantity()));

    }

    @Override
    public int getItemCount() {
        if(orderDetails!=null){
            return orderDetails.size();
        }
        return 0;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView txtProName, txtPrice, txtQuan, txtTotal;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.image_product_order_detail);
            txtProName=itemView.findViewById(R.id.product_name_order_detail);
            txtPrice=itemView.findViewById(R.id.product_price);
            txtQuan=itemView.findViewById(R.id.product_quantity);
            txtTotal=itemView.findViewById(R.id.product_price_total);
        }
    }
}
