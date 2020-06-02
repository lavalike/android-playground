package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.exercise.domain.BaseBean;
import com.android.exercise.domain.ItemBean;
import com.android.exercise.domain.TitleBean;
import com.android.exercise.ui.adapter.holder.ItemViewHolder;
import com.android.exercise.ui.adapter.holder.TitleViewHolder;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;

import java.util.List;

/**
 * 主页功能
 * Created by Administrator on 2016/4/12.
 */
public class FunctionAdapter extends BaseRecyclerAdapter<BaseBean, RecyclerView.ViewHolder> {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ITEM = 1;

    public FunctionAdapter(Context context, List<BaseBean> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            return new TitleViewHolder(parent);
        } else {
            return new ItemViewHolder(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position) instanceof TitleBean) {
            return TYPE_TITLE;
        }
        if (mDatas.get(position) instanceof ItemBean) {
            return TYPE_ITEM;
        }
        return RecyclerView.NO_POSITION;
    }
}
