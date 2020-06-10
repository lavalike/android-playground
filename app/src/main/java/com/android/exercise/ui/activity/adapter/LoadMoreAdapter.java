package com.android.exercise.ui.activity.adapter;

import android.os.Handler;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.ui.activity.adapter.holder.LoadViewHolder;
import com.dimeno.adapter.LoadRecyclerAdapter;

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
