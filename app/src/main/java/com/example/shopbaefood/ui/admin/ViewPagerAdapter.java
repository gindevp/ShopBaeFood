package com.example.shopbaefood.ui.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new UserManagerFragment();
            case 1: return new MerManagerFragment();
            case 2: return new M03Fragment();
            case 3: return new M04Fragment();
            case 4: return new M05Fragment();
            default: return new UserManagerFragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
