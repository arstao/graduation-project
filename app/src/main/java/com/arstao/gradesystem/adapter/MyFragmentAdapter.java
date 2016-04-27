package com.arstao.gradesystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.arstao.gradesystem.UI.MainPagerTab;

import java.util.List;

/**
 * Created by arstao on 2016/3/19.
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {
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
               if (mTabs[position].getClz()==null){
                   Log.i(TAG,"pager "+"null");
                   return  null;
               }
               else {
                   try {
                       Log.i(TAG,"pager "+titles.get(position));
                       return (Fragment) mTabs[position].getClz().newInstance();
                   } catch (InstantiationException e) {
                       e.printStackTrace();
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   }

               }
             return  null;
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
