package com.arstao.gradesystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arstao.gradesystem.UI.MainPagerTab;
import com.arstao.gradesystem.fragment.PagerTab;

import java.util.List;

/**
 * Created by arstao on 2016/3/19.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = MyFragmentAdapter.class.getSimpleName();
private  MainPagerTab[] mTabs =MainPagerTab.values();
    private List<String> titles;
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentAdapter(FragmentManager fragmentManager, List<String> titles) {
        super(fragmentManager);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==1){
            position =5;
        }else if(position==2){
            position=4;
        }
        return PagerTab.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
