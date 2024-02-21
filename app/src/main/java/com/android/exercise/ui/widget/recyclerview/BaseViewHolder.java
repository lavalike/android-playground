package com.android.exercise.ui.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder封装
 * Created by wangzhen on 2018/11/21.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public T data;
    public Context context;
    public View itemView;

    public BaseViewHolder(ViewGroup parent, int layoutId) {
        this(inflate(layoutId, parent, false));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        context = itemView.getContext();
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
