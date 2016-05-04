package com.arstao.gradesystem;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.arstao.gradesystem.Util.StringUtils;
import com.arstao.gradesystem.base.BaseApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Properties;
import java.util.UUID;


public class AppContext extends BaseApplication {

    public static final int PAGE_SIZE = 5;// 默认分页大小

//    private static PreferenceHelper mPreferenceHelper = new PreferenceHelper(getInstance());
//    public PreferenceHelper getPreferenceHelper(){
//        return mPreferenceHelper;
//    }



    private static AppContext instance;

    private int loginUid;

    private  int job;

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public void setLoginUid(int loginUid) {
        this.loginUid = loginUid;
    }

    private boolean login;

    private  static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.alien)
            .showImageOnFail(R.drawable.alien)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();
    public static  DisplayImageOptions getUilImageOptions(){
        return  options;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

//        init();
//        initLogin();

//        Thread.setDefaultUncaughtExceptionHandler(AppException
//                .getAppExceptionHandler(this));
//        UIHelper.sendBroadcastForNotice(this);
    }

    private void init() {
//        // 初始化网络请求
//        AsyncHttpClient client = new AsyncHttpClient();
//        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
//        client.setCookieStore(myCookieStore);
//        ApiHttpClient.setHttpClient(client);
//        ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));
//
//        // Log控制器
//        KJLoger.openDebutLog(true);
//        TLog.DEBUG = BuildConfig.DEBUG;
//
//        // Bitmap缓存地址
//        HttpConfig.CACHEPATH = "OSChina/imagecache";
    }


    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }


    public static void showToast(int resId){
        Toast.makeText(getInstance(),getInstance().getString(resId),Toast.LENGTH_SHORT).show();
    }
}
