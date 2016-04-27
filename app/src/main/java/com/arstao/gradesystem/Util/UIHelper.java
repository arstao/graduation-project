package com.arstao.gradesystem.Util;

import android.content.Context;
import android.content.Intent;

import com.arstao.gradesystem.UI.LoginActivity;
import com.arstao.gradesystem.UI.SimpleBackActivity;
import com.arstao.gradesystem.bean.SimpleBackPage;

/**
 * Created by arstao on 2016/2/24.
 */
public class UIHelper {
   public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
      intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    /**
     * 显示登录界面
     *
     * @param context
     */
    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
