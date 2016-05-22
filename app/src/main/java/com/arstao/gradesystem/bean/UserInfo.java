package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.base.BaseEnity;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/28 16:57
 * 修改人：Administrator
 * 修改时间：2016/4/28 16:57
 * 修改备注：
 */
public class UserInfo extends BaseEnity {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private String pname;
        private String sex;
        private String jemail;
        private String username;
        private String face;

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getJemail() {
            return jemail;
        }

        public void setJemail(String jemail) {
            this.jemail = jemail;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
