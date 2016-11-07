package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * 主页功能
 * Created by Administrator on 2016/4/12.
 */
public class FunctionAdapter<T> extends BaseRecyclerAdapter {

    public FunctionAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_function_layout, parent, false);
        FunctionViewHolder holder = new FunctionViewHolder(view);
        return holder;
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FunctionViewHolder viewHolder = (FunctionViewHolder) holder;
        String title = (String) mDatas.get(position);
        viewHolder.tv_title.setText(title);
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;

        public FunctionViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.item_title);
        }
    }
}
