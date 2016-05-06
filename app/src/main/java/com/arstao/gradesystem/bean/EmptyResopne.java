package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.base.BaseEnity;

import java.util.List;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/4 10:22
 * 修改人：Administrator
 * 修改时间：2016/5/4 10:22
 * 修改备注：
 */
public class EmptyResopne extends BaseEnity {
    private List<Object> data;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
