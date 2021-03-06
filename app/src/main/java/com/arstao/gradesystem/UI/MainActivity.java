package com.arstao.gradesystem.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.arstao.gradesystem.AppManager;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Util.PreferenceHelper;
import com.arstao.gradesystem.Util.UIHelper;
import com.arstao.gradesystem.bean.SimpleBackPage;
import com.arstao.gradesystem.fragment.NavigationDrawerFragment;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,TabHost.OnTabChangeListener {
    public FragmentTabHost mTabHost;
    private Toolbar toolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();


    }


    public void initView() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        initTabs();
    }

    private void initTabs() {
        mTabHost =(FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
//        PreferenceHelper helper = new PreferenceHelper(MainActivity.this);
        PreferenceHelper helper=PreferenceHelper.getInstance();

        boolean isPlayer = helper.getValue("user-job").equals("选手");
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            if(mainTab.getResName()==R.string.tab_grade&&isPlayer){
                continue;
            }
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            tab.setIndicator(getTabItemView(mainTab));
//            tab.setContent(new TabHost.TabContentFactory() {
//                @Override
//                public View createTabContent(String tag) {
//                    return new View(MainActivity.this);
//                }
//            });
            mTabHost.addTab(tab, mainTab.getClz(), null);
            mTabHost.setOnTabChangedListener(this);
        }
    }

    private View getTabItemView(MainTab mainTab) {
        View view = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mainTab.getResIcon());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(150,150));
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mainTab.getResName());
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                UIHelper.showSimpleBack(this, SimpleBackPage.SEARCH);
//                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onTouch(View w, MotionEvent event) {
        return false;
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }
}
