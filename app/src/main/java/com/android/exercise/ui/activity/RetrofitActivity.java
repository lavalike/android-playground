package com.android.exercise.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.BaseRecyclerAdapter;
import com.android.exercise.base.manager.APIManager;
import com.android.exercise.common.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.GithubBean;
import com.android.exercise.service.GithubService;
import com.android.exercise.ui.adapter.ReposAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        initSwipe();
        initRecycler();
        startLoading();
        loadList();
    }

    private void initSwipe() {
        swipeRepos.setColorSchemeResources(R.color.colorPrimary);
        swipeRepos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadList();
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
    public void loadList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIManager.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GithubService service = retrofit.create(GithubService.class);
        Call<List<GithubBean>> call = service.listRepos("lavalike");
        call.enqueue(new Callback<List<GithubBean>>() {
            @Override
            public void onResponse(Call<List<GithubBean>> call, Response<List<GithubBean>> response) {
                stopLoading();
                if (response.code() == 200) {
                    List<GithubBean> list = response.body();
                    if (list != null) {
                        mReposAdapter = new ReposAdapter(mContext, list);
                        mReposAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<GithubBean>() {
                            @Override
                            public void onItemClick(View view, int position, GithubBean data) {
                                String reposUrl = data.getHtml_url();
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reposUrl));
                                startActivity(intent);
                            }
                        });
                        recyclerRepos.setAdapter(mReposAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GithubBean>> call, Throwable t) {

            }
        });
    }
}
