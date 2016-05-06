package com.arstao.gradesystem.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.arstao.gradesystem.bean.MatchBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/5 16:03
 * 修改人：Administrator
 * 修改时间：2016/5/5 16:03
 * 修改备注：
 */
public class MatchDetailFragment extends BaseListFragment<GradeDetailBean.Data> {
    private MatchBean.Data bean;
    @Override
    protected ListBaseAdapter<GradeDetailBean.Data> getListAdapter() {
        return new GradeDetailAdapter();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_detail;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        bean = (MatchBean.Data)  getArguments().getSerializable("Argument");
        super.onViewCreated(view, savedInstanceState);

    }
    public void initView(View view) {
        super.initView(view);

        TextView tv_ename = (TextView) view.findViewById(R.id.tv_ename);
        TextView tv_place = (TextView) view.findViewById(R.id.tv_place);
        TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        tv_ename.setText(bean.getEname());
        tv_place.setText(bean.getEplace());
        tv_type.setText(bean.getCname());
        tv_content.setText(bean.getEshow());
        String prefix = "http://";
        String uri =prefix + bean.getEimg();
        ImageLoader.getInstance().displayImage(uri,iv_pic, AppContext.getUilImageOptions());

    }
    @Override
    protected void sendRequestData() {
        String url = "http://101.201.72.189/p1/testfinal/json/get_player_eve.php";
        Map<String, String> jsonParam = new HashMap<String, String>();
        jsonParam.put("ename",bean.getEname());
        jsonParam.put("num", "100");
        jsonParam.put("page",String.valueOf(mCurrentPage+1));
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<GradeDetailBean> matchRequest = new JsonRequestToEnity<GradeDetailBean>(Request.Method.POST, url, jsonObject, GradeDetailBean.class, new Response.Listener<GradeDetailBean>() {

            @Override
            public void onResponse(GradeDetailBean gradeDetailBeanhBean) {
                if(gradeDetailBeanhBean.getCode()>0){
                    executeOnLoadDataSuccess(gradeDetailBeanhBean.getData());
                }else{
                    AppContext.showToast(R.string.no_player);
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
}
