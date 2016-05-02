package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Util.PreferenceHelper;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.Volley.VolleyHelper;
import com.arstao.gradesystem.base.BaseFragment;
import com.arstao.gradesystem.bean.UserInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arstao on 2016/2/26.
 */
public class MineFragment extends BaseFragment {

    private TextView tv_username;
    private TextView tv_job;
    private TextView tv_sex;
    private TextView tv_email;
    private PreferenceHelper helper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_information;
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
//        helper = new PreferenceHelper(getActivity());
        helper=PreferenceHelper.getInstance();

    }

    @Override
    public void onStart() {
        if(!helper.getValue("user-id","0").equals("0")){
            getLocalData();
        }else {
            requestData();
        }
        super.onStart();
    }

    private void getLocalData() {
        tv_email.setText(helper.getValue("user-email"));
        tv_job.setText(helper.getValue("user-job"));
        tv_username.setText(helper.getValue("user-name"));
        tv_sex.setText(helper.getValue("user-sex"));
    }

    private void requestData() {
        String url = "http://101.201.72.189/p1/testfinal/json/get_user_mess.php";
        Map<String, Integer> jsonParam = new HashMap<String, Integer>();
        int job = 0;
        if(helper.getValue("user-job").equals("选手")){
            job =1;
        }
        String id =helper.getValue("user-id");
        jsonParam.put("id", Integer.valueOf(helper.getValue("user-id")));
        jsonParam.put("job", job);
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<UserInfo> matchRequest = new JsonRequestToEnity<UserInfo>(Request.Method.POST, url, jsonObject, UserInfo.class, new Response.Listener<UserInfo>() {

            @Override
            public void onResponse(UserInfo info) {
                if(info.getCode()>0){
                tv_email.setText(info.getData().getPemail());
                    tv_job.setText(helper.getValue("user-job"));
                    tv_username.setText(info.getData().getUsername());
                    tv_sex.setText(info.getData().getSex());

                    helper.setValue("user-email",info.getData().getPemail());
                    helper.setValue("user-name",info.getData().getUsername());
                    helper.setValue("user-sex",info.getData().getSex());
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


    @Override
    public void initView(View view) {
        view.findViewById(R.id.doctor_icon);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        tv_job = (TextView) view.findViewById(R.id.tv_job);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
    }


}
