package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.base.Enity;

/**
 * Created by arstao on 2016/3/26.
 */
public class PhoneInforamation extends Enity {
private String province;
    private  String castName;
    private String telString;
    public String getTelString() {
        return telString;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public void setTelString(String telString) {
        this.telString = telString;
    }



}
