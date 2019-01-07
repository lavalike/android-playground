package com.android.exercise.ui.adapter.holder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.ItemBean;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;
import com.android.exercise.util.GlideApp;
import com.android.exercise.util.UIUtils;

/**
 * ItemViewHolder
 * Created by wangzhen on 2018/11/21.
 */
public class ItemViewHolder extends BaseViewHolder<ItemBean> implements BaseRecyclerAdapter.OnItemClickListener<ItemBean> {
    public ImageView iv_icon;
    public TextView tv_name;

    public ItemViewHolder(ViewGroup parent) {
        super(UIUtils.inflate(R.layout.item_function_layout, parent, false));
        this.tv_name = (TextView) itemView.findViewById(R.id.item_name);
        this.iv_icon = (ImageView) itemView.findViewById(R.id.item_icon);
    }

    @Override
    protected void bindData() {
        tv_name.setText(data.getItemName());
        GlideApp.with(itemView.getContext())
                .load(R.mipmap.dog_2)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(iv_icon);
    }

    @Override
    public void onItemClick(View view, ItemBean data) {
        Class<?> targetClass = data.getTargetClass();
        if (targetClass != null) {
            Intent intent = new Intent(context, targetClass);
            context.startActivity(intent);
        }
    }
}
