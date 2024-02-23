package com.android.exercise.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wangzhen.adapter.RecyclerAdapter;
import com.wangzhen.adapter.base.RecyclerViewHolder;

import java.util.List;

/**
 * 加载更多
 * Created by wangzhen on 16/11/11.
 */

public class MoreAdapter extends RecyclerAdapter<String> {

    public MoreAdapter(List<String> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoreViewHolder(parent);
    }

    public static class MoreViewHolder extends RecyclerViewHolder<String> {
        TextView tv;

        public MoreViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
            tv = itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void bind() {
            tv.setText(mData);
        }
    }
}
