package com.example.shopbaefood.ui.merchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.shopbaefood.R;
import com.example.shopbaefood.model.dto.AccountToken;
import com.example.shopbaefood.service.MyFirebaseService;
import com.example.shopbaefood.util.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeMerchantActivity extends AppCompatActivity {
    private BroadcastReceiver receiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            // Xử lý thông báo từ service ở đây
            Log.d("onMes","onMes");
            Notification.sweetAlert(HomeMerchantActivity.this, SweetAlertDialog.WARNING_TYPE, "Notification", message );
        }
    };
    private AccountToken accountToken;
    private Gson gson;
    private SharedPreferences info;
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_merchant);

        Log.d("onCreate","onCreate");
        gson= new Gson();
        info=getSharedPreferences("info",Context.MODE_PRIVATE);
        accountToken= gson.fromJson(info.getString("info",""),AccountToken.class);
        intent = getIntent();
        if (intent.hasExtra("logSuccess")) {
            Notification.sweetAlertNow(this, SweetAlertDialog.SUCCESS_TYPE, "Login success", "Bạn đăng nhập thành công");
            intent.removeExtra("logSuccess");
        }
        mViewPager = findViewById(R.id.viewpageMerchant);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);

        int pageToDisplay = getIntent().getIntExtra("pageToDisplay", 0); // 0 là trang mặc định nếu không có dữ liệu được truyền
        mViewPager.setCurrentItem(pageToDisplay);
        switch (pageToDisplay) {
            case 0:
                mBottomNavigationView.getMenu().findItem(R.id.tab1_s).setChecked(true);
                break;
            case 1:
                mBottomNavigationView.getMenu().findItem(R.id.tab2_s).setChecked(true);
                break;
            case 2:
                mBottomNavigationView.getMenu().findItem(R.id.tab3_s).setChecked(true);
                break;
            case 3:
                mBottomNavigationView.getMenu().findItem(R.id.tab4_s).setChecked(true);
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
                        mBottomNavigationView.getMenu().findItem(R.id.tab1_s).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.tab2_s).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.tab3_s).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.tab4_s).setChecked(true);
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab1_s:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.tab2_s:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.tab3_s:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.tab4_s:
                    mViewPager.setCurrentItem(3);
                    break;

            }
            return true;
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart","onStart");
        MyFirebaseService.subscribeToTopic("order_merchant_"+accountToken.getMerchant().getId());
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("realtime"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop","onStop");
//        MyFirebaseService.unsubscribeFromTopic("order_merchant_"+accountToken.getMerchant().getId());
        // Hủy đăng ký receiver khi activity bị dừng
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {

    }
}