package com.fire.translation.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class PagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mFragmentTitles;

    public PagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragmentTitles = new ArrayList<>();
    }

    public PagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }
    public List<Fragment> getmFragments() {
        return mFragments;
    }
    public void addFragment(Fragment fragment, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
    public void addFragment(Fragment fragment, String title, int type) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
    public void addFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        mFragments.add(fragment);
        mFragmentTitles.add(bundle.getString("title"));
    }
    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position < mFragmentTitles.size()) {
            return mFragmentTitles.get(position);
        } else {
            return "";
        }
    }

    // 动态设置我们标题的方法
    public void setPageTitle(int position, String title) {
        if(position >= 0 && position < mFragmentTitles.size()) {
            mFragmentTitles.set(position, title);
            notifyDataSetChanged();
        }
    }
}

