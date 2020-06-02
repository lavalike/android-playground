package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.adapter.MoreAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.ZRecyclerView;
import com.android.exercise.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载更多
 * created by wangzhen on 2016/11/11
 */
public class RecyclerActivity extends BaseActivity {

    @BindView(R.id.recycler_more)
    ZRecyclerView recyclerMore;
    @BindView(R.id.swipe_loadmore)
    SwipeRefreshLayout swipeLoadmore;
    private MoreAdapter mMoreAdapter;
    private Handler handler = new Handler();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);
        initSwipeRefresh();
        setLoading(true);
        loadList();
    }

    private void loadList() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index = 0;
                mMoreAdapter = new MoreAdapter(mContext, getList());
                mMoreAdapter.setItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<String>() {
                    @Override
                    public void onItemClick(View view, String data) {
                        T.get(mContext).toast(data);
                    }
                });
                recyclerMore.setAdapter(mMoreAdapter);
                recyclerMore.setHeaderEnable(true);
                View mHeaderView = getLayoutInflater().inflate(R.layout.activity_anim24h, swipeLoadmore, false);
                recyclerMore.addHeaderView(mHeaderView);
                recyclerMore.notifyMoreFinish(true);
                setLoading(false);
            }
        }, 1500);
    }

    private void loadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index = mMoreAdapter.getItemCount();
                mMoreAdapter.addData(getList());
                mMoreAdapter.notifyItemRangeInserted(mMoreAdapter.getItemCount() + 1, getList().size());
                recyclerMore.notifyMoreFinish(true);
            }
        }, 100);
    }

    /**
     * 生成数据
     */
    private List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i = index; i < index + 20; i++) {
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
        recyclerMore.setLoadMoreListener(new ZRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
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
