package com.arstao.gradesystem.Volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/27 15:37
 * 修改人：Administrator
 * 修改时间：2016/4/27 15:37
 * 修改备注：
 */
public class JsonRequestToEnity<T> extends JsonRequest<T>{
private Class<T> mClazz;

    public JsonRequestToEnity(int method, String url, JSONObject jsonRequest,Class<T> clazz,Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest == null?null:jsonRequest.toString(), listener, errorListener);
        mClazz=clazz;

    }

    public JsonRequestToEnity(String url, JSONObject jsonRequest, Class<T> clazz,Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(jsonRequest == null?0:1, url, jsonRequest,clazz, listener, errorListener);

    }

    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String je = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            String substring = je.substring(4);
//            UserInfo userInfo = (UserInfo) new Gson().fromJson(substring, mClazz);
            return Response.success(new Gson().fromJson(je, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException var3) {
            return Response.error(new ParseError(var3));
        }
    }
}
