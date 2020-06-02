package com.android.exercise.ui.activity.download;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.wangzhen.download.DownloadClient;
import com.wangzhen.download.bean.ParamsBody;
import com.wangzhen.download.callback.OnDownloadCallback;

/**
 * DownloadActivity
 * Created by wangzhen on 2019-12-04.
 */
public class DownloadActivity extends BaseActivity {

    private TextView mTvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mTvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void onClick(View view) {
        mTvMsg.setText("-> 开始下载\n");
        String url = "http://10.100.119.192:8080/wangzhen/audio/time.mp3";
        DownloadClient.get().enqueue(new ParamsBody.Builder()
                .url(url)
                .callback(new OnDownloadCallback() {
                    int mProgress = 0;

                    @Override
                    public void onLoading(int progress) {
                        if (progress > 0 && progress % 10 == 0 && mProgress != progress) {
                            mProgress = progress;
                            mTvMsg.append("-> " + mProgress + "%");
                            mTvMsg.append("\n");
                        }
                    }

                    @Override
                    public void onSuccess(String path) {
                        mTvMsg.append("-> 下载成功:" + path);
                    }

                    @Override
                    public void onFail(String err) {
                        mTvMsg.append(err);
                    }
                })
                .build());
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_download));
    }
}
