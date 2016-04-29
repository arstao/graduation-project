package com.arstao.gradesystem.UI;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Util.CyptoUtils;
import com.arstao.gradesystem.Util.TDevice;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.Volley.VolleyHelper;
import com.arstao.gradesystem.base.BaseActivity;
import com.arstao.gradesystem.bean.Constants;
import com.arstao.gradesystem.bean.User;
import com.arstao.gradesystem.bean.UserRegister;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arstao on 2016/3/14.
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    //    private final int requestCode = REQUEST_CODE_INIT;
    private String mUserName = "";
    private String mPassword = "";
    private EditText et_pwd;
    private EditText et_username;
    private Button btn;

    public static final int REQUEST_CODE_INIT = 0;
    private static final String BUNDLE_KEY_REQUEST_CODE = "BUNDLE_KEY_REQUEST_CODE";
    private final int requestCode = REQUEST_CODE_INIT;
    private RadioButton rb_judge;
    private RadioButton rb_player;
    private RadioGroup rg_sex;
    private RadioButton rb_male;
    private RadioButton rb_female;
    private String type;
    private EditText et_email;
    private EditText et_name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Toolbar setToolBar() {
        return (Toolbar) findViewById(R.id.tb_login);
    }

    @Override
    public void initView() {
        type = getIntent().getExtras().getString("type");

        et_pwd = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        rb_judge = (RadioButton) findViewById(R.id.rb_judge);
        rb_player = (RadioButton) findViewById(R.id.rb_player);
        et_email = (EditText) findViewById(R.id.et_email);
        et_name = (EditText) findViewById(R.id.et_name);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        btn = (Button) findViewById(R.id.btn_login);
        if (type.equals(GuideActivity.LOGIN)) {

            btn.setText("登录");
            rg_sex.setVisibility(View.GONE);
            et_email.setVisibility(View.GONE);
            et_name.setVisibility(View.GONE);
        } else {
            btn.setText("注册");

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals(GuideActivity.LOGIN)) {
                    handleLogin();
                } else {
                    handleRegister();
                }
            }
        });
    }

    private void handleRegister() {
        ///注册前检查
        if (prepare()) {
            return;
        }
        mPassword = et_pwd.getText().toString();
        mUserName = et_username.getText().toString();
        String job = null;
        String sex = null;
        if (rb_judge.isChecked()) {
            job = "0";
        } else {
            job = "1";
        }
        if (rb_male.isChecked()) {
            sex = "男";
        } else {
            sex = "女";
        }
        String url = "http://101.201.72.189/p1/testfinal/json/register.php";
        Map<String, String> jsonParam = new HashMap<String, String>();
        jsonParam.put("username", mUserName);
        jsonParam.put("password", mPassword);
        jsonParam.put("job", job);
        jsonParam.put("sex", sex);
        jsonParam.put("name", et_name.getText().toString());
        jsonParam.put("email", et_email.getText().toString());
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<UserRegister> registerRequest = new JsonRequestToEnity<UserRegister>(Method.POST, url, jsonObject, UserRegister.class, new Response.Listener<UserRegister>() {


            @Override
            public void onResponse(UserRegister user) {
                if (user.getCode() > 0) {
//                    User user = new User();
//                    user.setId(user.);
//                    user.setName("tao");
//                    user.setAccount(mUserName);
//                    user.setPwd(mPassword);
//                    AppContext.getInstance().saveUserInfo(user);
                    AppContext.showToast(R.string.tip_register_success);
                    finish();
                } else {
                    AppContext.showToast(R.string.tip_register_fail);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppContext.showToast(R.string.tip_register_fail);
            }
        });
        VolleyHelper.getInstance().add(registerRequest);
    }
   String theJob ;
    private void handleLogin() {
        ///登录前检查
        if (prepare()) {
            return;
        }
        mPassword = et_pwd.getText().toString();
        mUserName = et_username.getText().toString();
        String job = null;
        String sex = null;
        if (rb_judge.isChecked()) {
            job = "0";
        } else {
            job = "1";
        }
        theJob =job;

        String url = "  http://101.201.72.189/p1/testfinal/json/dologin.php";

        Map<String, String> jsonParam = new HashMap<String, String>();
        jsonParam.put("username", mUserName);
        jsonParam.put("password", mPassword);
        jsonParam.put("job", job);
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<User> userRequest = new JsonRequestToEnity<User>(Method.POST, url, jsonObject, User.class, new Response.Listener<User>() {


            @Override
            public void onResponse(User user) {
                if (user.getCode() > 0) {
                    AppContext.getInstance().setLoginUid(Integer.valueOf(user.getData().getId()));
                    AppContext.getInstance().setJob(Integer.valueOf(theJob));
//                    User user = new User();
//                    user.setId(user.);
//                    user.setName("tao");
//                    user.setAccount(mUserName);
//                    user.setPwd(mPassword);
//                    AppContext.getInstance().saveUserInfo(user);

                    handleLoginSuccess();
                } else {
                    AppContext.showToast(R.string.tip_login_fail);
                    AppContext.getInstance().cleanLoginInfo();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppContext.showToast(R.string.tip_login_fail);
            }
        });
        VolleyHelper.getInstance().add(userRequest);

    }

    private void handleLoginSuccess() {
        Intent data = new Intent();
        data.putExtra(BUNDLE_KEY_REQUEST_CODE, requestCode);
        setResult(RESULT_OK, data);
        this.sendBroadcast(new Intent(Constants.INTENT_ACTION_USER_CHANGE));
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private boolean prepare() {
        if (!TDevice.hasInternet()) {
            AppContext.showToast(R.string.tip_no_internet);
            return true;
        }


        if (et_username.length() == 0) {
            et_username.setError("请输入账号");
            et_username.requestFocus();
            return true;
        }

        if (et_pwd.length() == 0) {
            et_pwd.setError("请输入密码");
            et_pwd.requestFocus();
            return true;
        }
        if (!rb_judge.isChecked() && !rb_player.isChecked()) {
            AppContext.showToast(R.string.tip_tocheck_radiobutton);
            return true;
        }
        if (type.equals(GuideActivity.LOGIN)) {
            return false;
        }

        if (et_name.length() == 0) {
            et_name.setError("请输入姓名");
            et_name.requestFocus();
            return true;
        }
        if (et_email.length() == 0) {
            et_email.setError("请输入邮箱");
            et_email.requestFocus();
            return true;
        }
        if (!rb_male.isChecked() && !rb_female.isChecked()) {
            AppContext.showToast(R.string.tip_tocheck_radiobutton);
            return true;
        }
        return false;
    }

    @Override
    public void initData() {
        et_username.setText(AppContext.getInstance()
                .getProperty("user.account"));
        et_pwd.setText(CyptoUtils.decode("GS", AppContext
                .getInstance().getProperty("user.pwd")));
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.login;
    }
}
