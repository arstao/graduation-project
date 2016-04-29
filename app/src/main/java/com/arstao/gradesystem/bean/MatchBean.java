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
public class MatchBean extends BaseEnity {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data{
        private String edate;

        public String getEdate() {
            return edate;
        }

        public void setEdate(String edate) {
            this.edate = edate;
        }

        private String eid;
        private String ename;
        private String cname;
        private String eimg;
        private String eshow;
        private String eplace;

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getEshow() {
            return eshow;
        }

        public void setEshow(String eshow) {
            this.eshow = eshow;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getEimg() {
            return eimg;
        }

        public void setEimg(String eimg) {
            this.eimg = eimg;
        }

        public String getEplace() {
            return eplace;
        }

        public void setEplace(String eplace) {
            this.eplace = eplace;
        }
    }

}
