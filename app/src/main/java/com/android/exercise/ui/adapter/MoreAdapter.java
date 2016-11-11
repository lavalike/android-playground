package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.base.BaseRecyclerAdapter;

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
        View view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
        return new MoreViewHolder(view);
    }

    @Override
    public void onMyBindViewHolder(MoreViewHolder holder, int position) {
        String item = mDatas.get(position);
        holder.tv.setText(item);
    }

    public class MoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView tv;

        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
