package com.android.exercise.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.CibaBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.FormUrlEncoded;

public class OKHttpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_okhttp), true);
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
        OkHttpUtils
                .post()
                .url("http://www.csdn.net/")
                .addParams("username", "lavalike")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, response);
                    }
                });
    }

    private void get() {
        OkHttpUtils.get()
                .url("http://open.iciba.com/dsapi/")
                .build()
                .execute(new CibaCallback());
//        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), "");
//        FormBody formBody=new FormBody.Builder()
//                .add("","")
//                .build();
//        Request request = new Request.Builder()
//                .VIDEO_URL("")
//                .post(body)
//                .build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
    }

    class CibaCallback extends Callback<CibaBean> {

        @Override
        public CibaBean parseNetworkResponse(Response response, int id) throws Exception {
            String body = response.body().string();
            return new Gson().fromJson(body, CibaBean.class);
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(CibaBean response, int id) {
            showToast(response.getNote());
        }
    }
}
