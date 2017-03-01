package com.android.exercise.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.BaseRecyclerAdapter;
import com.android.exercise.base.recycler.wrapper.HeaderAndFooterWrapper;
import com.android.exercise.base.retrofit.APIService;
import com.android.exercise.base.retrofit.RetrofitManager;
import com.android.exercise.base.retrofit.callback.CommonCallback;
import com.android.exercise.base.task.GithubReposTask;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.AppBean;
import com.android.exercise.domain.GithubBean;
import com.android.exercise.ui.adapter.ReposAdapter;
import com.android.exercise.util.C;
import com.android.exercise.util.T;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @OnClick({R.id.btn_github, R.id.btn_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_github:
                loadGithubList();
                break;
            case R.id.btn_app:
                loadAppList();
                break;
        }
    }

    private void loadAppList() {
        String url = "http://10.100.60.70:8080/ZBCM/user8531Controller.do?functionList";
        APIService apiService = RetrofitManager.getInstance().create(APIService.class);
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
