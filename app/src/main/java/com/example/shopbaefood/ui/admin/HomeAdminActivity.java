package com.example.shopbaefood.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shopbaefood.R;
import com.example.shopbaefood.ui.NavAdminFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Fragment();
                switch (item.getItemId()) {
                    case 1:
                        fragment = new NavAdminFragment();
                        break;
                    case 2:
//                        fragment = new DashboardFragment();
                        break;
                    case 3:
//                        fragment = new NotificationsFragment();
                        break;
                    default:
                        return false;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_nav_home, fragment)
                        .commit();

                return true;
            }
        });
    }
}