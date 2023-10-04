package com.example.shopbaefood.ui.public_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.MerchantAdapter;
import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.util.UtilApp;

import java.util.ArrayList;
import java.util.List;

public class P01Fragment extends Fragment {

private RecyclerView rcvMerchant;
    public P01Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_p01, container, false);
        rcvMerchant= view.findViewById(R.id.recycler_view_p);

//        GridLayoutManager gridLayoutManager= new GridLayoutManager(view.getContext(),3);
        LinearLayoutManager gridLayoutManager= new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false);
        rcvMerchant.setLayoutManager(gridLayoutManager);
        MerchantAdapter merchantAdapter= new MerchantAdapter(getListMerchant());
        rcvMerchant.setAdapter(merchantAdapter);

        return view;
    }

    private List<Merchant> getListMerchant() {
        List<Merchant> merchantList= new ArrayList<>();

        merchantList.add(new Merchant("quán ăn1","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn2","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn3","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn4","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));
        merchantList.add(new Merchant("quán ăn","Đông anh","https://lh3.googleusercontent.com/iELv7i2dvmivUV4wDDz0uagvWhzTitzTZRKaOEKZcV9u46Z7tKI5j3t1tRRl4KrTsRzUA-0SW6ZpxoZNCQCgoWLpg9yLlvwO7MKx07r7T1vPaFrscVUKDkUtBpbw0UmrmGCXITW7"));

        return merchantList;
    }
}