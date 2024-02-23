package com.android.exercise.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.exercise.R;
import com.android.exercise.databinding.ItemHoverLayoutBinding;
import com.wangzhen.adapter.RecyclerAdapter;
import com.wangzhen.adapter.base.RecyclerViewHolder;

import java.util.List;

public class HoverAdapter extends RecyclerAdapter<Integer> {

    public HoverAdapter(List<Integer> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new HoverHolder(parent);
    }

    public static class HoverHolder extends RecyclerViewHolder<Integer> {
        private final ItemHoverLayoutBinding binding;

        public HoverHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hover_layout, parent, false));
            binding = ItemHoverLayoutBinding.bind(itemView);
        }

        @Override
        public void bind() {
            binding.tvTitle.setText("the position at " + mData + ".");
        }
    }
}
