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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PagerTab extends BaseListFragment<MatchBean.Data>{
private  final int pageSize = 7;
    @Override
    protected ListBaseAdapter<MatchBean.Data> getListAdapter() {
        return new PagerTabAdapter();
    }
protected int getKind(){
    Bundle bundle = getArguments();
    int kind =0;
    if(bundle!=null) {
        kind =bundle.getInt("Argument");
    }
    return kind;
}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==mAdapter.getDataSize()){
                    return;
                }
                Bundle bundle = new Bundle();
                Serializable bean = (Serializable) mAdapter.getData().get(position);
                bundle.putSerializable("Argument",bean);
                UIHelper.showSimpleBackWithBundle(getActivity(), SimpleBackPage.MATCH_DETAIL,bundle);
            }
        });
    }

    @Override
    protected void sendRequestData() {

//         sendLocalData();
        String url = "http://101.201.72.189/p1/testfinal/json/get_home_page.php";

            Map<String, Integer> jsonParam = new HashMap<String, Integer>();
            jsonParam.put("num", pageSize);
            jsonParam.put("kind", getKind());
        jsonParam.put("page",mCurrentPage+1);
            JSONObject jsonObject = new JSONObject(jsonParam);
            JsonRequestToEnity<MatchBean> matchRequest = new JsonRequestToEnity<MatchBean>(Request.Method.POST, url, jsonObject, MatchBean.class, new Response.Listener<MatchBean>() {

                @Override
                public void onResponse(MatchBean matchBean) {
                    if(matchBean.getCode()>0){
                        executeOnLoadDataSuccess(matchBean.getData());
                    }else {
                        executeOnLoadDataError();
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
        PagerTab f =new PagerTab();
        // Supply num input as an argument.

        Bundle args =new Bundle();

        args.putInt("Argument", num);

        f.setArguments(args);



        return f;
    }
}
