package com.android.playground.ui.activity.loadmore;

import android.os.Handler;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.playground.ui.activity.loadmore.holder.LoadViewHolder;
import com.wangzhen.adapter.LoadRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * LoadMoreAdapter
 * Created by wangzhen on 2020/6/10.
 */
public class LoadMoreAdapter extends LoadRecyclerAdapter<String> {
    public LoadMoreAdapter(List<String> list, ViewGroup parent) {
        super(list, parent);
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoadViewHolder(parent);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(() -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list.add(String.valueOf(i + 1));
            }
            addData(list);
        }, 500);
    }
}
