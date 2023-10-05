package com.example.shopbaefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Product;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product= productList.get(position);
        if(product ==null){
            return;
        }
        holder.tvName.setText(product.getName());
        holder.tvOldPrice.setText(String.valueOf(product.getOldPrice()));
        holder.tvNewPrice.setText(String.valueOf(product.getNewPrice()));
        UtilApp.getImagePicasso(product.getImage(),holder.imgProduct);
        holder.imgAddToCart.setOnClickListener(view -> {
            // TODO: them code add to card kèm alert
        });
    }

    @Override
    public int getItemCount() {
        if(productList!=null){
            return productList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvNewPrice;
        private TextView tvOldPrice;
        private ImageView imgAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.image_product);
            tvName= itemView.findViewById(R.id.product_name);
            tvNewPrice= itemView.findViewById(R.id.product_price_new);
            tvOldPrice= itemView.findViewById(R.id.product_price_old);
            imgAddToCart= itemView.findViewById(R.id.img_add_to_cart);
        }
    }
}
