package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.BaseRecyclerAdapter;
import com.android.exercise.base.recycler.wrapper.HeaderAndFooterWrapper;
import com.android.exercise.base.retrofit.APIService;
import com.android.exercise.base.retrofit.RetrofitManager;
import com.android.exercise.base.retrofit.callback.CommonCallback;
import com.android.exercise.base.retrofit.progress.Done;
import com.android.exercise.base.retrofit.progress.ProgressListener;
import com.android.exercise.base.task.GithubReposTask;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.AppBean;
import com.android.exercise.domain.GithubBean;
import com.android.exercise.ui.adapter.ReposAdapter;
import com.android.exercise.util.C;
import com.android.exercise.util.T;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retrofit 2.0
 * created by wangzhen on 2016/11/07
 */
public class RetrofitActivity extends BaseActivity {

    @BindView(R.id.recycler_repos)
    RecyclerView recyclerRepos;
    @BindView(R.id.swipe_repos)
    SwipeRefreshLayout swipeRepos;
    private ReposAdapter mReposAdapter;
    private View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        initSwipe();
        initRecycler();
    }

    private void initSwipe() {
        swipeRepos.setColorSchemeResources(R.color.colorPrimary);
        swipeRepos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadGithubList();
            }
        });
    }

    private void startLoading() {
        swipeRepos.post(new Runnable() {
            @Override
            public void run() {
                swipeRepos.setRefreshing(true);
            }
        });
    }

    private void stopLoading() {
        swipeRepos.post(new Runnable() {
            @Override
            public void run() {
                swipeRepos.setRefreshing(false);
            }
        });
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerRepos.setLayoutManager(manager);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_retrofit), true);
    }

    /**
     * 加载数据
     */
    public void loadGithubList() {
        startLoading();
        new GithubReposTask(new CommonCallback<List<GithubBean>>() {

            @Override
            public void onError(String error) {
                T.get(mContext).toast(error);
            }

            @Override
            public void onSuccess(List<GithubBean> list) {
                mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.activity_greendao, null);
                mReposAdapter = new ReposAdapter(mContext, list);
                mReposAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<GithubBean>() {
                    @Override
                    public void onItemClick(View view, int position, GithubBean data) {
                        String reposUrl = data.getHtml_url();
                        Intent intent = new Intent(mContext, HtmlActivity.class);
                        intent.putExtra("url", reposUrl);
                        startActivity(intent);
                    }
                });
                //添加头部
                HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(mReposAdapter);
                wrapper.addHeaderView(mHeaderView);
                recyclerRepos.setAdapter(wrapper);
            }

            @Override
            public void onAfter() {
                stopLoading();
            }
        }).exe("lavalike");
    }

    @OnClick({R.id.btn_github, R.id.btn_upload_multipartbody, R.id.btn_upload_multipartbodypart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_github:
                loadGithubList();
                break;
            case R.id.btn_upload_multipartbody:
                uploadFileMultipartBody();
                break;
            case R.id.btn_upload_multipartbodypart:
                uploadFileMultipartBodyPart();
                break;
        }
    }

    /**
     * 文件上传
     */
    private void uploadFileMultipartBody() {
        String url = "http://10.100.47.155:8080/ServletDemo/upload";
        //文件路径
        File file1 = new File(Environment.getExternalStorageDirectory(), "123.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "456.jpg");
        File file3 = new File(Environment.getExternalStorageDirectory(), "video.mp4");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        files.add(file3);

        //MultipartBody方式上传
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            builder.addFormDataPart("file", file.getName(), RequestBody.create(null, file));
        }
        builder.setType(MultipartBody.FORM);

        APIService api = RetrofitManager.get().create(APIService.class);
        Call<ResponseBody> call = api.uploadFileMutilpartBody(url, builder.build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RetrofitActivity.this, "上传成功：" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "上传失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 文件上传
     */
    private void uploadFileMultipartBodyPart() {
        String url = "http://10.100.47.155:8080/ServletDemo/upload";
        //文件路径
        File file1 = new File(Environment.getExternalStorageDirectory(), "123.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "456.jpg");
        File file3 = new File(Environment.getExternalStorageDirectory(), "video.mp4");

        //MultipartBody.Part方式上传
        MultipartBody.Part part1 = MultipartBody.Part.createFormData("file", file1.getName(), RequestBody.create(null, file1));
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("file", file2.getName(), RequestBody.create(null, file2));
        MultipartBody.Part part3 = MultipartBody.Part.createFormData("file", file3.getName(), RequestBody.create(null, file3));
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(part1);
        parts.add(part2);
        parts.add(part3);

        APIService api = RetrofitManager.getProgressClient(new ProgressListener() {
            @Override
            public void onProgress(long bytesRead, long contentLength, Done done) {

            }
        }).create(APIService.class);
        Call<ResponseBody> call = api.uploadFileMutilpartBodyPart(url, parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RetrofitActivity.this, "上传成功：" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "上传失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAppList() {
        String url = "http://10.100.60.70:8080/ZBCM/user8531Controller.do?functionList";
        APIService apiService = RetrofitManager.get().create(APIService.class);
        Call<AppBean> call = apiService.listApp(url, C.USERINFO);
        call.enqueue(new Callback<AppBean>() {
            @Override
            public void onResponse(Call<AppBean> call, Response<AppBean> response) {
                AppBean bean = response.body();
                if (bean != null) {
                    showToast("共" + bean.getResult().size() + "条");
                }
            }

            @Override
            public void onFailure(Call<AppBean> call, Throwable t) {

            }
        });
    }
}
