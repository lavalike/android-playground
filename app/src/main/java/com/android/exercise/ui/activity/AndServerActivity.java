package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AndServerActivity.java
 * Created by wangzhen on 2017/3/22.
 */
public class AndServerActivity extends BaseActivity {

    private Server mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_server);
        ButterKnife.bind(this);
        initServer();
    }

    private void initServer() {
        AssetsWebsite website = new AssetsWebsite(getAssets(), "/markdown.html");
        mServer = new AndServer.Build()
                .website(website)
                .build()
                .createServer();
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_andServer), true);
    }

    @OnClick({R.id.btn_startServer, R.id.btn_stopServer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_startServer:
                startServer();
                break;
            case R.id.btn_stopServer:
                stopServer();
                break;
        }
    }

    private void stopServer() {
        if (mServer != null) {
            mServer.stop();
        }
        checkRunning();
    }

    private void startServer() {
        if (mServer != null) {
            mServer.start();
        }
        checkRunning();
    }

    private void checkRunning() {
        if (mServer.isRunning()) {
            Log.e(TAG, "Server Running.");
        } else {
            Log.e(TAG, "Server Stopped.");
        }
    }
}
