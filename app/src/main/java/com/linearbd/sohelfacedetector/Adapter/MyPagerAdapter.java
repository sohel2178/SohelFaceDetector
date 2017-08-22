package com.linearbd.sohelfacedetector.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genius 03 on 8/22/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager= fragmentManager;
        initFragmentList();
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
