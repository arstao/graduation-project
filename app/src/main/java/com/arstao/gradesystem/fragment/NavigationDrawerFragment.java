package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.BaseFragment;
import com.arstao.gradesystem.bean.SimpleBackPage;
import com.arstao.gradesystem.Util.UIHelper;

/**
 * Created by arstao on 2016/2/24.
 */
public class NavigationDrawerFragment extends BaseFragment {
    private String[] mPlanetTitles={"s","ss","sss","sssaa"}; // listView的每一个item的名字
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle; // 用来监听DrawerLayout事件
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerList = (ListView)  inflater.inflate(R.layout.navigation_drawer,
                container, false);
        initView();
        initData();
        return mDrawerList;
    }
   public  void  initView(){
       // 为ListView添加适配器
       mDrawerList.setAdapter((ListAdapter) new ArrayAdapter<String>(getActivity(),
               android.R.layout.simple_list_item_1, mPlanetTitles));
       // 监听ListView的点击事件
       mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


   }
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // 根据ListView的被选中的选项切换界面布局
//            Fragment fragment = new Fragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.realtabcontent, fragment).commit();
            UIHelper.showSimpleBack(getActivity(), SimpleBackPage.QUESTS);
            mDrawerList.setItemChecked(position, true);
            // 给操作栏设置标题
//            getActionBar().setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
    public void setUp(int fragmentId, DrawerLayout drawerLayout,Toolbar toolbar) {
//        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // 监听DrawerLayout的监听事件
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
                toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActivity().invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
