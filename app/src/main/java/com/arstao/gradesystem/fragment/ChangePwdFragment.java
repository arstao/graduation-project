package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Util.PreferenceHelper;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.Volley.VolleyHelper;
import com.arstao.gradesystem.base.BaseFragment;
import com.arstao.gradesystem.bean.EmptyResopne;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/6 15:19
 * 修改人：Administrator
 * 修改时间：2016/5/6 15:19
 * 修改备注：
 */
public class ChangePwdFragment extends BaseFragment  {
    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_change_pwd;
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
        final EditText et_old_pwd = (EditText) view.findViewById(R.id.et_old_pwd);
        final  EditText et_new_pwd = (EditText) view.findViewById(R.id.et_new_pwd);
        final EditText et_new_pwd2 = (EditText) view.findViewById(R.id.et_new_pwd2);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String url ="http://101.201.72.189/p1/testfinal/json/edit_password.php";
                Map<String, String> jsonParam = new HashMap<String, String>();
                String job = PreferenceHelper.getInstance().getValue("user-job", "");
                if(job.equals("裁判")){
                    job ="0";
                }else {
                    job="1";
                }
                jsonParam.put("username", PreferenceHelper.getInstance().getValue("user-username",""));
                jsonParam.put("job", job);
                jsonParam.put("password",PreferenceHelper.getInstance().getValue("user-pwd",""));
                jsonParam.put("password3",et_old_pwd.getText().toString());
                jsonParam.put("password1",et_new_pwd.getText().toString());
                jsonParam.put("password2",et_new_pwd2.getText().toString());
                JSONObject jsonObject = new JSONObject(jsonParam);
                JsonRequestToEnity<EmptyResopne> matchRequest = new JsonRequestToEnity<EmptyResopne>(Request.Method.POST, url, jsonObject, EmptyResopne.class, new Response.Listener<EmptyResopne>() {

                    @Override
                    public void onResponse(EmptyResopne matchBean) {
                        if(matchBean.getCode()>0) {
                            PreferenceHelper.getInstance().setValue("user-pwd",et_new_pwd.getText().toString());
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
        });
    }
}
