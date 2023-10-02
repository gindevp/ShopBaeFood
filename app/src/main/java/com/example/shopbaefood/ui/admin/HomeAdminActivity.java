package com.example.shopbaefood.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shopbaefood.R;
import com.example.shopbaefood.ui.NavAdminFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdminActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        mViewPager= findViewById(R.id.viewpageAdmin);
        mBottomNavigationView= findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 0: mBottomNavigationView.getMenu().findItem(R.id.tab1).setChecked(true);
                        break;
                        case 1: mBottomNavigationView.getMenu().findItem(R.id.tab2).setChecked(true);
                        break;
                        case 2: mBottomNavigationView.getMenu().findItem(R.id.tab3).setChecked(true);
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
                case R.id.tab1:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.tab2:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.tab3:
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
}