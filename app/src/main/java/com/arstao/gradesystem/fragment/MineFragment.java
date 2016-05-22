package com.arstao.gradesystem.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.Util.PreferenceHelper;
import com.arstao.gradesystem.Util.StringUtils;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.Volley.VolleyHelper;
import com.arstao.gradesystem.base.BaseFragment;
import com.arstao.gradesystem.bean.UserInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by arstao on 2016/2/26.
 */
public class MineFragment extends BaseFragment {

    private TextView tv_username;
    private TextView tv_job;
    private TextView tv_sex;
    private TextView tv_email;
    private PreferenceHelper helper;

    private RelativeLayout rl_icon;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_CAMER = 2;
    private String photoTempPath;
    private String imgPath = "";
    private File out;
    public int crop = 300;// 裁剪大小
    public static final int CROP_PHOTO_CODE = 12;
    private ImageView iv_user_icon;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_information;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }
    /**
     * 图片目录
     * @param pContext
     * @return
     */
    private   String getSDCardPath_Image(Context pContext) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            String pathImg = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            File ImgPathDir = new File(pathImg);
            if (!ImgPathDir.exists()) {
                ImgPathDir.mkdirs();
            }
            return pathImg;
        } else {
            return pContext.getCacheDir().getAbsolutePath();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper=PreferenceHelper.getInstance();
        photoTempPath = getSDCardPath_Image(getActivity()) + "/userprofile.jpg";
        initView(view);

    }

    @Override
    public void onStart() {
        String first = helper.getValue("user-firstLogin", "0");

        if(first.equals("first")){
            requestData();
            PreferenceHelper.getInstance().setValue("user-firstLogin","noFirst");
        }else {
             getLocalData();
        }
        super.onStart();
    }

    private void getLocalData() {
        tv_email.setText(helper.getValue("user-email"));
        tv_job.setText(helper.getValue("user-job"));
        tv_username.setText(helper.getValue("user-name"));
        tv_sex.setText(helper.getValue("user-sex"));
        photoTempPath = helper.getValue("user-icon"+helper.getValue("user-name"));
        iv_user_icon.setImageBitmap(getBitmap());
    }

    private void requestData() {
        String url = "http://101.201.72.189/p1/testfinal/json/get_user_mess.php";
        Map<String, Integer> jsonParam = new HashMap<String, Integer>();
        int job = 0;
        if(helper.getValue("user-job").equals("选手")){
            job =1;
        }
        String id =helper.getValue("user-id");
        jsonParam.put("id", Integer.valueOf(helper.getValue("user-id")));
        jsonParam.put("job", job);
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<UserInfo> matchRequest = new JsonRequestToEnity<UserInfo>(Request.Method.POST, url, jsonObject, UserInfo.class, new Response.Listener<UserInfo>() {

            @Override
            public void onResponse(UserInfo info) {
                if(info.getCode()>0){
                tv_email.setText(info.getData().getJemail());
                    tv_job.setText(helper.getValue("user-job"));
                    tv_username.setText(info.getData().getUsername());
                    tv_sex.setText(info.getData().getSex());

                    String photo =info.getData().getFace();
                    String prefix = "http://";
                    String uri =prefix + photo;
                    ImageLoader.getInstance().displayImage(uri, iv_user_icon, AppContext.getUilImageOptions());

                    helper.setValue("user-email",info.getData().getJemail());
                    helper.setValue("user-name",info.getData().getUsername());
                    helper.setValue("user-sex",info.getData().getSex());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppContext.showToast(R.string.tip_request_fail);
            }
        });
        VolleyHelper.getInstance().add(matchRequest);

    }


    @Override
    public void initView(View view) {
        rl_icon = (RelativeLayout) view.findViewById(R.id.rl_icon);
        rl_icon .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimg();
            }
        });
        iv_user_icon = (ImageView) view.findViewById(R.id.user_icon);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        tv_job = (TextView) view.findViewById(R.id.tv_job);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
    }

    private void selectimg() {
        final CharSequence[] items = { "拍照上传", "从相册选择" };
        new AlertDialog.Builder(getActivity()).setTitle("选择图片来源").setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == SELECT_PICTURE) {
                    toGetLocalImage();
                } else {
                    toGetCameraImage();
                }
            }
        }).create().show();
    }
    /**
     * 选择本地图片
     */
    public void toGetLocalImage() {
        // String photoname = "userprofile.jpg";
        out = new File(photoTempPath);
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // intent.setDataAndType(Intent.ACTION_GET_CONTENT, "image/*");
        startActivityForResult(intent, SELECT_PICTURE);

    }
    /**
     * 照相选择图片
     */
    public void toGetCameraImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 打开相机
        // String photoname = "userprofile.jpg";
        out = new File(photoTempPath);
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, SELECT_CAMER);
    }


    /**
     * 剪切
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", Uri.fromFile(out));
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop);
        intent.putExtra("outputY", crop);
        startActivityForResult(intent, CROP_PHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case SELECT_PICTURE:
                    if (null != data && null != data.getData()) {
                        cropPhoto(data.getData());
                    }
                    break;
                case SELECT_CAMER:

                    if (resultCode == Activity.RESULT_OK) {

                        if (null != out && !StringUtils.isNull(out.getAbsolutePath())) {
                            try {
                                cropPhoto(Uri.fromFile(out));
                            } catch (Exception e) {
                                AppContext.getInstance().showToast(R.string.tip_image_failed);
                            }
                        }
                    } else {
                        AppContext.getInstance().showToast(R.string.tip_image_failed);
                    }

                    break;
                case CROP_PHOTO_CODE:
                    try {
                        if (data != null) {
                            Bitmap bitmap = getBitmap();
                            upLoadByCommonPost(imgPath);

//                            iv_user_icon.setImageBitmap(bitmap);
////                            ImageLoader.getInstance().displayImage(bitmap, iv_user_icon, AppContext.getUilImageOptions());
////                            imgPath = photoTempPath;// out.getAbsolutePath();//Until.creatfile(mContext,
//                            // bm1, "zgz");
//                            helper.setValue("user-icon"+helper.getValue("user-name"),photoTempPath);
                        }
                    } catch (Exception e) {
                        AppContext.getInstance().showToast(R.string.tip_image_failed);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    private void upLoadByCommonPost(String photoTempPath) {
//        File file = new File(photoTempPath);
        File file =out;
        String value = helper.getValue("user-job");
        String  job ;
        if(value.equals("裁判")){
            job ="0";
        }
        else {
            job="1";
        }
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody requestBody = builder.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"username\""),
                RequestBody.create(null, helper.getValue("user-username"))).addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"job\""),
                RequestBody.create(null,job ))
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"userfile\";filename=\"userprofile.jpg\""), fileBody)
                .setType(MultipartBody.FORM).build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://101.201.72.189/p1/testfinal/json/edit_face.php")
                .post(requestBody)
                .build();

        okhttp3.Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             int i=1;
                String string = response.body().string();
                Log.e("TAG", string);
                i=2;
            }

        });

    }

    private Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        return BitmapFactory.decodeFile(photoTempPath, options);
    }



}
