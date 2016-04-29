package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arstao.gradesystem.AppManager;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Util.PreferenceHelper;
import com.arstao.gradesystem.base.BaseFragment;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/29 9:17
 * 修改人：Administrator
 * 修改时间：2016/4/29 9:17
 * 修改备注：
 */
public class SettingFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    private PreferenceHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
//        helper = new PreferenceHelper(getActivity());
        helper=PreferenceHelper.getInstance();

//
//        if(!helper.getValue("user-id","0").equals("0")){
//            getLocalData();
//        }else {
//            requestData();
//        }
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.btn_loginout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.removeAll();
                AppManager.getAppManager().finishAllActivity();
            }
        });
    }
}
