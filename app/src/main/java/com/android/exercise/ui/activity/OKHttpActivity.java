package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.okhttp.OKHttpManager;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.domain.CibaBean;
import com.android.exercise.util.NetworkUtil;
import com.google.gson.Gson;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OKHttpActivity
 * Created by wangzhen on 2017/2/9.
 */
public class OKHttpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_okhttp));
    }

    @OnClick({R.id.btn_okhttp_get, R.id.btn_okhttp_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_okhttp_get:
                get();
                break;
            case R.id.btn_okhttp_post:
                post();
                break;
        }
    }

    private void post() {

    }

    private void get() {
        OkHttpClient client = OKHttpManager.getClient().newBuilder().cache(new Cache(new File(getExternalCacheDir(), "okhttpcache"), 1024 * 1024 * 10)).build();
        Request request = new Request.Builder().url("http://open.iciba.com/dsapi/").cacheControl(NetworkUtil.isNetworkAvailable(this) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        CibaBean bean = new Gson().fromJson(body.string(), CibaBean.class);
                        if (bean != null) {
                            runOnUiThread(() -> {
                                Toast.makeText(mContext, bean.getNote(), Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}
