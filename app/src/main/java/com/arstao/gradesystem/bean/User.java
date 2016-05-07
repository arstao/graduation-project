package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.base.BaseEnity;

/**
 * Created by arstao on 2016/3/20.
 */
public class User extends BaseEnity {

    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

        private String name;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
