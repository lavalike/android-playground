package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.IKey;
import com.android.exercise.util.NetworkUtil;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AndServerActivity.java
 * Created by wangzhen on 2017/3/22.
 */
public class AndServerActivity extends BaseActivity {

    @BindView(R.id.btn_server_msg)
    Button btnServerMsg;
    private Server mServer;

    private int SERVER_PORT = 8080;
    private String ipAddress;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_server);
        ButterKnife.bind(this);
        initServer();
    }

    private void initServer() {
        AssetsWebsite website = new AssetsWebsite(getAssets(), "/speed.html");
        mServer = new AndServer.Build()
                .website(website)
                .port(SERVER_PORT)
                .build()
                .createServer();
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_andServer), true);
    }

    @OnClick({R.id.btn_startServer, R.id.btn_stopServer, R.id.btn_server_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_startServer:
                startServer();
                break;
            case R.id.btn_stopServer:
                stopServer();
                break;
            case R.id.btn_server_msg:
                if (!TextUtils.isEmpty(url)) {
                    Intent intent = new Intent(this, HtmlActivity.class);
                    intent.putExtra(IKey.HTML_URL, url);
                    startActivity(intent);
                }
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
            String ipAddress = NetworkUtil.getIPAddress(this);
            if (!TextUtils.isEmpty(ipAddress)) {
                btnServerMsg.setVisibility(View.VISIBLE);
                url = "http://" + ipAddress + ":" + SERVER_PORT + "/speed.html";
                btnServerMsg.setText("点击访问:" + url);
            }
        } else {
            Log.e(TAG, "Server Stopped.");
            btnServerMsg.setVisibility(View.GONE);
        }
    }
}
