package com.android.exercise.ui.adapter;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.domain.Caption;
import com.android.exercise.domain.Generic;
import com.android.exercise.domain.Item;
import com.wangzhen.adapter.RecyclerAdapter;
import com.wangzhen.adapter.base.RecyclerViewHolder;

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

    public static class TitleViewHolder extends RecyclerViewHolder<Caption> {
        public TextView tv_title;

        public TitleViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_function_title_layout);
            this.tv_title = findViewById(R.id.item_title);
        }

        @Override
        public void bind() {
            tv_title.setText(mData.getTitle());
        }
    }

    public static class ItemViewHolder extends RecyclerViewHolder<Item> {
        public TextView tv_name;

        public ItemViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_function_layout);
            this.tv_name = findViewById(R.id.item_name);
            itemView.setOnClickListener((v) -> {
                Class<?> targetClass = mData.clazz;
                if (targetClass != null) {
                    Intent intent = new Intent(v.getContext(), targetClass);
                    if (mData.bundle != null) {
                        intent.putExtras(mData.bundle);
                    }
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public void bind() {
            tv_name.setText(mData.name);
        }
    }
}
