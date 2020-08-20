package com.android.exercise.ui.adapter.holder;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.ItemBean;
import com.android.exercise.util.UIUtils;
import com.dimeno.adapter.base.RecyclerViewHolder;

/**
 * ItemViewHolder
 * Created by wangzhen on 2018/11/21.
 */
public class ItemViewHolder extends RecyclerViewHolder<ItemBean> {
    public TextView tv_name;

    public ItemViewHolder(ViewGroup parent) {
        super(UIUtils.inflate(R.layout.item_function_layout, parent, false));
        this.tv_name = findViewById(R.id.item_name);
        itemView.setOnClickListener((v) -> {
            Class<?> targetClass = mData.getTargetClass();
            if (targetClass != null) {
                Intent intent = new Intent(v.getContext(), targetClass);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void bind() {
        tv_name.setText(mData.getItemName());
    }
}
