package com.android.exercise.base;

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

    private View mHeaderView;
    private View mFooterView;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            setItemEvent(holder);
            onMyBindViewHolder((VH) holder, position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new FooterViewHolder(mFooterView);
        }
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
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mDatas.size();
        } else if (mHeaderView != null && mFooterView == null) {
            return mDatas.size() + 1;
        } else if (mHeaderView == null && mFooterView != null) {
            return mDatas.size() + 1;
        } else
            return mDatas.size() + 2;
    }

    public interface OnRecyclerItemClickListener<T> {
        void onItemClick(View view, int position, T data);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }
}
