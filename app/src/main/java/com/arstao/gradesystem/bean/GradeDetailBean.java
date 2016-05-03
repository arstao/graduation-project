package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.base.BaseEnity;

import java.util.List;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/3 15:13
 * 修改人：Administrator
 * 修改时间：2016/5/3 15:13
 * 修改备注：
 */
public class GradeDetailBean extends BaseEnity {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data{
        private String pname;
        private String username;
        private String style;

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }
    }
}
