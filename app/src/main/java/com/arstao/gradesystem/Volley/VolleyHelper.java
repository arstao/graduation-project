package com.arstao.gradesystem.Volley;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arstao.gradesystem.AppContext;

/**
 * Created by arstao on 2016/3/27.
 */
public class VolleyHelper {
    private static RequestQueue instance;
    private static class RequestQueueHolder {
        private static final RequestQueue INSTANCE = Volley.newRequestQueue(AppContext.getInstance());
    }
    private VolleyHelper (){}
    public static final RequestQueue getInstance() {
        return RequestQueueHolder.INSTANCE;
    }

}
