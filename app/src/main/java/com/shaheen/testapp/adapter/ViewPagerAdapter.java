package com.shaheen.testapp.adapter;


import com.shaheen.testapp.fragment.FragmentFeatured;
import com.shaheen.testapp.fragment.FragmentStars;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String title[] = {"Stars","Featured"};

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return FragmentStars.getInstance(position);
        }
        else if (position==1){
            return FragmentFeatured.getInstance(position);
        }
        else return null;
    }


    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
