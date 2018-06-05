package com.android.exercise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.exercise.R;
import com.android.exercise.ui.widget.recyclerview.BaseRecyclerAdapter;
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

    @Override
    public void onMyBindViewHolder(TikTokHolder holder, int position) {
        int id = mDatas.get(position);
        Glide.with(mContext).load(id).into(holder.imageView);
    }

    class TikTokHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_tik_tok)
        public ImageView imageView;

        public TikTokHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tok_layout, parent, false));
            ButterKnife.bind(this, itemView);
        }
    }
}
