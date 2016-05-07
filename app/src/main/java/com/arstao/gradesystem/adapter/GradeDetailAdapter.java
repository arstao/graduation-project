package com.arstao.gradesystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.GradeDetailBean;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/3 14:56
 * 修改人：Administrator
 * 修改时间：2016/5/3 14:56
 * 修改备注：
 */
public class GradeDetailAdapter extends ListBaseAdapter<GradeDetailBean.Data> {
    private  boolean isGrade =false;
    public GradeDetailAdapter(){
    super();
    }
    public GradeDetailAdapter(boolean isGrade){
        this.isGrade =isGrade;
    }
    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        convertView = getLayoutInflater(parent.getContext()).inflate(
                R.layout.item_grade_detail, null);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_score = (TextView) convertView.findViewById(R.id.tv_score);
        EditText et_score = (EditText) convertView.findViewById(R.id.et_score);
        GradeDetailBean.Data item =mDatas.get(position);
        tv_name.setText(item.getPname());
        //有成绩则显示成绩，没有就输入成绩提交
        if(item.getStatue().equals("1")){
          tv_score.setText(item.getScore());
            tv_score.setVisibility(View.VISIBLE);
        }
        else {
            tv_name.setTag(item.getUsername());
            if(isGrade) {
                if (item.getStyle().equals("0")) {
                    et_score.setHint(R.string.hint_score_time);
                } else {
                    et_score.setHint(R.string.hint_score);
                }
                et_score.setVisibility(View.VISIBLE);
            }else {
                tv_score.setText("选手暂无成绩");
                tv_score.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    @Override
    protected boolean hasFooterView() {
        return false;
    }
}
