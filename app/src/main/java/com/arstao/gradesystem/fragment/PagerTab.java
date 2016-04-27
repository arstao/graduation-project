package com.arstao.gradesystem.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.arstao.gradesystem.adapter.PagerTabAdapter;
import com.arstao.gradesystem.base.BaseListFragment;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.PhoneInforamation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PagerTab extends BaseListFragment<PhoneInforamation>{
    private TabLayout mTablyout;
    private ViewPager mViewPager;

    @Override
    protected ListBaseAdapter<PhoneInforamation> getListAdapter() {
        return new PagerTabAdapter();
    }

    @Override
    protected void sendRequestData() {
//        RequestQueue mQueue = Volley.newRequestQueue(getContext());

        MyStringRequest stringRequest = new MyStringRequest("http://www.baidu.com",
                new MyResponseListener(),new MyErrorListener());
        stringRequest.setShouldCache(true);
        mQueue.add(stringRequest);
    }

    @Override
    protected List<PhoneInforamation> parseList(String response) {
        PhoneInforamation p = new PhoneInforamation();
        Random random = new Random();

        p.setCastName( String.valueOf(random.nextInt())   );
        p.setProvince(String.valueOf(random.nextInt()) );
        p.setTelString(String.valueOf(random.nextInt()) );

        List data = new ArrayList<PhoneInforamation>();
        for(int i =0;i<10;i++){
            data.add(p);
        }
        return data;
    }
}
