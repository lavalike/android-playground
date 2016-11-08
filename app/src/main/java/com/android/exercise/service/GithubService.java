package com.android.exercise.service;

import com.android.exercise.domain.GithubBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wangzhen on 16/11/8.
 */

public interface GithubService {
    @GET("users/{user}/repos")
    Call<List<GithubBean>> listRepos(@Path("user") String user);
}
