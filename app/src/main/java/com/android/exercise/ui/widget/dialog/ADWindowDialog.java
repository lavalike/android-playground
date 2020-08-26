package com.android.exercise.ui.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.exercise.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 广告弹窗
 * Created by wangzhen on 2018/3/15.
 */
public class ADWindowDialog extends DialogFragment {

    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private AlertDialog dialog;
    private Uri uri;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ADWindowDialog);
        View view = View.inflate(getActivity(), R.layout.layout_ad_window, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        if (uri != null) {
            Glide.with(view.getContext()).load(uri).into(ivAd);
        }
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        return dialog;
    }

    @OnClick({R.id.iv_ad, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ad:
                break;
            case R.id.iv_close:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
        }
    }

    public ADWindowDialog setImageUri(Uri uri) {
        this.uri = uri;
        return this;
    }

    public void showDialog(FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(this, "ad_window_fragment");
        ft.commitAllowingStateLoss();
    }
}
