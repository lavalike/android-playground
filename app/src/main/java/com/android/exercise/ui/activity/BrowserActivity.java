package com.android.exercise.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;

import androidx.annotation.Nullable;

import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityWebBinding;
import com.android.exercise.util.IKey;
import com.wangzhen.commons.toolbar.impl.Toolbar;

public class BrowserActivity extends BaseActivity {
    private ActivityWebBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityWebBinding.inflate(getLayoutInflater())).getRoot());
        config();
        binding.webview.loadUrl(getIntent().getStringExtra(IKey.HTML_URL));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void config() {
        WebSettings settings = binding.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true); // 视频全屏点击支持回调
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSavePassword(false);//关闭明文存储密码
        settings.setSupportZoom(false); // 网页缩放
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getIntent().getStringExtra(IKey.HTML_TITLE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webview.destroy();
    }
}
