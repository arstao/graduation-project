package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.UI.widget.EmptyLayout;
import com.arstao.gradesystem.Util.UIHelper;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.adapter.PagerTabAdapter;
import com.arstao.gradesystem.base.BaseListFragment;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.MatchBean;
import com.arstao.gradesystem.bean.SimpleBackPage;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/9 10:47
 * 修改人：Administrator
 * 修改时间：2016/5/9 10:47
 * 修改备注：
 */
public class SearchFragment extends BaseListFragment<MatchBean.Data> {
    @Override
    protected ListBaseAdapter getListAdapter()  {
        return new PagerTabAdapter(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        final EditText et_search = (EditText) view.findViewById(R.id.et_search);
        Button btn_search = (Button) view.findViewById(R.id.btn_search);
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://101.201.72.189/p1/testfinal/json/get_home_page.php";
                Map<String, Integer> jsonParam = new HashMap<String, Integer>();
                jsonParam.put("num", 100);
                jsonParam.put("kind", 0);
                jsonParam.put("page",mCurrentPage+1);
                JSONObject jsonObject = new JSONObject(jsonParam);
                JsonRequestToEnity<MatchBean> matchRequest = new JsonRequestToEnity<MatchBean>(Request.Method.POST, url, jsonObject, MatchBean.class, new Response.Listener<MatchBean>() {

                    @Override
                    public void onResponse(MatchBean matchBean) {
                        if(matchBean.getCode()>0){
                            ArrayList<MatchBean.Data> datas = new ArrayList<>();
                            for (MatchBean.Data bean:matchBean.getData()){
                                 if (et_search.getText().toString().equals(bean.getEname()))
                                     datas.add(bean);
                            }
                            executeOnLoadDataSuccess(datas);
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
        });
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
    protected boolean onlyOne() {
        return  true;
    }
}
