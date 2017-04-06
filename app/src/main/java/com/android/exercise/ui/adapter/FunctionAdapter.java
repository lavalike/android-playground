package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.domain.ItemBean;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 主页功能
 * Created by Administrator on 2016/4/12.
 */
public class FunctionAdapter extends BaseRecyclerAdapter<ItemBean, FunctionAdapter.FunctionViewHolder> {


    public FunctionAdapter(Context context, List<ItemBean> list) {
        super(context, list);
    }

    @Override
    public FunctionViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_function_layout, parent, false);
        FunctionViewHolder holder = new FunctionViewHolder(view);
        return holder;
    }

    @Override
    public void onMyBindViewHolder(FunctionViewHolder holder, int position) {
        ItemBean item = mDatas.get(position);
        holder.tv_title.setText(item.getItemName());
        Glide.with(mContext)
                .load("http://pic.58pic.com/58pic/15/45/22/39g58PICA2i_1024.jpg")
                .into(holder.iv_icon);
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_icon;
        public TextView tv_title;

        public FunctionViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.item_title);
            this.iv_icon = (ImageView) view.findViewById(R.id.item_icon);
        }
    }
}
