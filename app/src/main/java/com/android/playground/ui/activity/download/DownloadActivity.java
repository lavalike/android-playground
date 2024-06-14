package com.android.playground.ui.activity.download;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;
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
        String url = "http://192.168.10.100:8080/wangzhen/audio/time.mp3";
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

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_download));
    }
}
