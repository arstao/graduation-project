package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.UI.widget.EmptyLayout;
import com.arstao.gradesystem.Util.PreferenceHelper;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.adapter.GradeDetailAdapter;
import com.arstao.gradesystem.base.BaseListFragment;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.EmptyResopne;
import com.arstao.gradesystem.bean.GradeDetailBean;

import org.json.JSONArray;
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
    private String ename;
    private String style;
    private Button btn_confitm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_grade_detail;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        btn_confitm = (Button) view.findViewById(R.id.btn_confirm);
         btn_confitm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dataSize = mAdapter.getDataSize();
                JSONArray jsonArray  = new JSONArray();
                Map<String, String> jsonParam = new HashMap<String, String>();
                for (int i=0;i<dataSize;i++) {
                    ViewGroup viewGroup = (ViewGroup) mListView.getChildAt(i);
                    TextView name = (TextView) viewGroup.getChildAt(0);
                    EditText score = (EditText) viewGroup.getChildAt(1);
                    if(score.length()==0){
                        score.setError("成绩不能为空");
                        return;
                    }
                    jsonParam.put("username", (String) name.getTag());
                    jsonParam.put("score", score.getText().toString());
                    jsonArray.put(new JSONObject(jsonParam));
                }
                String url = "http://101.201.72.189/p1/testfinal/json/iss.php";
                Map<String, Object> jsonParams = new HashMap<String, Object>();
                jsonParams.put("data",jsonArray);
                jsonParams.put("jname", PreferenceHelper.getInstance().getValue("user-name"));
                jsonParams.put("ename",ename);
                jsonParams.put("style",style);
                JSONObject jsonObject = new JSONObject(jsonParams);
                JsonRequestToEnity<EmptyResopne> matchRequest = new JsonRequestToEnity<EmptyResopne>(Request.Method.POST, url, jsonObject, EmptyResopne.class, new Response.Listener<EmptyResopne>() {

                    @Override
                    public void onResponse(EmptyResopne resopne) {
                        if(resopne.getCode()>0){
                            AppContext.showToast(R.string.tip_grade_success);
                            getActivity().finish();
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
        });


    }

    private void confirm() {



    }

    @Override
    protected void sendRequestData() {
        String url = "http://101.201.72.189/p1/testfinal/json/get_player_eve.php";
        Map<String, String> jsonParam = new HashMap<String, String>();
        ename = getArguments().getString("Argument","");
        jsonParam.put("ename",ename);
        jsonParam.put("num", "100");
        jsonParam.put("page",String.valueOf(mCurrentPage+1));
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<GradeDetailBean> matchRequest = new JsonRequestToEnity<GradeDetailBean>(Request.Method.POST, url, jsonObject, GradeDetailBean.class, new Response.Listener<GradeDetailBean>() {

            @Override
            public void onResponse(GradeDetailBean gradeDetailBeanhBean) {
                if(gradeDetailBeanhBean.getCode()>0){
                    if(gradeDetailBeanhBean.getData().get(0).getStatue().equals("0")) {
                        btn_confitm.setVisibility(View.VISIBLE);
                    }
                    style = gradeDetailBeanhBean.getData().get(0).getStyle();
                    executeOnLoadDataSuccess(gradeDetailBeanhBean.getData());
                }else{
                    mErrorLayout.setErrorType(EmptyLayout.NODATA);
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
        return new GradeDetailAdapter(true);
    }

}
