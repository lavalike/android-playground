package com.android.exercise.ui.activity.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityElasticBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.net.URI;
import java.net.URISyntaxException;

public class ElasticActivity extends BaseActivity {
    private ActivityElasticBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityElasticBinding.inflate(getLayoutInflater())).getRoot());
        String url = "https://juejin.cn/post/7149082070604578824";
        binding.webview.loadUrl(url);

        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_pull_behind, null);
        ((TextView) headerView.findViewById(R.id.tv_tip)).setText("网页由 " + getUrlHost(url) + " 提供");
        binding.elasticLayout.setHeaderView(headerView);
    }

    /**
     * 获取url的Host
     *
     * @param url VIDEO_URL
     * @return Host
     */
    private static String getUrlHost(String url) {
        if (TextUtils.isEmpty(url) || url.trim().equals("")) {
            return "";
        }
        String host = "";
        try {
            URI uri = new URI(url);
            host = uri.getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return host;
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_pull_layout));
    }
}
