package com.arstao.gradesystem.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.BaseFragment;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/29 9:17
 * 修改人：Administrator
 * 修改时间：2016/4/29 9:17
 * 修改备注：
 */
public class AboutFragment extends BaseFragment{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void initView(View view) {
        TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_version.setText("裁判评分系统"+getVerName(getActivity()));
    }
    private String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo("com.arstao.gradesystem", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verName;
    }
}
