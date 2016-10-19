package com.android.exercise.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView.Adapter基类
 * Created by Administrator on 2016/10/18.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    public List<T> mDatas;
    public OnRecyclerItemClickListener mListener;

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.mListener = listener;
    }

    public BaseRecyclerAdapter(List<T> list) {
        this.mDatas = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(View view, int position, Object data);
    }
}
