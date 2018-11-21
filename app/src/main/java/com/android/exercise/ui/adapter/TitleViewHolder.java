package com.android.exercise.ui.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.domain.TitleBean;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;
import com.android.exercise.util.UIUtils;

/**
 * TitleViewHolder
 * Created by wangzhen on 2018/11/21.
 */
public class TitleViewHolder extends BaseViewHolder<TitleBean> {
    public TextView tv_title;

    public TitleViewHolder(ViewGroup parent) {
        super(UIUtils.inflate(R.layout.item_function_title_layout, parent, false));
        this.tv_title = (TextView) itemView.findViewById(R.id.item_title);
    }

    @Override
    protected void bindData() {
        tv_title.setText(data.getTitle() + "▼");
    }

}
