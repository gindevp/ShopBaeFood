package com.example.shopbaefood.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Cart;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartList;
    private TextView total;
    public CartAdapter(List<Cart> cartList, TextView total) {
        this.cartList = cartList;
        this.total=total;
        int totall = 0;
        for (Cart cart : cartList
        ) {
            totall += cart.getTotalPrice();
        }
        this.total.setText(totall + " đ");
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        if (cart == null) {
            return;
        }
        UtilApp.getImagePicasso(cart.getProduct().getImage(), holder.imgCart);
        holder.cartProName.setText(cart.getProduct().getName());
        holder.cartProTotal.setText(String.valueOf(cart.getQuantity()));
        holder.imgCartUp.setOnClickListener(v -> {
            // TODO: them code add to card kèm alert
            Long proId = cart.getProduct().getId();
            holder.imgCartUp.setImageResource(R.drawable.add_to_cart_checked);
            new Handler().postDelayed(() -> {
                holder.imgCartUp.setImageResource(R.drawable.add_to_cart);
            }, 200);
            ApiService apiService = UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
            Call<ApiResponse> call = apiService.addToCart(proId, cart.getUser().getId());
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        //thêm thành công
                        holder.cartProTotal.setText(String.valueOf(
                                Integer.parseInt(String.valueOf(holder.cartProTotal.getText())) + 1
                        ));
                        setTotal((int) (cart.getProduct().getNewPrice()*1),"plus");
                    } else {
                        //thêm không thành công
                        Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Tăng không thành công");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    // Thông báo lỗi do hệ thống
                    Log.d("err", t.getMessage());
                    Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error server", "Lỗi hệ thống phía server");
                }
            });
        });
        holder.imgCartDown.setOnClickListener(v -> {
            // TODO: them code add to card kèm alert
            Long proId = cart.getProduct().getId();
            holder.imgCartDown.setImageResource(R.drawable.minus_clicked);
            new Handler().postDelayed(() -> {
                holder.imgCartDown.setImageResource(R.drawable.minus);
            }, 200);
            ApiService apiService = UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);

            if (Integer.parseInt(String.valueOf(holder.cartProTotal.getText())) == 1) {
                UtilApp.confirmationDialog(v.getContext(), "Xóa rỏ", "Giảm xuống 0 sẽ bị xóa bạn chắc có muốn xóa", "Xác nhận", "Hủy", new UtilApp.OnConfirmationListener() {
                    @Override
                    public void onConfirm() {
                        Call<ApiResponse> call1 = apiService.remove(cart.getProduct().getId());
                        call1.enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response1) {
                                if (response1.isSuccessful()) {
                                    setTotal((int) (Integer.parseInt(String.valueOf(holder.cartProTotal.getText()))* cart.getProduct().getNewPrice()),"minus");
                                    int position = holder.getAdapterPosition(); // Lấy vị trí của mục trong RecyclerView
                                    if (position != RecyclerView.NO_POSITION) {
                                        // Xác định vị trí hợp lệ, sau đó xóa mục khỏi danh sách dữ liệu
                                        cartList.remove(position);
                                        // Cập nhật RecyclerView để thực hiện xóa
                                        notifyItemRemoved(position);
                                    }
                                    Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "Xóa thành công");
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Xóa không thành công");
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            } else {
                Call<ApiResponse> call = apiService.downCart(proId, cart.getUser().getId());
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            holder.cartProTotal.setText(String.valueOf(
                                    Integer.parseInt(String.valueOf(holder.cartProTotal.getText())) - 1
                            ));
                            setTotal((int) (cart.getProduct().getNewPrice()*1),"minus");
                        } else {
                            //thêm không thành công
                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Giảm không thành công");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        // Thông báo lỗi do hệ thống
                        Log.d("err", t.getMessage());
                        Notification.sweetAlert(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error server", "Lỗi hệ thống phía server");
                    }
                });
            }
        });
        holder.imgCartClose.setOnClickListener(v -> {
            ApiService apiService = UtilApp.retrofitAuth(v.getContext()).create(ApiService.class);
            UtilApp.confirmationDialog(v.getContext(), "Xóa rỏ", "Bạn có chắc muốn xóa rỏ hàng này không", "Xác nhận", "Hủy", new UtilApp.OnConfirmationListener() {
                @Override
                public void onConfirm() {
                    Call<ApiResponse> call1 = apiService.remove(cart.getProduct().getId());
                    call1.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response1) {
                            if (response1.isSuccessful()) {
                                setTotal((int) (Integer.parseInt(String.valueOf(holder.cartProTotal.getText()))* cart.getProduct().getNewPrice()),"minus");
                                int position1 = holder.getAdapterPosition(); // Lấy vị trí của mục trong RecyclerView
                                if (position1 != RecyclerView.NO_POSITION) {
                                    // Xác định vị trí hợp lệ, sau đó xóa mục khỏi danh sách dữ liệu
                                    cartList.remove(position1);
                                    // Cập nhật RecyclerView để thực hiện xóa
                                    notifyItemRemoved(position1);
                                }
                                Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.SUCCESS_TYPE, "Success", "Xóa thành công");
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Notification.sweetAlertNow(v.getContext(), SweetAlertDialog.ERROR_TYPE, "Error", "Xóa không thành công");
                        }
                    });
                }

                @Override
                public void onCancel() {
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        if (cartList != null) {
            return cartList.size();
        }
        return 0;
    }

    private void setTotal(Integer price, String cal) {
        String totalS = String.valueOf(total.getText());

        Log.d("stringg",total.getText().toString());
        Log.d("stringg",totalS.substring(0, totalS.length() - 2));
        if ("plus".equals(cal)) {
            total.setText(
                    (Integer.parseInt(totalS.substring(0, totalS.length() - 2)) + price) + " đ"
            );
        } else {
            total.setText(
                    (Integer.parseInt(totalS.substring(0, totalS.length() - 2)) - price) + " đ"
            );
        }
    }
    public void clearCart() {
        total.setText("0 đ");
        cartList.clear();
        notifyDataSetChanged(); // Cập nhật RecyclerView để hiển thị danh sách trống
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCart;
        private TextView cartProName;
        private TextView cartProTotal;
        private ImageView imgCartDown;
        private ImageView imgCartUp;
        private ImageView imgCartClose;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.image_cart_product);
            cartProName = itemView.findViewById(R.id.product_cart_name);
            cartProTotal = itemView.findViewById(R.id.product_cart_total);
            imgCartDown = itemView.findViewById(R.id.img_cart_down);
            imgCartUp = itemView.findViewById(R.id.img_cart_up);
            imgCartClose = itemView.findViewById(R.id.img_cart_close);
        }

    }
}
