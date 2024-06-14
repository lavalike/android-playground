package com.android.playground.ui.activity.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityRecyclerBinding;
import com.android.playground.ui.adapter.MoreAdapter;
import com.android.playground.util.T;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载更多
 * created by wangzhen on 2016/11/11
 */
public class RecyclerActivity extends BaseActivity {
    private ActivityRecyclerBinding binding;
    private MoreAdapter mMoreAdapter;
    private Handler handler = new Handler();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityRecyclerBinding.inflate(getLayoutInflater())).getRoot());
        initSwipeRefresh();
        setLoading(true);
        loadList();
    }

    private void loadList() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index = 0;
                mMoreAdapter = new MoreAdapter(getList());
                mMoreAdapter.setOnClickCallback((itemView, position) -> T.get(mContext).toast(mMoreAdapter.getDatas().get(position)));
                binding.recyclerMore.setAdapter(mMoreAdapter);
                binding.recyclerMore.setHeaderEnable(true);
                View mHeaderView = getLayoutInflater().inflate(R.layout.activity_anim24h, binding.swipeLoadmore, false);
                binding.recyclerMore.addHeaderView(mHeaderView);
                binding.recyclerMore.notifyMoreFinish(true);
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
                binding.recyclerMore.notifyMoreFinish(true);
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
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_loadmore));
    }

    private void initSwipeRefresh() {
        binding.swipeLoadmore.setColorSchemeResources(R.color.colorPrimary);
        binding.swipeLoadmore.setOnRefreshListener(this::loadList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerMore.setLayoutManager(manager);
        binding.recyclerMore.setAutoLoadMoreEnable(true);
        binding.recyclerMore.setLoadMoreListener(this::loadMore);
    }

    /**
     * 设置加载状态
     *
     * @param loading
     */
    private void setLoading(final boolean loading) {
        binding.swipeLoadmore.post(() -> binding.swipeLoadmore.setRefreshing(loading));
    }
}
