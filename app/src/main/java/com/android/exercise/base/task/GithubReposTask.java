package com.android.exercise.base.task;

import com.android.exercise.base.retrofit.APIService;
import com.android.exercise.base.retrofit.RetrofitCallback;
import com.android.exercise.base.retrofit.RetrofitManager;
import com.android.exercise.base.retrofit.callback.CommonCallback;
import com.android.exercise.domain.GithubBean;

import java.util.List;

import retrofit2.Call;

/**
 * 查询Github仓库
 * Created by wangzhen on 16/11/9.
 */

public class GithubReposTask extends RetrofitCallback<List<GithubBean>> {

    public GithubReposTask(CommonCallback<List<GithubBean>> callback) {
        super(callback);
    }

    @Override
    public Call exe(String... params) {
        APIService mService = RetrofitManager.get().create(APIService.class);
        Call<List<GithubBean>> call = mService.listRepos(params[0]);
        call.enqueue(this);
        return call;
    }

}
