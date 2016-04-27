package com.arstao.gradesystem.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.UI.widget.EmptyLayout;
import com.arstao.gradesystem.Util.TDevice;
import com.arstao.gradesystem.Util.UIHelper;
import com.arstao.gradesystem.bean.Constants;

/**
 * Created by arstao on 2016/2/26.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private EmptyLayout mErrorLayout;
    private boolean mIsWatingLogin = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_LOGOUT);
        filter.addAction(Constants.INTENT_ACTION_USER_CHANGE);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn).setOnClickListener(this);
        view.findViewById(R.id.btn_logout).setOnClickListener(this);
        Button btn = (Button) view.findViewById(R.id.btn);
        mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout_mine);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                UIHelper.showLoginActivity(getActivity());
                break;
            case R.id.btn_logout:
                AppContext.getInstance().Logout();
                break;
        }
    }

    private void requestData(boolean refresh) {
        if (AppContext.getInstance().isLogin()) {
            mIsWatingLogin = false;
//            String key = getCacheKey();
            if (refresh || TDevice.hasInternet()) {
//                    && (!CacheManager.isExistDataCache(getActivity(), key))) {
//                sendRequestData();
            }
//            else {
//                readCacheData(key);
//            }
        } else {
            mIsWatingLogin = true;
        }
        steupUser();
    }


    private void steupUser() {
        if (mIsWatingLogin) {
            mErrorLayout.setVisibility(View.VISIBLE);
//            mUserContainer.setVisibility(View.GONE);
//            mUserUnLogin.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setVisibility(View.GONE);
//            mUserContainer.setVisibility(View.VISIBLE);
//            mUserUnLogin.setVisibility(View.GONE);
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.INTENT_ACTION_LOGOUT)) {
                if (mErrorLayout != null) {
                    mIsWatingLogin = true;
                    steupUser();
//                    mMesCount.hide();
                }
            } else if (action.equals(Constants.INTENT_ACTION_USER_CHANGE)) {
                requestData(true);
            } else if (action.equals(Constants.INTENT_ACTION_NOTICE)) {
//                setNotice();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }
}
