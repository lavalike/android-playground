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

    protected OnItemClickListener<T> mClickListener;
    protected OnItemLongClickListener<T> mLongClickListener;

    public void setItemClickListener(OnItemClickListener<T> listener) {
        this.mClickListener = listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.mLongClickListener = listener;
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
        setItemEvent(holder, position);
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
     * @param position
     */
    private void setItemEvent(final RecyclerView.ViewHolder holder, int position) {
        if (mClickListener != null) {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(innerClickListener);
        }
        if (mLongClickListener != null) {
            holder.itemView.setTag(position);
            holder.itemView.setOnLongClickListener(innerLongClickListener);
        }
    }

    private View.OnClickListener innerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                int realPosition = (int) v.getTag();
                mClickListener.onClick(v, realPosition, mDatas.get(realPosition));
            }
        }
    };

    private View.OnLongClickListener innerLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mLongClickListener != null) {
                int realPosition = (int) v.getTag();
                mLongClickListener.onLongClick(v, realPosition, mDatas.get(realPosition));
            }
            return true;
        }
    };

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener<T> {
        void onClick(View view, int position, T data);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T data);
    }
}
