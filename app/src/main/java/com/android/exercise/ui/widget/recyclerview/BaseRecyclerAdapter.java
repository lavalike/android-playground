package com.android.exercise.ui.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView.Adapter基类
 * Created by Administrator on 2016/10/18.
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;

    protected OnRecyclerItemClickListener<T> mItemClickListener;

    public void setOnItemClickListener(OnRecyclerItemClickListener<T> listener) {
        this.mItemClickListener = listener;
    }

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = list;
    }

    public void addData(T data) {
        mDatas.add(data);
    }

    public void addData(int position, T data) {
        mDatas.add(position, data);
    }

    public void addData(List<T> data) {
        mDatas.addAll(data);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        setItemEvent(holder);
        onMyBindViewHolder((VH) holder, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onMyCreateViewHolder(parent, viewType);
    }

    public abstract VH onMyCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onMyBindViewHolder(VH holder, int position);

    /**
     * 设置item点击事件
     *
     * @param holder
     */
    private void setItemEvent(final RecyclerView.ViewHolder holder) {
        if (mItemClickListener != null) {
            holder.itemView.setTag(holder);
            holder.itemView.setOnClickListener(innerClickListener);
        }
    }

    private View.OnClickListener innerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
                mItemClickListener.onItemClick(v, holder.getLayoutPosition(), mDatas.get(holder.getLayoutPosition()));
            }
        }
    };

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnRecyclerItemClickListener<T> {
        void onItemClick(View view, int position, T data);
    }
}
