package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityAndServerBinding;
import com.android.exercise.ui.activity.view.HtmlActivity;
import com.android.exercise.util.IKey;
import com.android.exercise.util.NetworkUtil;
import com.wangzhen.commons.toolbar.impl.Toolbar;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;

/**
 * AndServerActivity.java
 * Created by wangzhen on 2017/3/22.
 */
public class AndServerActivity extends BaseActivity {

    private ActivityAndServerBinding binding;
    private Server mServer;

    private int SERVER_PORT = 8080;
    private String ipAddress;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityAndServerBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
        initServer();
    }

    private void initServer() {
        AssetsWebsite website = new AssetsWebsite(getAssets(), "/speed.html");
        mServer = new AndServer.Build().website(website).port(SERVER_PORT).build().createServer();
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_andServer));
    }

    public void setEvents() {
        binding.btnStartServer.setOnClickListener(v -> startServer());
        binding.btnStopServer.setOnClickListener(v -> stopServer());
        binding.btnServerMsg.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(url)) {
                Intent intent = new Intent(this, HtmlActivity.class);
                intent.putExtra(IKey.HTML_URL, url);
                startActivity(intent);
            }
        });
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
            Log.e(tag, "Server Running.");
            String ipAddress = NetworkUtil.getIPAddress(this);
            if (!TextUtils.isEmpty(ipAddress)) {
                binding.btnServerMsg.setVisibility(View.VISIBLE);
                url = "http://" + ipAddress + ":" + SERVER_PORT + "/speed.html";
                binding.btnServerMsg.setText("点击访问:" + url);
            }
        } else {
            Log.e(tag, "Server Stopped.");
            binding.btnServerMsg.setVisibility(View.GONE);
        }
    }
}
