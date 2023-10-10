package com.example.shopbaefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order= orderList.get(position);
        if(order==null){
            return;
        }

    }

    @Override
    public int getItemCount() {
        if(orderList!=null){
            return orderList.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
