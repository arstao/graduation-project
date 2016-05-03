package com.arstao.gradesystem.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.arstao.gradesystem.R;

/**
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/5/3 10:07
 * 修改人：Administrator
 * 修改时间：2016/5/3 10:07
 * 修改备注：
 */
public class SignUpDialogFragment extends DialogFragment {
    interface OnConfirmListener{
 void onConfirm(String string);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sign_up, null);
//        TextView textView = new TextView(getActivity());
//        textView.setText("确定要报名吗?");
//        textView.setTextSize(16);
        builder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getTargetFragment()!=null) {
                    OnConfirmListener listener = (OnConfirmListener) getTargetFragment();
                    listener.onConfirm(getArguments().getString(SignUpFragment.ARGUMENT));
                }
            }
        }).setNegativeButton("取消",null);
        return builder.create();
    }
}
