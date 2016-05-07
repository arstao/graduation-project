package com.arstao.gradesystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.MatchBean;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by arstao on 2016/3/26.
 */
public class PagerTabAdapter extends ListBaseAdapter<MatchBean.Data>{
    private  boolean hasFooterView = true;
    public PagerTabAdapter(boolean hasFooterView) {
        super();
        this.hasFooterView = hasFooterView;
    }
    public PagerTabAdapter() {
        super();
    }
    @Override
    protected boolean hasFooterView() {
        return hasFooterView;
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        convertView = getLayoutInflater(parent.getContext()).inflate(
                R.layout.item_match, null);
        ImageView iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
        TextView tv_ename = (TextView) convertView.findViewById(R.id.tv_ename);
        TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        TextView tv_place = (TextView) convertView.findViewById(R.id.tv_place);
        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);

        MatchBean.Data match =mDatas.get(position);
  String prefix = "http://";
        String uri =prefix + match.getEimg();
        ImageLoader.getInstance().displayImage(uri,iv_pic, AppContext.getUilImageOptions());
tv_ename.setText(match.getEname());
        tv_content.setText(match.getEshow());
        tv_place.setText(match.getEplace());
        tv_type.setText(match.getCname());
        tv_time.setText(match.getEdate());
        return convertView;
    }
}
