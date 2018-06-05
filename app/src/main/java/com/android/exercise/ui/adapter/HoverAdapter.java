package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HoverAdapter extends BaseRecyclerAdapter<Integer, HoverAdapter.HoverHolder> {

    public HoverAdapter(Context context, List<Integer> list) {
        super(context, list);
    }

    @Override
    public HoverHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new HoverHolder(parent);
    }

    @Override
    public void onMyBindViewHolder(HoverHolder holder, int position) {
        int data = mDatas.get(position);
        holder.textView.setText("the position at " + data + ".");
    }

    public class HoverHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView textView;

        public HoverHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hover_layout, parent, false));
            ButterKnife.bind(this, itemView);
        }
    }
}
