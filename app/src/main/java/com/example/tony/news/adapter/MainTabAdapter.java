package com.example.tony.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tony.news.Fragment.FirstpagerFragment;

import java.util.List;

/**
 * Created by Tony on 16/11/18.
 */

public class MainTabAdapter extends FragmentPagerAdapter {

    private List<FirstpagerFragment> mList_fragment;
    private String[] mList_title;

    public MainTabAdapter(FragmentManager fm, List<FirstpagerFragment> list_fragment, String[] list_title) {
        super(fm);
        //赋值
        mList_fragment =  list_fragment;
        mList_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return mList_fragment.get(position);
    }

    @Override
    public int getCount() {
        return mList_fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList_title[position];
    }
}
