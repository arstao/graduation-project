package com.arstao.gradesystem.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.BaseActivity;
import com.arstao.gradesystem.bean.SimpleBackPage;

/**
 * Created by arstao on 2016/2/24.
 */
public class SimpleBackActivity extends BaseActivity {
    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";

    private  Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragment();


    }
    private Fragment mContentFragment  ;
    private void setFragment() {
        FragmentManager fm = getSupportFragmentManager();
//        mContentFragment = (Fragment) fm.findFragmentById();
        int page = (int) getIntent().getExtras().get(SimpleBackActivity.BUNDLE_KEY_PAGE);
//        String extraString = getIntent().getExtras().getString("Argument");
        Bundle extras = getIntent().getExtras();
        mContentFragment = fm.findFragmentByTag(String.valueOf(page));
        if(mContentFragment == null )
        {
            for (SimpleBackPage simple:
                  SimpleBackPage.values()) {
               if(simple.getValue()==page){
                   try {
                       Fragment f = (Fragment) simple.getClz().newInstance();
//                       Bundle args =new Bundle();
//                       args.putString("Argument", extraString);
                       f.setArguments(extras);
                       mContentFragment=f;
                       setTitle(simple.getTitle());
                       fm.beginTransaction().add(f,String.valueOf(page)).commit();
                       break;
                   } catch (InstantiationException e) {
                       e.printStackTrace();
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   }
                   break;
               }
            }

        }
        fm.beginTransaction().replace(R.id.fl_content,mContentFragment).commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simpleback;
    }

    @Override
    protected Toolbar setToolBar() {
        return (Toolbar)findViewById(R.id.tb_simpleback);
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {

    }
    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.login;
    }
}
