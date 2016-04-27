package com.arstao.gradesystem.UI;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.BaseActivity;

/**
 * Created by arstao on 2016/2/24.
 */
public class SimpleBackActivity extends BaseActivity {

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";

    private  Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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
