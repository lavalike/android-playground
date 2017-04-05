package com.android.exercise.base;

import com.android.exercise.domain.AppBean;
import com.android.exercise.domain.GithubBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @FormUrlEncoded
    @POST
    Call<AppBean> listApp(@Url String url, @Field("userinfo") String userInfo);

    @Multipart
    @POST
    Call<ResponseBody> uploadFileMutilpartBodyPart(@Url String url, @Part List<MultipartBody.Part> files);

    @POST
    Call<ResponseBody> uploadFileMutilpartBody(@Url String url, @Body MultipartBody files);
}
