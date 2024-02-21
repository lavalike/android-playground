package com.android.exercise.ui.activity.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityHtmlBinding;
import com.android.exercise.util.IKey;
import com.android.exercise.util.NetworkUtil;
import com.android.exercise.util.T;
import com.wangzhen.commons.toolbar.impl.Toolbar;

/**
 * HTML页面
 * created by wangzhen on 2016/11/07
 */
public class HtmlActivity extends BaseActivity {
    private ActivityHtmlBinding binding;

    //文件选择
    private String MIME = "image/*";
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityHtmlBinding.inflate(getLayoutInflater())).getRoot());
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            T.get(mContext).toast(getString(R.string.error_network_failure));
            return;
        }
        mUrl = getIntent().getStringExtra(IKey.HTML_URL);
        if (!TextUtils.isEmpty(mUrl)) {
            configWebView();
            binding.webview.loadUrl(mUrl);
        }
    }

    /**
     * 配置WebView
     */
    private void configWebView() {
        WebSettings settings = binding.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        // 不阻塞网络图片加载
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 网页缩放
        settings.setSupportZoom(false);

        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                setLoading(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                setLoading(false);
            }
        });
        binding.webview.setWebChromeClient(new UploadWebChromeClient());
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, "");
    }

    /**
     * 上传图片ChromeClient
     */
    public class UploadWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//            TipDialog tipDialog = new TipDialog(mContext);
//            tipDialog.setMessage(message);
//            tipDialog.setOnTipListener(new TipDialog.OnTipListener() {
//                @Override
//                public void onOK() {
//                    //点击确定按钮之后，继续执行网页中的操作
//                    result.confirm();
//                    finish();
//                }
//            });
//            tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    //点击确定按钮之后，继续执行网页中的操作
//                    result.confirm();
//                    finish();
//                }
//            });
//            tipDialog.show();
            return true;
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(MIME);
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(MIME);
            startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(MIME);
            startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(MIME);
            startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {

            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null) results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                closePage();
                break;
            default:
                break;
        }
        return true;
    }

    private void closePage() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack();
        } else {
            finish();
        }
    }

    /**
     * 设置加载状态
     *
     * @param loading
     */
    private void setLoading(boolean loading) {
        binding.progressbar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }
}
