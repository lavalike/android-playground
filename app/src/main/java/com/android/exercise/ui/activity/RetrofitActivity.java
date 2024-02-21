package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.exercise.R;
import com.android.exercise.base.APIService;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.retrofit.RetrofitManager;
import com.android.exercise.base.retrofit.progress.Done;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityRetrofitBinding;
import com.android.exercise.domain.GithubBean;
import com.android.exercise.ui.activity.view.HtmlActivity;
import com.android.exercise.ui.adapter.ReposAdapter;
import com.android.exercise.util.IKey;
import com.android.exercise.util.T;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private ActivityRetrofitBinding binding;
    private ReposAdapter mReposAdapter;
    private List<GithubBean> mReposList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetrofitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initSwipe();
        initRecycler();
        addEvents();
    }

    private void initSwipe() {
        binding.swipeRepos.setColorSchemeResources(R.color.colorPrimary);
        binding.swipeRepos.setOnRefreshListener(() -> loadGithubList());
    }

    private void startLoading() {
        binding.swipeRepos.post(() -> binding.swipeRepos.setRefreshing(true));
    }

    private void stopLoading() {
        binding.swipeRepos.post(() -> binding.swipeRepos.setRefreshing(false));
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerRepos.setLayoutManager(manager);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_retrofit));
    }

    public void addEvents() {
        binding.btnGithub.setOnClickListener(v -> loadGithubList());
        binding.btnUploadMultipartbody.setOnClickListener(v -> uploadFileMultipartBody());
        binding.btnUploadMultipartbodypart.setOnClickListener(v -> uploadFileMultipartBodyPart());
    }

    /**
     * 文件上传
     */
    private void uploadFileMultipartBody() {
        String url = "http://10.100.47.155:8080/ServletDemo/upload";
        //文件路径
        File file1 = new File(Environment.getExternalStorageDirectory(), "123.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "456.jpg");
//        File file3 = new File(Environment.getExternalStorageDirectory(), "video.mp4");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
//        files.add(file3);

        //MultipartBody方式上传
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            builder.addFormDataPart("file", file.getName(), RequestBody.create(null, file));
        }
        builder.setType(MultipartBody.FORM);

        APIService api = RetrofitManager.getProgressClient((bytesRead, contentLength, done) -> {
            if (done == Done.PROCESS_UP) {
                final int progress = (int) (100 * bytesRead / contentLength);
                Log.e(tag, "文件上传：" + progress);
                runOnUiThread(() -> binding.tvUploadProgress.setText("文件上传：" + progress));
            }
            if (done == Done.FINISH_UP) {
                Log.e(tag, "上传完成");
                runOnUiThread(() -> binding.tvUploadProgress.setText("上传完成"));
            }
        }).create(APIService.class);
        Call<ResponseBody> call = api.uploadFileMutilpartBody(url, builder.build());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
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
//        File file3 = new File(Environment.getExternalStorageDirectory(), "video.mp4");

        //MultipartBody.Part方式上传
        MultipartBody.Part part1 = MultipartBody.Part.createFormData("file", file1.getName(), RequestBody.create(null, file1));
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("file", file2.getName(), RequestBody.create(null, file2));
//        MultipartBody.Part part3 = MultipartBody.Part.createFormData("file", file3.getName(), RequestBody.create(null, file3));
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(part1);
        parts.add(part2);
//        parts.add(part3);

        APIService api = RetrofitManager.getProgressClient((bytesRead, contentLength, done) -> {
            if (done == Done.PROCESS_UP) {
                final int progress = (int) (100 * bytesRead / contentLength);
                Log.e(tag, "文件上传：" + progress);
                runOnUiThread(() -> binding.tvUploadProgress.setText("文件上传：" + progress));
            }
            if (done == Done.FINISH_UP) {
                Log.e(tag, "上传完成");
                runOnUiThread(() -> binding.tvUploadProgress.setText("上传完成"));
            }
        }).create(APIService.class);
        Call<ResponseBody> call = api.uploadFileMutilpartBodyPart(url, parts);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(RetrofitActivity.this, "上传失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载数据
     */
    public void loadGithubList() {
        startLoading();
        APIService mService = RetrofitManager.getClient().create(APIService.class);
        Call<List<GithubBean>> call = mService.listRepos("lavalike");
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GithubBean>> call, @NonNull Response<List<GithubBean>> response) {
                stopLoading();
                if (response.isSuccessful()) {
                    mReposList = response.body();
                    mReposList.addAll(mReposList);
                    mReposAdapter = new ReposAdapter(mContext, mReposList);
                    mReposAdapter.setItemClickListener((view, data) -> {
                        String reposUrl = data.getHtml_url();
                        Intent intent = new Intent(mContext, HtmlActivity.class);
                        intent.putExtra(IKey.HTML_URL, reposUrl);
                        startActivity(intent);
                    });
                    binding.recyclerRepos.setAdapter(mReposAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<GithubBean>> call, Throwable t) {
                stopLoading();
                T.get(mContext).toast(t.getMessage());
            }
        });
    }
}
