package com.android.exercise.base.retrofit;

import com.android.exercise.domain.GithubBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 存储请求接口
 * Created by wangzhen on 16/11/8.
 */

public interface APIService {
    /**
     * 获取Github仓库
     *
     * @param user
     * @return
     */
    @GET("users/{user}/repos")
    Call<List<GithubBean>> listRepos(@Path("user") String user);
}
