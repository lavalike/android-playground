package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.wangzhen.elastic.ElasticLayout;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ElasticActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.elastic_layout)
    ElasticLayout elastic_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elastic);
        ButterKnife.bind(this);
        String url = "https://juejin.cn/post/7149082070604578824";
        webview.loadUrl(url);

        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_pull_behind, null);
        ((TextView) headerView.findViewById(R.id.tv_tip)).setText("网页由 " + getUrlHost(url) + " 提供");
        elastic_layout.setHeaderView(headerView);
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

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_pull_layout));
    }
}
