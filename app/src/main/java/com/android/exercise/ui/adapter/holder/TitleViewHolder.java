package com.android.exercise.ui.adapter.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.Caption;
import com.wangzhen.adapter.base.RecyclerViewHolder;

/**
 * TitleViewHolder
 * Created by wangzhen on 2018/11/21.
 */
public class TitleViewHolder extends RecyclerViewHolder<Caption> {
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
