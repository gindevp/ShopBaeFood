package com.example.shopbaefood.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.shopbaefood.R;
import com.example.shopbaefood.util.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeUserActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        intent= getIntent();
        if(intent.hasExtra("logSuccess")){
            Notification.sweetAlertNow(this, SweetAlertDialog.SUCCESS_TYPE,"Login success","Bạn đăng nhập thành công");
            intent.removeExtra("logSuccess");
        }
        mViewPager = findViewById(R.id.viewpageUser);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);

        int pageToDisplay = getIntent().getIntExtra("pageToDisplay",0 ); // 0 là trang mặc định nếu không có dữ liệu được truyền
        mViewPager.setCurrentItem(pageToDisplay);
        switch (pageToDisplay) {
            case 0:
                mBottomNavigationView.getMenu().findItem(R.id.tab1_c).setChecked(true);
                break;
            case 1:
                mBottomNavigationView.getMenu().findItem(R.id.tab2_c).setChecked(true);
                break;
            case 2:
                mBottomNavigationView.getMenu().findItem(R.id.tab3_c).setChecked(true);
                break;

        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.tab1_c).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.tab2_c).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.tab3_c).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab1_c:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.tab2_c:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.tab3_c:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {

    }
}