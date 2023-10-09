package com.example.shopbaefood.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Cart;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartList;

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart= cartList.get(position);
        if(cart==null){
            return;
        }
        UtilApp.getImagePicasso(cart.getProduct().getImage(),holder.imgCart);
        holder.cartProName.setText(cart.getProduct().getName());
        holder.cartProTotal.setText(String.valueOf(cart.getQuantity()));
        holder.imgCartDown.setOnClickListener(v -> {
            holder.imgCartDown.setImageResource(R.drawable.minus_clicked);
            new Handler().postDelayed(() -> {
                holder.imgCartDown.setImageResource(R.drawable.minus);
            },1000);
        });

    }

    @Override
    public int getItemCount() {
        if(cartList!=null){
            return cartList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgCart;
        private TextView cartProName;
        private TextView cartProTotal;
        private ImageView imgCartDown;
        private ImageView imgCartUp;
        private ImageView imgCartClose;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCart=itemView.findViewById(R.id.image_cart_product);
            cartProName=itemView.findViewById(R.id.product_cart_name);
            cartProTotal=itemView.findViewById(R.id.product_cart_total);
            imgCartDown=itemView.findViewById(R.id.img_cart_down);
            imgCartUp=itemView.findViewById(R.id.img_cart_up);
            imgCartClose=itemView.findViewById(R.id.img_cart_close);
        }
    }
}
