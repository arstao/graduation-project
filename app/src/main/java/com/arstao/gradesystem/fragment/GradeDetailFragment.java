package com.arstao.gradesystem.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arstao.gradesystem.AppContext;
import com.arstao.gradesystem.R;
import com.arstao.gradesystem.UI.widget.EmptyLayout;
import com.arstao.gradesystem.Util.PreferenceHelper;
import com.arstao.gradesystem.Util.StringUtils;
import com.arstao.gradesystem.Volley.JsonRequestToEnity;
import com.arstao.gradesystem.adapter.GradeDetailAdapter;
import com.arstao.gradesystem.base.BaseListFragment;
import com.arstao.gradesystem.base.ListBaseAdapter;
import com.arstao.gradesystem.bean.EmptyResopne;
import com.arstao.gradesystem.bean.GradeDetailBean;
import com.arstao.gradesystem.bean.PhotoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
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
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/4/29 9:17
 * 修改人：Administrator
 * 修改时间：2016/4/29 9:17
 * 修改备注：
 */
public class GradeDetailFragment extends BaseListFragment<GradeDetailBean.Data>{
    private String ename;
    private String style;
    private Button btn_confitm;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_CAMER = 2;
    private String photoTempPath;
    private String imgPath = "";
    private File out;
    public int crop = 300;// 裁剪大小
    public static final int CROP_PHOTO_CODE = 12;
    private ImageView iv_pic;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_grade_detail;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        photoTempPath = getSDCardPath_Image(getActivity()) + "/grade_pic.jpg";
        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimg();
            }
        });
        iv_pic.setClickable(false);
        btn_confitm = (Button) view.findViewById(R.id.btn_confirm);
         btn_confitm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dataSize = mAdapter.getDataSize();
                JSONArray jsonArray  = new JSONArray();
                Map<String, String> jsonParam = new HashMap<String, String>();
                for (int i=0;i<dataSize;i++) {
                    ViewGroup viewGroup = (ViewGroup) mListView.getChildAt(i);
                    TextView name = (TextView) viewGroup.getChildAt(0);
                    EditText score = (EditText) viewGroup.getChildAt(1);
                    if(score.length()==0){
                        score.setError("成绩不能为空");
                        return;
                    }
                    jsonParam.put("username", (String) name.getTag());
                    jsonParam.put("score", score.getText().toString());
                    jsonArray.put(new JSONObject(jsonParam));
                }
                String url = "http://101.201.72.189/p1/testfinal/json/iss.php";
                Map<String, Object> jsonParams = new HashMap<String, Object>();
                jsonParams.put("data",jsonArray);
                jsonParams.put("jname", PreferenceHelper.getInstance().getValue("user-name"));
                jsonParams.put("ename",ename);
                jsonParams.put("style",style);
                JSONObject jsonObject = new JSONObject(jsonParams);
                JsonRequestToEnity<EmptyResopne> matchRequest = new JsonRequestToEnity<EmptyResopne>(Request.Method.POST, url, jsonObject, EmptyResopne.class, new Response.Listener<EmptyResopne>() {

                    @Override
                    public void onResponse(EmptyResopne resopne) {
                        if(resopne.getCode()>0){
                            AppContext.showToast(R.string.tip_grade_success);
                            getActivity().finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        AppContext.showToast(R.string.tip_request_fail);
                    }
                });
                mQueue.add(matchRequest);
            }
        });


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

    @Override
    protected void sendRequestData() {
        String url = "http://101.201.72.189/p1/testfinal/json/get_player_eve.php";
        Map<String, String> jsonParam = new HashMap<String, String>();
        ename = getArguments().getString("Argument","");
        jsonParam.put("ename",ename);
        jsonParam.put("num", "100");
        jsonParam.put("page", String.valueOf(mCurrentPage + 1));
        JSONObject jsonObject = new JSONObject(jsonParam);
        JsonRequestToEnity<GradeDetailBean> matchRequest = new JsonRequestToEnity<GradeDetailBean>(Request.Method.POST, url, jsonObject, GradeDetailBean.class, new Response.Listener<GradeDetailBean>() {

            @Override
            public void onResponse(GradeDetailBean gradeDetailBeanhBean) {
                if(gradeDetailBeanhBean.getCode()>0){
                    if(gradeDetailBeanhBean.getData().get(0).getStatue().equals("0")) {
                        btn_confitm.setVisibility(View.VISIBLE);
                        iv_pic.setClickable(true);
                    }else {
                        getEvidence();
                    }
                    style = gradeDetailBeanhBean.getData().get(0).getStyle();
                    executeOnLoadDataSuccess(gradeDetailBeanhBean.getData());
                }else{
                    mErrorLayout.setErrorType(EmptyLayout.NODATA);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppContext.showToast(R.string.tip_request_fail);
            }
        });



        mQueue.add(matchRequest);

    }

    private void getEvidence() {
        String url2 = "http://101.201.72.189/p1/testfinal/json/getalum.php";
        Map<String, String> jsonParam2 = new HashMap<String, String>();
        ename = getArguments().getString("Argument","");
        jsonParam2.put("ename",ename);
        jsonParam2.put("kind","1");
        JSONObject jsonObject2 = new JSONObject(jsonParam2);
        JsonRequestToEnity<PhotoBean> matchRequest2 = new JsonRequestToEnity<PhotoBean>(Request.Method.POST, url2  , jsonObject2, PhotoBean.class, new Response.Listener<PhotoBean>() {


            @Override
            public void onResponse(PhotoBean photoBean) {
                if(photoBean.getCode()>0){
                    String photo = photoBean.getData().get(0).getPhoto();
                    String prefix = "http://";
                    String uri =prefix + photo;
                    ImageLoader.getInstance().displayImage(uri, iv_pic, AppContext.getUilImageOptions());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppContext.showToast(R.string.tip_request_fail);
            }
        });
        mQueue.add(matchRequest2);

    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        return new GradeDetailAdapter(true);
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
                                cropPhoto(data.getData());
                            } catch (Exception e) {
                                AppContext.getInstance().showToast(R.string.tip_image_failed);
                            }
                        }
                    } else {
                        AppContext.getInstance().showToast(R.string.tip_image_failed);
                    }


                case CROP_PHOTO_CODE:
                    try {
                        if (data != null) {
                            upLoadByCommonPost(imgPath);
                            iv_pic.setImageBitmap(getBitmap());
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

    private void upLoadByCommonPost(String photoTempPath) {
        File file =out;
        ename = getArguments().getString("Argument","");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody requestBody = builder
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"ename\""),
                RequestBody.create(null,ename))
                .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"jusername\""),
                        RequestBody.create(null, "11"))
                .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"pusername\""),
                        RequestBody.create(null, "11"))
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"userfile\";filename=\"userprofile.jpg\""), fileBody)
                .setType(MultipartBody.FORM).build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://101.201.72.189/p1/testfinal/json/alum.php")
                .post(requestBody)
                .build();

        okhttp3.Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                int i = 1;
                String string = response.body().string();
                Log.e("TAG", string);
                i = 2;
            }

        });

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
    private Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        return BitmapFactory.decodeFile(photoTempPath, options);
    }
}
