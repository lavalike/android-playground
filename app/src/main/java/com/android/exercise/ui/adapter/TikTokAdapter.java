package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.exercise.R;
import com.android.exercise.databinding.ItemTikTokLayoutBinding;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

public class TikTokAdapter extends BaseRecyclerAdapter<Integer, TikTokAdapter.TikTokHolder> {

    public TikTokAdapter(Context context, List<Integer> list) {
        super(context, list);
    }

    @Override
    public TikTokHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new TikTokHolder(parent);
    }

    class TikTokHolder extends BaseViewHolder<Integer> {
        private ItemTikTokLayoutBinding binding;

        public TikTokHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tok_layout, parent, false));
            binding = ItemTikTokLayoutBinding.bind(itemView);
        }

        @Override
        protected void bindData() {
            Glide.with(mContext).load(data).into(binding.ivTikTok);
        }
    }
}
