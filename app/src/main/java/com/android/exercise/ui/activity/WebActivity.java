package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.listener.IJSCallback;
import com.android.exercise.ui.JSInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity implements IJSCallback {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        config();
        String path = "file:///android_asset/html/js.html";
        webview.loadUrl(path);
    }

    private void config() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.addJavascriptInterface(new JSInterface(this), "android");
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_webview));
    }

    @Override
    public void exeJS(final String data) {
        webview.post(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl("javascript:getAppInfoAsyncCallback('" + data + "')");
            }
        });
    }
}
