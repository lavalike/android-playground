package com.android.playground.ui.activity.biometric.manager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.android.playground.R;
import com.android.playground.util.UIUtils;

/**
 * FingerprintDialog
 * Created by wangzhen on 2020/6/5.
 */
public class FingerprintDialog extends Dialog {

    private TextView mTvTip;
    private final View inflate;

    public FingerprintDialog(@NonNull Context context) {
        super(context);
        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_fingerprint_verify, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflate);
        initViews(inflate);
    }

    private void initViews(View inflate) {
        mTvTip = inflate.findViewById(R.id.tv_tip);
        inflate.findViewById(R.id.btn_cancel).setOnClickListener((view) -> {
            dismiss();
        });
    }

    public void setSuccess(String msg) {
        mTvTip.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        mTvTip.setText(msg);
    }

    public void setError(String msg) {
        mTvTip.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        mTvTip.setText(msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = UIUtils.getScreenWidth(getContext()) * 3 / 4;
            window.setAttributes(attributes);
        }
    }
}
