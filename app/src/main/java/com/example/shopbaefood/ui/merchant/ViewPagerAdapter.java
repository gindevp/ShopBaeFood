package com.example.shopbaefood.ui.merchant;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shopbaefood.ui.publicc.P01Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new P01Fragment();
            case 1: return new OrderManagerFragment();
            case 2: return new S02Fragment();
            default: return new P01Fragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
