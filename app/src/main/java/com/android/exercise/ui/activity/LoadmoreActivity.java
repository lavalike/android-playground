package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.BaseRecyclerAdapter;
import com.android.exercise.base.recycler.wrapper.HeaderAndFooterWrapper;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.ui.adapter.MoreAdapter;
import com.android.exercise.util.T;

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
    RecyclerView recyclerMore;
    @BindView(R.id.swipe_loadmore)
    SwipeRefreshLayout swipeLoadmore;
    private MoreAdapter mMoreAdapter;
    private Handler handler = new Handler();

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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMoreAdapter = new MoreAdapter(mContext, getList());
                mMoreAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<String>() {
                    @Override
                    public void onItemClick(View view, int position, String data) {
                        T.get(mContext).toast(data);
                    }
                });
                HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(mMoreAdapter);
                View mHeaderView = getLayoutInflater().inflate(R.layout.activity_anim24h, recyclerMore, false);
                wrapper.addHeaderView(mHeaderView);
                recyclerMore.setAdapter(wrapper);
                setLoading(false);
            }
        }, 1500);
    }

    private void loadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMoreAdapter.addData(getList());
                mMoreAdapter.notifyItemRangeInserted(mMoreAdapter.getItemCount() + 1, getList().size());
            }
        }, 1500);
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
