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
import com.arstao.gradesystem.Util.UIHelper;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.adapter.PagerTabAdapter;
import com.arstao.gradesystem.base.BaseListFragment;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.MatchBean;
import com.arstao.gradesystem.bean.SimpleBackPage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GradeFragment extends BaseListFragment<MatchBean.Data>{
public static final String ARGUMENT ="Argument";
    @Override
    protected ListBaseAdapter<MatchBean.Data> getListAdapter() {
        return new PagerTabAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                String ename = mAdapter.getData().get(position).getEname();
                bundle.putString("Argument",ename);
                UIHelper.showSimpleBackWithBundle(getActivity(), SimpleBackPage.GRADE_DETAIL,bundle);
            }
        });
    }

    @Override
    protected void sendRequestData() {
//         sendLocalData();
        String url = "http://101.201.72.189/p1/testfinal/json/get_jug_eve.php";
            Map<String, String> jsonParam = new HashMap<String, String>();
        jsonParam.put("username","12");
            jsonParam.put("num", "2");
        jsonParam.put("page",String.valueOf(mCurrentPage+1));
            JSONObject jsonObject = new JSONObject(jsonParam);
            JsonRequestToEnity<MatchBean> matchRequest = new JsonRequestToEnity<MatchBean>(Request.Method.POST, url, jsonObject, MatchBean.class, new Response.Listener<MatchBean>() {

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
    public static Fragment newInstance(int num) {
        GradeFragment f =new GradeFragment();
        // Supply num input as an argument.
        Bundle args =new Bundle();
        args.putInt("Argument", num);
        f.setArguments(args);
        return f;
    }
}
