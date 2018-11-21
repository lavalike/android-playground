package com.android.exercise.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.exercise.R;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
import com.android.exercise.ui.widget.recyclerview.BaseViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TikTokAdapter extends BaseRecyclerAdapter<Integer, TikTokAdapter.TikTokHolder> {

    public TikTokAdapter(Context context, List<Integer> list) {
        super(context, list);
    }

    @Override
    public TikTokHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new TikTokHolder(parent);
    }

    class TikTokHolder extends BaseViewHolder<Integer> {

        @BindView(R.id.iv_tik_tok)
        public ImageView imageView;

        public TikTokHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tok_layout, parent, false));
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindData() {
            Glide.with(mContext).load(data).into(imageView);
        }
    }
}
