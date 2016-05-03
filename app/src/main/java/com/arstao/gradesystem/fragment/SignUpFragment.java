package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.Volley.VolleyHelper;
import com.arstao.gradesystem.adapter.SignUpAdapter;
import com.arstao.gradesystem.base.BaseListFragment;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.MatchBean;
import com.arstao.gradesystem.bean.UserInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class SignUpFragment extends BaseListFragment<MatchBean.Data> implements SignUpDialogFragment.OnConfirmListener {
    public static final String ARGUMENT = "argument";
    @Override
    protected ListBaseAdapter<MatchBean.Data> getListAdapter() {
        return new SignUpAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SignUpDialogFragment signUpDialogFragment = new SignUpDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARGUMENT,mAdapter.getData().get(position).getEname());
                signUpDialogFragment.setArguments(bundle);
                signUpDialogFragment.setTargetFragment(SignUpFragment.this,0);
                signUpDialogFragment.show(getFragmentManager(),"dialog_sign_up");
            }
        });
    }

    @Override
    protected void sendRequestData() {

        String url = "http://101.201.72.189/p1/testfinal/json/book_applied.php";
            Map<String, Integer> jsonParam = new HashMap<String, Integer>();
            JSONObject jsonObject = new JSONObject(jsonParam);
            JsonRequestToEnity<MatchBean> matchRequest = new JsonRequestToEnity<MatchBean>(Request.Method.POST, url, null, MatchBean.class, new Response.Listener<MatchBean>() {

                @Override
                public void onResponse(MatchBean matchBean) {
                    if(matchBean.getCode()>0){
                        executeOnLoadDataSuccess(matchBean.getData());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    AppContext.showToast(R.string.tip_request_fail);
                }
        });
        mQueue.add(matchRequest);
    }

    private void testSignUp() {
        String url = "http://101.201.72.189/p1/testfinal/json/book_applied.php";
        Map<String, String> jsonParam = new HashMap<String, String>();
        jsonParam.put("username", "aaa");
        jsonParam.put("name", "扣的");
        jsonParam.put("ename", "跳水");
        jsonParam.put("job", "1");
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<UserInfo> matchRequest = new JsonRequestToEnity<UserInfo>(Request.Method.POST, url, jsonObject, UserInfo.class, new Response.Listener<UserInfo>() {

            @Override
            public void onResponse(UserInfo info) {
                if(info.getCode()>0){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppContext.showToast(R.string.tip_request_fail);
            }
        });
        VolleyHelper.getInstance().add(matchRequest);
    }

    private void sendLocalData() {
        MatchBean.Data p = new MatchBean.Data();
        Random random = new Random();
        p.setEname(String.valueOf(random.nextInt()));
        List<MatchBean.Data> data = new ArrayList<MatchBean.Data>();
        for(int i =0;i<10;i++){
            data.add(p);
        }
        executeOnLoadDataSuccess(data);
    }

    public static Fragment newInstance(int num) {
        SignUpFragment f =new SignUpFragment();
        Bundle args =new Bundle();
        args.putInt("Argument", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onConfirm(String string) {
        String url = "http://101.201.72.189/p1/testfinal/json/book_applied.php";
        Map<String, String> jsonParam = new HashMap<String, String>();
        jsonParam.put("username", "aaa");
        jsonParam.put("name", "扣的");
        jsonParam.put("ename", string);
        jsonParam.put("job", "1");
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<UserInfo> matchRequest = new JsonRequestToEnity<UserInfo>(Request.Method.POST, url, jsonObject, UserInfo.class, new Response.Listener<UserInfo>() {

            @Override
            public void onResponse(UserInfo info) {
                if(info.getCode()>0){
                    AppContext.showToast(R.string.tip_sign_up_fail);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppContext.showToast(R.string.tip_sign_up_fail);
            }
        });
        VolleyHelper.getInstance().add(matchRequest);
    }
}
