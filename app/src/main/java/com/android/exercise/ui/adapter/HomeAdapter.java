package com.android.exercise.ui.adapter;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.domain.Generic;
import com.android.exercise.domain.Item;
import com.android.exercise.domain.Caption;
import com.android.exercise.ui.adapter.holder.ItemViewHolder;
import com.android.exercise.ui.adapter.holder.TitleViewHolder;
import com.wangzhen.adapter.RecyclerAdapter;

import java.util.List;

/**
 * 主页功能
 * Created by Administrator on 2016/4/12.
 */
public class HomeAdapter extends RecyclerAdapter<Generic> {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ITEM = 1;

    public HomeAdapter(List<Generic> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            return new TitleViewHolder(parent);
        } else {
            return new ItemViewHolder(parent);
        }
    }

    @Override
    public int getAbsItemViewType(int position) {
        if (mDatas.get(position) instanceof Caption) {
            return TYPE_TITLE;
        }
        if (mDatas.get(position) instanceof Item) {
            return TYPE_ITEM;
        }
        return RecyclerView.NO_POSITION;
    }
}
