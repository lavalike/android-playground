package com.android.exercise.ui.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewHolder封装
 * Created by wangzhen on 2018/11/21.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public T data;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(ViewGroup parent, int layoutId) {
        super(inflate(layoutId, parent, false));
    }

    private static View inflate(int layoutId, ViewGroup parent, boolean attachToRoot) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, attachToRoot);
    }

    public void setData(T data) {
        this.data = data;
        bindData();
    }

    protected abstract void bindData();
}
