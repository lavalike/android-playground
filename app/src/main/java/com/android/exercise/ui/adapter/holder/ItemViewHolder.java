package com.android.exercise.ui.adapter.holder;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.ItemBean;
import com.wangzhen.adapter.base.RecyclerViewHolder;

/**
 * ItemViewHolder
 * Created by wangzhen on 2018/11/21.
 */
public class ItemViewHolder extends RecyclerViewHolder<ItemBean> {
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
