package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.IKey;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    private String mTitle;
    private ToolBarCommonHolder mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        config();
        webview.loadUrl(getIntent().getStringExtra(IKey.HTML_URL));
    }

    private void config() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true); // 视频全屏点击支持回调
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSavePassword(false);//关闭明文存储密码
        settings.setSupportZoom(false); // 网页缩放

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle)) {
                    mToolbar.setTitle(title);
                }
            }
        });
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        mTitle = getIntent().getStringExtra(IKey.HTML_TITLE);
        mToolbar = new ToolBarCommonHolder(this, toolbar, mTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }
}
