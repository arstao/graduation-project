package com.arstao.gradesystem.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.arstao.gradesystem.AppContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/28 18:03
 * 修改人：Administrator
 * 修改时间：2016/4/28 18:03
 * 修改备注：
 */
public class PreferenceHelper {
 private static final PreferenceHelper instance = new PreferenceHelper(AppContext.getInstance());
    public static PreferenceHelper getInstance(){
        return instance;
    }
        public static final String mSetting = "setting";
        SharedPreferences mPreferences;

        SharedPreferences.Editor mEditor;

        Context context;

        public static final String CITYNAME = "city";
        public static final String CITYID = "city_id";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";

        private PreferenceHelper(Context c) {
            context = c;
            mPreferences = context.getSharedPreferences(mSetting, 0);
            mEditor = mPreferences.edit();
        }

        /**
         * 获取全部
         *
         * @return map
         */
        public Map<String, ?> getAll() {
            return mPreferences.getAll();
        }

        /**
         * 设置参数
         *
         * @param key
         * @param value
         */
        public void setValue(String key, String value) {
            mEditor = mPreferences.edit();
            mEditor.putString(key, value);
            mEditor.commit();

        }

        /**
         * 获取参数
         *
         * @param key
         * @return
         */
        public String getValue(String key) {
            return mPreferences.getString(key, "");
        }

        /**
         * 带默认值的获取参数
         *
         * @param key
         * @param defaultValue
         * @return
         */
        public String getValue(String key, String defaultValue) {
            if (!mPreferences.contains(key)) {
                return defaultValue;
            }
            return mPreferences.getString(key, defaultValue);
        }

        public void remove(String name) {
            mEditor.remove(name);
            mEditor.commit();
        }

        public void removeAll() {
            Map<String, ?> map = getAll();

            Set<String> key = map.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext();) {
                String sKey = it.next();
                remove(sKey);
                // setValue(sKey,null);
            }

        }



    }



