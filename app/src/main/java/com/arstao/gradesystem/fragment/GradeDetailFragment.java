package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.adapter.GradeDetailAdapter;
import com.arstao.gradesystem.base.BaseListFragment;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.GradeDetailBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/29 9:17
 * 修改人：Administrator
 * 修改时间：2016/4/29 9:17
 * 修改备注：
 */
public class GradeDetailFragment extends BaseListFragment<GradeDetailBean.Data>{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_grade_detail;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dataSize = mAdapter.getDataSize();
                JSONArray jsonArray  = new JSONArray();
                for (int i=0;i<dataSize;i++){

                    ViewGroup  viewGroup = (ViewGroup) mListView.getChildAt(i);
                    TextView name= (TextView) viewGroup.getChildAt(0);
                    EditText score = (EditText) viewGroup.getChildAt(1);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username",name.getTag());
                        jsonObject.put("score",score.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
jsonArray.put(jsonObject);
                }
            }
        });
    }
    @Override
    protected void sendRequestData() {
        String url = "http://101.201.72.189/p1/testfinal/json/get_player_eve.php";
        Map<String, String> jsonParam = new HashMap<String, String>();
        jsonParam.put("ename",getArguments().getString("Argument",""));
        jsonParam.put("num", "100");
        jsonParam.put("page",String.valueOf(mCurrentPage+1));
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<GradeDetailBean> matchRequest = new JsonRequestToEnity<GradeDetailBean>(Request.Method.POST, url, jsonObject, GradeDetailBean.class, new Response.Listener<GradeDetailBean>() {

            @Override
            public void onResponse(GradeDetailBean gradeDetailBeanhBean) {
                if(gradeDetailBeanhBean.getCode()>0){
                    executeOnLoadDataSuccess(gradeDetailBeanhBean.getData());
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

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new GradeDetailAdapter();
    }

}
