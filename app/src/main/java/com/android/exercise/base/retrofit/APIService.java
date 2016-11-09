package com.android.exercise.base.retrofit;

import com.android.exercise.domain.GithubBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 存储请求接口
 * Created by wangzhen on 16/11/8.
 */

public interface APIService {
    /**
     * 获取Githubc仓库
     *
     * @param user
     * @return
     */
    @GET("users/{user}/repos")
    Call<List<GithubBean>> listRepos(@Path("user") String user);
}
