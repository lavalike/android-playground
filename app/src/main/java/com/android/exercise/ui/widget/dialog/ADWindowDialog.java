package com.android.exercise.ui.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.exercise.R;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ADWindowDialog);
        View view = View.inflate(getActivity(), R.layout.layout_ad_window, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
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

    public void showDialog(FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(this, "ad_window_fragment");
        ft.commitAllowingStateLoss();
    }
}
