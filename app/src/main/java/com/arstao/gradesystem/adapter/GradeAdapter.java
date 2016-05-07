package com.arstao.gradesystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.MatchBean;

/**
 * Created by arstao on 2016/3/26.
 */
public class GradeAdapter extends ListBaseAdapter<MatchBean.Data>{
    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        convertView = getLayoutInflater(parent.getContext()).inflate(
                R.layout.item_match, null);
        TextView tv_ename = (TextView) convertView.findViewById(R.id.tv_ename);
        TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        TextView tv_place = (TextView) convertView.findViewById(R.id.tv_place);
        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);

        MatchBean.Data match =mDatas.get(position);

tv_ename.setText(match.getEname());
        tv_content.setText(match.getEshow());
        tv_place.setText(match.getEplace());
        tv_type.setText(match.getCname());
        tv_time.setText(match.getEdate());
        return convertView;
    }
}
