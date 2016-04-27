package com.arstao.gradesystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.PhoneInforamation;

/**
 * Created by arstao on 2016/3/26.
 */
public class PagerTabAdapter extends ListBaseAdapter<PhoneInforamation>{
    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        convertView = getLayoutInflater(parent.getContext()).inflate(
                R.layout.list_cell_pagertab, null);
        TextView phone = (TextView) convertView.findViewById(R.id.tv_telphone);
        TextView castName = (TextView) convertView.findViewById(R.id.tv_castName);
        TextView province = (TextView) convertView.findViewById(R.id.tv_province);

        PhoneInforamation p =mDatas.get(position);
phone.setText(p.getTelString());
        castName.setText(p.getCastName());
        province.setText(p.getProvince());
        return convertView;
    }
}
