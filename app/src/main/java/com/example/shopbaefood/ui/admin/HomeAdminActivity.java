package com.example.shopbaefood.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.shopbaefood.R;
import com.example.shopbaefood.util.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeAdminActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        Log.d("Activity","homeAdmin");
        mViewPager= findViewById(R.id.viewpageAdmin);
        mBottomNavigationView= findViewById(R.id.bottom_navigation);

        Intent intent= getIntent();
        if(intent.hasExtra("logSuccess")){
            Notification.sweetAlertNow(this,SweetAlertDialog.SUCCESS_TYPE,"Login success","Bạn đăng nhập thành công");
        }
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 0: mBottomNavigationView.getMenu().findItem(R.id.tab1_m).setChecked(true);
                        break;
                        case 1: mBottomNavigationView.getMenu().findItem(R.id.tab2_m).setChecked(true);
                        break;
                        case 2: mBottomNavigationView.getMenu().findItem(R.id.tab3_m).setChecked(true);
                        break;
                        case 3: mBottomNavigationView.getMenu().findItem(R.id.tab4).setChecked(true);
                        break;
                        case 4: mBottomNavigationView.getMenu().findItem(R.id.tab5).setChecked(true);
                        break;
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab1_m:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.tab2_m:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.tab3_m:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.tab4:
                    mViewPager.setCurrentItem(3);
                    break;
                case R.id.tab5:
                    mViewPager.setCurrentItem(4);
                    break;

            }

            return true;
        });
    }

    @Override
    public void onBackPressed() {
        Notification.sweetAlertNow(this,SweetAlertDialog.NORMAL_TYPE,"Không thể quay lại","",1000);
    }
}