package com.example.shopbaefood.ui.merchant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopbaefood.R;
import com.example.shopbaefood.ui.LoginActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link S02Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class S02Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public S02Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment S02Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static S02Fragment newInstance(String param1, String param2) {
        S02Fragment fragment = new S02Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_s02, container, false);

        Button button = view.findViewById(R.id.logout1);
        button.setOnClickListener(v -> {
            SweetAlertDialog sweetAlertDialog= new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Logout?")
                    .setContentText("Bạn có chắc chắn muốn đăng xuất khỏi thiết bị này?")
                    .setConfirmText("Có")
                    .setConfirmClickListener(sDialog -> {
                        // Xử lý khi người dùng xác nhận
                        sDialog.dismissWithAnimation(); // Đóng hộp thoại sau khi xử lý
                        // Thêm mã xử lý xóa dữ liệu ở đây
                        SharedPreferences info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = info.edit();
                        editor.remove("info");
                        editor.apply();
                        Intent intent= new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);
                    })
                    .setCancelText("Hủy")
                    .setCancelClickListener(sDialog -> {
                        // Xử lý khi người dùng hủy bỏ
                        sDialog.cancel();
                    });
            sweetAlertDialog.show();
        });
        return view;
    }
}