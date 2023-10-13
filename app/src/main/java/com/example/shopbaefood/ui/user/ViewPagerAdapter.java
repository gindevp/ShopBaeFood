package com.example.shopbaefood.ui.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shopbaefood.ui.publicc.HomeFragment;
import com.example.shopbaefood.ui.publicc.UserDetailFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new C01Fragment();
            case 2: return new UserDetailFragment();
            default: return new HomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
