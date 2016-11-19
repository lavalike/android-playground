package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.adapter.MoreAdapter;
import com.android.exercise.ui.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载更多
 * created by wangzhen on 2016/11/11
 */
public class LoadmoreActivity extends BaseActivity {

    @BindView(R.id.recycler_more)
    LoadMoreRecyclerView recyclerMore;
    @BindView(R.id.swipe_loadmore)
    SwipeRefreshLayout swipeLoadmore;
    private MoreAdapter mMoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);
        ButterKnife.bind(this);
        initSwipeRefresh();
        setLoading(true);
        loadList();
    }

    private void loadList() {
        mMoreAdapter = new MoreAdapter(mContext, getList());
        recyclerMore.setAdapter(mMoreAdapter);
        setLoading(false);
    }

    private void loadMore() {
        mMoreAdapter.addData(getList());
        mMoreAdapter.notifyItemRangeInserted(mMoreAdapter.getItemCount(), mMoreAdapter.getItemCount() + getList().size());
        recyclerMore.notifyMoreFinish(true);
    }

    /**
     * 生成数据
     */
    private List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_loadmore), true);
    }

    private void initSwipeRefresh() {
        swipeLoadmore.setColorSchemeResources(R.color.colorPrimary);
        swipeLoadmore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadList();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMore.setLayoutManager(manager);
        recyclerMore.setAutoLoadMoreEnable(true);
        recyclerMore.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                    }
                }, 1000);
            }
        });
    }

    /**
     * 设置加载状态
     *
     * @param loading
     */
    private void setLoading(final boolean loading) {
        swipeLoadmore.post(new Runnable() {
            @Override
            public void run() {
                swipeLoadmore.setRefreshing(loading);
            }
        });
    }
}
