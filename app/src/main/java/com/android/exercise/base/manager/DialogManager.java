package com.android.exercise.base.manager;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.exercise.R;

/**
 * 录音对话框
 * Created by wangzhen on 2017/8/9.
 */
public class DialogManager {
    private static final long DELAY_DIALOG = 1000;
    private final Context context;
    TextView tvVolume;
    TextView tvTip;
    private Dialog mDialog;
    private Handler handler = new Handler();

    public DialogManager(Context ctx) {
        this.context = ctx;
    }

    public void showDialog() {
        mDialog = new Dialog(context, R.style.AudioDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recorder_dialog, null);
        tvVolume = (TextView) view.findViewById(R.id.tv_volume);
        tvTip = (TextView) view.findViewById(R.id.tv_tip);
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void tooShort() {
        tvTip.setText("录音时间太短");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissDialog();
            }
        }, DELAY_DIALOG);
    }

    public void isAboutToCancel() {
        tvTip.setText("松开手指，取消发送");
    }

    public void updateVoiceLevel(int voiceLevel) {
        tvVolume.setText("音量：" + voiceLevel);
    }

    public void recording() {
        tvTip.setText("手指上滑，取消发送");
    }

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
