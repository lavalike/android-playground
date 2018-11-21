package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载更多
 * Created by wangzhen on 16/11/11.
 */

public class MoreAdapter extends BaseRecyclerAdapter<String, MoreAdapter.MoreViewHolder> {

    public MoreAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public MoreViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoreViewHolder(parent);
    }

    public class MoreViewHolder extends BaseViewHolder<String> {

        @BindView(android.R.id.text1)
        TextView tv;

        public MoreViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null));
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindData() {
            tv.setText(data);
        }
    }
}
