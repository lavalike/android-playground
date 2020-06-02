package com.android.exercise.ui.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;

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
        setItemEvent(holder);
        if (!onMyBindViewHolder((VH) holder, position)) {
            if (holder instanceof BaseViewHolder) {
                ((BaseViewHolder) holder).setData(mDatas.get(position));
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onMyCreateViewHolder(parent, viewType);
    }

    public abstract VH onMyCreateViewHolder(ViewGroup parent, int viewType);

    public boolean onMyBindViewHolder(VH holder, int position) {
        return false;
    }

    /**
     * 设置item点击事件
     *
     * @param holder ViewHolder
     */
    private void setItemEvent(final RecyclerView.ViewHolder holder) {
        holder.itemView.setTag(R.id.id_holder, holder);
        if (mClickListener != null || holder instanceof OnItemClickListener) {
            holder.itemView.setOnClickListener(innerClickListener);
        }
        if (mLongClickListener != null || holder instanceof OnItemLongClickListener) {
            holder.itemView.setOnLongClickListener(innerLongClickListener);
        }
    }

    private View.OnClickListener innerClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Object tag = v.getTag(R.id.id_holder);
            if (tag instanceof RecyclerView.ViewHolder) {
                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) tag;
                int position = holder.getLayoutPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(v, mDatas.get(position));
                }
                if (holder instanceof OnItemClickListener) {
                    ((OnItemClickListener) holder).onItemClick(holder.itemView, mDatas.get(position));
                }
            }
        }
    };

    private View.OnLongClickListener innerLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            Object tag = v.getTag(R.id.id_holder);
            if (tag instanceof RecyclerView.ViewHolder) {
                RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) tag;
                int position = holder.getLayoutPosition();
                if (mLongClickListener != null) {
                    mLongClickListener.onItemLongClick(v, mDatas.get(position));
                }
                if (holder instanceof OnItemLongClickListener) {
                    ((OnItemLongClickListener) holder).onItemLongClick(holder.itemView, mDatas.get(position));
                }
            }
            return true;
        }
    };

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, T data);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View view, T data);
    }
}
