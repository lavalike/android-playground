package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.exercise.R;
import com.android.exercise.databinding.ItemHoverLayoutBinding;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;

import java.util.List;

public class HoverAdapter extends BaseRecyclerAdapter<Integer, HoverAdapter.HoverHolder> {

    public HoverAdapter(Context context, List<Integer> list) {
        super(context, list);
    }

    @Override
    public HoverHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new HoverHolder(parent);
    }

    public class HoverHolder extends BaseViewHolder<Integer> {
        private ItemHoverLayoutBinding binding;

        public HoverHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hover_layout, parent, false));
            binding = ItemHoverLayoutBinding.bind(itemView);
        }

        @Override
        protected void bindData() {
            binding.tvTitle.setText("the position at " + data + ".");
        }
    }
}
