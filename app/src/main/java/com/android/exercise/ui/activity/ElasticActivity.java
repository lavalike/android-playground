package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

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
        String url = "https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&amp;mid=2247483998&amp;idx=1&amp;sn=03cb1942533247ac23876448fdf1b39a&amp;chksm=96cda313a1ba2a050f8cc2325e36b468f620d3d167d9f0dbc3108127c5ba16d14dc83cabc3c6&amp;scene=21#wechat_redirect";
        webview.loadUrl(url);
        View behindView = elastic_layout.getBehindView();
        if (behindView != null) {
            ((TextView) behindView.findViewById(R.id.tv_tip)).setText("网页由 " + getUrlHost(url) + " 提供");
        }
    }

    /**
     * 获取url的Host
     *
     * @param url url
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
