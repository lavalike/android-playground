package com.android.playground.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.playground.R;
import com.android.playground.databinding.ItemTikTokLayoutBinding;
import com.bumptech.glide.Glide;
import com.wangzhen.adapter.RecyclerAdapter;
import com.wangzhen.adapter.base.RecyclerViewHolder;

import java.util.List;

/**
 * TikTokAdapter
 * Created by wangzhen on 2024/2/23/023
 */
public class TikTokAdapter extends RecyclerAdapter<Integer> {

    public TikTokAdapter(List<Integer> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new TikTokHolder(parent);
    }

    static class TikTokHolder extends RecyclerViewHolder<Integer> {
        private final ItemTikTokLayoutBinding binding;

        public TikTokHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tok_layout, parent, false));
            binding = ItemTikTokLayoutBinding.bind(itemView);
        }

        @Override
        public void bind() {
            Glide.with(itemView.getContext()).load(mData).into(binding.ivTikTok);
        }
    }
}
