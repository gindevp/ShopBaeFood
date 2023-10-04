package com.example.shopbaefood.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

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
}
