package com.example.shopbaefood.util;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.Product;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Notification {
    public static void sweetAlertNow(Context context, int type, String title, String content, long delaymilis) {
        // Tạo hộp thoại "success" với nút OK vô hiệu hóa
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, type)
                .setTitleText(title)
                .setContentText(content);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng hộp thoại với hiệu ứng
                sweetAlertDialog.dismissWithAnimation();
            }
        }, delaymilis); // 3000 milliseconds = 3 giây

// Hiển thị hộp thoại
        sweetAlertDialog.show();
    }

    public static void sweetAlertNow(Context context, int type, String title, String content) {
        // Tạo hộp thoại "success" với nút OK vô hiệu hóa
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, type)
                .setTitleText(title)
                .setContentText(content);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng hộp thoại với hiệu ứng
                sweetAlertDialog.dismissWithAnimation();
            }
        }, 2000); // 3000 milliseconds = 3 giây

// Hiển thị hộp thoại
        sweetAlertDialog.show();
    }
    public static void sweetAlert(Context context, int type, String title, String content) {
        new SweetAlertDialog(context, type)
                .setTitleText(title)
                .setContentText(content).show();
    }
    public static void confirmationDialog(Context context, String title, String content,
                                          String confirmText, String cancelText,
                                          final OnConfirmationListener listener) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirmText)
                .setCancelText(cancelText)
                .setConfirmClickListener(sDialog -> {
                    if (listener != null) {
                        listener.onConfirm();
                    }
                    sDialog.dismissWithAnimation();
                })
                .setCancelClickListener(sDialog -> {
                    if (listener != null) {
                        listener.onCancel();
                    }
                    sDialog.cancel();
                })
                .show();
    }

    public interface OnConfirmationListener {
        void onConfirm();

        void onCancel();
    }
    public static void showProductDetailDialog(Context context, Product product) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.product_detail_layout, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        ImageView imageView = view.findViewById(R.id.productImageDetail);
        ImageButton imageButton = view.findViewById(R.id.closeButtonPrDetail);
        TextView editName= view.findViewById(R.id.productNameDetail);
        TextView editDes= view.findViewById(R.id.productDescriptionDetail);
        TextView editPriceOld= view.findViewById(R.id.oldPriceDetail);
        TextView editPriceNew= view.findViewById(R.id.newPriceDetail);
        TextView editQuan= view.findViewById(R.id.quantityDetail);
        dialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = 1050; // Chiều rộng tùy ý
        layoutParams.height = 1620; // Chiều cao tùy ý
        dialog.getWindow().setAttributes(layoutParams);
        imageButton.setOnClickListener(v->{
            dialog.cancel();
        });
        UtilApp.getImagePicasso(product.getImage(),imageView);
        editName.setText(product.getName());
        editDes.setText(product.getShortDescription());
        editPriceOld.setText(product.getOldPrice().toString());
        editPriceNew.setText(product.getNewPrice().toString());
        editQuan.setText(String.valueOf(product.getQuantity()));
    }
}
