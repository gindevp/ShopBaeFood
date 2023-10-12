package com.example.shopbaefood.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Order;
import com.example.shopbaefood.ui.user.CartActivity;
import com.example.shopbaefood.ui.user.OrderDetailActivity;

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
        holder.txtOrderName.setText(order.getMerchant().getName());
        holder.txtOrderStatus.setText(order.getStatus());
        holder.txtOrderTime.setText(order.getOrderdate());
        holder.txtOrderPrice.setText( String.valueOf(order.getTotalPrice()));
        holder.orderBtn.setOnClickListener(v->{
            Intent intent= new Intent();
            intent.setClass(v.getContext(), OrderDetailActivity.class);
            intent.putExtra("order",order);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(orderList!=null){
            return orderList.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        private TextView txtOrderName, txtOrderPrice, txtOrderStatus, txtOrderTime;
        private ImageView orderBtn;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderName=itemView.findViewById(R.id.order_of_merchant_name);
            txtOrderPrice=itemView.findViewById(R.id.order_price2);
            txtOrderStatus=itemView.findViewById(R.id.order_status2);
            orderBtn=itemView.findViewById(R.id.oder_btn);
            txtOrderTime=itemView.findViewById(R.id.order_time);
        }

    }
}