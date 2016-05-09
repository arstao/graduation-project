package com.arstao.gradesystem.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.UI.widget.EmptyLayout;
import com.arstao.gradesystem.Util.TDevice;
import com.arstao.gradesystem.Volley.VolleyHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arstao on 2016/3/26.
 */
public abstract class BaseListFragment<T > extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";
    private static final String TAG =BaseListFragment.class.getSimpleName() ;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected ListView mListView;

    protected ListBaseAdapter<T> mAdapter;

    protected EmptyLayout mErrorLayout;

    protected int mStoreEmptyState = -1;

    protected int mCurrentPage = 0;

    protected int mCatalog = 1;

    protected RequestQueue mQueue;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_listview;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        mListView = (ListView) view.findViewById(R.id.listview);
        mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);
        mQueue = VolleyHelper.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onDestroyView() {
        mStoreEmptyState = mErrorLayout.getErrorState();
        super.onDestroyView();
    }

    @Override
    public void initView(View view) {
        super.initView(view);


        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mState = STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                requestData(true);
            }
        });

        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getListAdapter();
            mListView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mState = STATE_NONE;
                requestData(false);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }

        }
        if (mStoreEmptyState != -1) {
            mErrorLayout.setErrorType(mStoreEmptyState);
        }

    }

    // 下拉刷新数据
    @Override
    public void onRefresh() {
        if (mState == STATE_REFRESH) {
            return;
        }
        // 设置顶部正在刷新
        mListView.setSelection(0);
        setSwipeRefreshLoadingState();
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        requestData(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(mAdapter.getFooterView()) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (mState == STATE_NONE && scrollEnd) {
            if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE
                    || mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
                mCurrentPage++;
                mState = STATE_LOADMORE;
                requestData(false);
                mAdapter.setFooterViewLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }

    protected abstract ListBaseAdapter<T> getListAdapter();

    /**
     * 设置顶部正在加载的状态
     */
    protected void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    protected void requestData(boolean refresh) {
        if(!TDevice.hasInternet()){
            executeOnLoadDataError();
            return;
        }
            sendRequestData();
    }

    protected  void readCacheData(){

        // 需要请求时服务器那边的response 缓存不能为空则volley才会缓存
        //现在没有网络显示加载中，点击没有反应是因为errorLayout在加载中设置clickable为false
//        String response = mQueue.getCache().get("http://www.baidu.com").toString();
//        Log.i(TAG,response);
//        executeOnLoadDataSuccess(parseList(response));
    }

    protected boolean isReadCacheData(boolean refresh) {
//        String key = getCacheKey();
        if (!TDevice.hasInternet()) {
            return true;
        }
        // 第一页若不是主动刷新，缓存存在，优先取缓存的
        if (mQueue.getCache().get("http://www.baidu.com") != null && !refresh) {
            Log.i(TAG,"有缓存");
            return true;

        }
        // 第一页若不是主动刷新，缓存存在，优先取缓存的
        if (mQueue.getCache().get("http://www.baidu.com") != null && !mQueue.getCache().get("http://www.baidu.com").isExpired()
                &&mCurrentPage!=0) {
            Log.i(TAG,"其他页数有缓存");
            return true;

        }

    return false;
}

    protected void sendRequestData() {
    }

    // 完成刷新
    protected void executeOnLoadFinish() {
        setSwipeRefreshLoadedState();
        mState = STATE_NONE;
    }

    /**
     * 设置顶部加载完毕的状态
     */
    protected void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    protected void executeOnLoadDataSuccess(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }

//        if (mResult != null && !mResult.OK()) {
//            AppContext.showToast(mResult.getErrorMessage());
//            // 注销登陆，密码已经修改，cookie，失效了
//            AppContext.getInstance().Logout();
//        }

        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }
//        //读取缓存时，排除与adapter重复的数据
//        for (int i = 0; i < data.size(); i++) {
//            if (compareTo(mAdapter.getData(), data.get(i))) {
//                data.remove(i);
//                i--;
//            }
//        }
        int adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
        if ((mAdapter.getCount() + data.size()) == 0) {
            adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
        } else if (data.size() == 0
                || (data.size() < getPageSize() && mCurrentPage == 0)) {
            adapterState = ListBaseAdapter.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = ListBaseAdapter.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
        mAdapter.addData(data);

        // 判断等于是因为最后有一项是listview的状态
        if (mAdapter.getCount() == 1) {
            if(onlyOne()){
                executeOnLoadFinish();
                return;
            }
            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }

        executeOnLoadFinish();
    }
protected boolean onlyOne(){
    return  false;
}
    //加载失败后
    protected void executeOnLoadDataError() {
        //当前为第一页且没有缓存
        if (mCurrentPage == 0
//                && !CacheManager.isExistDataCache(getActivity(), getCacheKey())
                ) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {

            //在无网络时，滚动到底部时，mCurrentPage先自加了，然而在失败时却
            //没有减回来，如果刻意在无网络的情况下上拉，可以出现漏页问题
            //find by TopJohn
            mCurrentPage--;

            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
            mAdapter.notifyDataSetChanged();
        }
        executeOnLoadFinish();
    }

    protected int getPageSize() {
        return AppContext.PAGE_SIZE;
    }

    /**
     * 是否需要隐藏listview，显示无数据状态
     */
    protected boolean needShowEmptyNoData() {
        return true;
    }

  public class MyStringRequest extends StringRequest {
        public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

        @Override
        public String getCacheKey() {
            return getUrl()+mCurrentPage;
        }
    }
    public class MyResponseListener implements Response.Listener<String>{


        @Override
        public void onResponse(String response) {
Log.i(TAG,response);
//            executeOnLoadDataSuccess(parseList(response));
        }
    }
public  class  MyErrorListener implements Response.ErrorListener{

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("TAG", error.getMessage(), error);
        executeOnLoadDataError();
    }
}
//    protected abstract List<T> parseList(String response);
}
