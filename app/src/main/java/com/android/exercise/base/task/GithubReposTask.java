package com.android.exercise.base.task;

import com.android.exercise.base.retrofit.APIBaskTask;
import com.android.exercise.base.retrofit.APICallback;
import com.android.exercise.base.retrofit.APIService;
import com.android.exercise.base.retrofit.RetrofitManager;
import com.android.exercise.domain.GithubBean;

import java.util.List;

import retrofit2.Call;

/**
 * 查询Github仓库
 * Created by wangzhen on 16/11/9.
 */

public class GithubReposTask extends APIBaskTask<List<GithubBean>> {

    public GithubReposTask(APICallback<List<GithubBean>> callback) {
        super(callback);
    }

    @Override
    public Call exe(String... params) {
        APIService mService = RetrofitManager.getInstance().create(APIService.class);
        Call<List<GithubBean>> call = mService.listRepos(params[0]);
        call.enqueue(this);
        return call;
    }

}
