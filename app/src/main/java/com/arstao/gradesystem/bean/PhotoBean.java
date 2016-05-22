package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.base.BaseEnity;

import java.util.List;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/28 9:33
 * 修改人：Administrator
 * 修改时间：2016/4/28 9:33
 * 修改备注：
 */
public class PhotoBean extends BaseEnity{

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        private String photo;
    }

}
