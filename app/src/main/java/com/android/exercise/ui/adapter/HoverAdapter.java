package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;

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

    public class HoverHolder extends BaseViewHolder<Integer> {
        @BindView(R.id.tv_title)
        TextView textView;

        public HoverHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hover_layout, parent, false));
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindData() {
            textView.setText("the position at " + data + ".");
        }
    }
}
