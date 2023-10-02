package com.example.shopbaefood.util;

import android.view.View;
import android.widget.Toast;

public class Notification {
    public static void showToast(View v,String message) {
        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
