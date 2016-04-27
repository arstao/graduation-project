package com.arstao.gradesystem.base;

/**
 * Created by arstao on 2016/3/26.
 */
public  class  Enity<E>  {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //    public List<E> getData() {
//        return Data;
//    }
//
//    public void setData(List<E> data) {
//        Data = data;
//    }
//
//    private  List<E> Data;
}
