package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.UI.MainPagerTab;
import com.arstao.gradesystem.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arstao on 2016/2/24.
 */
public class QuestsFragment extends Fragment {
    private static final String TAG = QuestsFragment.class.getSimpleName();
    private  MainPagerTab[] mTabs =MainPagerTab.values();
    private TabLayout mTablyout;
    private ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"create");
        View view = inflater.inflate(R.layout.sss, null);
        mTablyout = (TabLayout) view.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_main);
            initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    private void initView() {
        MainPagerTab[] tabs = MainPagerTab.values();
        final int size = tabs.length;
        List<String> titles = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
        mTablyout.addTab(mTablyout.newTab().setText(tabs[i].getResName()));
            titles.add(getString(tabs[i].getResName()));
        }
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(),titles);
        mTablyout.setTabsFromPagerAdapter(myFragmentAdapter);
        mViewPager.setAdapter(myFragmentAdapter);
            mTablyout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "destroy");
    }
}
