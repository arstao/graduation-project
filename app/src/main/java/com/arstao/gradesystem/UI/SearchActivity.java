package com.arstao.gradesystem.UI;

import android.support.v7.widget.Toolbar;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.BaseActivity;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/9 11:39
 * 修改人：Administrator
 * 修改时间：2016/5/9 11:39
 * 修改备注：
 */
public class SearchActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected Toolbar setToolBar() {
        return (Toolbar) findViewById(R.id.tb_search);
    }
    @Override
    public void initView() {
        mActionBar.setTitle("");
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void initData() {

    }
}
