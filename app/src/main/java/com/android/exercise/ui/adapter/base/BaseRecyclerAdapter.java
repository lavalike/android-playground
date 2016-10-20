package com.android.exercise.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * RecyclerView.Adapter基类
 * Created by Administrator on 2016/10/18.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    protected OnRecyclerItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = list;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        setItemEvent(holder);
        onMyBindViewHolder(holder, position);
    }

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

    public abstract void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position);

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
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(View view, int position, Object data);
    }
}
